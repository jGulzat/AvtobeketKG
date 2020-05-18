package com.manas.avtobeketkg.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.manas.avtobeketkg.Model.Answer;
import com.manas.avtobeketkg.Model.Passenger;
import com.manas.avtobeketkg.Model.PassengerInfo;
import com.manas.avtobeketkg.Model.Route;
import com.manas.avtobeketkg.R;
import com.manas.avtobeketkg.ViewModel.LoginViewModel;
import com.manas.avtobeketkg.ViewModel.PaymentViewModel;
import com.manas.avtobeketkg.adapter.PassengerAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class PassengersDataActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Token = "token";
    public static final String Date = "date";

    RecyclerView passengerdataRecycleView;
    PassengerAdapter passengerAdapter;
    public ArrayList<Passenger>passengersArrayList = new ArrayList<>();

    Set<Integer>selectedseats = new HashSet<>();
    Button payBtn;
    int routeIdTo, routeIdBack, passengerNumber;
    String action,token,routeway,price;
    private PaymentViewModel viewModel;
    TextView infoTV;
    String date2;
    PassengerInfo passengerInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passengers_data);
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        if (sharedpreferences.contains(Token))
        { Log.d("TAG", "run: tokennnnnnnnnnn" + sharedpreferences.getString(Token, ""));
          token = sharedpreferences.getString(Token,"");
        }
        if(sharedpreferences.contains(Date))
        {
            Log.d("TAG", "onCreate: date" + sharedpreferences.getString(Date, ""));
            date2 = sharedpreferences.getString(Date,"");
        }

        viewModel = ViewModelProviders.of(this).get(PaymentViewModel.class);
        init();

    }

    public void init(){
        infoTV = findViewById(R.id.progileTextView);
        passengerdataRecycleView = findViewById(R.id.passengerRecycleView);
        passengerdataRecycleView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        routeIdTo = intent.getIntExtra("routeIdTo",0);
        routeIdBack = intent.getIntExtra("routeIdBack",0);
        action = intent.getStringExtra("action");
        routeway = intent.getStringExtra("routeWay");
        price = intent.getStringExtra("price");
        passengerNumber = intent.getIntExtra("passengerNumber",0);
        if(passengerNumber == 0) {
            selectedseats = (Set<Integer>) intent.getSerializableExtra("selectedseats");
            Log.d("PassengerData", "onCreate: " + selectedseats + "\nid:" + routeIdBack + "\naction: " + action);

            Iterator iterator = selectedseats.iterator();
            int s = 1, k;
            while (iterator.hasNext()) {
                k = (int) iterator.next();
                Log.d("TAG", "onCreate: Passenger: " + k);
                passengersArrayList.add(new Passenger(s, k));
                s++;
            }
        }
        else {
            for(int i = 0; i < passengerNumber; i++){
                passengersArrayList.add(new Passenger(i+1,0));
            }
        }
        infoTV.setText("Данные пассажиров(" + routeway + ")");
        passengerAdapter = new PassengerAdapter(this,passengersArrayList);
        passengerdataRecycleView.setAdapter(passengerAdapter);

        payBtn = findViewById(R.id.btnPay);
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "onClick: action: " + action);

                  initAlertDialog(action);
            }
        });

    }

    public void initAlertDialog(String action){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.alert_dialog_booking, null);
        TextView infoTV = alertLayout.findViewById(R.id.infoPassenger);
        TextView routeTV = alertLayout.findViewById(R.id.routeTV);
        TextView summaTV = alertLayout.findViewById(R.id.summaTV);
        AlertDialog.Builder alert = new AlertDialog.Builder(PassengersDataActivity.this);

        if(action.equals("B")){  alert.setTitle("Бронировать билеты по этим данным:"); }
        else if(action.equals("S")) {  alert.setTitle("Купить билеты по этим данным:"); }

        String info = "";
        routeTV.setText(routeway);
        for (int i = 0; i < passengersArrayList.size(); i++)
        {
            if(passengerNumber == 0) info += "\nPassenger "+ (i+1) + "\nИмя: "
                    + passengersArrayList.get(i).getFullname() + "\nemail:"
                    + passengersArrayList.get(i).getEmail() + "\n";
            else  info += "\nPassenger "+ (i+1) + "\nИмя: "
                    + passengersArrayList.get(i).getFullname() + "\nemail:"
                    + passengersArrayList.get(i).getEmail() + "\nМесто №"
                    + passengersArrayList.get(i).getPlace_number() + "\n";
        }
        infoTV.setText(info);
        summaTV.setText("Сумма: " + 500*passengersArrayList.size());
        alert.setView(alertLayout);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(passengerNumber == 0)  passengerInfo = new PassengerInfo(routeIdTo,passengersArrayList,action);
                else   passengerInfo = new PassengerInfo(routeIdTo,routeIdBack,passengersArrayList,action);
                if(action.equals("B")){
                    viewModel.buyOrBook(token,passengerInfo).observe(PassengersDataActivity.this, new Observer<Answer>() {
                        @Override
                        public void onChanged(Answer answer) {
                            Log.d("TAG", "onChanged:answer:  " );
                            Toast.makeText(getBaseContext(), "Билеты успешно забранированы", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if(action.equals("S")){
                    Intent intent  = new Intent(PassengersDataActivity.this, PaymentOptionActivity.class);
                    intent.putExtra("passengerInfo", passengerInfo);
                    startActivity(intent);
                }


            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void query(){

    }
}
