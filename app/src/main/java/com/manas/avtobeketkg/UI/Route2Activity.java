package com.manas.avtobeketkg.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.manas.avtobeketkg.Model.Route;
import com.manas.avtobeketkg.Model.Search;
import com.manas.avtobeketkg.R;
import com.manas.avtobeketkg.ViewModel.RouteViewModel;
import com.manas.avtobeketkg.adapter.RoutesAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Route2Activity extends AppCompatActivity implements RoutesAdapter.OnRouteListener, Serializable {

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Token = "token";

    Button plusBtn, minusBtn;
    TextView quantityTV;
    String quantity, token;
    RecyclerView routeToRecyclerView;
    int quantityPassenger;
    RouteViewModel routeViewModel;
    Search searchTo, searchFrom;
    ArrayList<Route> routes = new ArrayList<>();
    RoutesAdapter routesAdapter;
    String routeWay, date1, date2;
    int toId, fromId, count = 0, toSelected = 0, fromSelected = 0,countTo, countFrom, routeIdTo, routeIdBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route2);
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        if (sharedpreferences.contains(Token))
        {  Log.d("RouteActivity", "run: tokennnnnnnnnnn" + sharedpreferences.getString(Token, ""));
            token = sharedpreferences.getString(Token,""); }
        Intent intent = getIntent();
        toId = intent.getIntExtra("to",0);
        fromId = intent.getIntExtra("from",0);
        routeWay = intent.getStringExtra("routeWay");
        date1 = intent.getStringExtra("date1");
        date2 = intent.getStringExtra("date2");

        routes.clear();


        quantityPassenger = 0;
        searchTo = new Search(4,3,"2020-04-20");
        searchFrom = new Search(4,3,"2020-04-20");
        routeViewModel = ViewModelProviders.of(this).get(RouteViewModel.class);
        initUI();
    }

    private void initUI() {
        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        quantityTV = findViewById(R.id.quantityTV);
        routeToRecyclerView = findViewById(R.id.route2RecyclerView);

    }

    @Override
    protected void onStart() {
        super.onStart();
        initDate();
    }

    private void initDate() {
        routeToRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        routeViewModel.getRoute("Token " + token, searchTo).observe(this, new Observer<List<Route>>() {
            @Override
            public void onChanged(List<Route> routesTo) {
                setStatus((ArrayList<Route>) routesTo,"to");
            }
        });
        routeViewModel.getRoute("Token " + token, searchFrom).observe(this, new Observer<List<Route>>() {
            @Override
            public void onChanged(List<Route> routesFrom) {
                setStatus((ArrayList<Route>) routesFrom, "from");
                routesAdapter = new RoutesAdapter(Route2Activity.this, routes, Route2Activity.this);
                routeToRecyclerView.setAdapter(routesAdapter);
            }
        });

    }
    public void setStatus(ArrayList<Route> route, String nav){

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

    public void minusBtn(View view) {
        quantityPassenger--;
        quantity = String.valueOf(quantityPassenger);
        quantityTV.setText(quantity);
    }

    public void plusBtnClick(View view) {
        quantityPassenger++;
        quantity = String.valueOf(quantityPassenger);
        quantityTV.setText(quantity);
    }

    @Override
    public void onRouteClick(View view, int position) {
        if(routes.get(position).getStatus().equals("to")){
            if(toSelected == 1){
                for(int i = 0; i < routes.size(); i++)
                { if(routes.get(i).getStatus().equals("to"))
                 {
                     if(routes.get(i).isSelected()) routes.get(i).setSelected(false);
                     toSelected = 0;
                 }
                }
                routes.get(position).setSelected(true);
                toSelected = 1;
            }
            else if(toSelected == 0){
                routes.get(position).setSelected(true);
                toSelected = 1;
            }
            countTo = routes.get(position).getNumberOfSeats();
            routeIdTo = routes.get(position).getId();
        }

        else if(routes.get(position).getStatus().equals("from")){
            if(fromSelected == 1){
                for(int i = 0; i < routes.size(); i++)
                { if(routes.get(i).getStatus().equals("from"))
                 {
                     if(routes.get(i).isSelected()) routes.get(i).setSelected(false);
                     fromSelected = 0;
                 }
                }
                routes.get(position).setSelected(true);
                fromSelected = 1;
            }
            else if(fromSelected == 0){
                routes.get(position).setSelected(true);
                fromSelected = 1;
            }
            countFrom = routes.get(position).getNumberOfSeats();
            routeIdBack = routes.get(position).getId();
        }
        routesAdapter.notifyDataSetChanged();

    }

    @Override
    public void onRouteImageClick(int position) {

    }

    @Override
    public void onServiceImageClick(int position) {

    }

    public void route2BuyBtn(View view) {
        if(quantityPassenger > 0){
        if(toSelected == 1 && fromSelected == 1){
            if(quantityPassenger <= countTo && quantityPassenger <= countFrom){
                Intent intent  = new Intent(this, PassengersDataActivity.class);
                intent.putExtra("passengerNumber", quantityPassenger);
                intent.putExtra("routeIdTo",routeIdTo);
                intent.putExtra("routeIdBack", routeIdBack);
                intent.putExtra("action","S");
                startActivity(intent);
            }
            else Toast.makeText(this,"В выбранных маршрутах не достаточно свободных мест!", Toast.LENGTH_LONG).show();

        }
        else Toast.makeText(this,"Вы должны выбрать два маршрута!", Toast.LENGTH_LONG).show();
        } else Toast.makeText(this,"Выберите количесто пассажиров!", Toast.LENGTH_LONG).show();
    }

    public void route2BookBtn(View view) {
    }
}
