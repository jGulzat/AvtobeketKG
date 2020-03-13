package com.manas.avtobeketkg.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.manas.avtobeketkg.Model.User;
import com.manas.avtobeketkg.R;
import com.manas.avtobeketkg.ViewModel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final LoginViewModel loginViewModel1 = ViewModelProviders.of(this).get(LoginViewModel.class);
        userCheck(loginViewModel1);
        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewModel1.getLoginResult();
            }
        });


        initial();
    }
    private void initial(){
        loginBtn = findViewById(R.id.loginButton);

    }

    private void userCheck(LoginViewModel myViewModel){
        /*myViewModel.getLoginResult().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (null == user) return;
                Log.d("TAG/////////////", "onChanged: " + user.getToken());

            }
        });*/
    }

}
