package com.play.edit_dp_and_post.editpost_dp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import yuku.ambilwarna.AmbilWarnaDialog;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Editp extends AppCompatActivity {
    ImageView etiv;
    private View sizeview,clrview,allfillpicview,fontview,shadowview,wordspaceview,bckClrview,allgradclr,allbrdopacty;
    private float xval,radval,yval;
    private int shadcolor;
    private LinearLayout poplay;
    private File mydir,picname,sharefile;
    private FileOutputStream fos=null,fshare;
    int mDefaultColor;
    private HorizontalScrollView popview;
    private  String imgpath,printname;
    private Uri picuri;
    private ImageButton writetextbtn,deltextbtn,addtextbtn,savebtn;
   // public TextView ePrtname,Prtname2;
    public Border_text Prtname,Prtname2;
    private EditText nameEdtext;
    private float xdown=0,ydown=0,x2down=0,y2down=0;
    //private Bitmap Golbitmap;
    private int flag1,flag2,is1poss=1,is2poss=0,delfix1=0,delfix2=0;
    private long back_pressed;
    private RelativeLayout savelay;
    private int clrflag,grdflag;
    private int firtgrd_clr,secgrd_clr;
    private int f_clr=1,f_grd,s_clr=1,s_grd,s_firtgrd_clr,s_secgrd_clr;


    private RewardedAd mRewardedAd;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_editp);
        etiv=findViewById(R.id.imageView);
        writetextbtn=findViewById(R.id.imageButton4);
        deltextbtn=findViewById(R.id.textdelbtn);
        addtextbtn=findViewById(R.id.textaddbtn);
        savebtn=findViewById(R.id.edsavebtn);
        savelay=findViewById(R.id.savelay);

        load_Radd();
        ///////
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                poplay.setVisibility(View.GONE);

                AlertDialog.Builder builder=new AlertDialog.Builder(Editp.this);
                //View dialoglay=getLayoutInflater().inflate(R.layout.custom_alert,null);
                  builder .setTitle("Saving...");
                 builder.setMessage("Are you want to save image ?");

                builder.setCancelable(false);

                builder.setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                       saveimg(savelay);
                    }//postive btn on click close
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {

                                Toast.makeText(getApplicationContext(), "click cancle", Toast.LENGTH_LONG).show();

                            }
                        }
                );

                builder.show();

            }
        });

        /////////
        poplay=findViewById(R.id.poplay);
       // popview=findViewById(R.id.popview);
       // nameEdtext=findViewById(R.id.printname);
       // Prtname=findViewById(R.id.printNametv);
        Prtname=(Border_text)findViewById(R.id.cprintNametv);


       // Prtname2=findViewById(R.id.printNametv2);
        Prtname2=(Border_text)findViewById(R.id.cprintNametv2);

        mDefaultColor=0;
        ///////////// change

       Intent intent =getIntent();

       imgpath=intent.getStringExtra("key_uri");
       picuri= Uri.parse(imgpath);
        ////////////////////close
        Picasso.get().load(picuri).into(etiv);


        /////////////
        etiv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                poplay.setVisibility(View.INVISIBLE);
                Prtname.setBackground(null);
                Prtname2.setBackground(null);
                delfix1=0;
                delfix2=0;
                flag1=0;
                flag2=0;

                return true;
            }
        });
///////////////////////////////////////
        deltextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // is2poss=0; //new remove
                if(delfix1==101)
                {
                    is1poss=0;
                   // is2poss=1;  //new remove
                    Prtname.setVisibility(View.GONE);

                }
               else if(delfix2==102)
                {   is2poss=0;

                    Prtname2.setVisibility(View.GONE);
                }
               else{
                   Toast.makeText(Editp.this,"First Select Text View for Remove",Toast.LENGTH_LONG).show();
                }

            }
        });
///////////////////////////////////////////
        addtextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(is2poss==0 && is1poss==1)
                {
                    Prtname2.setVisibility(View.VISIBLE);
                    Prtname2.setText("Text2 View");
                    Prtname2.setTextSize(30);
                    Prtname2.setStrokeWidth(1);
                    Prtname2.setStrokeColor(Color.BLACK);
                    Prtname2.setLetterSpacing(0.0f);
                    Prtname2.setIsGrd_fill(0);
                    Typeface typeface = Typeface.createFromAsset(getAssets(),"AAntiCorona-PKZx7.otf");
                    Prtname2.setTypeface(typeface);

                    Prtname2.setShadowLayer(0f,0f,0f, Color.RED);
                    Prtname2.getPaint().setShader(null);
                    Prtname2.setTextColor(Color.BLUE);
                    is2poss=1;
                    Prtname2.setBackgroundResource(R.drawable.box_bord);
                    Toast.makeText(Editp.this,"new Text View inserted",Toast.LENGTH_LONG).show();
                }
                if(is1poss==0)
                {
                    Prtname.setVisibility(View.VISIBLE);
                    Prtname.setText("Text1 View");
                    Prtname.setTextSize(30);
                    Prtname.setStrokeWidth(1);

                    Prtname.setStrokeColor(Color.BLACK);
                    Prtname.setLetterSpacing(0.0f);
                    Prtname.setShadowLayer(0f,0f,0f, Color.RED);
                    Prtname.setIsGrd_fill(0);
                   Typeface typeface = Typeface.createFromAsset(getAssets(),"AAntiCorona-PKZx7.otf");
                   Prtname.setTypeface(typeface);

                   // Prtname.remove_eff();
                    Prtname.getPaint().setShader(null);
                    Prtname.setTextColor(Color.BLUE);
                    is1poss=1;
                   // is2poss=0;  //new remove
                    Prtname.setBackgroundResource(R.drawable.box_bord);
                    Toast.makeText(Editp.this,"new Text View inserted",Toast.LENGTH_LONG).show();

                }
                if(is1poss==1 && is2poss ==1)
                    Toast.makeText(Editp.this,"Only 2 writing box you can add",Toast.LENGTH_LONG).show();

            }
        });


 ////////////////  ///   ////  ////////////////////////////

        Prtname2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
               delfix1=0;
               delfix2=102;
               flag1=0;
               flag2=2;
                Prtname2.setBackgroundResource(R.drawable.touch_box_bord);
                Prtname.setBackground(null);


                switch (event.getActionMasked())
                {
                    case MotionEvent.ACTION_DOWN:
                        x2down=event.getX();
                        y2down=event.getY();

                        break;
                    case MotionEvent.ACTION_MOVE:

                        float x2move,y2move;
                        x2move=event.getX();
                        y2move=event.getY();
                        //////
                        float distX=x2move-x2down;
                        float distY=y2move-y2down;
                        ///
                        Prtname2.setX( Prtname2.getX()+distX);
                        Prtname2.setY( Prtname2.getY()+distY);



                        break;
                }//s close
                return true;
            }
        });
        //moveable close



        //////////make moveable
        Prtname.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                delfix1=101;
                delfix2=0;
                flag1=1;
                flag2=0;
               Prtname.setBackgroundResource(R.drawable.touch_box_bord);
                Prtname2.setBackground(null);



                switch (event.getActionMasked())
                {
                    case MotionEvent.ACTION_DOWN:
                        xdown=event.getX();
                        ydown=event.getY();

                        break;
                    case MotionEvent.ACTION_MOVE:

                        float xmove,ymove;
                        xmove=event.getX();
                        ymove=event.getY();
                        //////
                        float distX=xmove-xdown;
                        float distY=ymove-ydown;
                        ///
                        Prtname.setX( Prtname.getX()+distX);
                        Prtname.setY( Prtname.getY()+distY);
                        /////
                        //  xdown=xmove;
                        //ydown=ymove;
                        //   xcod.setText((int) captiontext.getX());
                        //  ycod.setText((int) captiontext.getY());


                        break;
                }//s close
                return true;
            }
        });
        //moveable close

    }//on create close
