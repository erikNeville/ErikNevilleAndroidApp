package com.example.erik.eriknevilead340;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.squareup.picasso.Picasso;


public class TrafficRecycler extends RecyclerView.Adapter<TrafficRecycler.MyViewHolder> {
    private static final String TAG = "TrafficRecycler";

    private Context context;
    private ArrayList<TrafficModel> listData;

    public RecyclerAdapterTraffic(Context context, ArrayList<TrafficModel> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_traffic_list, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: -----times");

        TrafficModel item = listData.get(position);
        String desc = item.getDescription();
        String imgURL = item.getImgURL();
        String type = item.getType();

        holder.Description.setText(desc);
        Picasso.with(context).load(imgURL).fit().centerInside().into(holder.image2);
        holder.type.setType(type);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        getSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = getApplicationContext();


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Description, type;
        ImageView image2;


        public MyViewHolder(View itemView) {
            super(itemView);

            Description = itemView.findViewById(R.id.trafficDescription);
            image2 = itemView.findViewById(R.id.image2);
            type = itemView.findViewById(R.id.trafficType);
        }
    }
}
