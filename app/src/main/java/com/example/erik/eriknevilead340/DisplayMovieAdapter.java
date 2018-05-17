package com.example.erik.eriknevilead340;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * This class adapts the individual movie list items to the general RecyclerView
 * container layout.
 */
public class DisplayMovieAdapter extends RecyclerView.Adapter<DisplayMovieAdapter.ViewHolder> {

    private static final String TAG = "DisplayMovieAdapter";

    // variables that store all the information needed about each movie
    private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<String> mTitles = new ArrayList<>();
    private ArrayList<String> mYears = new ArrayList<>();
    private ArrayList<String> mDirectors = new ArrayList<>();
    private ArrayList<String> mDescription = new ArrayList<>();
    private Context mContext;

    /**
     * Constructor which initializes each variable declared above.
     */
    public DisplayMovieAdapter(ArrayList<String> mImages, ArrayList<String> mTitles, ArrayList<String> mYears,
                               ArrayList<String> mDirectors, ArrayList<String> mDescription, Context mContext) {

        this.mImages = mImages;
        this.mTitles = mTitles;
        this.mYears = mYears;
        this.mDirectors = mDirectors;
        this.mDescription = mDescription;
        this.mContext = mContext;
    }

    /**
     * View Holder class which inflates the view
     * @param parent the parent ViewGroup
     * @param viewType where the widget item is held is position
     * @return the holder of widget items
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        Glide.with(mContext).asBitmap().load(mImages.get(position)).into(holder.image);

        holder.movieTitle.setText(mTitles.get(position));

        // retrieve the images and set into ImageView widget
        Glide.with(mContext).asBitmap().load(mImages.get(position)).into(holder.image);

        // retrieve the title of the movie to be set next to its image
        holder.movieTitle.setText(mTitles.get(position));

        // create an on click listener to each item
        holder.movieLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + mTitles.get(position));

                Toast.makeText(mContext, mTitles.get(position), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, MovieInfoActivity.class);
                intent.putExtra("image_url", mImages.get(position));
                intent.putExtra("movie_title", mTitles.get(position));
                intent.putExtra("movie_year", mYears.get(position));
                intent.putExtra("movie_director", mDirectors.get(position));
                intent.putExtra("movie_description", mDescription.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    /**
     * getter for the size of the list created
     * @return the size of the list
     */
    @Override
    public int getItemCount() {
        return mTitles.size();
    }


    /**
     * create a ViewHolder class which will store the images with names
     * next to them in a RelativeLayout form.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView image;
        TextView movieTitle;
        RelativeLayout movieLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            movieTitle = itemView.findViewById(R.id.movie_title);
            movieLayout = itemView.findViewById(R.id.movie_layout);
        }
    }

}