////////////////////////
private void load_Radd() {
    AdRequest adRequest = new AdRequest.Builder().build();
    RewardedAd.load(this, "ca-app-pub-5526284131088012/1105059880",
            adRequest, new RewardedAdLoadCallback() {
                //////////////
                @Override
                public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                    mRewardedAd = rewardedAd;
                    //Toast.makeText(Editp.this, "onAdLoaded()", Toast.LENGTH_SHORT).show();
                    ///////////////////////////////////
                    mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                                                 @Override
                                                                 public void onAdFailedToShowFullScreenContent(AdError adError) {
                                                                     //Toast.makeText(Editp.this, "add fail", Toast.LENGTH_SHORT).show();
                                                                 }

                                                                 @Override
                                                                 public void onAdShowedFullScreenContent() {
                                                                     // Called when ad is shown.
                                                                     //Toast.makeText(Editp.this, "add show", Toast.LENGTH_SHORT).show();
                                                                     mRewardedAd = null;
                                                                 }

                                                                 @Override
                                                                 public void onAdDismissedFullScreenContent() {
                                                                     Toast.makeText(Editp.this, "Downloaded Image in picture folder..", Toast.LENGTH_SHORT).show();


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
                    //Log.d(TAG, loadAdError.getMessage());
                    mRewardedAd = null;
                }
            }//RewaredAdloadcallback close;
    );//REwarded.load close

}//load_Radd close
  ///////-------------------
  private void showRwardadd()
  {
      //Log.d("anil","showreward call");
      //Log.d("anil","mRewaredad value ->" +mRewardedAd);
      if (mRewardedAd != null)
      {
          //Log.d("anil","not null if run of showreward");
          mRewardedAd.show(Editp.this, new OnUserEarnedRewardListener() {
              @Override
              public void onUserEarnedReward(@NonNull RewardItem rewardItem)
              {
                 // Toast.makeText(Editp.this, "Reward get as downloaded Image", Toast.LENGTH_SHORT).show();

              }
          });
      }
      else{
          //Log.d("anil","else run of showreward");
          Toast.makeText(Editp.this, "Downloaded Image in picture folder..", Toast.LENGTH_SHORT).show();

          //Toast.makeText(Editp.this, "The rewarded ad wasn't ready yet.", Toast.LENGTH_SHORT).show();

      }
  }
////////////////////////

