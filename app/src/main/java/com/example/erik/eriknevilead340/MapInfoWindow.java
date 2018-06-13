package com.example.erik.eriknevilead340;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Callback;

public class MapInfoWindow implements GoogleMap.InfoWindowAdapter {
    private Context context;

    public MapInfoWindow(Context context) {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.map_info_window, null);

        TextView camName = view.findViewById(R.id.info_window_description);
        ImageView camPic = view.findViewById(R.id.info_window_pic);

        camName.setText(marker.getTitle());
        MapWindowModel camData = (MapWindowModel)marker.getTag();
        String imageURL = camData.getImageURL();

        Picasso.with(view.getContext()).load(imageURL).error(R.mipmap.ic_launcher).resize(450, 330).into(camPic, new MarkerCallback(marker));

        return view;
    }

    static class MarkerCallback implements Callback {
        Marker marker = null;

        MarkerCallback(Marker marker)
        {
            this.marker = marker;
        }

        @Override
        public void onError() {}

        @Override
        public void onSuccess()
        {
            if (marker == null)
            {
                return;
            }

            if (!marker.isInfoWindowShown())
            {
                return;
            }

            marker.hideInfoWindow();
            marker.showInfoWindow();
        }
    }
}
