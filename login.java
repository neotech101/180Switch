package com.example.text_to_speech;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity implements View.OnClickListener {

    private TextView email;
    private TextView password;
    private TextView reg;
    private TextView login;

    login obj;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    public login() {
//        this.email = email;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (TextView) findViewById(R.id.editText6);
        password = (TextView) findViewById(R.id.editText5);
        login = (TextView) findViewById(R.id.signin);
        reg = (TextView) findViewById(R.id.register);

        String eid = email.getText().toString().trim();
        String pass = password.getText().toString().trim();

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        login.setOnClickListener(this);
        reg.setOnClickListener(this);
    }


    private void userLogin()
    {
        String eid = email.getText().toString().trim();
        String pass = password.getText().toString().trim();

        progressDialog.setMessage("Signing user");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(eid,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful())
                        {
                            String eid = email.getText().toString().trim();
                            Intent i = new Intent(getApplicationContext(),Converter.class);
                            i.putExtra("Username",eid);
                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Invalid E-ID or password",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }




    @Override
    public void onClick(View v) {
        if(v == login)
        {
            initialize();
            if(!validate())
            {
                Toast.makeText(this,"Login has failed",Toast.LENGTH_SHORT).show();
            }
            else
            userLogin();
        }
        if(v==reg)
        {
            String s = "login_act";
            Intent i = new Intent(getApplicationContext(), signup.class); //'this' is Activity A
            i.putExtra("FROM_ACTIVITY", s);
            startActivity(i);
            finish();
        }
    }

    private boolean validate() {
        String eid = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        boolean valid = true;
        if(eid.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(eid).matches())
        {
            email.setError("Invalid Email-ID");
            valid = false;
        }
        if(pass.isEmpty())
        {
            password.setError("Please enter Password");
            valid = false;
        }
        if(pass.length()<8)
        {
            password.setError("Password must be 8 characters long");
            valid = false;
        }
        return valid;
    }
    private void initialize() {

        String eid = email.getText().toString().trim();
        String pass = password.getText().toString().trim();

    }
}
