package com.example.kinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class GenderActivity extends AppCompatActivity {

    Button submitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);

        getIntent();
        submitButton=findViewById(R.id.button_submit_gender);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GenderActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });




    }
}