////////////////////////////whaytpp
    private void saveimg(RelativeLayout etlay)
    {
        load_Radd();
        showRwardadd();

        Bitmap bitmap =Bitmap.createBitmap(etlay.getWidth(),etlay.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        etlay.draw(c);
        //////////
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {

            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1100);

            }
        }
        ////////
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
        File file=new File(mydir, UUID.randomUUID().toString()+".jpg");
        fos=null;
        try{
            fos=new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
            fos.flush();
            fos.close();
            // Image.Media.
            MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,"posto app","download from posto app");

            Toast.makeText(getApplicationContext(),"DONE,Insert in picture folder",Toast.LENGTH_LONG).show();

        }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"ERROR insert"+e.getMessage(),Toast.LENGTH_LONG).show();
        }


        /////////


    }

    @Override
    public void onBackPressed() {
        if(back_pressed+2000>System.currentTimeMillis())
        {
            super.onBackPressed();
        }else
        {
            Toast.makeText(getApplicationContext(),"Press again to exit",Toast.LENGTH_LONG).show();
        }
        back_pressed=System.currentTimeMillis();

    }



    public void writenamebtn(View view)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(Editp.this);
        View dialoglay=getLayoutInflater().inflate(R.layout.takename_alert,null);
        nameEdtext=dialoglay.findViewById(R.id.printname);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if(flag1==1) {
                    Prtname.setText(nameEdtext.getText().toString());
                }else if (flag2==2) {
                    Prtname2.setText(nameEdtext.getText().toString());
                }else
                    Toast.makeText(getApplicationContext(),"first Select Text box ",Toast.LENGTH_LONG).show();

            }
        });
        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setView(dialoglay);

        builder.show();
    }
    /////////////////write alert close

    public void size(View view)
    {
        //popview.setVisibility(View.VISIBLE);
       // poplay.setVisibility(View.VISIBLE);



        poplay.setVisibility(View.VISIBLE);
        poplay.removeView(clrview);
        poplay.removeView(sizeview);
        poplay.removeView(wordspaceview);
        poplay.removeView(shadowview);
        poplay.removeView(fontview);
        poplay.removeView(bckClrview);
        poplay.removeView(allgradclr);
        poplay.removeView(allbrdopacty);
        poplay.removeView(allfillpicview);



        sizeview = getLayoutInflater().inflate(R.layout.size_inflate, null, false);
               SeekBar sizebar = sizeview.findViewById(R.id.sizeseekBar);
               TextView sinc =sizeview.findViewById(R.id.inc);
              final TextView sval =sizeview.findViewById(R.id.sval);
              TextView sdec =sizeview.findViewById(R.id.dec);

            sinc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int size= Integer.parseInt(sval.getText().toString());
                    size++;
                    sval.setText(size+"");
                    if(flag1==1)
                   // Prtname.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
                        Prtname.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
                    else if(flag2==2)
                        Prtname2.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
                    else
                        Toast.makeText(Editp.this,"First Select Text View",Toast.LENGTH_LONG).show();

                }
            });
            //////
        sdec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size= Integer.parseInt(sval.getText().toString());
                size--;
                sval.setText(size+"");
                if(flag1==1)
                    Prtname.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
                else if(flag2==2)
                    Prtname2.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
                else
                    Toast.makeText(Editp.this,"First Select Text View",Toast.LENGTH_LONG).show();

            }
        });


        //float fs =  30;
               //sizebar.setProgress((int)fs);
               sizebar.setProgress(30);
               // Prtname.setTextSize(TypedValue.COMPLEX_UNIT_DIP,sizebar.getProgress());
               sizebar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                   @Override
                   public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
                   {

                       sval.setText(progress+50+"");
                       if (flag1 == 1)
                       {

                           Prtname.setTextSize(TypedValue.COMPLEX_UNIT_SP, progress + 50);
                       } else if (flag2 == 2) {
                           Prtname2.setTextSize(TypedValue.COMPLEX_UNIT_SP, progress + 50);

                       }
                   }

                   @Override
                   public void onStartTrackingTouch(SeekBar seekBar) {

                      if(flag1==1||flag2==2)
                      {}
                       else
                           {
                          Toast.makeText(getApplicationContext(), "first Select Text box for size ", Toast.LENGTH_LONG).show();
                              return;

                      }
                   }

                   @Override
                   public void onStopTrackingTouch(SeekBar seekBar) {

                   }
               });
               poplay.addView(sizeview);


    }
    //size btn fun close

    public void addcolor(View view)
    {
        poplay.setVisibility(View.VISIBLE);
        poplay.removeView(clrview);
        poplay.removeView(sizeview);
        poplay.removeView(wordspaceview);
        poplay.removeView(shadowview);
        poplay.removeView(fontview);
        poplay.removeView(bckClrview);
        poplay.removeView(allgradclr);
        poplay.removeView(allbrdopacty);
        poplay.removeView(allfillpicview);



        clrview=  getLayoutInflater().inflate(R.layout.add_color,null,false);
        ////for fill color
        Button clrbtn=clrview.findViewById(R.id.clrbtn);
        clrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Prtname.getPaint().reset();
                // Prtname.getPaint().setShader(null);
                openColorPickerDialogue(1);
            }
        });
     //////////fill color close


        //////for fill image
        Button fillpicbtn=clrview.findViewById(R.id.fillpicbtn);
        /*fillpicbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

               if(flag1==1)
               {
                   Prtname.getPaint().setShader(null);
                   Prtname.setTextColor(Color.RED);
               }else if(flag2==2)
               {
                   Prtname2.getPaint().setShader(null);
                   Prtname2.setTextColor(Color.RED);
               }
               else
               {
                   Toast.makeText(getApplicationContext(),"first select Text box",Toast.LENGTH_LONG).show();
                   return;
               }

              //  Toast.makeText(getApplicationContext(), "proce....", Toast.LENGTH_SHORT).show();

                //Toast.makeText(getApplicationContext(), "pick fill", Toast.LENGTH_SHORT).show();


              Picasso.get().load(picuri).into(new Target()
              {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from)
                    {
                        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT,Shader.TileMode.REPEAT);
                        //Paint p = new Paint();
                        if(flag1==1)
                        {   Prtname.setIsGrd_fill(0);
                               ///////////////pra for border or shadow

                            Prtname.getPaint().setShader(shader);
                            Prtname.setTextColor(Color.parseColor("#F97C3C"));
                            Toast.makeText(getApplicationContext(), "Pic fill as color in text", Toast.LENGTH_SHORT).show();

                        }else if(flag2==2)
                        {
                            Prtname.setIsGrd_fill(0);
                            ///////////////pra for border or shadow
                            /////////////
                           Prtname.getPaint().setShader(shader);
                           Prtname.setTextColor(Color.parseColor("#F97C3C"));
                            Toast.makeText(getApplicationContext(), "Pic fill as color in text", Toast.LENGTH_SHORT).show();

                        }else
                        {
                            Toast.makeText(getApplicationContext(),"first select Text box",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable)
                    {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable)
                    {
                        //Toast.makeText(getApplicationContext(), "bitmap prepare..", Toast.LENGTH_SHORT).show();

                    }
                });



            }//on click close
        });



        ////fill image close
        */
        //////////////texture pra ststr
        fillpicbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                poplay.removeView(clrview);
                poplay.removeView(allfillpicview);


                final int [] pics = {R.drawable.t1j,R.drawable.t2j,R.drawable.t1j,R.drawable.t1j,R.drawable.t1j,R.drawable.t1j,R.drawable.t1j,R.drawable.t1j,R.drawable.t1j,R.drawable.t1j};
                allfillpicview=getLayoutInflater().inflate(R.layout.fill_pic_lay,null,false);
                GridView fillpicgv=allfillpicview.findViewById(R.id.fillpicgv);
                ImageView currImg=allfillpicview.findViewById(R.id.currimg);
                ////////////////////
                Picasso.get().load(picuri).into(currImg);



                currImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(flag1==1)
                        {
                            Prtname.getPaint().setShader(null);
                            Prtname.setTextColor(Color.RED);
                        }else if(flag2==2)
                        {
                            Prtname2.getPaint().setShader(null);
                            Prtname2.setTextColor(Color.RED);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"first select Text box",Toast.LENGTH_LONG).show();
                            return;
                        }

                        clrflag=1;
                        grdflag=0;
                        f_clr=1;
                        f_grd=0;
                          //Prtname.setIsPic_fill(1);
                        Picasso.get().load(picuri).into(new Target()
                   {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from)
                    {
                        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT,Shader.TileMode.REPEAT);
                        //Paint p = new Paint();
                        if(flag1==1)
                        {   Prtname.setIsGrd_fill(0);
                            ///////////////pra for border or shadow
//                            Prtname.getPaint().setShader(null);

                            Prtname.getPaint().setShader(shader);
                            Prtname.setTextColor(Color.parseColor("#F97C3C"));
                            Toast.makeText(getApplicationContext(), "Pic fill as color in text", Toast.LENGTH_SHORT).show();

                        }else if(flag2==2)
                        {
                            Prtname2.setIsGrd_fill(0);
                            ///////////////pra for border or shadow
                            /////////////
                            Prtname2.getPaint().setShader(shader);
                            Prtname2.setTextColor(Color.parseColor("#F97C3C"));
                            Toast.makeText(getApplicationContext(), "Pic fill as color in text", Toast.LENGTH_SHORT).show();

                        }else
                        {
                            Toast.makeText(getApplicationContext(),"first select Text box",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable)
                    {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable)
                    {
                        //Toast.makeText(getApplicationContext(), "bitmap prepare..", Toast.LENGTH_SHORT).show();

                    }
                });
                //////////
                    }
                });



                ////////////////////
                CustomFillPic adp =new CustomFillPic(pics);
                fillpicgv.setAdapter(adp);
                  ////
                fillpicgv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        clrflag=1;
                        grdflag=0;
                        f_clr=1;
                        f_grd=0;
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),pics[position]);

                        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);

                        if(flag1==1) {
                            Prtname.getPaint().setShader(null);
                            Prtname.setTextColor(Color.RED);
                            Prtname.setIsGrd_fill(0);
                            Prtname.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

                            Prtname.getPaint().setShader(shader);
                           // Prtname.setTextColor(Color.parseColor("#F97C3C"));

                        }else if(flag2==2)
                        {
                            Prtname2.getPaint().setShader(null);
                            Prtname2.setTextColor(Color.RED);
                            Prtname2.setIsGrd_fill(0);
                            Prtname2.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

                            Prtname2.getPaint().setShader(shader);

                        }else
                            {Toast.makeText(Editp.this,"First Select Text View",Toast.LENGTH_LONG).show();}


                    }
                });
                  //////////
                poplay.addView(allfillpicview);

            }//on click close



        });////listner close



   /////////////pra close
        ///fill gradiant

        ////////////
        Button grdbtn=clrview.findViewById(R.id.grdbtn);
        grdbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                poplay.removeView(clrview);
                poplay.removeView(allgradclr);

                final List<GradiantColor> list =new ArrayList<>();


                list.add(new GradiantColor(Color.BLUE,Color.WHITE));
                list.add(new GradiantColor(Color.BLACK,Color.WHITE));
                list.add(new GradiantColor(Color.RED,Color.WHITE));
                list.add(new GradiantColor(Color.GREEN,Color.WHITE));
                list.add(new GradiantColor(Color.RED,Color.BLUE));
                list.add(new GradiantColor(Color.YELLOW,Color.BLUE));



                allgradclr=getLayoutInflater().inflate(R.layout.all_gradiant_clr,null,false);
                GridView clrgv=allgradclr.findViewById(R.id.gradClrgv);
                CustomGradclr adp =new CustomGradclr(list);
                clrgv.setAdapter(adp);

                clrgv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {

                        //Shader textShade;
                        clrflag=0;
                        grdflag=1;
                        //Prtname.remove_eff();
                        ///////////////////change
                        //Prtname.brd_clr_G_fill(color,firtgrd_clr,secgrd_clr);
                        //String df = Prtname.getText().toString();
                        //Prtname.setText(df);
                        if(flag1==1)
                        {  f_clr=0;
                          f_grd=1;
                            Prtname.setFgradClr(list.get(position).getFcolor());
                            Prtname.setSgradClr(list.get(position).getScolor());
                            /////////change close
                            firtgrd_clr = list.get(position).getFcolor();
                            secgrd_clr = list.get(position).getScolor();
                            Prtname.getPaint().setShader(null);
                            Prtname.setTextColor(Color.RED);
                            Shader textShader;
                            if(position%2==0)
                            {
                                 textShader =new RadialGradient(0f, 0f, 4f,  list.get(position).getFcolor(), list.get(position).getScolor(), Shader.TileMode.CLAMP) ;
                            }
                             else
                                  textShader = new LinearGradient(0f, 0f, 0f, Prtname.getTextSize(), list.get(position).getFcolor(), list.get(position).getScolor(), Shader.TileMode.CLAMP);

                            Prtname.getPaint().setShader(textShader);
                            Prtname.setTextColor(Color.parseColor("#F97C3C"));
                        }else if(flag2==2)
                        {
                            s_clr=0;
                            s_grd=1;
                            Prtname2.setFgradClr(list.get(position).getFcolor());
                            Prtname2.setSgradClr(list.get(position).getScolor());
                            /////////change
                           // firtgrd_clr = list.get(position).getFcolor();
                           // secgrd_clr = list.get(position).getScolor();
                            ///// close
                            s_firtgrd_clr = list.get(position).getFcolor();
                            s_secgrd_clr = list.get(position).getScolor();


                            Prtname2.getPaint().setShader(null);
                            Prtname2.setTextColor(Color.RED);
                            //float width = paint.measureText("world");
                            //Prtname.getPaint().setShader(null);
                            Shader textShader = new LinearGradient(0f, 0f, 0f, Prtname2.getTextSize(), list.get(position).getFcolor(), list.get(position).getScolor(), Shader.TileMode.CLAMP);

                            Prtname2.getPaint().setShader(textShader);
                            Prtname2.setTextColor(Color.parseColor("#F97C3C"));

                        }else
                        {
                            Toast.makeText(Editp.this,"first select Text box",Toast.LENGTH_LONG).show();
                        }




                     /* Shader textShader = new LinearGradient(0, 0, width, textView.getTextSize(),
                new int[]{
                        Color.parseColor("#F97C3C"),
                        Color.parseColor("#FDB54E"),
                        Color.parseColor("#64B678"),
                        Color.parseColor("#478AEA"),
                        Color.parseColor("#8446CC"),
                }, null, Shader.TileMode.CLAMP);

                      */


                    }//onitemclick close
                });



                poplay.addView(allgradclr);

            }
        });///list close
        /////////////gradiant close
        poplay.addView(clrview);
    }
    ////////////////for fill pic
    class CustomFillPic extends BaseAdapter
    {
        int[] fillpics;

        public CustomFillPic(int[] fillpics) {
            this.fillpics = fillpics;
        }

        @Override
        public int getCount() {
            return fillpics.length;
        }

        @Override
        public Object getItem(int position) {
            return fillpics[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {   View vv =getLayoutInflater().inflate(R.layout.fill_pic_gv_design,parent,false);
            ImageView imgbox= vv.findViewById(R.id.textureiv);
            imgbox.setImageResource(fillpics[position]);
            return vv;
        }
    }//adpter close


    //////////////for fill pic close


    /////////////////////add color close
    //////////////////////
class CustomGradclr extends BaseAdapter
    {
        private List<GradiantColor> llist;

        public CustomGradclr(List<GradiantColor> llist) {
            this.llist = llist;
        }

        @Override
        public int getCount() {
            return llist.size();
        }

        @Override
        public Object getItem(int position) {
            return llist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View vv =getLayoutInflater().inflate(R.layout.all_gradiant_clr_design,parent,false);
            TextView grdbox= vv.findViewById(R.id.textView20);
            //grdbox.setBackgroundColor(llist.get(position));
            int []colors={llist.get(position).getFcolor(),llist.get(position).getScolor()};
            GradientDrawable gradientDrawable =new GradientDrawable();
            gradientDrawable.setShape(GradientDrawable.RECTANGLE);
            gradientDrawable.setColors(colors);
            gradientDrawable.setStroke(3,Color.BLACK);
            grdbox.setBackground(gradientDrawable);

            return vv;
        }
    }

//////////////////////adpter close

    private void openColorPickerDialogue(final int i)
    {
        final AmbilWarnaDialog colorPickerDialogue = new AmbilWarnaDialog(this, mDefaultColor,
                new AmbilWarnaDialog.OnAmbilWarnaListener() {


                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {

                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color)
                    {
                        //mDefaultColor = color;

                        // now change the picked color
                        // preview box to mDefaultColor
                        // Prtname.setTextColor(mDefaultColor);
                        if(flag1==1)
                        {
                         if (i == 1)//fill text clr
                          {
                            clrflag=1;
                            grdflag=0;
                            f_clr=1;
                            f_grd=0;
                            Prtname.setIsGrd_fill(0);
                           // Prtname.remove_eff();
                            Prtname.getPaint().setShader(null);

                            Prtname.setTextColor(color);
                            String df = Prtname.getText().toString();
                            Prtname.setText(df);
                          }
                          if (i == 2)//set border clr
                          {
                             /* if(clrflag==1)
                            {
                                Prtname.brd_clr_C_fill(color);
                                String df = Prtname.getText().toString();
                                Prtname.setText(df);
                                Toast.makeText(Editp.this,"clr run",Toast.LENGTH_LONG).show();
                            }else if (grdflag==1)
                            {
                                Prtname.brd_clr_G_fill(color,firtgrd_clr,secgrd_clr);
                                String df = Prtname.getText().toString();
                                Prtname.setText(df);
                                Toast.makeText(Editp.this,"ggrad run",Toast.LENGTH_LONG).show();

                            }*/
                              if(f_clr==1)
                              {
                                  Prtname.brd_clr_C_fill(color);
                                  String df = Prtname.getText().toString();
                                  Prtname.setText(df);
                                  //Toast.makeText(Editp.this,"clr run",Toast.LENGTH_LONG).show();
                              }else if (f_grd==1)
                              {
                                  Prtname.brd_clr_G_fill(color,firtgrd_clr,secgrd_clr);
                                  String df = Prtname.getText().toString();
                                  Prtname.setText(df);
                                  //Toast.makeText(Editp.this,"ggrad run",Toast.LENGTH_LONG).show();

                              }



                          }
                            if (i == 3)//set shadow layer
                        {
                            shadcolor = color;
                            Prtname.setShadowLayer(radval, xval, yval, shadcolor);
                        }
                      }else if (flag2==2)
                        {
                            if (i == 1)//fill text clr
                            {
                                clrflag=1;
                                grdflag=0;
                                s_clr=1;
                                s_grd=0;
                                Prtname2.setIsGrd_fill(0);
                                // Prtname.remove_eff();
                                Prtname2.getPaint().setShader(null);

                                Prtname2.setTextColor(color);
                                String df = Prtname2.getText().toString();
                                Prtname2.setText(df);
                            }
                            if (i == 2)//set border clr
                            {
                                /*if(clrflag==1)
                              {
                                Prtname2.brd_clr_C_fill(color);
                                String df = Prtname2.getText().toString();
                                Prtname2.setText(df);
                                  Toast.makeText(Editp.this,"clr run",Toast.LENGTH_LONG).show();

                              }else if (grdflag==1)
                                {
                                Prtname2.brd_clr_G_fill(color,firtgrd_clr,secgrd_clr);
                                String df = Prtname2.getText().toString();
                                Prtname2.setText(df);
                                    Toast.makeText(Editp.this,"grdd run",Toast.LENGTH_LONG).show();

                                }

                                 */
                                if(s_clr==1)
                                {
                                    Prtname2.brd_clr_C_fill(color);
                                    String df = Prtname2.getText().toString();
                                    Prtname2.setText(df);
                                    //Toast.makeText(Editp.this,"clr run",Toast.LENGTH_LONG).show();

                                }else if (s_grd==1)
                                {
                                    Prtname2.brd_clr_G_fill(color,s_firtgrd_clr,s_secgrd_clr);
                                    String df = Prtname2.getText().toString();
                                    Prtname2.setText(df);
                                    //Toast.makeText(Editp.this,"grdd run",Toast.LENGTH_LONG).show();

                                }



                            }
                            if (i == 3)
                            {
                                shadcolor = color;
                                Prtname2.setShadowLayer(radval, xval, yval, shadcolor);
                            }



                        }else
                            Toast.makeText(getApplicationContext(),"first Select Text box",Toast.LENGTH_LONG).show();
                        //Prtname.setAlpha(.5f);
                    }
                });
        colorPickerDialogue.show();
    }
    /////////////////////////////

    public void setfont(View view)
    {
        poplay.setVisibility(View.VISIBLE);
        poplay.removeView(clrview);
        poplay.removeView(sizeview);
        poplay.removeView(wordspaceview);
        poplay.removeView(shadowview);
        poplay.removeView(fontview);
        poplay.removeView(bckClrview);
        poplay.removeView(allgradclr);
        poplay.removeView(allbrdopacty);
        poplay.removeView(allfillpicview);


        fontview=getLayoutInflater().inflate(R.layout.set_font,null,false);

        GridView fontgv=fontview.findViewById(R.id.fontgv);
       // final String  path="font.otf"; "DreamHerRegular-lalw.ttf";

///src of data
        final String [] fontname={"AwareBold-qZo3x.ttf","Emilio20-A5G7.ttf","Royal2-MOXe.ttf","EvilEmpire-4BBVK.ttf","Jfsnowbiz-47mD.ttf", "PreciousMoments-47M6.ttf","Foglihtenno04-aM2E.otf","TribalTwo-WR4.ttf","Moteefe-lgL5q.ttf","Anglicantext-owZo.ttf","RazzleDazzle-nRDBR.ttf","Library3amsoft-6zgq.otf"
               ,"AAntiCorona-PKZx7.otf","TeamSpiritNf-V8B6.ttf","EgyptienneZierinitialien-Rpj6.ttf","Flamer-AL8YA.ttf","Gardeniavictorian-v2r9.ttf","CastIron-MBEr.ttf","EdgeOfTheGalaxyRegular-OVEa6.otf",
                "StarJediOutline-y0xm.ttf","GallagherGallagher-l31d.ttf","Woodlook-nvyP.ttf","ActionIsShadedJl-y7a5.ttf","WildWestUsa-OYrp.ttf","Carmencita-KO5y.otf","NeonFeel-YxA2.ttf","Arizonia-mV7G.ttf","Monoton-RXOM.ttf","Portabell-15ZB.ttf","Ampad3DRegular-qpl1.otf","Prida65-P1ex.otf","Metalover-wP5P.ttf","ApexlakeRegular-KYGy.otf",
                "AgendaDuDirecteur-ye32.ttf"};
   //////adpter
        FontAdpt adp =new FontAdpt(fontname);
        fontgv.setAdapter(adp);
/////////////////////////
        fontgv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Typeface typeface = Typeface.createFromAsset(getAssets(),fontname[position]);
               if(flag1==1)
                Prtname.setTypeface(typeface);
               else if (flag2==2)
                   Prtname2.setTypeface(typeface);
               else
                   Toast.makeText(Editp.this,"First Select Text View",Toast.LENGTH_LONG).show();
            }
        });

    ////////////
         poplay.addView(fontview);
    }
