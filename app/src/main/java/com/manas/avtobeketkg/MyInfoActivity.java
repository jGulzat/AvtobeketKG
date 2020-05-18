package com.manas.avtobeketkg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.manas.avtobeketkg.Model.User;
import com.manas.avtobeketkg.UI.ChangeMyInfoActivity;
import com.manas.avtobeketkg.ViewModel.MyInfoViewModel;

public class MyInfoActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Token = "token";

    private TextView nameTextView, surnameTextVew, usernameTextView, mailTextView;
    User userResponse;
    String token;
    MyInfoViewModel myInfoViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        if (sharedpreferences.contains(Token)) {
            Log.d("TAG", "run: tokennnnnnnnnnn" + sharedpreferences.getString(Token, ""));
            token = sharedpreferences.getString(Token,"");}

            myInfoViewModel = ViewModelProviders.of(this).get(MyInfoViewModel.class);
    }

    @Override
    protected void onStart() {
        super.onStart();

     myInfoViewModel.getUserInfo("Token " + token).observe(this, new Observer<User>() {
         @Override
         public void onChanged(User user) {
             Log.d("TAG", "onChanged: " + user.getFirstname());
             setValue(user.getFirstname(),user.getLast_name(),user.getEmail(),user.getUsername());
         }
     });

    }

    public void  setValue(String name, String surname, String email, String username){
        nameTextView = findViewById(R.id.nameTextView);
        surnameTextVew = findViewById(R.id.surnameTextView);
        usernameTextView = findViewById(R.id.usernameTextView);
        mailTextView = findViewById(R.id.emailTextView);

        nameTextView.setText(name);
        surnameTextVew.setText(surname);
        usernameTextView.setText(username);
        mailTextView.setText(email);
    }


    public void editProfile(View view) {
        Intent i = new Intent(this, ChangeMyInfoActivity.class);
        startActivity(i);
    }
}
