package com.play.edit_dp_and_post.editpost_dp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private Button cp, recent, cpstatus, girldp, girlatt, boydp, boyatt, bithday, sadgirl, sadboy, god, motiv, goodmor;
    private long back_pressed;

    private AdView mbannerAdview;
    public String applink=null;
    private FirebaseFirestore db;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        applink="https://play.google.com/store/apps/details?id=" + getPackageName();
        /////for add
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });
        ////////////////////////////////////
        connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();


        if (!(networkInfo != null && networkInfo.isConnected())) {
            //connection hai

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Network Problem...");

            builder.setMessage("Check Your Network Connection...");

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }//postive btn on click close
            });
              builder.setCancelable(true);
             builder.show();
         ////////////////---------------------
        }

        //////////////////////////////////networl issue close
        /*db = FirebaseFirestore.getInstance();
        db.collection("Link")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("anil", document.getId() + " => " + document.getData());
                                 applink= (String) document.get("linkval");
                                 Log.d("anil","link is->"+applink);
                            }
                        } else {
                            Log.w("anil", "Error getting documents.", task.getException());
                        }
                    }
                });


         */

        //////////////////////////

        ////////banner add

        mbannerAdview =findViewById(R.id.home_banner_adview);
        AdRequest adRequest = new AdRequest.Builder().build();
        mbannerAdview.loadAd(adRequest);
                //////////

                cp = findViewById(R.id.couple_dp);
        recent = findViewById(R.id.recent);
        cpstatus = findViewById(R.id.couple_status);
        girldp = findViewById(R.id.girl_dp);

        girlatt = findViewById(R.id.girlatt);
        boydp = findViewById(R.id.boy_dp);

        boyatt = findViewById(R.id.boyatt);
        bithday = findViewById(R.id.birthday);
        sadgirl = findViewById(R.id.sadgirl);
        sadboy = findViewById(R.id.sadboy);
        god = findViewById(R.id.god);
        motiv = findViewById(R.id.motivation);
        goodmor = findViewById(R.id.goodmor);

