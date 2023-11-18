package com.play.edit_dp_and_post.editpost_dp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
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
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class other_app extends AppCompatActivity {

    private GridView otherappgview;
    private FirebaseFirestore db;
    private StorageReference sref;
    private ArrayList<NewApp> newapplist = new ArrayList<>();
    //////////////
    private RewardedAd mRewardedAd;
    private Button reward_add_btn;
    ///////////////
    private RewardedInterstitialAd rewardedInterstitialAd;
    private Button inter_reward_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_app);
        otherappgview = findViewById(R.id.otherappgv);
        db = FirebaseFirestore.getInstance();
        sref = FirebaseStorage.getInstance().getReference();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("-> " + "Other App");
        /////////////
        reward_add_btn = findViewById(R.id.reward_adbtn);
        reward_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRwardadd();
            }
        });
        load_Radd();
        ///////
       inter_reward_btn =findViewById(R.id.int_rewd_btn);
       inter_reward_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               inter_showRwardadd();
           }
       });
        Inte_reward_loadAd();
        ///---------------
        //src of data
        //src of data
        //retrive from firebase
        db.collection("NewApp").orderBy("sno", Query.Direction.DESCENDING)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // public List<Imgload> imglist = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : task.getResult()) {

                        NewApp newappobj = doc.toObject(NewApp.class);
                        newapplist.add(newappobj);

                    }//for close

                    //create recycler adapter
                    CustomGridAdp adp = new CustomGridAdp(newapplist);
                    //attach adapter
                    otherappgview.setAdapter(adp);
                    //                  prodig1.dismiss();
                    ////////////////////////////
                    //////for progess bar

                }//if close
                else {

                    Toast.makeText(getApplicationContext(), "NEW app detalis not reterive..", Toast.LENGTH_LONG).show();
                }
            }
        });
        //////////////////
    }
    //on create close

    private void load_Radd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917",
                adRequest, new RewardedAdLoadCallback() {
                    //////////////
                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        Toast.makeText(other_app.this, "onAdLoaded()", Toast.LENGTH_SHORT).show();
                        ///////////////////////////////////
                        mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                                                     @Override
                                                                     public void onAdFailedToShowFullScreenContent(AdError adError) {
                                                                         Toast.makeText(other_app.this, "add fail", Toast.LENGTH_SHORT).show();
                                                                     }

                                                                     @Override
                                                                     public void onAdShowedFullScreenContent() {
                                                                         // Called when ad is shown.
                                                                         Toast.makeText(other_app.this, "add show", Toast.LENGTH_SHORT).show();
                                                                         mRewardedAd = null;
                                                                     }

                                                                     @Override
                                                                     public void onAdDismissedFullScreenContent() {
                                                                         Toast.makeText(other_app.this, "Add dismiss", Toast.LENGTH_SHORT).show();
                                                                         Intent intent = new Intent(other_app.this, Home.class);
                                                                         // intent.putExtra("cru_email",emailid);
                                                                         startActivity(intent);
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
    ////////////////---------------------------------
    private void showRwardadd()
    {
        if (mRewardedAd != null)
        {
            mRewardedAd.show(other_app.this, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem)
                {
                    Toast.makeText(other_app.this, "Reward get as download", Toast.LENGTH_SHORT).show();

                }
            });
        }
        else{
            Toast.makeText(other_app.this, "The rewarded ad wasn't ready yet.", Toast.LENGTH_SHORT).show();

        }
    }
   /////////////------------------

    public void Inte_reward_loadAd() {
        // Use the test ad unit ID to load an ad.
        RewardedInterstitialAd.load(other_app.this, "ca-app-pub-3940256099942544/5354046379",
                new AdRequest.Builder().build(),  new RewardedInterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(RewardedInterstitialAd ad) {
                        rewardedInterstitialAd = ad;
                        Log.e("anil", "onAdLoaded");
                        //////////
                        rewardedInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent();
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull  AdError adError) {
                                super.onAdFailedToShowFullScreenContent(adError);
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                super.onAdShowedFullScreenContent();
                            }
                        });

                        ///////------------callback close

                    }//onadloadclose
                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        Log.e("anil", "onAdFailedToLoad");
                    }
                });
    }

    ///////////////////////
    private void inter_showRwardadd()
    {
        if(rewardedInterstitialAd !=null)
        {
            rewardedInterstitialAd.show(other_app.this, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull  RewardItem rewardItem) {
                    Toast.makeText(other_app.this, "interstial Reward get as download", Toast.LENGTH_SHORT).show();

                }
            });
        }
        else
        {
            Toast.makeText(other_app.this, "The interstial rewarded ad wasn't ready yet.", Toast.LENGTH_SHORT).show();

        }
    }

    //........custon adpter

    class CustomGridAdp extends BaseAdapter {
        private ArrayList<NewApp> newapplisst;

        public CustomGridAdp(ArrayList<NewApp> newapplisst) {
            this.newapplisst = newapplisst;
        }


        @Override
        public int getCount() {
            return newapplisst.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View vv = getLayoutInflater().inflate(R.layout.otherapp_gv_design, parent, false);

            TextView imname = vv.findViewById(R.id.otherappnameTv);
            final ImageView imgview = vv.findViewById(R.id.newappphotos);
            ////put data
            final String appname = newapplisst.get(position).getTextshow();
            final String coverpicname = newapplisst.get(position).getNewappimgname();
            final Uri link = Uri.parse(newapplisst.get(position).getAppuri());
            ///////////click listner for send to play store send
             /*imname.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Toast.makeText(other_app.this,"Go for Install",Toast.LENGTH_LONG).show();
                     Intent intent = new Intent(Intent.ACTION_VIEW,link);
                     startActivity(intent);


                 }
             });

              */
            //////////////////////
            //for name show
            imname.setText(appname);
            //for img show
            sref.child("NewApp/" + coverpicname).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {

                    //        Toast.makeText(getContext(),"holder view value"+holder.iv,Toast.LENGTH_LONG).show();

                    try {
                        // Target iv= ((Target) holder).iv;
                        //Picasso.get().load(uri).into(iv);
                        Picasso.get().load(uri).into(imgview);
//                       prodig2.dismiss();

                    } catch (IllegalArgumentException e) {
                        Toast.makeText(getApplicationContext(), "picasso lib not support.." + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    //Toast.makeText(getContext(),"picass use",Toast.LENGTH_LONG).show();


                    // Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(simgview);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Error" + e.getMessage() + "due to SLOW NETWORK..", Toast.LENGTH_LONG).show();
                    // err.setText("error"+e.getMessage());
                }
            });

///////////////////////on click listner

            imname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Ready for Install", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Intent.ACTION_VIEW, link);
                    startActivity(intent);
                }
            });
            imgview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Ready for Install", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Intent.ACTION_VIEW, link);
                    startActivity(intent);
                }
            });

            ///////////////////////////////


            /////


            return vv;
        }
    }
    //............close adpter

    //////////////for secret upload

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.entry, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void goupload(MenuItem item) {
        Intent intent = new Intent(other_app.this, login.class);
        startActivity(intent);

    }
////////////////////////////////

}//main close