//////////////////////////////////////////////////font close
    class FontAdpt extends BaseAdapter
{
    String [] fontnamearr;

    public FontAdpt(String[] fontnamearr) {
        this.fontnamearr = fontnamearr;
    }

    @Override
    public int getCount() {
        return fontnamearr.length;
    }

    @Override
    public Object getItem(int position) {
        return fontnamearr[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vv= getLayoutInflater().inflate(R.layout.font_gv_design,parent,false);
        TextView fontbox=vv.findViewById(R.id.textView21);
        Typeface typeface = Typeface.createFromAsset(getAssets(),fontnamearr[position]);
        fontbox.setTypeface(typeface);
        return vv;
    }
}




   /////font adpter close
    public void setborder(View view)
    {
        poplay.setVisibility(View.VISIBLE);
        poplay.removeView(clrview);
        poplay.removeView(sizeview);
        poplay.removeView(wordspaceview);
        poplay.removeView(shadowview);
        poplay.removeView(fontview);
        poplay.removeView(bckClrview);
        poplay.removeView(allgradclr);
        poplay.removeView(allbrdopacty);
        poplay.removeView(allfillpicview);


        bckClrview =getLayoutInflater().inflate(R.layout.text_bckclr,null,false);

        TextView setclr= bckClrview.findViewById(R.id.textView18);
        ////


        ////
        TextView bopacity= bckClrview.findViewById(R.id.textView19);
////////////////        ///clr for border



        setclr.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                openColorPickerDialogue(2);
            }
        });
