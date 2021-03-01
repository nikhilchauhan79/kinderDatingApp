package com.example.kinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CreatePassword extends AppCompatActivity {
    EditText editTextPassword, editTextReEnter;
    Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);

        editTextPassword = findViewById(R.id.password);
        editTextReEnter = findViewById(R.id.password_re_en);

        buttonSubmit = findViewById(R.id.submit_button);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CreatePassword.this, AddPhotosActivity.class);
                startActivity(intent);


            }
        });
    }
}