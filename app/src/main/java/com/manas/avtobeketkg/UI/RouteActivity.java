package com.manas.avtobeketkg.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;
import com.manas.avtobeketkg.MainActivity;
import com.manas.avtobeketkg.Model.Route;
import com.manas.avtobeketkg.Model.Search;
import com.manas.avtobeketkg.Model.Stations;
import com.manas.avtobeketkg.R;
import com.manas.avtobeketkg.ViewModel.RouteViewModel;
import com.manas.avtobeketkg.adapter.RoutesAdapter;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class RouteActivity extends AppCompatActivity implements RoutesAdapter.OnRouteListener, Serializable {

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Token = "token";

    RecyclerView routesRecycleView;
    RoutesAdapter routesAdapter;
    ArrayList<Route> routes = new ArrayList<>();
    RouteViewModel routeViewModel;
    String date, token, routeWay,datePrevS, dateNextS;
    int from,to,count = 0;
    Search search;
    RadioButton sortPrice, sortTime;
    TextView routeTV;
    Date currentDate, prevDate,nextDate;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        if (sharedpreferences.contains(Token))
        {  Log.d("RouteActivity", "run: tokennnnnnnnnnn" + sharedpreferences.getString(Token, ""));
           token = sharedpreferences.getString(Token,""); }

        Intent intent = getIntent();
        from = intent.getIntExtra("from",0);
        to = intent.getIntExtra("to",0);
        date = intent.getStringExtra("date");
       // date = "2020-05-27";


        Log.d("TAG", "onCreate:getIntentrouteACtivity: " + from + "\n" + to + "\n" + date + "\n" + token );
        search = new Search(from,to,date);



        routeViewModel = ViewModelProviders.of(this).get(RouteViewModel.class);
        initUI();

    }

    private void initUI() {
        sortPrice = findViewById(R.id.sortPrice);
        sortTime  = findViewById(R.id.sortTime);
        routeTV = findViewById(R.id.routeTextView);

    }

    @Override
    protected void onStart() {
        super.onStart();
        routeList();
    }

    public void routeList() {
        routesRecycleView = findViewById(R.id.routeRecycleView);
        routesRecycleView.setLayoutManager(new LinearLayoutManager(this));
        cal1 = Calendar.getInstance();

        try {
            currentDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        getRouteQuery(date);
    }

    private void getRouteQuery(String date) {
        search = new Search(from,to,date);
        routes.clear();
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(RouteActivity.this);
        progressDoalog.setMessage("Its loading....");
        progressDoalog.setTitle("Routes");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        routeViewModel.getRoute("Token " + token,search).observe(this, new Observer<List<Route>>() {
            @Override
            public void onChanged(List<Route> routes1) {

                progressDoalog.dismiss();
                if(routes1.get(0).getSucces()){
                    routeWay = routes1.get(0).getFrom() + " --> " + routes1.get(0).getTo();
                    setStatus((ArrayList<Route>) routes1,"to");
                    routeTV.setText(routeWay);
                    Log.d("TAG", "onChanged: " + routes.get(0).getTrans_schema() + "\n" + routes);
                    routesAdapter = new RoutesAdapter(RouteActivity.this,routes,RouteActivity.this);
                    routesRecycleView.setAdapter(routesAdapter);
                }
                else
                    Toast.makeText(getBaseContext(), routes1.get(0).getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setStatus(ArrayList<Route>route, String nav) {
        for(int i = 0; i < route.size(); i++ )
        {
            count = 0;
            route.get(i).setStatus(nav);
            String shema = route.get(i).getTrans_schema();
            for(int j = 0; j < shema.length(); j ++){
                if(shema.charAt(j) == 'P') count++;
            } route.get(i).setNumberOfSeats(count);

            routes.add(route.get(i));
            Log.d("TAG", "setStatus:count "  + count + "\nroutes: " + routes.get(i).getNumberOfSeats());
        }
    }


    @Override
    public void onRouteClick(View view, int position) {

        Intent intent = new Intent(this,ChooseSeatActivity.class);
        intent.putExtra("trans_shema", routes.get(position).getTrans_schema());
        routeWay = routes.get(position).getFrom() + " --> " + routes.get(position).getTo();
        int routeId = routes.get(position).getId();
        intent.putExtra("routeId",routeId);
        intent.putExtra("routeway",routeWay);
        intent.putExtra("price", routes.get(position).getPrice());
        startActivity(intent);

    }

    @Override
    public void onRouteImageClick(int position) {

        Intent intent = new Intent(this,RouteMapActivity.class);
        intent.putExtra("routes",  routes.get(position));
        routeWay = routes.get(position).getFrom() + " --> " + routes.get(position).getTo();
        intent.putExtra("routeway",routeWay);
        startActivity(intent);
        Log.d("TAG", "onRouteClickImageClick: " + routes.get(position).getTrans_schema());
    }

    @Override
    public void onServiceImageClick(int position) {
        routeWay = routes.get(position).getFrom() + " --> " + routes.get(position).getTo();
        Intent intent = new Intent(this, ServiceActivity.class);
        intent.putExtra("foodServices", (Serializable) routes.get(position).getFoods());
        intent.putExtra("baggage",routes.get(position).getBaggage());
        intent.putExtra("routeWay", routeWay);
        startActivity(intent);
    }


    public void onRadioButtonPriceClicked(View view) {
        Collections.sort(routes, new Comparator<Route>() {
            @Override
            public int compare(Route o1, Route o2) {
                return o1.getPrice().compareToIgnoreCase(o2.getPrice());
            }
        });
        routesAdapter.notifyDataSetChanged();
    }

    public void onRadioButtonTimeClicked(View view) {

        Collections.sort(routes, new Comparator<Route>() {
            @Override
            public int compare(Route o1, Route o2) {
                return o1.getArrive_time().compareToIgnoreCase(o2.getArrive_time());
            }
        });
        routesAdapter.notifyDataSetChanged();
    }

    public void routesPrevios(View view) throws ParseException {

        cal1.setTime(currentDate);
        cal1.add(Calendar.DAY_OF_YEAR, -1);
        prevDate = cal1.getTime();

        datePrevS = dateFormat.format(prevDate);
        getRouteQuery(datePrevS);
        Log.d("TAG", "routesPrevious: " + datePrevS);
        currentDate = prevDate;


    }

    public void routesNext(View view) throws ParseException {
        cal1.setTime(currentDate);
        cal1.add(Calendar.DAY_OF_YEAR, + 1);
        nextDate = cal1.getTime();
        dateNextS = dateFormat.format(nextDate);
        getRouteQuery(dateNextS);
        Log.d("TAG", "routesNext: " + dateNextS);
        currentDate = nextDate;

    }
}
