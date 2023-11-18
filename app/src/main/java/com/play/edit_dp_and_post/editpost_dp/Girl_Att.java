package com.play.edit_dp_and_post.editpost_dp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Girl_Att extends Fragment {
    private RecyclerView arview;
    private ProgressBar pbar;
    private FirebaseFirestore db;
    private StorageReference sref;
    private DocumentSnapshot lastVisible;
    private boolean isScrolling = false;
    private boolean isLastItemReached = false;
    private TextView loadtv;
    // public List<ImgLoad> imglist = new ArrayList<>();
    //private  ArrayList<ImgLoad> imglist=new ArrayList<>();
    String databasename="GirlAttitude";
    String storagename="GirlAttitude/";





    public Girl_Att() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Toast.makeText(getContext(), "Girl Status", Toast.LENGTH_LONG).show();

        db = FirebaseFirestore.getInstance();
        sref = FirebaseStorage.getInstance().getReference();

        // Inflate the layout for this fragment
        View vv=inflater.inflate(R.layout.fragment_girl__att, container, false);
        arview = vv.findViewById(R.id.girlattrecyclerview);
        pbar=vv.findViewById(R.id.progressBar4);

        loadtv=vv.findViewById(R.id.loadtext);
        /////////
        int mNoOfColumns=Utility.calculateNoOfColumns(getContext(),150);
      //  Toast.makeText(getContext(), "no of columns in recent"+mNoOfColumns+"for 150dp" , Toast.LENGTH_LONG).show();


        arview.setLayoutManager(new GridLayoutManager(getContext(), mNoOfColumns));
        /////
        //src of data
        final ArrayList<ImgLoad> imglist=new ArrayList<>();
        //final List<User> list = new ArrayList<>();
        final ProductAdapter productAdapter = new ProductAdapter(imglist);
        arview.setAdapter(productAdapter);
        final CollectionReference productsRef = db.collection("GirlAttitude");
        Query query = productsRef.limit(25);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        ImgLoad productModel = document.toObject(ImgLoad.class);
                        imglist.add(productModel);
                    }
                    productAdapter.notifyDataSetChanged();
                    lastVisible = task.getResult().getDocuments().get(task.getResult().size() - 1);

                    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                                isScrolling = true;
                            }
                        }
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            LinearLayoutManager linearLayoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
                            int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                            int visibleItemCount = linearLayoutManager.getChildCount();
                            int totalItemCount = linearLayoutManager.getItemCount();

                            if (isScrolling && (firstVisibleItemPosition + visibleItemCount == totalItemCount) && !isLastItemReached)
                            {  pbar.setVisibility(View.VISIBLE);
                                loadtv.setVisibility(View.INVISIBLE);
                                isScrolling = false;
                                Query nextQuery = productsRef.startAfter(lastVisible).limit(15);
                                nextQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> t) {
                                        if (t.isSuccessful()) {
                                            for (DocumentSnapshot d : t.getResult()) {
                                                ImgLoad productModel = d.toObject(ImgLoad.class);
                                                imglist.add(productModel);
                                            }
                                            pbar.setVisibility(View.INVISIBLE);
                                            productAdapter.notifyDataSetChanged();
                                            lastVisible = t.getResult().getDocuments().get(t.getResult().size()-1);
                                            if (t.getResult().size() < 15) {
                                                isLastItemReached = true;
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    };
                    arview.addOnScrollListener(onScrollListener);
                }
            }
        });


        /////////

        return vv;

        //retrive from firebase
       /* db.collection("GirlAttitude")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                   // Toast.makeText(getContext(), "all dp image load", Toast.LENGTH_LONG).show();

                    // public List<Imgload> imglist = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : task.getResult()) {

                        ImgLoad imgobj = doc.toObject(ImgLoad.class);
                        imglist.add(imgobj);
                    }//for close
                    loadtv.setVisibility(View.GONE);
                    //create recycler adapter
                    Rvadpter adp = new Rvadpter(imglist);
                    Toast.makeText(getContext(), "plzz wait DISPLAYING image... ", Toast.LENGTH_LONG).show();
                    Toast.makeText(getContext(), "Slide Category Slide bar for more Dp & Post ", Toast.LENGTH_LONG).show();

                    //attach adapter
                    arview.setAdapter(adp);
                    /////
                }//if close
                else {
                    Toast.makeText(getContext(), "image not load due to SLOW NETWORK..", Toast.LENGTH_LONG).show();
                }
            }
        });
        //////////////////

////////////////////
        return vv;

        */

    }//on cretae close
    ////////////
    class  ProductAdapter extends RecyclerView.Adapter<ProductViewHolder>
    {
        private ArrayList<ImgLoad>imglist;

        public ProductAdapter(ArrayList<ImgLoad> imglist) {
            this.imglist = imglist;
        }

        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View vv= getLayoutInflater().inflate(R.layout.recent_grid_design,parent,false);
            ProductViewHolder mv =new ProductViewHolder(vv);



            return mv;
        }

        @Override
        public void onBindViewHolder(@NonNull final ProductViewHolder holder, final int position)
        {
            final String fimageuri = imglist.get(position).getImgpath();
            //get and load image
            sref.child("GirlAttitude/" + fimageuri).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {

                    //        Toast.makeText(getContext(),"holder view value"+holder.iv,Toast.LENGTH_LONG).show();

                    try {
                        // Target iv= ((Target) holder).iv;
                        //Picasso.get().load(uri).into(iv);
                        Picasso.get().load(uri).placeholder(R.drawable.ic_rotate_24dp).into(holder.iv);
                        // Toast.makeText(getContext(), "plzz wait  showing all images....." , Toast.LENGTH_LONG).show();


                    } catch (IllegalArgumentException e) {
                        Toast.makeText(getContext(), "picasso lib not support.." + e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                    //Toast.makeText(getContext(),"picass use",Toast.LENGTH_LONG).show();


                    // Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(simgview);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Error" + e.getMessage()+"due to SLOW NETWORK..", Toast.LENGTH_LONG).show();
                    // err.setText("error"+e.getMessage());
                }
            });

            //storege end
            //setonclick listner
            holder.all_lin_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getContext(),imgPview.class);
                    intent.putExtra("data",imglist);
                    intent.putExtra("position",position);
                    intent.putExtra("databasename",databasename);
                    intent.putExtra("storagename",storagename);
                    //intent.putExtra("post",position);
                    startActivity(intent);
                    //intent.putExtra("imguri",fimageuri);


                }
            });
