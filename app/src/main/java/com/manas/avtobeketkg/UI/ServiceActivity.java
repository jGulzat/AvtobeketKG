package com.manas.avtobeketkg.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.manas.avtobeketkg.Model.FoodService;
import com.manas.avtobeketkg.R;
import com.manas.avtobeketkg.adapter.ServiceAdapter;

import java.util.ArrayList;

public class ServiceActivity extends AppCompatActivity {

    RecyclerView serviceRecyclerView;
    ServiceAdapter serviceAdapter;
    ArrayList<FoodService> foodService = new ArrayList<>();
    String routeWay, baggage;
    TextView baggageTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        Intent intent = getIntent();
        routeWay = intent.getStringExtra("routeWay");
        foodService = (ArrayList<FoodService>) intent.getSerializableExtra("foodServices");
        baggage = intent.getStringExtra("baggage");
        Log.d("ServiceActivity", "onCreate:size " + foodService.size());
        initUI();
    }

    private void initUI() {
        serviceRecyclerView = findViewById(R.id.serviceRV);
        baggageTV = findViewById(R.id.baggageTV);

    }

    @Override
    protected void onStart() {
        super.onStart();
        initDate();
    }

    private void initDate() {


        serviceRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Log.d("TAG", "initDate: " + foodService.size());
        //service = new FoodService(foodService);
        serviceAdapter = new ServiceAdapter(this,foodService);
        serviceRecyclerView.setAdapter(serviceAdapter);

        baggageTV.setText("В каждом автобусе есть много места для багажа." +
                "Чтобы сделать вашу поездку максимально комфортной, мы просим вас соблюдать несколько правил. " +
                "\n" +
                "Билет дает возможность БЕСПЛАТНО провозить: \nОдно место ручной клади\nОдно место сдаваемого багажа\n" +
                "А также вы можете взять с собой 1 сверхнормативный багаж на каждого пассажира.\n Стоимость сверхнормативный багажа: " + baggage);
    }
}
