package com.example.kinder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneNumberLogin extends AppCompatActivity {



    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    private Button button;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private String mVerificationId;



    private EditText editTextPhoneNo;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_phone_number_login);
        editTextPhoneNo = findViewById(R.id.edit_text_mob_no);
        button = findViewById(R.id.button);


        mAuth = FirebaseAuth.getInstance();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputPhoneNumber = "";
                inputPhoneNumber = editTextPhoneNo.getText().toString();
                String complete_phone_no = "+91" + inputPhoneNumber;
                startPhoneNumberVerification(complete_phone_no);
            }
        });

    }
    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                Log.d("completed", "onVerificationCompleted: "+"code sent");
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                                Intent intent =new Intent(PhoneNumberLogin.this,SignUpActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Log.w("failed", "onVerificationFailed");
                                Toast.makeText(PhoneNumberLogin.this, "verification failed", Toast.LENGTH_SHORT).show();;
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                Log.d("sent", "onCodeSent: "+s);
                                mVerificationId = s;
                                mResendToken = forceResendingToken;
                                Intent intent =new Intent(PhoneNumberLogin.this,OtpActivity.class);
                                intent.putExtra("verificationId", s);
                                intent.putExtra("resendToken", mVerificationId);

                                startActivity(intent);
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        Intent intent=new Intent(PhoneNumberLogin.this,OtpActivity.class);
        startActivity(intent);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("success", "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("failed", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }


}