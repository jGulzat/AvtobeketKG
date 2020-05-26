package com.manas.avtobeketkg.UI.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Constraints;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.manas.avtobeketkg.Api.ApiService;
import com.manas.avtobeketkg.Model.MapResponse;
import com.manas.avtobeketkg.Model.Stations;
import com.manas.avtobeketkg.R;
import com.manas.avtobeketkg.ViewModel.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.google.android.gms.wearable.DataMap.TAG;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {
    public MapFragment() {
        // Required empty public constructor
    }
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Token = "token";

    GoogleMap mGooglemap;
    MapView mapView;
    ArrayList<Stations> stations1 = new ArrayList<>();
    AutoCompleteTextView fromAutoCompleteTextView, toAutoCompleteTextView;
    Button routeBtn;
    String token,fromStation,toStation;
    View view;
    private SearchViewModel searchViewModel;

    Polyline polyline;
    ArrayList<LatLng> latLngs = new ArrayList<>();
    String origin,destination;
    LatLng place1,place2;

    private static final String BASE_URL = "https://maps.googleapis.com/";
    ApiService apiService;
    Retrofit retrofit;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedpreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        if (sharedpreferences.contains(Token)) {
            token = sharedpreferences.getString(Token, "");
            Log.d("TAG", "run: tokennnnnnnnnnn" + sharedpreferences.getString(Token, ""));
        }
            searchViewModel = ViewModelProviders.of(requireActivity()).get(SearchViewModel.class);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_map, container, false);

        init();
        return view;
    }



    public void init(){
        fromAutoCompleteTextView = view.findViewById(R.id.fromSTN);
        toAutoCompleteTextView = view.findViewById(R.id.toSTN);
        routeBtn = view.findViewById(R.id.btn_route);
        routeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRouteInfo();
            }
        });

    }

    private void getRouteInfo() {
        fromStation = fromAutoCompleteTextView.getText().toString();
        toStation = toAutoCompleteTextView.getText().toString();

        if(!(fromStation.isEmpty() && toStation.isEmpty())){

            for(int i = 0; i < stations1.size(); i++ )
            { if(stations1.get(i).getName().equals(fromStation) )
              {  place1 = new LatLng(stations1.get(i).getLat(),stations1.get(i).getLng());
                  Log.d(TAG, "getRouteInfo1: " + place1.latitude + " :"  + place1.longitude);
              }
              if(stations1.get(i).getName().equals(toStation))
              {  place2 = new LatLng(stations1.get(i).getLat(),stations1.get(i).getLng());
                  Log.d(TAG, "getRouteInfo2: " + place2.latitude + " :"  + place2.longitude);
              }
            }
            Log.d(TAG, "getRouteInfo: " + place1.latitude + "  place2: " + place2.latitude);
            drawRoute(place1,place2);
        }



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView =  view.findViewById(R.id.map);
        if(mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);

        }
        Log.d(TAG, "onViewCreated: qyeryyyyyyyyyyyyyyyy");
        final MapResponse mapResponse = new MapResponse();
         retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
         apiService = retrofit.create(ApiService.class);


        searchViewModel.getListStations(token).observe(this, new Observer<List<Stations>>() {
            @Override
            public void onChanged(List<Stations> stations) {
                Log.d(Constraints.TAG, "onChanged: " + stations.get(0).getName());
                stations1 = (ArrayList<Stations>) stations;
                setStation(stations);
                String stationName[] = new String[stations.size()];
                for (int i = 0; i < stations.size(); i++)
                {   stationName[i] = stations.get(i).getName();
                    Log.d(Constraints.TAG, "onResponse: " + stations.get(i).getName());
                }
                fromAutoCompleteTextView.setAdapter(new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_dropdown_item_1line, stationName));
                toAutoCompleteTextView.setAdapter(new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_dropdown_item_1line, stationName));
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        stations1.add(new Stations(1,"Bishkek",41.21968725,74.97738709));
        stations1.add(new Stations(2,"Bishkek",42.8799039,74.58589061));
        /*stations.add(new Stations(1,"Bishkek",42.5829746,71.8293567));
        stations.add(new Stations(1,"Bishkek", 42.4916808,78.3901729));*/
        latLngs = new ArrayList<LatLng>();

        MapsInitializer.initialize(getContext());
        mGooglemap = googleMap;
        mGooglemap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CameraPosition googlePlex = CameraPosition.builder()
                .target(new LatLng(41.21968725, 74.97738709))
                .zoom(7)
                .bearing(0)
                .tilt(45)
                .build();

        mGooglemap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 5000, null);

        mGooglemap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.d(TAG, "onMarkerClick: " + marker.getPosition().latitude);
                marker.showInfoWindow();


                latLngs.add(new LatLng(marker.getPosition().latitude,marker.getPosition().longitude));

                if(latLngs.size() == 2){
                   // if(polyline != null) polyline.remove();
                    place1 = new LatLng(latLngs.get(0).latitude,latLngs.get(0).longitude);
                    place2 = new LatLng(latLngs.get(1).latitude,latLngs.get(1).longitude);
                    drawRoute(place1, place2);

                   origin = Double.toString(place1.latitude) + "," + Double.toString(place1.longitude);
                   destination = Double.toString(place2.latitude) + "," + Double.toString(place2.longitude);
                    Log.d(TAG, "0000000000onMarkerClick: origin: " + origin + " destination: " + destination);
                    Call<MapResponse> call = apiService.getDirection(origin,
                            destination,"AIzaSyC0QHkHmtE9PHzWR-EbYKaygi0VNhRFqW0");
                    call.enqueue(new Callback<MapResponse>() {
                        @Override
                        public void onResponse(Call<MapResponse> call, Response<MapResponse> response) {
                            if(response.isSuccessful()){
                                if(response.body().getStatus().equals("OK")){
                                    Toast.makeText(getActivity(),response.body().getRoutes().get(0).getLegs().get(0).getDistance().getText()
                                                    + "  duration: " + response.body().getRoutes().get(0).getLegs().get(0).getDuration().getText()
                                            ,Toast.LENGTH_LONG).show();

                                    Log.d(TAG, "2222222222222222222222onResponse: succes:distance:" +
                                            response.body().getRoutes().get(0).getLegs().get(0).getDistance().getText()
                                            + "  duration: " + response.body().getRoutes().get(0).getLegs().get(0).getDuration().getText());

                                }
                                else Toast.makeText(getActivity(),"Такого маршрута не существует!!!",Toast.LENGTH_LONG).show();


                            }
                            else Log.d(TAG, "22222222222222222222onResponse: ubsucces" + response.code());
                        }

                        @Override
                        public void onFailure(Call<MapResponse> call, Throwable t) {
                            Log.d(TAG, "onFailure: " + t.getMessage());
                        }
                    });


                    latLngs.clear();
                }

                return true;

            }
        });


    }

    public void drawRoute(LatLng place1, LatLng place2){
        Log.d(TAG, "drawRoute: " + mGooglemap + " : " + place1.longitude + " ; " + place2.latitude);
        if(polyline != null) polyline.remove();
       /* place1 = new LatLng(41.2674528,74.9501034);
        place2 = new LatLng(42.9191833,74.5467319);*/
        polyline = mGooglemap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(place1, place2));
        polyline.setColor(Color.GREEN);
        polyline.setWidth(8);
        polyline.setPoints(latLngs);
        polyline.setVisible(true);
        Log.d(TAG, "drawRoute: polyline:  " + polyline.isVisible());

    }


    public void setStation(List<Stations> station)
    {
        Log.d(TAG, "setStation: listStationbaza: " + station.get(0).getName());
        BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.mipmap.marker);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 30, 30, false);
        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(smallMarker);
        for(int i = 0 ; i < station.size() ; i++) {
            Log.d(TAG, "onMapReady: " + station.get(i).getName() + "\n" + station.get(i).getLat());
            mGooglemap.addMarker(new MarkerOptions().position(new LatLng(station.get(i).getLat(),station.get(i).getLng()))
                    .title(station.get(i).getName()).icon(icon));
        }
        //CameraPosition first = CameraPosition.builder().target(new LatLng(41.21968725,74.97738709))
          //      .zoom(5).bearing(2).tilt(30).build();
        float zoomLevel = 6.2f; //This goes up to 21
        LatLng latLng = new LatLng(41.21968725, 74.97738709);
        mGooglemap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));

    }
}
