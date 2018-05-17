package com.example.erik.eriknevilead340;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    public static final String EXTRA_MESSAGE = "com.example.erik.eriknevilead340";

    ImageView img1, img2, img3, img4;
    private DrawerLayout drawer;
    private SharedPreferencesHelper mSharedPreferencesHelper;
    private SharedPreferences mSharedPreferences;

    public void sendMessage(View view) {

        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText)findViewById(R.id.editText);
        String message = editText.getText().toString();
        if (validInput(message)) {
            mSharedPreferencesHelper.saveEntry(message);
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, "Please enter some text", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mSharedPreferences = this.getSharedPreferences(Context.MODE_PRIVATE);
        mSharedPreferencesHelper = new SharedPreferencesHelper(mSharedPreferences);


        /**
         * Creating the GridView images which each have their own onclick listeners.
         * The first image will direct to the DisplayMovieActivity, which is a RecyclerView,
         * while the rest will display a Toast message
         */
        img1 = (ImageView)findViewById(R.id.button1);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayMovieActivity.class);
                startActivity(intent);
            }
        });

        img2 = (ImageView)findViewById(R.id.button2);
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Button 2", Toast.LENGTH_SHORT).show();
            }
        });

        img3 = (ImageView)findViewById(R.id.button3);
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Button 3", Toast.LENGTH_SHORT).show();
            }
        });

        img4 = (ImageView)findViewById(R.id.button4);
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Button 4", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.nav_about:
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_movies:
                intent = new Intent(this, DisplayMovieActivity.class);
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

    public boolean validInput(String msg) {
        if (msg.length() == 0) {
            return false;
        }
        return true;
    }
}
