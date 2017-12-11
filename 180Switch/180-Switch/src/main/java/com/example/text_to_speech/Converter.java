package com.example.text_to_speech;

import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Converter extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    Converter c1;

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
   // @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//
//        // Checks the orientation of the screen
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
//        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
//            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
//        }
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int repeat = 1000 ;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);
        //getSupportActionBar();

//        if (android.provider.Settings.System.getInt(getContentResolver(),
//                Settings.System.ACCELEROMETER_ROTATION, 0) == 1){
//            Toast.makeText(getApplicationContext(), "Rotation ON", Toast.LENGTH_SHORT).show();
//
//        }
//        else{
//            Toast.makeText(getApplicationContext(), "Rotation OFF", Toast.LENGTH_SHORT).show();
//        }

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null)
        {
            startActivity(new Intent(this,login.class));
            finish();
        }


        final ImageView backgroundOne = (ImageView) findViewById(R.id.background_one);
        final ImageView backgroundTwo = (ImageView) findViewById(R.id.background_two);

        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(5000L);
        animator.setRepeatMode(ValueAnimator.REVERSE);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = backgroundOne.getWidth();
                final float translationX = width * progress;
                backgroundOne.setTranslationX(translationX);
                backgroundTwo.setTranslationX(translationX - width);
            }
        });
        animator.start();


        Button sett,t2s,s2t,hist,Upload;

        Upload = (Button) findViewById(R.id.button);//Upload
        t2s = (Button) findViewById(R.id.button2);//text to speech
        s2t = (Button) findViewById(R.id.button3);// speech to text
       // hist = (Button) findViewById(R.id.button4);// History
        sett = (Button) findViewById(R.id.button5); //Settings


        Upload.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),upload.class));
            }
        });

        sett.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
               up();
            }
        });


        t2s.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), text2speech.class);
                startActivity(i);
            }
        });

        s2t.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), speech2text.class);
                startActivity(i);
            }
        });


    }

    public void up()
    {
        Intent i = new Intent(Converter.this, settings.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(Converter.this);

        builder.setMessage("Do you want to close the app?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton("No", null);

        AlertDialog alert = builder.create();
        alert.show();

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

        Intent i = new Intent();
        String Useremail = getIntent().getStringExtra("Username");


        switch (item.getItemId()) {

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

            case R.id.out:
                //Toast.makeText(getApplicationContext(),"Heloohehsbk b",Toast.LENGTH_SHORT).show();
                Intent in = new Intent(getApplicationContext(), login.class);
                startActivity(in);
                finish();
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
