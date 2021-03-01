package com.example.kinder;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kinder.Model.ProfileDetail;
import com.example.kinder.adapter.ProfileAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.ArrayList;
import java.util.List;

public class ProfileDetailActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView textViewUserName,textViewDescription;
    private ProfileAdapter profileAdapter;
    private DatabaseReference databaseReference;
    private List<ProfileDetail> profileDetails;
    private StorageReference storageReference;
    private StorageTask uploadTask;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_detail_activity);

        getIntent();
        databaseReference= FirebaseDatabase.getInstance().getReference("profile_details");
        uploadProfileDetails();
        recyclerView=findViewById(R.id.recycler_view_profile_detail);
        textViewUserName=findViewById(R.id.text_view_profile_username);
        textViewDescription=findViewById(R.id.text_view_profile_description);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        profileDetails=new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    ProfileDetail profile=snapshot1.getValue(ProfileDetail.class);
                    profileDetails.add(profile);
                }
                ProfileAdapter profileAdapter=new ProfileAdapter(profileDetails,getApplicationContext());
                recyclerView.setAdapter(profileAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText( getApplicationContext(),"Error: "+error.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void uploadProfileDetails() {

//        storageReference = storageReference.child("image-"+System.currentTimeMillis()
//                + "." + getFileExtension(mImageUri));

        Toast.makeText(ProfileDetailActivity.this, "Successfully uploaded", Toast.LENGTH_LONG).show();
        ProfileDetail profileDetail=new ProfileDetail("https://scx2.b-cdn.net/gfx/news/2019/2-nature.jpg"," mountain");
        String profileId=databaseReference.push().getKey();
        databaseReference.child(profileId).setValue(profileDetail);


    }


}
