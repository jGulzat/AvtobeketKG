package com.manas.avtobeketkg.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.manas.avtobeketkg.Model.User;
import com.manas.avtobeketkg.R;
import com.manas.avtobeketkg.ViewModel.MyInfoViewModel;

public class ChangeMyInfoActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Token = "token";

    User userInfo;
    private EditText nameEdittext, surnameEdittext, usernameEdittext, emailEdittext;
    String token;
    MyInfoViewModel myInfoViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_my_info);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(Token))
        {   Log.d("TAG", "run: tokennnnnnnnnnn" + sharedpreferences.getString(Token, ""));
            token = sharedpreferences.getString(Token,"");  }

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
        nameEdittext = findViewById(R.id.editNameEditText);
        surnameEdittext = findViewById(R.id.editSurnameEdittext);
        usernameEdittext = findViewById(R.id.editUsernameEdittext);
        emailEdittext = findViewById(R.id.editEmailEdittext);

        nameEdittext.setText(name);
        surnameEdittext.setText(surname);
        usernameEdittext.setText(username);
        emailEdittext.setText(email);
    }

    public void saveNewInfo(View view) {
        Log.d("info", "saveNewInfo: " + nameEdittext.getText().toString());
        User userInfo = new User(nameEdittext.getText().toString(),
                surnameEdittext.getText().toString(),usernameEdittext.getText().toString(),emailEdittext.getText().toString());

        myInfoViewModel.editProfile("Token " + token,userInfo).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Log.d("TAG", "onChanged: " + user.getFirstname());
                setValue(user.getFirstname(),user.getLast_name(),user.getEmail(),user.getUsername());
                Toast.makeText(ChangeMyInfoActivity.this, "Ваш профиль успешно обновлен",Toast.LENGTH_LONG).show();
            }
        });
    }
}
