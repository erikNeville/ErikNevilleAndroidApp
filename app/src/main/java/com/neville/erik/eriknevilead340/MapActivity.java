package com.example.erik.eriknevilead340;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MapActivity";
    private static final int LOCATION_PERMISSION = 1;
    public static final float ZOOM = 10f;
    private Boolean locationPermissionGranted = false;
    private GoogleMap gMap;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.erikneville.erik.eriknevilead340.R.layout.activity_map);

//        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(myToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        getLocationPermission();
    }

    private void moveCamera(LatLng latLng, float zoom) {
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(com.erikneville.erik.eriknevilead340.R.id.map);
        mapFragment.getMapAsync(MapActivity.this);
    }

    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        if (locationPermissionGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            gMap.setMyLocationEnabled(true);
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://web6.seattle.gov/Travelers/api/Map/Data?zoomId=17&type=2";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Features");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject feature = jsonArray.getJSONObject(i);

                                JSONArray coordinates = feature.getJSONArray("PointCoordinate");
                                double latitude = coordinates.getDouble(0);
                                double longitude = coordinates.getDouble(1);

                                JSONArray cameras = feature.getJSONArray("Cameras");
                                JSONObject camera = cameras.getJSONObject(0);
                                String type = camera.getString("Type");
                                String imageURL = camera.getString("ImageUrl");
                                if (type.equals("sdot")) {
                                    imageURL = "http://www.seattle.gov/trafficcams/images/" + imageURL;
                                } else {
                                    imageURL = "http://images.wsdot.wa.gov/nw/" + imageURL;
                                }
                                String camDesc = camera.getString("Description");

                                LatLng latlng = new LatLng(latitude, longitude);
                                MarkerOptions markerOptions = new MarkerOptions();

                                markerOptions.position(latlng).title(camDesc);

                                MapWindowModel info = new MapWindowModel();
                                info.setImageURL(imageURL);

                                MapInfoWindow customInfoWindow = new MapInfoWindow(MapActivity.this);
                                gMap.setInfoWindowAdapter(customInfoWindow);

                                //add map markers
                                Marker m = gMap.addMarker(markerOptions);
                                m.setTag(info);
                                m.showInfoWindow();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                ;
            }
        });
        requestQueue.add(request);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        switch (requestCode) {
            case LOCATION_PERMISSION: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            locationPermissionGranted = false;
                            return;
                        }
                    }
                    locationPermissionGranted = true;
                    initMap();
                }
            }
        }
    }

    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION);
        }
    }

    private void getDeviceLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (locationPermissionGranted) {
                Task location = fusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Location currentLocation = (Location) task.getResult();
                            if(currentLocation != null) {
                                LatLng currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                                moveCamera(currentLatLng, ZOOM);
                            }
                        } else {
                            Toast.makeText(MapActivity.this, "Could not find location requested", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException" + e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.erikneville.erik.eriknevilead340.R.menu.drawer_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case com.erikneville.erik.eriknevilead340.R.id.nav_home:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);

            case com.erikneville.erik.eriknevilead340.R.id.nav_about:
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);

            case com.erikneville.erik.eriknevilead340.R.id.nav_movies:
                intent = new Intent(this, DisplayMovieActivity.class);
                startActivity(intent);

            case com.erikneville.erik.eriknevilead340.R.id.nav_traffic:
                intent = new Intent(this, ShowTraffic.class);
                startActivity(intent);

            case com.erikneville.erik.eriknevilead340.R.id.nav_settings:
                Toast toast = Toast.makeText(this, "Settings Clicked", Toast.LENGTH_SHORT);
                toast.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
