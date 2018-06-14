package com.example.erik.eriknevilead340;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity which will display the message which was typed in the main activity
 */
public class DisplayMessageActivity extends AppCompatActivity {

    private static final String TAG = "DisplayMessageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.erikneville.erik.eriknevilead340.R.layout.activity_display_message);

//        Toolbar mToolbar = (Toolbar) findViewById(R.id.drawer_layout);
//        setSupportActionBar(mToolbar);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Intent intent = getIntent();
            String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

            TextView textView = findViewById(com.erikneville.erik.eriknevilead340.R.id.textView);
            textView.setText(message);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.erikneville.erik.eriknevilead340.R.menu.drawer_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case com.erikneville.erik.eriknevilead340.R.id.nav_settings:
                Toast toast = Toast.makeText(this, "Settings", Toast.LENGTH_SHORT);
                toast.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: Started");
    }

    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: resuming");
    }

    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: paused");
    }

    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: stopped");
    }

    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: restarted");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: destroyed");
    }
}
