package com.manas.avtobeketkg.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.manas.avtobeketkg.Model.User;
import com.manas.avtobeketkg.R;
import com.manas.avtobeketkg.ViewModel.LoginViewModel;
import com.manas.avtobeketkg.ViewModel.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameEdittext, surnameEdittext, usernameEdittext, mailEdittext, paswordEdittext,confirmPasswordEdittext;
    RegisterViewModel registerViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        initial();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }
    public void initData(){
        findViewById(R.id.registerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEdittext.getText().toString().trim();
                String surname = surnameEdittext.getText().toString().trim();
                String username = usernameEdittext.getText().toString().trim();
                String mail = mailEdittext.getText().toString().trim();
                String password = paswordEdittext.getText().toString().trim();
                String confirmPassword = confirmPasswordEdittext.getText().toString().trim();
                name = "Gulzat";            surname = "Zheenbekova";
                mail = "gulza97@mail.ru";   username = "gulzat2zhk";
                password = "12345678dfk";      confirmPassword = "12345678dfk";

                if(validateData(name,surname,username,mail,password,confirmPassword)){
                    User newUser = new User(name,surname,username,password,confirmPassword);
                    registerViewModel.getRegisterResult(newUser).observe(RegisterActivity.this, new Observer<User>() {
                        @Override
                        public void onChanged(User user) {
                            Log.d("TAG", "onChanged: " + user.getToken());
                        }
                    });

                }
            }
        });
    }

    public void initial(){
        nameEdittext = findViewById(R.id.nameEditText);
        surnameEdittext = findViewById(R.id.surnameEditText);
        mailEdittext = findViewById(R.id.mailEditText);
        usernameEdittext = findViewById(R.id.userNameEditText);
        paswordEdittext = findViewById(R.id.passwordEditText);
        confirmPasswordEdittext = findViewById(R.id.confirmPasswordEditText);
    }

    public boolean validateData(String name, String surname, String username, String mail, String password, String confirmPassword){

        if(name.isEmpty()){
            nameEdittext.setError("enter a name");
            nameEdittext.requestFocus();
            return false;
        }
        if(surname.isEmpty()){
            surnameEdittext.setError("enter a surname");
            surnameEdittext.requestFocus();
            return false;
        }
        if(username.isEmpty()){
            usernameEdittext.setError("enter an username");
            usernameEdittext.requestFocus();
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            mailEdittext.setError("enter a valid email");
            mailEdittext.requestFocus();
            return false;
        }
        if(password.isEmpty()){
            paswordEdittext.setError("enter a password");
            paswordEdittext.requestFocus();
            return false;
        }
        if(password.length() < 8){
            paswordEdittext.setError("password is short");
            mailEdittext.requestFocus();
            return false;
        }
        if(confirmPassword.isEmpty()){
            confirmPasswordEdittext.setError("Повторите пароль");
            mailEdittext.requestFocus();
            return false;
        }
        if(confirmPassword.length() < 8 || password!=confirmPassword ){
            confirmPasswordEdittext.setError("Не правильно ввели повторный пароль");
            confirmPasswordEdittext.requestFocus();
            return false;
        }
        return true;
    }
}
