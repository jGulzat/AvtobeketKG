package com.manas.avtobeketkg.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.manas.avtobeketkg.R;
import com.manas.avtobeketkg.ViewModel.ChooseSeatViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChooseSeatActivity extends AppCompatActivity implements View.OnClickListener {

    TextView wayTV;
    ViewGroup layout;
    ChooseSeatViewModel chooseSeatViewModel;
    int routeId;
    String routeway, price;
    Set<Integer> selectedsets = new HashSet<Integer>();
    String seats;
           /* = "EKKKEn"
            +"PEEEn"
            +"PPEPn"
            +"PPEPn"
            +"PPEPn"
            +"____n"
            +"PPEPn"
            +"PPEPn"
            +"PPEPn"
            +"PEEPn";
   /* String seats
            = "_KKK_/"
            + "_____/"
            + "UU_AR/"
            + "UU_UA/"
            + "AA_AA/"
            + "AA_RU/"
            + "UU_UA/"
            + "RR_AA/"
            + "AA_RR/"
            + "AA_UU/"
            + "_____/"
            + "UU_AR/"
            + "RR_AA/"
            + "AA_UA/"
            + "AA_AA/"
            + "_____/";*/

    List<TextView> seatViewList = new ArrayList<>();
    int seatSize = 100;
    int seatGaping = 5;

    int status = 0;
    int STATUS_AVAILABLE = 1;
    int STATUS_BOOKED = 2;
    int STATUS_RESERVED = 3;
    String selectedIds = "";

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_seat);
         chooseSeatViewModel = ViewModelProviders.of(this).get(ChooseSeatViewModel.class);

         Intent intent = getIntent();
         seats = intent.getStringExtra("trans_shema");
         routeId = intent.getIntExtra("routeId",-1);
         routeway = intent.getStringExtra("routeway");
         price = intent.getStringExtra("price");
         selectedsets.clear();
         selectedIds = "";

        Log.d("TAG", "onCreate: transshema: " + seats + "\nid: " + routeId + "\nway: " + routeway);


        wayTV = findViewById(R.id.routeWay);
        wayTV.setText(routeway);
        layout = findViewById(R.id.layoutSeat);
        seats = "n" + seats;

        LinearLayout layoutSeat = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutSeat.setOrientation(LinearLayout.VERTICAL);
        layoutSeat.setLayoutParams(params);
        layoutSeat.setPadding(8 * seatGaping, 8 * seatGaping, 8 * seatGaping, 8 * seatGaping);
        layout.addView(layoutSeat);

        LinearLayout layout = null;

        int count = 0;

        for (int index = 0; index < seats.length(); index++) {
            if (seats.charAt(index) == 'n') {
                layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.HORIZONTAL);
                layoutSeat.addView(layout);

            } else if (seats.charAt(index) == 'S') {
                count++;
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setPadding(0, 0, 0, 2 * seatGaping);
                view.setId(count);
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource(R.drawable.ic_seats_book);
                view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#04004C")));
                view.setTextColor(Color.WHITE);
                view.setTag(STATUS_BOOKED);
                view.setText(count + "");
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                layout.addView(view);
                seatViewList.add(view);
                view.setOnClickListener(this);
            } else if (seats.charAt(index) == 'P') {
                count++;
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setPadding(0, 0, 0, 2 * seatGaping);
                view.setId(count);
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource(R.drawable.ic_seats_book);
                view.setText(count + "");
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                view.setTextColor(Color.BLACK);
                view.setTag(STATUS_AVAILABLE);
                layout.addView(view);
                seatViewList.add(view);
                view.setOnClickListener(this);
            } else if (seats.charAt(index) == 'B') {
                count++;
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setPadding(0, 0, 0, 2 * seatGaping);
                view.setId(count);
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource(R.drawable.ic_seats_book);
                view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#DB1864")));
                view.setText(count + "");
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                view.setTextColor(Color.WHITE);
                view.setTag(STATUS_RESERVED);
                layout.addView(view);
                seatViewList.add(view);
                view.setOnClickListener(this);
            } else if (seats.charAt(index) == 'E') {
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, 50);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping,0);
                view.setLayoutParams(layoutParams);
                view.setBackgroundColor(Color.TRANSPARENT);
                view.setText("");
                layout.addView(view);
            }else if(seats.charAt(index) == 'V'){
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, 0);
                view.setLayoutParams(layoutParams);
                view.setPadding(0, 0, 0, 2 * seatGaping);
                view.setId(count);
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource(R.drawable.driver);
                layout.addView(view);
                seatViewList.add(view);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        routeList();
    }

    public void routeList(){


    }

    @Override
    public void onClick(View view) {
        if ((int) view.getTag() == STATUS_AVAILABLE) {
            if (selectedIds.contains(view.getId() + ",")) {
                selectedIds = selectedIds.replace(+view.getId() + ",", "");
                view.setBackgroundResource(R.drawable.ic_seats_book);

                selectedsets.remove(view.getId());
                Log.d("TAG", "onClick: remove: " + selectedsets);


                Log.d("TAG", "onClick: seatsId" + view.getId());
            } else {
                selectedsets.add(view.getId());

                selectedIds = selectedIds + view.getId() + ",";
                view.setBackgroundResource(R.drawable.ic_seats_selected);
                Log.d("TAG", "onClick: seatsId1" + view.getId() + "\nselected: " + selectedsets
                        + "\nid: " + view.getId());
            }
        } else if ((int) view.getTag() == STATUS_BOOKED) {
            Toast.makeText(this, "Seat " + view.getId() + " is Booked", Toast.LENGTH_SHORT).show();
        } else if ((int) view.getTag() == STATUS_RESERVED) {
            Toast.makeText(this, "Seat " + view.getId() + " is Reserved", Toast.LENGTH_SHORT).show();
        }

        Log.d("TAG", "onClick: seats" + seats);
    }

    public void BuyTicket(View view) {

        Intent intent = new Intent(this,PassengersDataActivity.class);
        intent.putExtra("selectedseats", (Serializable) selectedsets);
        intent.putExtra("routeIdTo",routeId);
        intent.putExtra("action","S");
        intent.putExtra("price",price);
        intent.putExtra("routeWay",routeway);
        startActivity(intent);
        finish();
    }

    public void bookTicket(View view) {

        Intent intent = new Intent(this,PassengersDataActivity.class);
        intent.putExtra("selectedseats", (Serializable) selectedsets);
        intent.putExtra("routeIdTo",routeId);
        intent.putExtra("routeWay",routeway);
        intent.putExtra("action","B");
        intent.putExtra("price",price);
        startActivity(intent);
        finish();
    }
}
