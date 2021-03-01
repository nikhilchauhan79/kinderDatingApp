package com.example.kinder;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.kinder.Model.UploadImage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class AddPhotosActivity extends AppCompatActivity {
    private static int SELECT_PICTURE = 1;
    CardView cardView1,cardView2,cardView3,cardView4;
    Button nextButtonPhotos;
    private Uri mImageUri;
    private StorageTask uploadTask;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photos);

        cardView1=findViewById(R.id.card_1);
        cardView2=findViewById(R.id.card_2);
        cardView3=findViewById(R.id.card_3);
        cardView4=findViewById(R.id.card_4);
        nextButtonPhotos=findViewById(R.id.next_button_photos);

        storageReference= FirebaseStorage.getInstance().getReference("images");
        databaseReference= FirebaseDatabase.getInstance().getReference("images");


        nextButtonPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddPhotosActivity.this,GenderActivity.class);
                startActivity(intent);
            }
        });

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchImageFromGallery(v);
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchImageFromGallery(v);

            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchImageFromGallery(v);

            }
        });
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchImageFromGallery(v);

            }
        });
    }

    void fetchImageFromGallery(View view){
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

                mImageUri = data.getData();

                Log.d("image uri", "onActivityResult: "+mImageUri.toString());

                if(uploadTask!=null && uploadTask.isInProgress()){
                    Toast.makeText(this, "upload in progress", Toast.LENGTH_SHORT).show();
                }else {
                    if (mImageUri != null) {
                        uploadFile();
                    }
                }

            }
        }


    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private void uploadFile() {
        storageReference = storageReference.child("image-"+System.currentTimeMillis()
                + "." + getFileExtension(mImageUri));

        uploadTask=storageReference.putFile(mImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){

                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri downloadUri = uri;
                            String download_url = uri.toString();
//                            databaseReference.child("images").setValue(download_url);
                            if(task.isSuccessful()) {

                                Toast.makeText(AddPhotosActivity.this, "Successfully uploaded", Toast.LENGTH_LONG).show();
                                UploadImage uploadImage=new UploadImage("image-"+System.currentTimeMillis(),download_url);
                                String uploadId=databaseReference.push().getKey();
                                databaseReference.child(uploadId).setValue(uploadImage);
                            }else {
                                Toast.makeText(AddPhotosActivity.this, "Error happened during the upload process", Toast.LENGTH_LONG).show();
                            }
                            }
                    });

                }else{
                    Toast.makeText(AddPhotosActivity.this, "Error happened during the upload process", Toast.LENGTH_LONG ).show();
                }
            }
        });


    }
}
