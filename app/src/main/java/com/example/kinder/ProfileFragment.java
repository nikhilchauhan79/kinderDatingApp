package com.example.kinder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kinder.Model.ProfileDetail;
import com.example.kinder.Model.UserProfileData;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Uri mImageUri;
    private StorageTask uploadTask;


    private List<ProfileDetail> profiles;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private ProfileAdapter adapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        databaseReference= FirebaseDatabase.getInstance().getReference("profiles");
        uploadProfile();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView=view.findViewById(R.id.recycler_view_profile);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        profiles=new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    ProfileDetail profile=snapshot1.getValue(ProfileDetail.class);
                    String imageUrl=profile.getImageUrl();
                    Log.d("image Url", "onDataChange: "+imageUrl +" "+profile.getName());
                    profiles.add(profile);
                }
                adapter =new ProfileAdapter(profiles,getContext());
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText( getContext(),"Error: "+error.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile, container, false);
        Button buttonEditText,buttonSettings;
        TextView userName,description;

        buttonEditText= view.findViewById(R.id.edit_button);
        buttonSettings=view.findViewById(R.id.settings_button);
        userName=view.findViewById(R.id.user_name);
        description=view.findViewById(R.id.description);

        buttonEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),EditDetailActivity.class);
                startActivity(intent);
            }
        });

        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),SettingsActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void uploadProfile() {

//        storageReference = storageReference.child("image-"+System.currentTimeMillis()
//                + "." + getFileExtension(mImageUri));

        Toast.makeText(getContext(), "Successfully uploaded", Toast.LENGTH_LONG).show();

        UserProfileData profileData=new UserProfileData("Nikhil","description","https://d1whtlypfis84e.cloudfront.net/guides/wp-content/uploads/2019/07/23090714/nature-1024x682.jpeg");

        String profileDataId=databaseReference.push().getKey();
        databaseReference.child(profileDataId).setValue(profileData);


    }



}