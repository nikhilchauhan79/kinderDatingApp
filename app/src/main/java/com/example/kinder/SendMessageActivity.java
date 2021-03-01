package com.example.kinder;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kinder.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SendMessageActivity extends AppCompatActivity {
    FirebaseUser user;
    DatabaseReference reference;

    Intent intent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users").child("user");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                //username.setText()
                //
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /*
        String msg=text_send.getString()
        check for empty string
        sendMessage(user.getid())

        else toast empty message

        */

    }

    private void sendMessage(String sender,String receiver,String message){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap=new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("message",message);

        reference.child("Chats").push().setValue(hashMap);



    }
}
