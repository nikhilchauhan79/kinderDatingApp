package com.example.kinder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;

public class OtpActivity extends AppCompatActivity {
    private OtpTextView otpTextView;
    private FirebaseAuth mAuth;
    Button submitButtonOtp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        otpTextView=findViewById(R.id.otp_view);
        submitButtonOtp=findViewById(R.id.submit_button_otp);
        otpTextView = findViewById(R.id.otp_view);

        Intent intent=getIntent();
        String verificationId=intent.getStringExtra("verificationId");
        String resendToken=intent.getStringExtra("resendToken");

        mAuth = FirebaseAuth.getInstance();


        submitButtonOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otpTextView.setOtpListener(new OTPListener() {
                    @Override
                    public void onInteractionListener() {
                        // fired when user types something in the Otpbox
                    }
                    @Override
                    public void onOTPComplete(String otp) {
                        // fired when user has entered the OTP fully.
                        Intent intent=new Intent(OtpActivity.this,CreatePassword.class);
                        startActivity(intent);
                        String otpText=otpTextView.getOTP(); // retrieves the OTP entered by user (works for partial otp input too)
                        if(otpText==verificationId){
                            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,resendToken);
                            signInWithPhoneAuthCredential(credential);


                        }
                        Toast.makeText(OtpActivity.this, "The OTP is " + otp,  Toast.LENGTH_SHORT).show();

                    }
                });
            }


        });

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