/////////////////////
        bopacity.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                poplay.removeView(bckClrview);
                poplay.removeView(allbrdopacty);

                allbrdopacty=getLayoutInflater().inflate(R.layout.text_brd_opacty,null,false);
                //GridView clrgv=allbrdopacty.findViewById(R.id.gradClrgv
               //////////remove brd effect
                TextView removebrd= allbrdopacty.findViewById(R.id.textView0);
                removebrd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                            if(f_clr==1)
                            {
                                Prtname.brd_clr_C_fill(Color.TRANSPARENT);
                                String df = Prtname.getText().toString();
                                Prtname.setText(df);
                                //Toast.makeText(Editp.this,"clr run",Toast.LENGTH_LONG).show();
                            }else if (f_grd==1)
                            {
                                Prtname.brd_clr_G_fill(Color.TRANSPARENT,firtgrd_clr,secgrd_clr);
                                String df = Prtname.getText().toString();
                                Prtname.setText(df);
                                //Toast.makeText(Editp.this,"ggrad run",Toast.LENGTH_LONG).show();

                            }

                    }
                });






                //////////////////



                TextView fst= allbrdopacty.findViewById(R.id.textView6);
                fst.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                      if(flag1==1)
                      {
                          /*if (clrflag == 1) {
                              Prtname.brd_width_C_fill(2);
                              String df = Prtname.getText().toString();
                              Prtname.setText(df);
                          } else if (grdflag == 1) {
                              Prtname.brd_width_G_fill(2);
                              String df = Prtname.getText().toString();
                              Prtname.setText(df);
                          }

                           */
                          if (f_clr == 1) {
                              Prtname.brd_width_C_fill(2);
                              String df = Prtname.getText().toString();
                              Prtname.setText(df);
                          } else if (f_grd == 1) {
                              Prtname.brd_width_G_fill(2,firtgrd_clr,secgrd_clr);
                              String df = Prtname.getText().toString();
                              Prtname.setText(df);
                          }
                      }else if(flag2==2)
                      {
                          /*if (clrflag == 1) {
                              Prtname2.brd_width_C_fill(2);
                              String df = Prtname2.getText().toString();
                              Prtname2.setText(df);
                          } else if (grdflag == 1) {
                              Prtname2.brd_width_G_fill(2);
                              String df = Prtname2.getText().toString();
                              Prtname2.setText(df);
                          }

                           */
                          if (s_clr == 1) {
                              Prtname2.brd_width_C_fill(2);
                              String df = Prtname2.getText().toString();
                              Prtname2.setText(df);
                          } else if (s_grd== 1) {
                              Prtname2.brd_width_G_fill(2,s_firtgrd_clr,s_secgrd_clr);
                              String df = Prtname2.getText().toString();
                              Prtname2.setText(df);
                          }

                      }else
                          {
                         Toast.makeText(Editp.this,"First Select Text View",Toast.LENGTH_LONG).show();
                         }

                    }
                });

                TextView sec= allbrdopacty.findViewById(R.id.textView7);
                sec.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(flag1==1)
                        {
                          /*if (clrflag == 1) {
                              Prtname.brd_width_C_fill(2);
                              String df = Prtname.getText().toString();
                              Prtname.setText(df);
                          } else if (grdflag == 1) {
                              Prtname.brd_width_G_fill(2);
                              String df = Prtname.getText().toString();
                              Prtname.setText(df);
                          }

                           */
                            if (f_clr == 1) {
                                Prtname.brd_width_C_fill(6);
                                String df = Prtname.getText().toString();
                                Prtname.setText(df);
                            } else if (f_grd == 1) {
                                Prtname.brd_width_G_fill(6,firtgrd_clr,secgrd_clr);
                                String df = Prtname.getText().toString();
                                Prtname.setText(df);
                            }
                        }else if(flag2==2)
                        {
                          /*if (clrflag == 1) {
                              Prtname2.brd_width_C_fill(2);
                              String df = Prtname2.getText().toString();
                              Prtname2.setText(df);
                          } else if (grdflag == 1) {
                              Prtname2.brd_width_G_fill(2);
                              String df = Prtname2.getText().toString();
                              Prtname2.setText(df);
                          }

                           */
                            if (s_clr == 1) {
                                Prtname2.brd_width_C_fill(6);
                                String df = Prtname2.getText().toString();
                                Prtname2.setText(df);
                            } else if (s_grd== 1) {
                                Prtname2.brd_width_G_fill(6,s_firtgrd_clr,s_secgrd_clr);
                                String df = Prtname2.getText().toString();
                                Prtname2.setText(df);
                            }

                        }else
                        {
                            Toast.makeText(Editp.this,"First Select Text View",Toast.LENGTH_LONG).show();
                        }

                        /////
                    }
                });
                TextView th= allbrdopacty.findViewById(R.id.textView8);
                th.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(flag1==1)
                        {
                            if (f_clr == 1) {
                                Prtname.brd_width_C_fill(8);
                                String df = Prtname.getText().toString();
                                Prtname.setText(df);
                            } else if (f_grd == 1) {
                                Prtname.brd_width_G_fill(8,firtgrd_clr,secgrd_clr);
                                String df = Prtname.getText().toString();
                                Prtname.setText(df);
                            }
                        }else if(flag2==2)
                        {

                            if (s_clr == 1) {
                                Prtname2.brd_width_C_fill(8);
                                String df = Prtname2.getText().toString();
                                Prtname2.setText(df);
                            } else if (s_grd== 1) {
                                Prtname2.brd_width_G_fill(8,s_firtgrd_clr,s_secgrd_clr);
                                String df = Prtname2.getText().toString();
                                Prtname2.setText(df);
                            }

                        }else
                        {
                            Toast.makeText(Editp.this,"First Select Text View",Toast.LENGTH_LONG).show();
                        }

                        //////
                    }
                });
                TextView fur= allbrdopacty.findViewById(R.id.textView9);
                fur.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(flag1==1)
                        {
                            if (f_clr == 1) {
                                Prtname.brd_width_C_fill(10);
                                String df = Prtname.getText().toString();
                                Prtname.setText(df);
                            } else if (f_grd == 1) {
                                Prtname.brd_width_G_fill(10,firtgrd_clr,secgrd_clr);
                                String df = Prtname.getText().toString();
                                Prtname.setText(df);
                            }
                        }else if(flag2==2)
                        {

                            if (s_clr == 1) {
                                Prtname2.brd_width_C_fill(10);
                                String df = Prtname2.getText().toString();
                                Prtname2.setText(df);
                            } else if (s_grd== 1) {
                                Prtname2.brd_width_G_fill(10,s_firtgrd_clr,s_secgrd_clr);
                                String df = Prtname2.getText().toString();
                                Prtname2.setText(df);
                            }

                        }else
                        {
                            Toast.makeText(Editp.this,"First Select Text View",Toast.LENGTH_LONG).show();
                        }

                        //////
                    }
                });

                TextView fiv= allbrdopacty.findViewById(R.id.textView10);
                fiv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(flag1==1)
                        {
                            if (f_clr == 1) {
                                Prtname.brd_width_C_fill(12);
                                String df = Prtname.getText().toString();
                                Prtname.setText(df);
                            } else if (f_grd == 1) {
                                Prtname.brd_width_G_fill(12,firtgrd_clr,secgrd_clr);
                                String df = Prtname.getText().toString();
                                Prtname.setText(df);
                            }
                        }else if(flag2==2)
                        {

                            if (s_clr == 1) {
                                Prtname2.brd_width_C_fill(12);
                                String df = Prtname2.getText().toString();
                                Prtname2.setText(df);
                            } else if (s_grd== 1) {
                                Prtname2.brd_width_G_fill(12,s_firtgrd_clr,s_secgrd_clr);
                                String df = Prtname2.getText().toString();
                                Prtname2.setText(df);
                            }

                        }else
                        {
                            Toast.makeText(Editp.this,"First Select Text View",Toast.LENGTH_LONG).show();
                        }

                        //////
                    }
                });
                TextView six= allbrdopacty.findViewById(R.id.textView11);
                six.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(flag1==1)
                        {
                            if (f_clr == 1) {
                                Prtname.brd_width_C_fill(14);
                                String df = Prtname.getText().toString();
                                Prtname.setText(df);
                            } else if (f_grd == 1) {
                                Prtname.brd_width_G_fill(14,firtgrd_clr,secgrd_clr);
                                String df = Prtname.getText().toString();
                                Prtname.setText(df);
                            }
                        }else if(flag2==2)
                        {

                            if (s_clr == 1) {
                                Prtname2.brd_width_C_fill(14);
                                String df = Prtname2.getText().toString();
                                Prtname2.setText(df);
                            } else if (s_grd== 1) {
                                Prtname2.brd_width_G_fill(14,s_firtgrd_clr,s_secgrd_clr);
                                String df = Prtname2.getText().toString();
                                Prtname2.setText(df);
                            }

                        }else
                        {
                            Toast.makeText(Editp.this,"First Select Text View",Toast.LENGTH_LONG).show();
                        }

                        //////
                    }
                });

                //////////////
                poplay.addView(allbrdopacty);
            }
        });
