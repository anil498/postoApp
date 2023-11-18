package com.play.edit_dp_and_post.editpost_dp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class Upload extends AppCompatActivity {
    private ImageView iv;
    private EditText choice;//,edpicname;
    private int choi;
   // private String select;
    private Uri imguri;
    private String imgname;//,imagename;
    private ImgLoad igload;
    private StorageReference srf;
    private FirebaseFirestore db;
    private Button uploadbt;
    private long mLastClickTime=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        iv=findViewById(R.id.recentimage);
        choice=findViewById(R.id.editText3);
        uploadbt=findViewById(R.id.button);
        //edpicname=findViewById(R.id.editText);
      // choi= Integer.parseInt(choice.getText().toString());
        //select=choice.getText().toString();
        db = FirebaseFirestore.getInstance();

        srf= FirebaseStorage.getInstance().getReference();

    }//on create close

    public void rectpic(View view) {
        Intent gallery = new Intent(Intent.ACTION_PICK);
        gallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, 201);
    }//pic close



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==201&&resultCode==RESULT_OK)
        {
            imguri=data.getData();
            iv.setImageURI(imguri);
        }
    }//onactivity close

    public void recent(View view)

    {
        uploadbt.setBackgroundColor(Color.RED);
        ///prevvent multiple click on share
        if(SystemClock.elapsedRealtime()-mLastClickTime<9000)
        {
            Toast.makeText(getApplicationContext(),"multiple time click",Toast.LENGTH_LONG).show();
            return;
        }
        mLastClickTime=SystemClock.elapsedRealtime();
        /////
        choi= Integer.parseInt(choice.getText().toString());
       // imagename = edpicname.getText().toString();
        Toast.makeText(getApplicationContext(),"your enter choice"+choi,Toast.LENGTH_LONG).show();


        switch (choi)
        {


            case 1:
                uploadd("Couple/","Couple");
                break;

            case 2:
                uploadd("Recent/","Recent");
                break;

                case 3:
                uploadd("CoupleStatus/","CoupleStatus");
                break;

            case 4:
                uploadd("GirlDp/","GirlDp");
                break;



                case 5:
                    uploadd("GirlAttitude/","GirlAttitude");
                    break;

            case 6:
                uploadd("BoyDp/","BoyDp");
                break;

            case 7:
                uploadd("BoyAttitude/","BoyAttitude");
                break;



            case 8:
                uploadd("BirthDay/","BirthDay");
                break;

            case 9:
                uploadd("SadGirl/","SadGirl");
                break;

            case 10:
                uploadd("SadBoy/","SadBoy");
                break;

            case 11:
                uploadd("God/","God");
                break;

            case 12:
                uploadd("Motivation/","Motivation");
                break;

            case 13:
                uploadd("GoodMorning/","GoodMorning");
                break;



             default:
                 break;


        }



    }


    public void uploadd(final String sname, final String dname) {
        Toast.makeText(getApplicationContext(), "storage " + sname + "database " + dname, Toast.LENGTH_LONG).show();

        imgname= "f" + UUID.randomUUID().toString();;


     //   Toast.makeText(getApplicationContext(), "image name "+ , Toast.LENGTH_LONG).show();


            // imgname="hogya";
            if (imguri != null) {
                igload = new ImgLoad("", imgname, System.currentTimeMillis());

                db.collection(dname).add(igload).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "done in DATABASE", Toast.LENGTH_LONG).show();
                        /////insert in storage
                        srf.child(sname + imgname).putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(getApplicationContext(), "upload in STORAGE", Toast.LENGTH_LONG).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "fail in storage", Toast.LENGTH_LONG).show();


                            }
                        });
                        //////////insertion n storage close


                    }////db succes close
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "fail in db", Toast.LENGTH_LONG).show();

                    }
                });

                ///close insertion

            /* srf.child(sname+imgname).putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(),"upload in STORAGE", Toast.LENGTH_LONG).show();
                  /////insert in db
                 igload=new ImgLoad("",imgname,System.currentTimeMillis());

                 db.collection(dname).add(igload).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                     @Override
                     public void onSuccess(DocumentReference documentReference) {
                         Toast.makeText(getApplicationContext(),"done in DATABASE", Toast.LENGTH_LONG).show();

                     }
                 }).addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         Toast.makeText(getApplicationContext(),"fail in db", Toast.LENGTH_LONG).show();

                     }
                 });




                  //////close inserrt in db
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"fail in storage",Toast.LENGTH_LONG).show();

                }
            });

            */
            }//if close


        }//loadclose




}//main close
