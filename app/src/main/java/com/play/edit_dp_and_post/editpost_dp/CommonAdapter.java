package com.play.edit_dp_and_post.editpost_dp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.MyViewHolder>
{
    List<ImgLoad> imglistcp;
    Context context;
    //String dbname;

    public CommonAdapter(List<ImgLoad> imglistcp, Context context)
    {//, String dbname) {
        this.imglistcp = imglistcp;
        this.context = context;
        //this.dbname = dbname;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View vv = LayoutInflater.from(context).inflate(R.layout.recent_grid_design,parent,false);
        MyViewHolder mv= new MyViewHolder(vv);
        return mv;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position)
    {
        final String fimageuri = imglistcp.get(position).getImgpath();
        //get and load image

        StorageReference sref = FirebaseStorage.getInstance().getReference();


        sref.child("GirlDp/" + fimageuri).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                //        Toast.makeText(getContext(),"holder view value"+holder.iv,Toast.LENGTH_LONG).show();

                try {
                    // Target iv= ((Target) holder).iv;
                    //Picasso.get().load(uri).into(iv);
                    Picasso.get().load(uri).placeholder(R.drawable.ic_rotate_24dp).into(holder.iv);
                    // loadtext.setVisibility(View.GONE);

//                       prodig2.dismiss();

                } catch (IllegalArgumentException e) {
                    Toast.makeText(context, "your phone not support piccaso lib" + e.getMessage(), Toast.LENGTH_LONG).show();

                }
                //Toast.makeText(getContext(),"picass use",Toast.LENGTH_LONG).show();



                // Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(simgview);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Error " + e.getMessage()+"Due to SLOW NETWORK SPEED ", Toast.LENGTH_LONG).show();
                // err.setText("error"+e.getMessage());
            }
        });

        //storege end
        //setonclick listner
        /*holder.all_lin_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),"img path"+fimageuri,Toast.LENGTH_LONG).show();
                //Intent intent=new Intent(getContext(),all_dp_big_imag.class);
                //intent.putExtra("imguri",fimageuri);
                //startActivity(intent);
                //Toast.makeText(getContext(),"click on "+position,Toast.LENGTH_LONG).show();

                //Toast.makeText(getContext(),"img path of "+position+fimageuri,Toast.LENGTH_LONG).show();

                //Toast.makeText(getContext(),"img path"+fimageuri,Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getContext(),imgPview.class);
                intent.putExtra("data",imglistcp);
                intent.putExtra("position",position);
                intent.putExtra("databasename",databasename);
                intent.putExtra("storagename",storagename);
                //intent.putExtra("post",position);
                //intent.putExtra("imguri",fimageuri);
                startActivity(intent);
                //intent.putExtra("imguri",fimageuri);


            }
        });*/
//click listner clsoe



    }///

    @Override
    public int getItemCount() {
        return imglistcp.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder
{
    public ImageView iv;
    public LinearLayout all_lin_lay;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        iv = itemView.findViewById(R.id.imageView2);
        //Toast.makeText(getContext(),"iv value in myviewholder class"+iv,Toast.LENGTH_LONG).show();
        all_lin_lay = itemView.findViewById(R.id.recent_grid_lay);
    }
}//my adpter close

}//common adpter close
