package com.example.text_to_speech;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static com.example.text_to_speech.R.id.imageView;

public class upload extends AppCompatActivity implements View.OnClickListener  {

    Button Upload;
    TextView file;
    public static final int PICK_FILE_REQUEST = 234;
    //private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Toast.makeText(getApplicationContext(), "Welcome to File Upload", Toast.LENGTH_SHORT).show();

        Upload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v==Upload)
        {
            Intent i = new Intent();
            i.setType("text/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(i, "Select a file"), PICK_FILE_REQUEST);
        }
    }
}