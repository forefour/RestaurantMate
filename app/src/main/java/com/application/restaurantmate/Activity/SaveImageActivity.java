package com.application.restaurantmate.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.storage.StorageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.application.restaurantmate.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SaveImageActivity extends AppCompatActivity {
    private ImageButton imageButton;
    private EditText name;
    private Button submit;
    private static final int GA_REQUEST =1;
    private Uri imageUri;
    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_image);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference();
        imageButton = (ImageButton) findViewById(R.id.save_image_imageButton);
        name = (EditText) findViewById(R.id.save_image_name);
        submit = (Button) findViewById(R.id.save_image_save);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GA_REQUEST);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }
        });
    }

    private void startPosting(){

        final String title_val = name.getText().toString().trim();
            StorageReference filepath = mStorage.child("Test").child(imageUri.getLastPathSegment());
            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                    DatabaseReference newPost = mDatabase.child("test-image").push();
                    newPost.child("pic").setValue(downloadUri.toString());
                    newPost.child("name").setValue(title_val);
                }
            });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("FFF","1");
        if(requestCode == GA_REQUEST && resultCode == RESULT_OK){
            imageUri = data.getData();
            Log.d("imageuri",imageUri.toString());
            Log.d("FFF","2");
            //imageButton.setImageURI(imageUri);
        }
    }
}