//for couple
        cp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cp.setBackgroundColor(Color.WHITE);
                cpstatus.setBackgroundColor(Color.YELLOW);
                recent.setBackgroundColor(Color.YELLOW);

                girldp.setBackgroundColor(Color.YELLOW);
                girlatt.setBackgroundColor(Color.YELLOW);
                boydp.setBackgroundColor(Color.YELLOW);
                boyatt.setBackgroundColor(Color.YELLOW);
                bithday.setBackgroundColor(Color.YELLOW);
                sadgirl.setBackgroundColor(Color.YELLOW);
                sadboy.setBackgroundColor(Color.YELLOW);
                god.setBackgroundColor(Color.YELLOW);
                motiv.setBackgroundColor(Color.YELLOW);
                goodmor.setBackgroundColor(Color.YELLOW);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new Couple_Dp()).commit();
            }
        });

        ///
        cpstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cp.setBackgroundColor(Color.YELLOW);
                cpstatus.setBackgroundColor(Color.WHITE);
                recent.setBackgroundColor(Color.YELLOW);

                girldp.setBackgroundColor(Color.YELLOW);
                girlatt.setBackgroundColor(Color.YELLOW);
                boydp.setBackgroundColor(Color.YELLOW);
                boyatt.setBackgroundColor(Color.YELLOW);
                bithday.setBackgroundColor(Color.YELLOW);
                sadgirl.setBackgroundColor(Color.YELLOW);
                sadboy.setBackgroundColor(Color.YELLOW);
                god.setBackgroundColor(Color.YELLOW);
                motiv.setBackgroundColor(Color.YELLOW);
                goodmor.setBackgroundColor(Color.YELLOW);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new Couple_status()).commit();
            }
        });
        ////fun time
        recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cp.setBackgroundColor(Color.YELLOW);
                cpstatus.setBackgroundColor(Color.YELLOW);
                recent.setBackgroundColor(Color.WHITE);

                girldp.setBackgroundColor(Color.YELLOW);
                girlatt.setBackgroundColor(Color.YELLOW);
                boydp.setBackgroundColor(Color.YELLOW);
                boyatt.setBackgroundColor(Color.YELLOW);
                bithday.setBackgroundColor(Color.YELLOW);
                sadgirl.setBackgroundColor(Color.YELLOW);
                sadboy.setBackgroundColor(Color.YELLOW);
                god.setBackgroundColor(Color.YELLOW);
                motiv.setBackgroundColor(Color.YELLOW);
                goodmor.setBackgroundColor(Color.YELLOW);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new Recent()).commit();
            }
        });
        ///
        girldp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cp.setBackgroundColor(Color.YELLOW);
                cpstatus.setBackgroundColor(Color.YELLOW);
                recent.setBackgroundColor(Color.YELLOW);

                girldp.setBackgroundColor(Color.WHITE);
                girlatt.setBackgroundColor(Color.YELLOW);
                boydp.setBackgroundColor(Color.YELLOW);
                boyatt.setBackgroundColor(Color.YELLOW);
                bithday.setBackgroundColor(Color.YELLOW);
                sadgirl.setBackgroundColor(Color.YELLOW);
                sadboy.setBackgroundColor(Color.YELLOW);
                god.setBackgroundColor(Color.YELLOW);
                motiv.setBackgroundColor(Color.YELLOW);
                goodmor.setBackgroundColor(Color.YELLOW);


                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new Girl_Dp()).commit();
            }
        });
        ///


        //for girl att
        girlatt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cp.setBackgroundColor(Color.YELLOW);
                cpstatus.setBackgroundColor(Color.YELLOW);
                recent.setBackgroundColor(Color.YELLOW);

                girldp.setBackgroundColor(Color.YELLOW);
                girlatt.setBackgroundColor(Color.WHITE);
                boydp.setBackgroundColor(Color.YELLOW);
                boyatt.setBackgroundColor(Color.YELLOW);
                bithday.setBackgroundColor(Color.YELLOW);
                sadgirl.setBackgroundColor(Color.YELLOW);
                sadboy.setBackgroundColor(Color.YELLOW);
                god.setBackgroundColor(Color.YELLOW);
                motiv.setBackgroundColor(Color.YELLOW);
                goodmor.setBackgroundColor(Color.YELLOW);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new Girl_Att()).commit();
            }
        });
////
        boydp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cp.setBackgroundColor(Color.YELLOW);
                cpstatus.setBackgroundColor(Color.YELLOW);
                recent.setBackgroundColor(Color.YELLOW);

                girldp.setBackgroundColor(Color.YELLOW);
                girlatt.setBackgroundColor(Color.YELLOW);
                boydp.setBackgroundColor(Color.WHITE);
                boyatt.setBackgroundColor(Color.YELLOW);
                bithday.setBackgroundColor(Color.YELLOW);
                sadgirl.setBackgroundColor(Color.YELLOW);
                sadboy.setBackgroundColor(Color.YELLOW);
                god.setBackgroundColor(Color.YELLOW);
                motiv.setBackgroundColor(Color.YELLOW);
                goodmor.setBackgroundColor(Color.YELLOW);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new Boy_Dp()).commit();
            }
        });

        //for boy att
        boyatt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cp.setBackgroundColor(Color.YELLOW);
                cpstatus.setBackgroundColor(Color.YELLOW);
                recent.setBackgroundColor(Color.YELLOW);

                girldp.setBackgroundColor(Color.YELLOW);
                girlatt.setBackgroundColor(Color.YELLOW);
                boydp.setBackgroundColor(Color.YELLOW);
                boyatt.setBackgroundColor(Color.WHITE);
                bithday.setBackgroundColor(Color.YELLOW);
                sadgirl.setBackgroundColor(Color.YELLOW);
                sadboy.setBackgroundColor(Color.YELLOW);
                god.setBackgroundColor(Color.YELLOW);
                motiv.setBackgroundColor(Color.YELLOW);
                goodmor.setBackgroundColor(Color.YELLOW);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new Boy_Att()).commit();
            }
        });
