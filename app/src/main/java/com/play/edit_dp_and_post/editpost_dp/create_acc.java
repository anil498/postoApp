package com.play.edit_dp_and_post.editpost_dp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class create_acc extends AppCompatActivity {

    private EditText uemail, upass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acc);

        uemail = findViewById(R.id.accusername);
        upass = findViewById(R.id.accpass);
        //tv=findViewById(R.id.editText);
        mAuth = FirebaseAuth.getInstance();

    }/////

    public void newaccount(View view) {

        final String emailid = uemail.getText().toString();
        String password = upass.getText().toString();
        //Toast.makeText(Createacc.this, "inside newacc", Toast.LENGTH_SHORT).show();
        if (emailid.equals("")) {
            Toast.makeText(getApplicationContext(), "please fill your email", Toast.LENGTH_LONG).show();
            return;
        }
        if (password.equals("")) {
            Toast.makeText(getApplicationContext(), "please fill your password", Toast.LENGTH_LONG).show();
            return;
        }
        if (password.length()<7) {
            Toast.makeText(getApplicationContext(), "password contain more than 7 charcter", Toast.LENGTH_LONG).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(emailid, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "WELCOME..", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(create_acc.this, login.class);
                            // intent.putExtra("cru_email",emailid);
                            startActivity(intent);
                        } else {
                            Log.d("anil","sign"+task.getException());
                            // tv.setText("error"+task.getException());
                            Toast.makeText(getApplicationContext(), "failed..please Try Again", Toast.LENGTH_LONG).show();
                        }
                    }
                });



    }////




}/////
