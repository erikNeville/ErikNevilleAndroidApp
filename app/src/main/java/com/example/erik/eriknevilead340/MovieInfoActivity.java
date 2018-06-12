package com.example.erik.eriknevilead340;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MovieInfoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MovieInfoActivity";
    private DrawerLayout drawer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movieinfo);
        Log.d(TAG, "onCreate: started");

        getIntents();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }



    /**
     * retrieves the intents for each detail required about each movie
     */
    private void getIntents() {
        Log.d(TAG, "getIntents: checking for incoming intents");

        if (getIntent().hasExtra("image_url") && getIntent().hasExtra("movie_title") && getIntent().hasExtra("movie_year")
                && getIntent().hasExtra("movie_director") && getIntent().hasExtra("movie_description")) {

            Log.d(TAG, "getIntents: found intent extras");

            String imageURL = getIntent().getStringExtra("image_url");
            String movieTitle = getIntent().getStringExtra("movie_title");
            String movieYear = getIntent().getStringExtra("movie_year");
            String movieDirector = getIntent().getStringExtra("movie_director");
            String movieDescription = getIntent().getStringExtra("movie_description");

            setIntents(imageURL, movieTitle, movieYear, movieDirector, movieDescription);
        }
    }

    /**
     * Retrieves each view which displays the necessary information about the movie
     */
    private void setIntents(String imageURL, String movieTitle, String movieYear, String movieDirector, String movieDescription) {
        Log.d(TAG, "setIntents: setting intents to widgets");

        TextView title = findViewById(R.id.movieTitle);
        title.setText(movieTitle);

        ImageView imageView = findViewById(R.id.movieImage);
        Glide.with(this).asBitmap().load(imageURL).into(imageView);

        TextView year = findViewById(R.id.movieYear);
        year.setText(movieYear);

        TextView director = findViewById(R.id.movieDirector);
        director.setText(movieDirector);

        TextView description = findViewById(R.id.movieDescription);
        description.setText(movieDescription);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.nav_home:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_about:
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_traffic:
                intent = new Intent(this, ShowTraffic.class);
                startActivity(intent);
                break;


            case R.id.nav_settings:
                Toast toast = Toast.makeText(this, "Settings", Toast.LENGTH_SHORT);
                toast.show();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