//for birthday
        bithday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cp.setBackgroundColor(Color.YELLOW);
                cpstatus.setBackgroundColor(Color.YELLOW);
                recent.setBackgroundColor(Color.YELLOW);

                girldp.setBackgroundColor(Color.YELLOW);
                girlatt.setBackgroundColor(Color.YELLOW);
                boydp.setBackgroundColor(Color.YELLOW);
                boyatt.setBackgroundColor(Color.YELLOW);
                bithday.setBackgroundColor(Color.WHITE);
                sadgirl.setBackgroundColor(Color.YELLOW);
                sadboy.setBackgroundColor(Color.YELLOW);
                god.setBackgroundColor(Color.YELLOW);
                motiv.setBackgroundColor(Color.YELLOW);
                goodmor.setBackgroundColor(Color.YELLOW);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new BirthDay()).commit();
            }
        });
        //for sad girl
        sadgirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cp.setBackgroundColor(Color.YELLOW);
                cpstatus.setBackgroundColor(Color.YELLOW);
                recent.setBackgroundColor(Color.YELLOW);

                girldp.setBackgroundColor(Color.YELLOW);
                girlatt.setBackgroundColor(Color.YELLOW);
                boydp.setBackgroundColor(Color.YELLOW);
                boyatt.setBackgroundColor(Color.YELLOW);
                bithday.setBackgroundColor(Color.YELLOW);
                sadgirl.setBackgroundColor(Color.WHITE);
                sadboy.setBackgroundColor(Color.YELLOW);
                god.setBackgroundColor(Color.YELLOW);
                motiv.setBackgroundColor(Color.YELLOW);
                goodmor.setBackgroundColor(Color.YELLOW);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new Sad_girl()).commit();
            }
        });

        //for sad boy
        sadboy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cp.setBackgroundColor(Color.YELLOW);
                cpstatus.setBackgroundColor(Color.YELLOW);
                recent.setBackgroundColor(Color.YELLOW);

                girldp.setBackgroundColor(Color.YELLOW);
                girlatt.setBackgroundColor(Color.YELLOW);
                boydp.setBackgroundColor(Color.YELLOW);
                boyatt.setBackgroundColor(Color.YELLOW);
                bithday.setBackgroundColor(Color.YELLOW);
                sadgirl.setBackgroundColor(Color.YELLOW);
                sadboy.setBackgroundColor(Color.WHITE);
                god.setBackgroundColor(Color.YELLOW);
                motiv.setBackgroundColor(Color.YELLOW);
                goodmor.setBackgroundColor(Color.YELLOW);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new Sad_Boy()).commit();
            }
        });
        //for god
        god.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cp.setBackgroundColor(Color.YELLOW);
                cpstatus.setBackgroundColor(Color.YELLOW);
                recent.setBackgroundColor(Color.YELLOW);

                girldp.setBackgroundColor(Color.YELLOW);
                girlatt.setBackgroundColor(Color.YELLOW);
                boydp.setBackgroundColor(Color.YELLOW);
                boyatt.setBackgroundColor(Color.YELLOW);
                bithday.setBackgroundColor(Color.YELLOW);
                sadgirl.setBackgroundColor(Color.YELLOW);
                sadboy.setBackgroundColor(Color.YELLOW);
                god.setBackgroundColor(Color.WHITE);
                motiv.setBackgroundColor(Color.YELLOW);
                goodmor.setBackgroundColor(Color.YELLOW);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new God()).commit();
            }
        });
        //for motivation
        motiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cp.setBackgroundColor(Color.YELLOW);
                cpstatus.setBackgroundColor(Color.YELLOW);
                recent.setBackgroundColor(Color.YELLOW);

                girldp.setBackgroundColor(Color.YELLOW);
                girlatt.setBackgroundColor(Color.YELLOW);
                boydp.setBackgroundColor(Color.YELLOW);
                boyatt.setBackgroundColor(Color.YELLOW);
                bithday.setBackgroundColor(Color.YELLOW);
                sadgirl.setBackgroundColor(Color.YELLOW);
                sadboy.setBackgroundColor(Color.YELLOW);
                god.setBackgroundColor(Color.YELLOW);
                motiv.setBackgroundColor(Color.WHITE);
                goodmor.setBackgroundColor(Color.YELLOW);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new Motivation()).commit();
            }
        });
        ///moring
        goodmor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cp.setBackgroundColor(Color.YELLOW);
                cpstatus.setBackgroundColor(Color.YELLOW);

                recent.setBackgroundColor(Color.YELLOW);

                girldp.setBackgroundColor(Color.YELLOW);
                girlatt.setBackgroundColor(Color.YELLOW);
                boydp.setBackgroundColor(Color.YELLOW);
                boyatt.setBackgroundColor(Color.YELLOW);
                bithday.setBackgroundColor(Color.YELLOW);
                sadgirl.setBackgroundColor(Color.YELLOW);
                sadboy.setBackgroundColor(Color.YELLOW);
                god.setBackgroundColor(Color.YELLOW);
                motiv.setBackgroundColor(Color.YELLOW);
                goodmor.setBackgroundColor(Color.WHITE);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new Good_mor()).commit();
            }
        });

    }//on create close

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            //Toast.makeText(getApplicationContext(),"Press again to exit",Toast.LENGTH_LONG).show();
            //////////////////
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            //View dialoglay=getLayoutInflater().inflate(R.layout.custom_alert,null);
            builder.setTitle("Exit...");
