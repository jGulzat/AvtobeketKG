package com.manas.avtobeketkg.UI.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.manas.avtobeketkg.R;
import com.manas.avtobeketkg.UI.Route2Activity;
import com.manas.avtobeketkg.UI.RouteActivity;
import com.manas.avtobeketkg.ViewModel.SearchViewModel;
import com.manas.avtobeketkg.Model.Stations;
import com.squareup.timessquare.CalendarPickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    public SearchFragment() {
        // Required empty public constructor
    }

    public SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Token = "token";

    private SearchViewModel searchViewModel;
    private Date today;
    private Calendar nextYear;
    private EditText date1Edittext, date2Edittext;
    private TextView dateTextView;
    private AutoCompleteTextView fromAutoCompleteTextView, toAutoCompleteTextView;
    private Button searchBtn;
    private String fromStation,toStation;
    private String date1, date2;
    private View v;
    private String token;
    private CalendarPickerView calendar_view;
    RelativeLayout relativeLayoutcpv;
    private RadioButton radioButton1, radioButton2;
    private List<Date> selecteddates;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private List<Stations> stations1;
    private String routeWay;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchViewModel = ViewModelProviders.of(requireActivity()).get(SearchViewModel.class);
        sharedpreferences = this.getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(Token))
        { Log.d("SearchFragment", "run: tokennnnnnnnnnn" + sharedpreferences.getString(Token, ""));
          token = sharedpreferences.getString(Token, ""); }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_search, container, false);

        initUI();
        initDate();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchViewModel.getListStations(token).observe(this, new Observer<List<Stations>>() {
            @Override
            public void onChanged(List<Stations> stations) {
                Log.d(TAG, "onChanged: " + stations.get(0).getName() + " id:" + stations.get(0).getId());
                String stationName[] = new String[stations.size()];
                stations1 = stations;

                for (int i = 0; i < stations.size(); i++)
                {   stationName[i] = stations.get(i).getName(); }

                fromAutoCompleteTextView.setAdapter(new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_dropdown_item_1line, stationName));
                toAutoCompleteTextView.setAdapter(new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_dropdown_item_1line, stationName));
            }
        });
    }

    public void initUI(){
        relativeLayoutcpv = v.findViewById(R.id.relativelayoutCPV);
        radioButton1 = v.findViewById(R.id.radiobtn_1);
        radioButton2 = v.findViewById(R.id.radiobtn_2);
        date1Edittext = v.findViewById(R.id.date1EdT);
        date2Edittext = v.findViewById(R.id.date2EdT);
        dateTextView = v.findViewById(R.id.currentDate);
        fromAutoCompleteTextView = v.findViewById(R.id.fromACTV);
        toAutoCompleteTextView = v.findViewById(R.id.toACTV);
        searchBtn = v.findViewById(R.id.searchBtn);
        dateTextView = v.findViewById(R.id.currentDate);
        calendar_view = v.findViewById(R.id.calendar_view1);
    }

    public void initDate(){
        nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        today = new Date();
        calendar_view.init(today, nextYear.getTime());
        selecteddates = calendar_view.getSelectedDates();

        Date todayDate = Calendar.getInstance().getTime();
        String todayString = formatter.format(todayDate);
        dateTextView.setText("Today\n" + todayString);

        if(radioButton1.isChecked()) OnRadioButton1Clicked();
        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioButton1.isChecked()) OnRadioButton1Clicked();
            }
        });

        radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioButton2.isChecked()) OnRadioButton2Clicked();
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchRoute();
            }
        });
    }

    private void searchRoute() {
        fromStation = fromAutoCompleteTextView.getText().toString();
        toStation = toAutoCompleteTextView.getText().toString();

        int fromId = 0, toId = 0;

        if(!(fromStation.isEmpty() && toStation.isEmpty())){
            routeWay = fromStation + " --> " + toStation;

            if(date1 != null && date2 == null){

                for(int i = 0; i < stations1.size(); i++ )
                {   if(stations1.get(i).getName().equals(fromStation) ) fromId = stations1.get(i).getId();
                    if(stations1.get(i).getName().equals(toStation)) toId = stations1.get(i).getId();
                }
                Intent i = new Intent(getActivity(), RouteActivity.class);
                i.putExtra("routeway", routeWay);
                i.putExtra("from", fromId);
                i.putExtra("to", toId);
                i.putExtra("date", date1);
                startActivity(i);
            }
            else if(date1 != null && date2 != null){

                for(int i = 0; i < stations1.size(); i++ )
                {   if(stations1.get(i).getName().equals(fromStation) ) fromId = stations1.get(i).getId();
                    if(stations1.get(i).getName().equals(toStation)) toId = stations1.get(i).getId();
                }
                Intent intent = new Intent(getContext(), Route2Activity.class);
                intent.putExtra("routeWay", routeWay);
                intent.putExtra("from", fromId);
                intent.putExtra("to", toId);
                intent.putExtra("date1", date1);
                intent.putExtra("date2", date2);
                startActivity(intent);

            }
        }
    }

    public void OnRadioButton1Clicked(){
        date2 = null; date1 = null;
        Log.d(TAG, "OnRadioButton1Clicked: " + "worked");
        date2Edittext.setVisibility(View.GONE);
        date1Edittext.getText().clear();
        calendar_view.init(today, nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.SINGLE);

        calendar_view.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                date1 = formatter.format(calendar_view.getSelectedDate());
                date1Edittext.setText(date1);
                Log.d(TAG, "onDateSelected: " + date1);
            }
            @Override
            public void onDateUnselected(Date date) {

            }
        });
    }

    public void OnRadioButton2Clicked(){
        date2 = null; date1 = null;
        Log.d(TAG, "OnRadioButton2Clicked: " + "worked");
        date1Edittext.getText().clear();
        date2Edittext.getText().clear();
        date2Edittext.setVisibility(View.VISIBLE);
        calendar_view.init(today, nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE);


        calendar_view.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                selecteddates = calendar_view.getSelectedDates();
                if(selecteddates.size() >= 2){
                    date1 = formatter.format(selecteddates.get(0));
                    date2 = formatter.format(selecteddates.get(selecteddates.size()-1));
                    Log.d(TAG, "onDateSelected: ssss" + selecteddates + "\ns0:   " + date2);
                    date1Edittext.setText(date1);
                    date2Edittext.setText(date2);
                }
            }
            @Override
            public void onDateUnselected(Date date) {

            }
        });
    }
}
