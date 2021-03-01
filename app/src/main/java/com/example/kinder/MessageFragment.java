package com.example.kinder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kinder.Model.ChatHome;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ChatHomeAdapter chatHomeAdapter;
    private DatabaseReference databaseReference;
    private List<ChatHome> chats;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MessageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhotosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageFragment newInstance(String param1, String param2) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseReference= FirebaseDatabase.getInstance().getReference("messages");
        uploadMessages();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Chats");
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    private void uploadMessages() {

//        storageReference = storageReference.child("image-"+System.currentTimeMillis()
//                + "." + getFileExtension(mImageUri));

        Toast.makeText(getContext(), "Successfully uploaded", Toast.LENGTH_LONG).show();
        ChatHome chatHome=new ChatHome("nikhil","brett lee","we won match tonight","https://upload.wikimedia.org/wikipedia/commons/thumb/4/42/Shaqi_jrvej.jpg/1200px-Shaqi_jrvej.jpg");


        String profileId=databaseReference.push().getKey();
        databaseReference.child(profileId).setValue(chatHome);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView=view.findViewById(R.id.recycler_view_chat);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        chats=new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    ChatHome chatHome=snapshot1.getValue(ChatHome.class);
                    chats.add(chatHome);
                }
                chatHomeAdapter=new ChatHomeAdapter(getContext(),chats);
                recyclerView.setAdapter(chatHomeAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText( getContext(),"Error: "+error.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }
}
