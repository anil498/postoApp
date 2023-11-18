package com.play.edit_dp_and_post.editpost_dp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class write_name extends AppCompatActivity {

    private  String name;
    private EditText writenametv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_name);
       writenametv=findViewById(R.id.editText);

    }

    public void savename(View view)
    {
       name=writenametv.getText().toString();
        Intent intent =new Intent(write_name.this,Editp.class);
        intent.putExtra("key_name",name);
        startActivity(intent);
    }
}
