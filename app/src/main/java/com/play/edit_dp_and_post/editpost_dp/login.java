package com.play.edit_dp_and_post.editpost_dp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    private EditText etuid,etupass;
    private FirebaseAuth mAuth;
    private EditText codevaltv;
    private int code;
    private Button newbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //mAuth = FirebaseAuth.getInstance();
        //etuid=findViewById(R.id.edusername);
        //etupass=findViewById(R.id.edpass);
        codevaltv=findViewById(R.id.code);
        newbtn=findViewById(R.id.newacc);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.entry, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void goupload(MenuItem item) {

      codevaltv.setVisibility(View.VISIBLE);
      newbtn.setVisibility(View.VISIBLE);

    }


   /* public void login(View view)
    {
        final String email= etuid.getText().toString();
        String password=etupass.getText().toString();
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),"login done",Toast.LENGTH_LONG).show();
                            Intent intent =new Intent(login.this,Upload.class);
                           // intent.putExtra("u_email",email);
                            startActivity(intent);
                        }else
                        {
                            Toast.makeText(getApplicationContext(), "failed..Try Again", Toast.LENGTH_LONG).show();
                            Log.d("anil","sign"+task.getException());
                        }
                    }
                });





    }//login

    */

    public void check(View view) {
        code= Integer.parseInt(codevaltv.getText().toString());
          if(code==1025000)
          {
              //newacc();
              Toast.makeText(getApplicationContext(),"login done",Toast.LENGTH_LONG).show();
              Intent intent =new Intent(login.this,Upload.class);
              // intent.putExtra("u_email",email);
              startActivity(intent);
          }

    }


    /*public void newacc() {
        Intent intent=new Intent(login.this,create_acc.class);
        startActivity(intent);
    }*/


}//main close