//click listner clsoe


        }///////

        @Override
        public int getItemCount() {
            return imglist.size();
        }
    }
    /////
    class ProductViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView iv;
        public LinearLayout all_lin_lay;

        public ProductViewHolder(@NonNull View itemView)
        {
            super(itemView);
            iv = itemView.findViewById(R.id.imageView2);
            //Toast.makeText(getContext(),"iv value in myviewholder class"+iv,Toast.LENGTH_LONG).show();
            all_lin_lay = itemView.findViewById(R.id.recent_grid_lay);


        }
    }







    ///create adp
    /*class Rvadpter extends RecyclerView.Adapter<Myviewholder>
    {
        private ArrayList<ImgLoad>imglist;

        public Rvadpter(ArrayList<ImgLoad> imglist) {
            this.imglist = imglist;
        }

        @NonNull
        @Override
        public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View vv =getLayoutInflater().inflate(R.layout.recent_grid_design,parent,false);
            Myviewholder mv =new Myviewholder(vv);
            return mv;
        }
        //////

        @Override
        public void onBindViewHolder(@NonNull final Myviewholder holder, final int position) {
            final String fimageuri = imglist.get(position).getImgpath();
            //get and load image
            sref.child("GirlAttitude/" + fimageuri).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {

                    //        Toast.makeText(getContext(),"holder view value"+holder.iv,Toast.LENGTH_LONG).show();

                    try {
                        // Target iv= ((Target) holder).iv;
                        //Picasso.get().load(uri).into(iv);
                        Picasso.get().load(uri).placeholder(R.drawable.ic_rotate_24dp).into(holder.iv);

                    } catch (IllegalArgumentException e) {
                        Toast.makeText(getContext(), "picasso lib not support.." + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    //Toast.makeText(getContext(),"picass use",Toast.LENGTH_LONG).show();


                    // Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(simgview);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Error" + e.getMessage()+"due to SLOW NETWORK..", Toast.LENGTH_LONG).show();
                    // err.setText("error"+e.getMessage());
                }
            });

            //storege end
            //setonclick listner
            holder.all_lin_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getContext(),"img path"+fimageuri,Toast.LENGTH_LONG).show();
                    //Intent intent=new Intent(getContext(),all_dp_big_imag.class);
                    //intent.putExtra("imguri",fimageuri);
                    //startActivity(intent);
                   // Toast.makeText(getContext(),"img path"+fimageuri,Toast.LENGTH_LONG).show();
                    //Intent intent=new Intent(getContext(),imgPview.class);
                    //intent.putExtra("post",position);
                    //intent.putExtra("imguri",fimageuri);
                    //Toast.makeText(getContext(),"img path"+fimageuri,Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(getContext(),imgPview.class);
                    intent.putExtra("data",imglist);
                    intent.putExtra("position",position);
                    intent.putExtra("databasename",databasename);
                    intent.putExtra("storagename",storagename);
                    //intent.putExtra("post",position);
                    startActivity(intent);
                    //intent.putExtra("imguri",fimageuri);


                }
            });
//click listner clsoe


        }//onbind close

        @Override
        public int getItemCount() {
            return imglist.size();
        }
    }
//adpt cllose
    class Myviewholder extends RecyclerView.ViewHolder
  {
      public ImageView iv;
      public LinearLayout all_lin_lay;

      public Myviewholder(@NonNull View itemView) {
          super(itemView);
          iv = itemView.findViewById(R.id.imageView2);
          //Toast.makeText(getContext(),"iv value in myviewholder class"+iv,Toast.LENGTH_LONG).show();
          all_lin_lay = itemView.findViewById(R.id.recent_grid_lay);

      }
  }
    //myview holder close

     */
}//main close


