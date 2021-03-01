package com.example.kinder;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ncorti.slidetoact.SlideToActView;

public class SettingsActivity extends AppCompatActivity {

    SlideToActView slideToActViewNot,slideToActViewGlobal,slideToActViewPrivate;
    Button buttonLogOut;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.settings);

        slideToActViewGlobal=findViewById(R.id.global);
        slideToActViewNot=findViewById(R.id.notification);
        buttonLogOut=findViewById(R.id.settings_button);


        slideToActViewGlobal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        slideToActViewNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}
