package com.manas.avtobeketkg.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.manas.avtobeketkg.Model.User;
import com.manas.avtobeketkg.R;
import com.manas.avtobeketkg.ViewModel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    Button loginBtn;
    EditText usernameeditText,passwordEdittext;
    String token = null;
    String username,password;
    private LoginViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

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
                username = "gulzat";
                password = "123456789";

                if(validateData(username,password)){
                    viewModel.getLoginResult(username,password).observe(LoginActivity.this, new Observer<User>() {
                        @Override
                        public void onChanged(User user) {
                            if(user.getToken()!=null){
                                Log.d("TAG", "not null: " + user.getToken());
                                Toast.makeText(LoginActivity.this, "Login succes",Toast.LENGTH_LONG).show();

                                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
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
}
