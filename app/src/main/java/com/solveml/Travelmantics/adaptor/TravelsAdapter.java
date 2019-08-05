package com.solveml.Travelmantics.adaptor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.solveml.Travelmantics.AdminActivity;
import com.solveml.Travelmantics.R;
import com.solveml.Travelmantics.enitities.FirebaseUtil;
import com.solveml.Travelmantics.enitities.TravelEntity;
import com.squareup.picasso.Picasso;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TravelsAdapter extends RecyclerView.Adapter<TravelsAdapter.TravelsViewHolder> implements View.OnClickListener{


    // Class variables for the List that holds task data and the Context


    private ArrayList<TravelEntity> travelEntities;


    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;
    private FirebaseDatabase firebaseDatabase;
    private  Context mcontext;
    private  Activity activity;
    private  ImageView imageViewShow;

    public  TravelsAdapter(Activity  activity){

        this.activity=activity;
        FirebaseUtil.openFBReference("traveldeals",this.activity);
        firebaseDatabase =FirebaseUtil.firebaseDatabase;
        databaseReference = FirebaseUtil.databaseReference;
        travelEntities=FirebaseUtil.mtravelEntities;

        childEventListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                TravelEntity travelEntity =dataSnapshot.getValue(TravelEntity.class);
                travelEntity.setId(dataSnapshot.getKey());
                travelEntities.add(travelEntity);
                notifyItemInserted(travelEntities.size()-1);



            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved( DataSnapshot dataSnapshot,  String s) {

            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

            }
        };

        databaseReference.addChildEventListener(childEventListener);
    }

    @Override
    public TravelsViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.travels_views,parent,false);

        return new TravelsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TravelsViewHolder holder, int position) {


        TravelEntity travelEntity = travelEntities.get(position);
        String place = travelEntity.getPlace();
        String  mile = travelEntity.getMiles();
        String description = travelEntity.getDescription();
        String image =travelEntity.getImage();




        //Set values

        showImage(image,holder.imageViewImg);
        holder.placeText.setText(place);
        holder.mileText.setText(String.valueOf(mile));
        holder.descText.setText(description);











    }



    @Override
    public int getItemCount() {
        if (travelEntities == null) {
            return 0;
        }
        return travelEntities.size();
    }

    @Override
    public void onClick(View view) {

    }




    public List<TravelEntity> getTravelEntities() {
        return travelEntities;
    }

    public void setTravelEntities(ArrayList<TravelEntity> travelEntities) {
        this.travelEntities = travelEntities;
        notifyDataSetChanged();
    }

    public class TravelsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {



        TextView placeText;
        TextView mileText;
        TextView descText;
        ImageView  imageViewImg;





        public TravelsViewHolder( View itemView) {
            super(itemView);

            placeText = itemView.findViewById(R.id.textViewTravelPlace);
            mileText = itemView.findViewById(R.id.textViewTravelMile);
            descText=itemView.findViewById(R.id.textViewTravelDesc);
            imageViewImg=itemView.findViewById(R.id.imageViewTravelImg);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {


            TravelEntity travelEntity = travelEntities.get(getAdapterPosition());
            String elementId = travelEntity.getId();
            //Toast.makeText(view.getContext(), elementId, Toast.LENGTH_LONG).show();

            Intent adminIntent = new Intent(view.getContext(), AdminActivity.class);
            adminIntent.putExtra("Deal",  travelEntity);
            view.getContext().startActivity(adminIntent);

        }
    }


    public  void showImage(String url,ImageView imageView){

        if (url !=null && url.isEmpty()==false){

            int width= Resources.getSystem().getDisplayMetrics().widthPixels;
            Picasso.get().load(url).resize(80,80).centerCrop().into(imageView);


        }



    }


}
