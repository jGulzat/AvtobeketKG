package com.manas.avtobeketkg.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.manas.avtobeketkg.Model.User;
import com.manas.avtobeketkg.R;
import com.manas.avtobeketkg.ViewModel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Token = "token";

    Button loginBtn;
    EditText usernameeditText,passwordEdittext;
    String username,password;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        initUI();

    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    private void initData() {

        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameeditText.getText().toString();
                password = passwordEdittext.getText().toString();
                username = "gulzats";
                password = "jeenbekova98";

                if(validateData(username,password)){
                    viewModel.getLoginResult(username,password).observe(LoginActivity.this, new Observer<User>() {
                        @Override
                        public void onChanged(User user) {
                            if(user.getToken()!=null){
                                Log.d("TAG", "not null: " + user.getToken());
                                Toast.makeText(LoginActivity.this, "Login succes",Toast.LENGTH_SHORT).show();

                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(Token, user.getToken());
                                editor.commit();

                                Intent i = new Intent(LoginActivity.this,NavigationActivity.class);
                                startActivity(i);
                            }
                        }
                    });
                }
            }
        });


    }

    private void initUI() {
        loginBtn = findViewById(R.id.loginButton);
        usernameeditText = findViewById(R.id.usernameEditText);
        passwordEdittext = findViewById(R.id.passwordEditText);
    }
    public Boolean validateData(String mail, String password)
    {

        if(mail.isEmpty()){
            usernameeditText.setError("enter an username");
            usernameeditText.requestFocus();
            return false;
        }
        if(password.isEmpty()){
            passwordEdittext.setError("enter a password");
            passwordEdittext.requestFocus();
            return false;
        }
        if(password.length() < 8){
            passwordEdittext.setError("password is short");
            passwordEdittext.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void RegisterNewUser(View view) {
        Intent i = new Intent(this,RegisterActivity.class);
        startActivity(i);
    }
}
