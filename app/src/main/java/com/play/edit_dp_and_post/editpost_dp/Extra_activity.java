package com.play.edit_dp_and_post.editpost_dp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Extra_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.entry, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void goupload(MenuItem item) {
        Intent intent = new Intent(Extra_activity.this, login.class);
        startActivity(intent);

    }
}