package com.example.kinder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {
    CardView cardViewGoogle;
    LoginButton buttonFacebook;
    CardView cardViewMobile;
    CallbackManager callbackManager;
    private AccessTokenTracker tokenTracker;
    FirebaseAuth.AuthStateListener authStateListener;
    private GoogleSignInClient signInClient;
    private static final int RC_SIGN_IN = 9001;
    FirebaseUser user;

    public static final String TAG_FACEBOOK = "facebook Auth";
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getIntent();


        cardViewGoogle = findViewById(R.id.sign_in_google);
        buttonFacebook = findViewById(R.id.sign_in_facebook);
        cardViewMobile = findViewById(R.id.sign_in_phone_no);

        callbackManager = CallbackManager.Factory.create();
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        signInClient = GoogleSignIn.getClient(getApplicationContext(), googleSignInOptions);
        firebaseAuth = FirebaseAuth.getInstance();

        user = firebaseAuth.getCurrentUser();

        cardViewGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();

            }
        });
        buttonFacebook.setReadPermissions("email","public_profile");
        buttonFacebook.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                buttonFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Log.d(TAG_FACEBOOK, "onSuccess: " + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG_FACEBOOK, "onCancel: " );

                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.d(TAG_FACEBOOK, "onCancel: "+exception );

                    }
                });
            }
        });

        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user!=null){
                    updateUIFacebook(user);
                }
                else{
                    updateUIFacebook(null);
                }
            }
        };

        tokenTracker=new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken!=null){
                    firebaseAuth.signOut();
                }
            }
        };

        cardViewMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    Intent intent = new Intent(LoginActivity.this, PhoneNumberLogin.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
        updateUIFacebook(currentUser);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(authStateListener!=null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("token", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("success", "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateUIFacebook(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("failed", "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUIFacebook(null);
                        }

                        // ...
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
            super.onActivityResult(requestCode, resultCode, data);

        }


    }


    private void firebaseAuthWithGoogle(String idToken) {
        // [START_EXCLUDE silent]
        // [END_EXCLUDE]
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateUI(user);
                            Toast.makeText(LoginActivity.this, "sign in success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), GenderActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "sign in failure", Toast.LENGTH_SHORT).show();
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }

                    }
                });
    }


    private void updateUI(FirebaseUser user) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

        if (user != null) {
            String name = account.getDisplayName();
            String personFamilyName = account.getFamilyName();
            String givenName = account.getGivenName();
            String email = account.getEmail();
            String personId = account.getId();

            Uri personPhoto = account.getPhotoUrl();

            Toast.makeText(getApplicationContext(), name + "\n " + personFamilyName + "\n " + givenName + "\n" + email + "\n" + personId, Toast.LENGTH_SHORT).show();
            Log.d("details: ", "updateUI: " + name + "\n " + personFamilyName + "\n " + givenName + "\n" + email + "\n" + personId);
        }

    }

    private void signIn() {
        Intent signInIntent = signInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void updateUIFacebook(FirebaseUser user) {
        if (user != null) {
            Log.d("TAG", "updateUIFacebook: " + user.getDisplayName());
            Log.d("TAG", "updateUIFacebook: " + user.getUid());
            Log.d("TAG", "updateUIFacebook: " + user.getPhoneNumber());
            Log.d("TAG", "updateUIFacebook: " + user.getEmail());
            Log.d("TAG", "updateUIFacebook: " + user.getPhotoUrl());
            Toast.makeText(this, user.getDisplayName(), Toast.LENGTH_SHORT).show();
        }
    }


}
