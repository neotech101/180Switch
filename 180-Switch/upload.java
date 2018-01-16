

//NewGen Coders - Piyush Rana, Tunde Olokun, Kachail Fahmid

package com.example.text_to_speech;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database .DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Locale;

public class upload extends AppCompatActivity implements View.OnClickListener {

    Button Upload, Select;
    ImageView myImage;
    TextView title,showData;
//    public static final int PICK_FILE_REQUEST = 234;

    public final int READ_EXTERNAL_STORAGE = 0;
    private static final int GALLERY_INTENT = 2;

    private ProgressDialog mProgressDialog;
    public Firebase mRoofRef;
    public Uri mImageUri = null;
    public DatabaseReference mdatabaseRef;
    public StorageReference mStorage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        Firebase.setAndroidContext(this);
//
////        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
        //Toast.makeText(getApplicationContext(), "Welcome to File Upload", Toast.LENGTH_SHORT).show();
        Upload = (Button) findViewById(R.id.upload);
        Select = (Button) findViewById(R.id.select);
        myImage = (ImageView) findViewById(R.id.myimage);
        title = (EditText) findViewById(R.id.file);
        showData = (TextView) findViewById(R.id.seeData);

        mProgressDialog = new ProgressDialog(upload.this);
//
        Select.setOnClickListener(this);

        mdatabaseRef = FirebaseDatabase.getInstance().getReference();
        mRoofRef = new Firebase("https://switch-e1142.firebaseio.com/").child("User details").push();
        mStorage  = FirebaseStorage.getInstance().getReferenceFromUrl("gs://switch-e1142.appspot.com");

        Upload.setOnClickListener(this);

        showData.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v.equals(Upload))
        {
            Upload_data();
        }
        else if(v.equals(Select))
        {
            selectData();
        }
        else if(v.equals(showData))
        {
            Intent i = new Intent(getApplicationContext(), history.class);
            startActivity(i);
        }
    }
    public void selectData()
    {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), R.string.call_forperm, Toast.LENGTH_SHORT).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE);
            }
        } else {
            callgallary();
        }
    }

    public void Upload_data()
    {
        final String mName = title.getText().toString().trim();

        if(mName.isEmpty())
        {
            Toast.makeText(upload.this, R.string.Fill_field, Toast.LENGTH_SHORT).show();
            return;
        }

        Firebase childRef_name = mRoofRef.child("File_Title");
        childRef_name.setValue(mName);
//
        Toast.makeText(upload.this, R.string.Updated_info, Toast.LENGTH_SHORT).show();

    }
//
//    //Check for the run time permission
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        switch (requestCode)
        {
            case READ_EXTERNAL_STORAGE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    callgallary();
                }
                return;
        }
        Toast.makeText(this, "...", Toast.LENGTH_SHORT).show();
    }

    public void callgallary() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_INTENT);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            mImageUri = data.getData();
            myImage.setImageURI(mImageUri);
            StorageReference filepath = mStorage.child("User Image").child(mImageUri.getLastPathSegment());

            mProgressDialog.setMessage("Uploading Image...");
            mProgressDialog.show();

            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUri = taskSnapshot.getDownloadUrl();

                    mRoofRef.child("File_URL").setValue(downloadUri.toString());

                    Glide.with(getApplicationContext())
                            .load(downloadUri)
                            .crossFade()
                            .placeholder(R.drawable.download)
                            .diskCacheStrategy(DiskCacheStrategy.RESULT)
                            .into(myImage);

                    Toast.makeText(upload.this, R.string.Updated, Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                }
            });
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;
            case R.id.mic:
                Intent t2s = new Intent(android.content.Intent.ACTION_VIEW);
                t2s.setData(Uri.parse("http://searchmobilecomputing.techtarget.com/definition/text-to-speech"));
                startActivity(t2s);
                return true;
            case R.id.help:
                Intent help = new Intent(android.content.Intent.ACTION_VIEW);
                help.setData(Uri.parse("http://searchmobilecomputing.techtarget.com/definition/text-to-speech"));
                startActivity(help);

                return true;
            case R.id.name:
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final String email = user.getEmail();

                final TextView useremail = (TextView) findViewById(R.id.name);

                useremail.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        useremail.setText(email);
                    }
                });

                return true;

            case R.id.exit:
                new AlertDialog.Builder(this).setTitle(R.string.exit).setMessage(R.string.exitCon).setCancelable(true)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                System.exit(0);
                            }
                        }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
//        return false;
    }


}

