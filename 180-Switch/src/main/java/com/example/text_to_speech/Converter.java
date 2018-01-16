
//NewGen Coders - Piyush Rana, Tunde Olokun, Kachail Fahmid

package com.example.text_to_speech;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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

import java.util.Locale;

public class Converter extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth firebaseAuth;
    Converter c1;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mTogg;
    Button sett,t2s,s2t,hist,Upload;

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int repeat = 1000 ;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        mDrawerLayout = findViewById(R.id.drawerLayout);
        mTogg = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.app_open,R.string.app_close);

        mDrawerLayout.addDrawerListener(mTogg);
        mTogg.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//Creating a firebase instance to access Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            startActivity(new Intent(this, login.class));
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


        Upload = findViewById(R.id.button);//Upload
        t2s = findViewById(R.id.button2);//text to speech
        s2t = findViewById(R.id.button3);// speech to text
        hist = findViewById(R.id.button4);// History
        sett = findViewById(R.id.button5); //Settings
    }

    public void Settings(View v)
    {
        startActivity(new Intent(Converter.this,settings.class));
    }

    public void History(View v)
    {
        startActivity(new Intent(Converter.this,history.class));
    }

    public void Uploads(View v)
    {
        startActivity(new Intent(Converter.this,upload.class));
    }

    public void text2speech(View v)
    {
        startActivity(new Intent(Converter.this,text2speech.class));
    }

    public void speech2text(View v)
    {
        startActivity(new Intent(Converter.this,speech2text.class));
    }

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(Converter.this);

        builder.setMessage(R.string.doU_want);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton(R.string.no, null);

        AlertDialog alert = builder.create();
        alert.show();

    }
    public void changeEn()
    {
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        setContentView(R.layout.activity_converter);
    }
    public void changeFr()
    {Locale locale = new Locale("fr");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        setContentView(R.layout.activity_converter);
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

        if(mTogg.onOptionsItemSelected(item))
        {
            return true;
        }


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
            //===========================================================================================================
            case R.id.language_en:
                changeEn();

                return true;
            case R.id.language_fr:
                changeFr();

                return true;
            //===========================================================================================================
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.Upload)
        {
            startActivity(new Intent(getApplicationContext(),upload.class));
        }
        if (id == R.id.Speech2Text)
        {
            startActivity(new Intent(getApplicationContext(),speech2text.class));
        }
        if (id == R.id.Text2Speech)
        {
            startActivity(new Intent(getApplicationContext(),text2speech.class));
        }
        if (id == R.id.History)
        {
            startActivity(new Intent(getApplicationContext(),history.class));
        }
        if (id == R.id.Settings)
        {
            startActivity(new Intent(getApplicationContext(),settings.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}


