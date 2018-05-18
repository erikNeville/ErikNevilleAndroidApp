package com.example.erik.eriknevilead340;

import android.drm.DrmStore;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowTraffic extends AppCompatActivity {
    private static final String TAG = "ShowTraffic";

    private ArrayList<TrafficModel> trafficList;
    private RecyclerView recyclerView;
    private TrafficRecycler recyclerAdapter;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_traffic);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        trafficList = new ArrayList<TrafficModel>();
        recyclerAdapter = new TrafficRecycler(ShowTraffic.this, trafficList);
        recyclerView.setAdapter(recyclerAdapter);

        requestQueue = Volley.newRequestQueue(this);
        jsonrequest();
    }

    private void jsonrequest() {
        String jsonURL = "https://web6.seattle.gov/Travelers/api/Map/Data?zoomId=13&type=2";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, jsonURL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Features");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject top = jsonArray.getJSONObject(i);

                                JSONArray bottom = top.getJSONArray("Cameras");

                                for (int j = 0; j < bottom.length(); j++) {
                                    JSONObject cameroObject = bottom.getJSONObject(j);
                                    String description = cameroObject.getString("Description");
                                    String imgURL = cameroObject.getString("ImageUrl");
                                    String type = cameroObject.getString("Type");
                                    if (type.equals("wsdot")) {
                                        imgURL = "http://images.wsdot.wa.gov/nw/" + imgURL;
                                    } else {
                                        imgURL = "http://www.seattle.gov/trafficcams/images/" + imgURL;
                                    }
                                    trafficList.add(new TrafficModel(description, imgURL, type));
                                }
                            }

                            recyclerAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_settings:
                Toast toast = Toast.makeText(this, "Settings", Toast.LENGTH_SHORT);
                toast.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

