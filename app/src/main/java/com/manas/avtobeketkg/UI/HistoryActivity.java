package com.manas.avtobeketkg.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.manas.avtobeketkg.Model.History;
import com.manas.avtobeketkg.R;
import com.manas.avtobeketkg.ViewModel.HistoryViewModel;
import com.manas.avtobeketkg.adapter.HistoryAdapter;
import com.manas.avtobeketkg.adapter.RoutesAdapter;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Token = "token";

    RecyclerView historyRecycleView;
    HistoryAdapter historyAdapter;
    ArrayList<History> historyArrayList = new ArrayList<>();
    String token;
    HistoryViewModel historyViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getToken();
        initUI();
        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);


    }

    private void initUI() {
        historyRecycleView = findViewById(R.id.historyRV);
    }

    private void getToken() {
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        if (sharedpreferences.contains(Token))
        {  Log.d("RouteActivity", "run: tokennnnnnnnnnn" + sharedpreferences.getString(Token, ""));
            token = sharedpreferences.getString(Token,""); }

    }

    @Override
    protected void onStart() {
        super.onStart();
        getHistory();
    }

    private void getHistory() {
        historyRecycleView.setLayoutManager(new LinearLayoutManager(this));

        historyViewModel.getLiveDataHistory("Token " + token).observe(this, new Observer<List<History>>() {
            @Override
            public void onChanged(List<History> histories) {
                historyArrayList = (ArrayList<History>) histories;
                historyAdapter = new HistoryAdapter(HistoryActivity.this,historyArrayList);
                historyRecycleView.setAdapter(historyAdapter);

            }
        });
    }
}
