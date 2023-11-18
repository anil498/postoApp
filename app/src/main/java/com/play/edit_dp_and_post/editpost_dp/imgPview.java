package com.play.edit_dp_and_post.editpost_dp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class imgPview extends AppCompatActivity
{

    private ViewPager vpager;
    private AlertDialog.Builder box;
    private int val,show;

    Bitmap bitmap,bmp;
    private FirebaseFirestore db;
    private StorageReference sref;
    File mydir,picname,sharefile;
    FileOutputStream fos=null,fshare;
    int perfalg=0;
    String uristring;
   // Mytask tt = new Mytask();
    int pos;
  ArrayList<ImgLoad>imgdata=new ArrayList<>();
    String databasename;
    String storagename;
    private long eLastClickTime=0;
    private long sLastClickTime=0;
    private long dLastClickTime=0;
//    private ProgressDialog prodig1,prodig2;

    private RewardedAd mRewardedAd;


    // public List<ImgLoad> imagelist = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_img_pview);
        ActionBar actionBar=getSupportActionBar();

        load_Radd();

        vpager=findViewById(R.id.viewpager2);

        db = FirebaseFirestore.getInstance();
        //box=new AlertDialog.Builder(getApplicationContext());
        ///////////
/////////////////pra



        /////////////////////

        Toast.makeText(this,"Swipe left for more pic..",Toast.LENGTH_LONG).show();




        sref= FirebaseStorage.getInstance().getReference();

        imgdata=(ArrayList<ImgLoad>)getIntent().getSerializableExtra("data");
        databasename=getIntent().getStringExtra("databasename");
        storagename=getIntent().getStringExtra("storagename");
        //////////////////
             if(databasename.equals("BirthDay")) {
              //   showAlert();

             }
        if(databasename.equals("Recent"))
            actionBar.setTitle("-> "+"Trending");
        else  if(databasename.equals("GoodMorning"))
            actionBar.setTitle("-> "+"Wishes");
        else
            actionBar.setTitle("-> "+databasename);




        pos=getIntent().getIntExtra("position",0);

        //custom adapter
        // Viewpageadpter adp=new Viewpageadpter(imagelist);
        Viewpageadpter adp=new Viewpageadpter(imgdata);
        //attach adpter
        vpager.setAdapter(adp);
        vpager.setCurrentItem(pos);

        //src of data imglist



    }//on create close

    private void load_Radd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(this, "ca-app-pub-5526284131088012/5408973268",
                adRequest, new RewardedAdLoadCallback() {
                    //////////////
                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        Log.d("anil","onadloaded run");
                       // Toast.makeText(imgPview.this, "onAdLoaded()", Toast.LENGTH_SHORT).show();
                        ///////////////////////////////////
                        mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                                                     @Override
                                                                     public void onAdFailedToShowFullScreenContent(AdError adError) {
                                                                         Toast.makeText(imgPview.this, "add fail", Toast.LENGTH_SHORT).show();
                                                                     }

                                                                     @Override
                                                                     public void onAdShowedFullScreenContent() {
                                                                         // Called when ad is shown.
                                                                        // Toast.makeText(imgPview.this, "add show", Toast.LENGTH_SHORT).show();
                                                                         mRewardedAd = null;
                                                                     }

                                                                     @Override
                                                                     public void onAdDismissedFullScreenContent() {
                                                                         Toast.makeText(imgPview.this, "Downloaded Image in picture folder..", Toast.LENGTH_SHORT).show();
                                                                        // Intent intent = new Intent(imgPview.this, Home.class);
                                                                         // intent.putExtra("cru_email",emailid);
                                                                         //startActivity(intent);
                                                                     }

                                                                     @Override
                                                                     public void onAdImpression() {
                                                                         super.onAdImpression();
                                                                     }
                                                                 }//fullscreen_callback close
                        );//setfullscreen_callback close
                        //////////////////////////////////
                    }//Onadd Loaded close

                    ////////////
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                       // Log.d("anil","fail run"+ loadAdError.getMessage());
                        mRewardedAd = null;
                    }
                }//RewaredAdloadcallback close;
        );//REwarded.load close

    }//load_Radd close
    //////////////-------------
    private void showRwardadd()
    {
        //Log.d("anil","showreward call");
        //Log.d("anil","mRewaredad value ->" +mRewardedAd);
        if (mRewardedAd != null)
        {
            //Log.d("anil","not null if run of showreward");
            mRewardedAd.show(imgPview.this, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem)
                {
                    //Toast.makeText(imgPview.this, "Reward get as downloaded Image", Toast.LENGTH_SHORT).show();

                }
            });
        }
        else{
            //Log.d("anil","else run of showreward");
            Toast.makeText(imgPview.this, "Downloaded Image in picture folder..", Toast.LENGTH_SHORT).show();

           // Toast.makeText(imgPview.this, "The rewarded ad wasn't ready yet.", Toast.LENGTH_SHORT).show();

        }
    }

    //////////////---------------

    private void showAlert() {
        val=0;
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
         View dialoglay=getLayoutInflater().inflate(R.layout.custom_alert,null);
         //  builder .setTitle("Share App");
        // builder.setMessage("first Share App to see all pic of BirthDay");
        builder.setCancelable(false);
        builder.setPositiveButton("share",
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                //Toast.makeText(getApplicationContext(),"click share",Toast.LENGTH_LONG).show();
               val=1;
               //////for whatapp

                String message="anil ka try";
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.setPackage("com.whatsapp");
                // Give your message here
                intent.putExtra(Intent.EXTRA_TEXT,"All try dksjkgsng "+"https://play.google.com/store/apps/details?id=com.prankandthegeek.learnandroidappdevelopment" );//message);
               // intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                // Checking whether Whatsapp
                // is installed or not
                if (intent
                        .resolveActivity(
                                getPackageManager())
                        == null) {
                    Toast.makeText(getApplicationContext(), "Please install whatsapp first.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Starting Whatsapp
                startActivity(intent);
                ////for what app  close
            }//postive btn on click close
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                        //Toast.makeText(getApplicationContext(), "click cancle", Toast.LENGTH_LONG).show();
                          if(val==0)
                              finish();
                    }
                }
        );
        builder.setView(dialoglay);

        builder.show();

    }


    //custom adp
    class Viewpageadpter extends PagerAdapter
    {  // private List<ImgLoad> imglist;
        private ArrayList<ImgLoad>imglist;

        public Viewpageadpter(ArrayList<ImgLoad> imglist)
        {
            this.imglist = imglist;
        }

        /*public Viewpageadpter(List<ImgLoad> imglist) {
            this.imglist = imglist;
        }

         */


        @Override
        public int getCount() {
            return imglist.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==((ConstraintLayout)object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            View vv=getLayoutInflater().inflate(R.layout.slide_window,container,false);
            final ImageView iv=vv.findViewById(R.id.slideimg);
            final Button sharebtn=vv.findViewById(R.id.share);
            final Button dload=vv.findViewById(R.id.download);
            final Button editbtn=vv.findViewById(R.id.editpic);
            ////////banner add

           AdView imgPview_bannerAd =vv.findViewById(R.id.img_pview_bannerAd);
            AdRequest adRequest = new AdRequest.Builder().build();
            imgPview_bannerAd.loadAd(adRequest);
            //////////add close


            // final EditText showuri=vv.findViewById(R.id.tvuri);
            ////////
            final String fimageuri=imglist.get(position).getImgpath();

            //////////////////////pratice
           /* editbtn.setText("Edit..");
            editbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                { Intent intent=new Intent(imgPview.this,Editp.class);
                    //intent.putExtra("key_uri",uri.toString());
                    startActivity(intent);


                }
            });

            */





            /////////////////////////////// close


            //Toast.makeText(getContext(),"imguri is"+fimageuri,Toast.LENGTH_LONG).show();

            //get and load image
            sref.child(storagename+fimageuri).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
            {
                @Override
                public void onSuccess(final Uri uri) {

                    //Uri imageUri = data.getData();





                    try {
                        // Target iv= ((Target) holder).iv;
                        //Picasso.get().load(uri).into(iv);
                        Picasso.get().load(uri).placeholder(R.drawable.ic_rotate_24dp).into(iv);
                        ///////////////
                        //////////make global bitmap close

                        ////for edit  btn
                        editbtn.setText("Edit..");
                        editbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {  editbtn.setBackgroundColor(Color.RED);
                                ///prevvent multiple click on share
                                if(SystemClock.elapsedRealtime()-eLastClickTime<9000)
                                {
                                    // Toast.makeText(getApplicationContext(),"multiple time click",Toast.LENGTH_LONG).show();
                                    return;
                                }
                                eLastClickTime=SystemClock.elapsedRealtime();

                                Intent intent=new Intent(imgPview.this,Editp.class);
                                intent.putExtra("key_uri",uri.toString());
                                startActivity(intent);


                            }
                        });
                        /////////edit btn close

                        sharebtn.setText("Share..");
                        ////for share btn
                        sharebtn.setOnClickListener(new View.OnClickListener()
                        {

                            /////////
                            @Override
                            public void onClick(View v) {
                                sharebtn.setBackgroundColor(Color.YELLOW);
                                ///prevvent multiple click on share
                                if(SystemClock.elapsedRealtime()-sLastClickTime<9000)
                                {
                                    Toast.makeText(getApplicationContext(),"Sharing Start....",Toast.LENGTH_LONG).show();
                                    return;
                                }
                                sLastClickTime=SystemClock.elapsedRealtime();

                              //  Toast.makeText(getApplicationContext(),"click on "+databasename,Toast.LENGTH_LONG).show();

                         //if(databasename.equals("BirthDay"))
                           //         Toast.makeText(getApplicationContext(),"plzzz first share ",Toast.LENGTH_LONG).show();



                                Picasso.get().load(uri).into(new Target() {
                                    @Override
                                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from)
                                    {
                                        StrictMode.VmPolicy.Builder builder=new StrictMode.VmPolicy.Builder();
                                        StrictMode.setVmPolicy(builder.build());

                                        sharefile=new File(getExternalCacheDir()+"/"+getResources().getString(R.string.app_name)+".png");

                                        Intent intent=new Intent(Intent.ACTION_SEND);


                                        try{
                                            fshare=new FileOutputStream(sharefile);
                                            bitmap.compress(Bitmap.CompressFormat.PNG,100,fshare);
                                            fshare.flush();
                                            fshare.close();
                                            intent.setType("image/*");
                                            intent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(sharefile));
                                            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);

                                        }
                                        catch (Exception e) {
                                            e.printStackTrace(); }

                                        startActivity(intent.createChooser(intent,"shareing via....."));


                                    }

                                    @Override
                                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                                    }

                                    @Override
                                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                                    }
                                });

                            }
                        });

                        ///for share close

                       ////for download
                        //........................

                        dload.setText("Download..");


                        // ....premission pra succes pra

                        /*
                        checkPermission();
                        dload.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if(perfalg==1)
                                {


                                    //piccaso start

                                    Picasso.get().load(uri).into(new Target() {
                                        @Override
                                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from)
                                        {
                                            Toast.makeText(getApplicationContext(),"run for save",Toast.LENGTH_LONG).show();
                                            //make dirctory
                                            mydir=new File(Environment.getExternalStorageDirectory()+"/comfhdkaapp");

                                            // mydir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/rajapp");
                                            if (!mydir.exists()) {
                                                if (mydir.mkdir()) {
                                                    Toast.makeText(getApplicationContext(), "create", Toast.LENGTH_LONG).show();
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "not create", Toast.LENGTH_LONG).show();

                                                }
                                            } else
                                            {
                                                Toast.makeText(getApplicationContext(), "allready exist", Toast.LENGTH_LONG).show();

                                            }
                                            //dir make process close
                                            File file=new File(mydir,"dhhaj"+".jpg");

                                            fos=null;
                                            try{

                                                fos=new FileOutputStream(file);

                                                bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
                                                fos.flush();
                                                fos.close();
                                              //  MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,"by only media","adus");
                                                //
                                                // Image.Media.
                                                Toast.makeText(getApplicationContext(),"insert",Toast.LENGTH_LONG).show();

                                            }catch (Exception e)
                                            {
                                                Toast.makeText(getApplicationContext(),"ERROR insert"+e.getMessage(),Toast.LENGTH_LONG).show();

                                            }
                                        }
                                        ////onbitmap loadclose

                                        @Override
                                        public void onBitmapFailed(Exception e, Drawable errorDrawable)
                                        {

                                        }

                                        @Override
                                        public void onPrepareLoad(Drawable placeHolderDrawable)
                                         {

                                        }
                                    });
                                    //piccaso close

                                 }
                                //if close
                                else
                                    {
                                        Toast.makeText(getApplicationContext(),"permission req...",Toast.LENGTH_LONG).show();
                                        checkPermission();
                                    }
                                //else cllose

                            }//on click close
                        });
                       */
                        //////pratice close.....



                        dload.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v)
                            {
                                //for permission
                                //Log.d("anil","click on download btn");

                                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                                {

                                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                                    {
                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1100);

                                    }
                                }

                                 //Toast.makeText(getApplicationContext(),"click on "+databasename,Toast.LENGTH_LONG).show();

                                //////for birthday 23 dec
                             /*  if(databasename.equals("BirthDay"))
                               {
                                   Toast.makeText(getApplicationContext(),"click on BY IF"+databasename,Toast.LENGTH_LONG).show();


                               }

                              */

                                //////for birthday 23 dec close





                                ////permission close

                                //....for alert box
                                /*
                                AlertDialog.Builder builder=new AlertDialog.Builder(getApplicationContext());
                                builder .setTitle("Del conf");
                                builder.setMessage("you want del?");
                                builder.setPositiveButton("share", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                       // Toast.makeText(getApplicationContext(),"click share",Toast.LENGTH_LONG).show();
                                    }
                                });
                                builder.setNegativeButton("cancle", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                         //       Toast.makeText(getApplicationContext(), "click cancle", Toast.LENGTH_LONG).show();

                                            }
                                        }
                                );

                                builder.show();
                                */
                                dload.setBackgroundColor(Color.GREEN);
                                ///prevvent multiple click on share
                                if(SystemClock.elapsedRealtime()-dLastClickTime<2000)
                                {
                                   // Toast.makeText(getApplicationContext(),"multiple time click",Toast.LENGTH_LONG).show();
                                    return;
                                }
                                dLastClickTime=SystemClock.elapsedRealtime();

                               // Log.d("anil","value of isdownloadallow before showreward call- >" +isdownloadallow );
                               //isdownloadallow=false;
                                load_Radd();
                                showRwardadd();
                                //Log.d("anil","isdownload after showreward call-> " + isdownloadallow);
                               //...for alert box close
                             //if(isdownloadallow) {
                                // Log.d("anil","go  for insert pic");
                                 Picasso.get().load(uri).into(new Target() {
                                     @Override
                                     public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                         //  Toast.makeText(getApplicationContext(),"run for save",Toast.LENGTH_LONG).show();
                                         //make dirctory in outside picture folder
                                         //mydir=new File(Environment.getExternalStorageDirectory()+"/comfhdkaapp");

                                         //make dirctory inside picture folder

                                         mydir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Posto");
                                         if (!mydir.exists()) {
                                             if (mydir.mkdir()) {
                                                 //        Toast.makeText(imgPview.this, "create", Toast.LENGTH_LONG).show();
                                             } else {
                                                 //      Toast.makeText(imgPview.this, "not create", Toast.LENGTH_LONG).show();

                                             }
                                         } else {
                                             //Toast.makeText(imgPview.this, "allready exist", Toast.LENGTH_LONG).show();

                                         }
                                         //dir make process close
                                         //"f" + UUID.randomUUID().toString();;
                                         File file = new File(mydir, UUID.randomUUID().toString() + ".jpg");
                                         fos = null;
                                         try {
                                             fos = new FileOutputStream(file);
                                             bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                                             fos.flush();
                                             fos.close();
                                             // Image.Media.
                                             MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "posto app", "download from posto app");

                                            // Toast.makeText(getApplicationContext(), "DONE,Insert in picture folder", Toast.LENGTH_LONG).show();

                                         } catch (Exception e) {
                                             Toast.makeText(getApplicationContext(), "ERROR insert" + e.getMessage(), Toast.LENGTH_LONG).show();
                                         }

                                     }
                                     ////onbitmap loadclose

                                     @Override
                                     public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                                         Toast.makeText(getApplicationContext(), "Error:bitmap fail", Toast.LENGTH_LONG).show();


                                     }

                                     @Override
                                     public void onPrepareLoad(Drawable placeHolderDrawable) {

                                     }
                                 });
                             //}


                            }//on click close

                        });//listner close

                        ///////for donwload close
                    }catch (IllegalArgumentException e)
                    {
                        Toast.makeText(getApplicationContext(),"picass use"+e.getMessage(),Toast.LENGTH_LONG).show();

                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"error"+e.getMessage(),Toast.LENGTH_LONG).show();
                    // err.setText("error"+e.getMessage());
                }
            });

            // iv.setImageResource(images[position]);


            Objects.requireNonNull(container).addView(vv);
            return vv;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((ConstraintLayout)object);
        }

    }//close adpter
//////////
    //........................
   /*
    public boolean checkPermission()
    {
        int READ_EXTERNAL_PERMISSION= ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if((READ_EXTERNAL_PERMISSION!=PackageManager.PERMISSION_GRANTED))
         {
             ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1101);
         return false;
         }
    return true;
    }
*/
    /*
    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if(requestCode==1100 && grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
         {
             perfalg=1;
             Toast.makeText(getApplicationContext(),"permission done",Toast.LENGTH_LONG).show();
         }
    }


     */

///......................................
}//main close
