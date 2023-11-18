package com.play.edit_dp_and_post.editpost_dp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tablayout;
    ViewPager viewPager;
    PageAdp pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tablayout=findViewById(R.id.tablayout);
        viewPager=findViewById(R.id.pagerview);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pagerAdapter =new PageAdp(this,getSupportFragmentManager());
        //attach adpter
        pagerAdapter.addFragment(new Couple_Dp(),"Couple Dp");
      pagerAdapter.addFragment(new Girl_Att(),"Girl Attitude");
         pagerAdapter.addFragment(new Boy_Att(),"Boy Attitude");


        pagerAdapter.addFragment(new BirthDay(),"BirthDay");
        pagerAdapter.addFragment(new Recent(),"Recent");



        viewPager.setAdapter(pagerAdapter);
        tablayout.setupWithViewPager(viewPager);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.h_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    public void upload(MenuItem item) {
        Intent intent=new Intent(MainActivity.this,Upload.class);
        startActivity(intent);
    }
}//main close

  /*
   private ImageView iv;
    private Uri imguri;
    private String imgname;

    private StorageReference srf;

  public void pic(View view) {
        Intent gallery = new Intent(Intent.ACTION_PICK);
        gallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100&&resultCode==RESULT_OK)
        {
            imguri=data.getData();
            iv.setImageURI(imguri);
        }
    }


    public void load(View view) {
        imgname="raj";
        if(imguri!=null)
        {
            srf.child("Recent/"+imgname).putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(),"done",Toast.LENGTH_LONG).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"fail",Toast.LENGTH_LONG).show();

                }
            });
        }//if close

    }//load close
*/
