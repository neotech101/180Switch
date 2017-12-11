package com.example.text_to_speech;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

import static android.R.attr.name;
import static android.R.attr.switchMinWidth;

public class text2speech extends AppCompatActivity {

    TextToSpeech toSpeech;
    int res;
    EditText editText;
    String text;


    @RequiresApi(api = Build.VERSION_CODES.DONUT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text2speech);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Toast.makeText(this,"Welcome to text to speech conversion",Toast.LENGTH_SHORT).show();

        editText = (EditText) findViewById(R.id.editText2);
        toSpeech = new TextToSpeech(this,new TextToSpeech.OnInitListener()
        {
            public void onInit(int status)
            {
                if(status == TextToSpeech.SUCCESS)
                {
                    res = toSpeech.setLanguage(Locale.ENGLISH);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Your device doesn't support this feature",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void textTOspeech(View view)
    {
        switch(view.getId()) {
            case R.id.Listen:
                if (res == TextToSpeech.LANG_MISSING_DATA || res == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(getApplicationContext(), "Your device doesn't support this feature", Toast.LENGTH_SHORT).show();

                } else {
                    text = editText.getText().toString();
                    toSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                    toSpeech.setSpeechRate(5f);
                }
                break;
            case R.id.Stop:
                if(toSpeech != null)
                {
                    toSpeech.stop();
                }
                break;

        }
    }

//    private int setSpeechRate(float speechRate)
//    {
//        speechRate = 2.0f;
//    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if(toSpeech!=null)
        {
            toSpeech.stop();
            toSpeech.shutdown();
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
                new AlertDialog.Builder(this).setTitle(R.string.nameinfo)
                        .setMessage(R.string.nInfo).setCancelable(false)
                        .setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
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