///////////////            /////////////opacity listner close

       poplay.addView(bckClrview);
    }

    //////////////////////////////////////////bck color close

    public void wordspace(View view)
    {
        poplay.setVisibility(View.VISIBLE);
        poplay.removeView(clrview);
        poplay.removeView(sizeview);
        poplay.removeView(wordspaceview);
        poplay.removeView(shadowview);
        poplay.removeView(fontview);
        poplay.removeView(bckClrview);
        poplay.removeView(allgradclr);
        poplay.removeView(allbrdopacty);
        poplay.removeView(allfillpicview);



        wordspaceview =getLayoutInflater().inflate(R.layout.word_space,null,false);

       final TextView fst= wordspaceview.findViewById(R.id.textView6);
       fst.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


               if(flag1==1)
               Prtname.setLetterSpacing(.0f);
               else if(flag2==2)
                   Prtname2.setLetterSpacing(.0f);
               else
                   Toast.makeText(Editp.this,"First Select Text View",Toast.LENGTH_LONG).show();


           }
       });

        TextView sec= wordspaceview.findViewById(R.id.textView7);
        sec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag1==1)
                    Prtname.setLetterSpacing(.05f);
                else if(flag2==2)
                    Prtname2.setLetterSpacing(.05f);
                else
                    Toast.makeText(Editp.this,"First Select Text View",Toast.LENGTH_LONG).show();

            }
        });
        TextView th= wordspaceview.findViewById(R.id.textView8);
        th.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag1==1)
                    Prtname.setLetterSpacing(.08f);
                else if(flag2==2)
                    Prtname2.setLetterSpacing(.08f);
                else
                    Toast.makeText(Editp.this,"First Select Text View",Toast.LENGTH_LONG).show();

            }
        });
        TextView fur= wordspaceview.findViewById(R.id.textView9);
        fur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag1==1)
                    Prtname.setLetterSpacing(.1f);
                else if(flag2==2)
                    Prtname2.setLetterSpacing(.1f);
                else
                    Toast.makeText(Editp.this,"First Select Text View",Toast.LENGTH_LONG).show();

            }
        });
        TextView fiv= wordspaceview.findViewById(R.id.textView10);
        fiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag1==1)
                    Prtname.setLetterSpacing(.2f);
                else if(flag2==2)
                    Prtname2.setLetterSpacing(.2f);
                else
                    Toast.makeText(Editp.this,"First Select Text View",Toast.LENGTH_LONG).show();

            }
        });
        TextView six= wordspaceview.findViewById(R.id.textView11);
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag1==1)
                    Prtname.setLetterSpacing(.3f);
                else if(flag2==2)
                    Prtname2.setLetterSpacing(.3f);
                else
                    Toast.makeText(Editp.this,"First Select Text View",Toast.LENGTH_LONG).show();

            }
        });


     poplay.addView(wordspaceview);
    }