///////////////////
            List<String> list=new ArrayList<String>();
            //Adding elements in the List
            list.add("Tushi Na Jaoo Na Yarr ,Dear User !..");
            list.add("See You Soon ,Dear User !..  ");
            list.add("Vaada Kero Phir Miloge,Dear User !..");
            list.add("Jaa Rhe Ho Per Aana Jaroor,Dear User !.." );
            list.add("Duaaon mein yaad rakhna,Dear User !..");
            list.add("Jaldi Milte Hai,Dear User !..");
            int max=5 ;//n-1//max exclusive hai
            int min=0;//min count hogi
            int b = (int)(Math.random()*(max-min+1)+min);
//////////////////////
            builder.setMessage(list.get(b));

            builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }//postive btn on click close
            });
            builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            finish();
                        }
                    }
            );

            builder.setNeutralButton("Rate Us", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(applink!=null) {
                        try {
                            Uri marketUri = Uri.parse("market://details?id=" + getPackageName());//getPackageName()
                            Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
                            startActivity(marketIntent);
                        }catch(ActivityNotFoundException e) {
                            Uri marketUri = Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName());
                            Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
                            startActivity(marketIntent);
                        }
                    }
                }
            });


            // builder.setView(dialoglay);

            builder.show();


            //////////////////////////
        }
        back_pressed = System.currentTimeMillis();

    }
    //////backpree close

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.h_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public void otherApp(MenuItem item) {
        Intent intent = new Intent(Home.this, login.class);
        startActivity(intent);
        /*Intent intent = new Intent(Home.this, other_app.class);
        startActivity(intent);*/
    }

    public void shareapp(MenuItem item) {
        if(applink!=null) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "All Type Of Morden and Creative Dp and Status at Posto App " + applink);//message);
            //intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(sharefile));
            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent.createChooser(intent, "Shareing....."));
            startActivity(intent);
        }
    }

    public void otherapp(View view) {
        Intent intent = new Intent(Home.this, Extra_activity.class);
        startActivity(intent);
    }
}//close
