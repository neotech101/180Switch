

//NewGen Coders - Piyush Rana, Tunde Olokun, Kachail Fahmid

package com.example.text_to_speech;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.media.AudioManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

import static android.R.attr.name;

public class settings extends AppCompatActivity {

    SeekBar seekbar;
    TextView textview;
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Toast.makeText(this,"Welcome To Settings!",Toast.LENGTH_SHORT).show();

        seekbar = (SeekBar)findViewById(R.id.seekBar1);
        textview = (TextView)findViewById(R.id.textView1);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        seekbar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                textview.setText("Media Volume : " + i);

                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

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
