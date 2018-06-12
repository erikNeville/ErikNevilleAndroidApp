package com.example.erik.eriknevilead340;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class TrafficRecycler extends RecyclerView.Adapter<TrafficRecycler.MyViewHolder> {
    private static final String TAG = "TrafficDisplay";

    private Context context;
    private ArrayList<TrafficModel> listData;

    public TrafficRecycler(Context context, ArrayList<TrafficModel> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.activity_traffic_list, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TrafficModel item = listData.get(position);

        String description = item.getDescription();
        String imageUrl = item.getImgURL();
        String type = item.getType();

        holder.Description.setText(description);
        Picasso.with(context).load(imageUrl).fit().centerInside().into(holder.image2);
        holder.type.setText(type);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Description;
        ImageView image2;
        TextView type;

        public MyViewHolder(View itemView){
            super(itemView);

            Description = itemView.findViewById(R.id.trafficDescription);
            image2 = itemView.findViewById(R.id.image2);
            type = itemView.findViewById(R.id.trafficType);
        }
    }
}
