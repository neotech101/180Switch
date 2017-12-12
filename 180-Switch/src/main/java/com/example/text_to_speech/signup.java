

//NewGen Coders - Piyush Rana, Tunde Olokun, Kachail Fahmid

package com.example.text_to_speech;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signup extends AppCompatActivity implements View.OnClickListener {

    TextView email;
    TextView password;
    TextView reg;
    TextView login;
//
//    String eid = email.getText().toString().trim();
//    String pass = password.getText().toString().trim();


    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_signup);

        String s = "";

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             s=extras.getString("FROM_ACTIVITY");
        }
        if(!s.equals("login_act")) {
            if (firebaseAuth.getCurrentUser() != null) {
                startActivity(new Intent(this, Converter.class));
                finish();
            }
        }



//    firebaseAuth = FirebaseAuth.getInsta  nce();

    progressDialog = new ProgressDialog(this);

    email = (TextView) findViewById(R.id.editText6);
    password = (TextView) findViewById(R.id.editText5);
    reg = (TextView) findViewById(R.id.register);
    login = (TextView) findViewById(R.id.textView4);

    reg.setOnClickListener(this);
    login.setOnClickListener(this);

    }

    private void registerUser()
    {
        String eid = email.getText().toString().trim();
        String pass = password.getText().toString().trim();

        progressDialog.setMessage("Registering user");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(eid,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                      if(task.isSuccessful())
                      {
                          finish();
                          Toast.makeText(getApplicationContext(),"Registration Successful",Toast.LENGTH_SHORT).show();
                          startActivity(new Intent(getApplicationContext(),login.class));
                      }else
                      {
                          Toast.makeText(getApplicationContext(),"Registered failed: Please check your email and password",Toast.LENGTH_SHORT).show();

                      }
                      progressDialog.hide();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view == reg)
        {
            initialize();
            if(!validate())
            {
                Toast.makeText(this,"Signup has failed",Toast.LENGTH_SHORT).show();
            }
            else
            registerUser();
        }
        if(view == login)
        {
            startActivity(new Intent(getApplicationContext(),login.class));
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