///////////////////////word space close

    public void shadow(View view)
    {
        poplay.setVisibility(View.VISIBLE);
        poplay.removeView(clrview);
        poplay.removeView(sizeview);
        poplay.removeView(wordspaceview);
        poplay.removeView(shadowview);
        poplay.removeView(fontview);
        poplay.removeView(bckClrview);
        poplay.removeView(allgradclr);
        poplay.removeView(allbrdopacty);
        poplay.removeView(allfillpicview);




        shadowview =getLayoutInflater().inflate(R.layout.text_shadow,null,false);
     final TextView delall=shadowview.findViewById(R.id.textView13);
     //////remove all
        delall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               if(flag1==1)
                Prtname.setShadowLayer(0f,0f,0f, Color.RED);
               else if(flag2==2)
                   Prtname2.setShadowLayer(0f,0f,0f, Color.RED);
               else
                   Toast.makeText(Editp.this,"First Select Text View",Toast.LENGTH_LONG).show();

            }
        });

        ///close remove all
     TextView setclr=shadowview.findViewById(R.id.textView12);

    /////////for clor
        setclr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
              openColorPickerDialogue(3);
            }
        });
   ///////color close
     SeekBar xsh=shadowview.findViewById(R.id.seekBar);
     SeekBar ysh=shadowview.findViewById(R.id.seekBar2);
     SeekBar rad=shadowview.findViewById(R.id.seekBar3);
     ////////////////
        rad.setProgress(5);
        xsh.setProgress(6);
        ysh.setProgress(7);
     //////////////

      rad.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
          @Override
          public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
          {

            radval=(float)progress;
             if(flag1==1)
              Prtname.setShadowLayer(radval,xval,yval, shadcolor);
             else if (flag2==2)
                 Prtname2.setShadowLayer(radval,xval,yval, shadcolor);
             else
                 Toast.makeText(Editp.this,"First Select Text View",Toast.LENGTH_LONG).show();


          }

          @Override
          public void onStartTrackingTouch(SeekBar seekBar) {

          }

          @Override
          public void onStopTrackingTouch(SeekBar seekBar) {

          }
      });
///////////////
        xsh.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
            xval=(float)progress;
            if(flag1==1)
                Prtname.setShadowLayer(radval,xval,yval, shadcolor);
            else if (flag2==2)
                Prtname2.setShadowLayer(radval,xval,yval, shadcolor);
            else
                Toast.makeText(Editp.this,"First Select Text View",Toast.LENGTH_LONG).show();


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
//////////////////////////
        ysh.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                yval=(float)progress;
                if(flag1==1)
                Prtname.setShadowLayer(radval,xval,yval,shadcolor);
                else if(flag2==2)
                    Prtname2.setShadowLayer(radval,xval,yval,shadcolor);
                else
                    Toast.makeText(Editp.this,"First Select Text View",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
/////////////////////////////////

     poplay.addView(shadowview);

    }
}//main close
