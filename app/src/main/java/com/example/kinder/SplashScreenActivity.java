package com.example.kinder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private TextView textViewLine;
    private ImageView imageViewLogo;

    private static int SPLASH_SCREEN=5000;
    Animation bottomAnim,topAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getIntent();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);

        textViewLine=findViewById(R.id.text_view_line);
        imageViewLogo=findViewById(R.id.logo);

        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        imageViewLogo.setAnimation(topAnim);
        textViewLine.setAnimation(bottomAnim);

      new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
              Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
          }
      },SPLASH_SCREEN);
    }

    Intent intent=new Intent(SplashScreenActivity.this,OtpActivity.class);
}