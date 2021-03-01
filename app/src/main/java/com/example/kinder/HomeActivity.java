package com.example.kinder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeActivity extends AppCompatActivity {
    FloatingActionButton actionButton;
    private static int SELECT_PICTURE = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getIntent();
        actionButton = findViewById(R.id.fab);
        androidx.appcompat.widget.Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("title");

        BottomNavigationView navigationView = findViewById(R.id.bottom_nav_view);
        navigationView.getMenu().getItem(2).setEnabled(false);
        navigationView.setBackground(null);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_home, new LikeFragment()).commit();
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchImageFromGallery(v);
            }
        });

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.nav_like:
                        selectedFragment = new LikeFragment();
                        break;
                    case R.id.nav_notif:
                        selectedFragment = new MessageFragment();
                        break;

                    case R.id.nav_message:
                        selectedFragment = new MessageFragment();
                        break;
                    case R.id.nav_profile:
                        selectedFragment = new ProfileFragment();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_home, selectedFragment).commit();
                return true;

            }
        });
    }

    void fetchImageFromGallery(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {

                Uri selectedImageURI = data.getData();
                Log.d("image uri", "onActivityResult: " + selectedImageURI.toString());

            }
        }
    }
}