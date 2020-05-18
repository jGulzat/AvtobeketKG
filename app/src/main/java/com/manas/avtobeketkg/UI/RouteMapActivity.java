 package com.manas.avtobeketkg.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.manas.avtobeketkg.Model.DirectionsJSONParser;
import com.manas.avtobeketkg.Model.FetchURL;
import com.manas.avtobeketkg.Model.Route;
import com.manas.avtobeketkg.Model.Search;
import com.manas.avtobeketkg.Model.Stations;
import com.manas.avtobeketkg.Model.TaskLoadedCallback;
import com.manas.avtobeketkg.R;
import com.manas.avtobeketkg.ViewModel.RouteViewModel;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RouteMapActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {

    private GoogleMap mMap;
    private MarkerOptions place1, place2,placeS,placeE,place;

    Button getDirection;
    String token;
    List<Integer>stationM;
    private Polyline currentPolyline;
    RouteViewModel routeViewModel1;
    Route routemap;
    ArrayList<Stations> stations = new ArrayList<>();

    ArrayList<LatLng> markerPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        routemap = (Route) intent.getSerializableExtra("routes");
        routeViewModel1 = ViewModelProviders.of(this).get(RouteViewModel.class);

        markerPoints = new ArrayList<LatLng>();

        for (int i = 1; i < routemap.getRoute().size()-1; i++)
        { markerPoints.add(new LatLng(routemap.getRoute().get(i).getLat(), routemap.getRoute().get(i).getLng()));
            Log.d("TAG", "onCreate: " + routemap.getRoute().get(i).getLat() + routemap.getRoute().get(i).getLng());
        }

        Log.d("TAG", "onCreateRouteMap: " + routemap.getFrom() + "\nstationM:" + routemap.getRoute().size()
                + "  size:" + routemap.getRoute().get(0).getLat());

        setContentView(R.layout.activity_route_map);
        getDirection = findViewById(R.id.btnGetDirection);
        int size = routemap.getRoute().size();

        place1 = new MarkerOptions().position(new LatLng(routemap.getRoute().get(0).getLat(), routemap.getRoute().get(0).getLng()))
                .title(routemap.getFrom());
        place2 = new MarkerOptions().position(new LatLng(routemap.getRoute().get(size-1).getLat(), routemap.getRoute().get(size-1).getLng()))
                .title(routemap.getTo());

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapNearBy);
        mapFragment.getMapAsync(this);

        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FetchURL(RouteMapActivity.this)
                        .execute(getUrl(place1.getPosition(),place2.getPosition(),"driving"), "driving");


            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
     //   fillStation();
    }
   /* public void fillStation(){
        routeViewModel1.getListStations(token).observe(this, new Observer<List<Stations>>() {
            @Override
            public void onChanged(List<Stations> stations1) {
                stations = new ArrayList<>(stations1);
                Log.d("TAG", "stations not null: " + stations.get(0).getName() + " stations1: " + stations1.get(0).getName());
            }
        });
    }*/

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("mylog", "Added Markers");
        mMap.addMarker(place1);
        mMap.addMarker(place2);

        CameraPosition googlePlex = CameraPosition.builder()
                .target(new LatLng(41.21968725, 74.97738709))
                .zoom(7)
                .bearing(0)
                .tilt(45)
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 5000, null);
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String waypoints = "";
        for(int i=0;i<markerPoints.size();i++){
            LatLng point  = (LatLng) markerPoints.get(i);
            if(i==0)
            { waypoints = "waypoints="; }
            waypoints += point.latitude + "," + point.longitude + "|";
            Log.d("TAG", "getDirectionsUrl11111111111111111111: " + waypoints);
        }

        String parameters = str_origin + "&" + str_dest + "&" +  "sensor=false" + "&" + waypoints ;

        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }
    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }
}
