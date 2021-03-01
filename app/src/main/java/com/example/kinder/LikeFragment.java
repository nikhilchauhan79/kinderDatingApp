package com.example.kinder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kinder.Model.Profile;
import com.example.kinder.adapter.LikeAdapter;
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
 * Use the {@link LikeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LikeFragment extends Fragment implements LikeAdapter.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private RecyclerView recyclerViewLike;
    private LikeAdapter likeAdapter;
    private DatabaseReference databaseReference;
    private List<Profile> profiles;
    private StorageReference storageReference;
    private StorageTask uploadTask;


    public LikeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LikeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LikeFragment newInstance(String param1, String param2) {
        LikeFragment fragment = new LikeFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseReference= FirebaseDatabase.getInstance().getReference("profiles");
        uploadProfile();


    }

    private void uploadProfile() {

//        storageReference = storageReference.child("image-"+System.currentTimeMillis()
//                + "." + getFileExtension(mImageUri));

            Toast.makeText(getContext(), "Successfully uploaded", Toast.LENGTH_LONG).show();
            Profile profile=new Profile("name","sample description",20,true,"https://upload.wikimedia.org/wikipedia/commons/thumb/4/42/Shaqi_jrvej.jpg/1200px-Shaqi_jrvej.jpg");

            String profileId=databaseReference.push().getKey();
            databaseReference.child(profileId).setValue(profile);


    }

    private String getFileExtension(Uri selectedUri) {

        String fileExtension
                = MimeTypeMap.getFileExtensionFromUrl(selectedUri.toString());
        String mimeType
                = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);
        return mimeType;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView=view.findViewById(R.id.recycler_view_like);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        profiles=new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    Profile profile=snapshot1.getValue(Profile.class);
                    profiles.add(profile);
                }
                likeAdapter=new LikeAdapter(profiles,getContext());
                recyclerView.setAdapter(likeAdapter);
                likeAdapter.setOnItemClickListener(LikeFragment.this::onItemClick);


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
//        databaseReference= FirebaseDatabase.getInstance().getReference("profiles");
//        LikeAdapter adapter = new LikeAdapter(profiles,getContext());
        //Add Item Click listener

        return inflater.inflate(R.layout.fragment_like, container, false);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent=new Intent(getContext(),ProfileDetailActivity.class);
        startActivity(intent);
    }
}