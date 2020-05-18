package com.manas.avtobeketkg.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.manas.avtobeketkg.Model.Password;
import com.manas.avtobeketkg.R;
import com.manas.avtobeketkg.ViewModel.MyInfoViewModel;

public class ChangePassActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Token = "token";

    private EditText oldPass,newPass,validNewPass;
    String oldPassword, newPassword, validNewPasword,token;

    MyInfoViewModel myInfoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
          if (sharedpreferences.contains(Token))
           { token = sharedpreferences.getString(Token, ""); }

          myInfoViewModel = ViewModelProviders.of(this).get(MyInfoViewModel.class);
          init();
    }

    public void changePassword(View view) {
        oldPassword = oldPass.getText().toString();
        newPassword = newPass.getText().toString();
        validNewPasword = validNewPass.getText().toString();

        oldPassword =  "jeenbekova97";
        newPassword = "jeenbekova98";
        validNewPasword = "jeenbekova98";

        if(newPassword == validNewPasword) {
            Password password = new Password(oldPassword,newPassword);
            myInfoViewModel.changePass("Token " + token,password)
                    .observe(this, new Observer<Password>() {
                        @Override
                        public void onChanged(Password password) {
                            Toast.makeText(ChangePassActivity.this,"Пароль успешно изменен!", Toast.LENGTH_SHORT).show();
                        }
                    });

        }
        }

    private void init() {
        oldPass = findViewById(R.id.oldPassEditText);
        newPass = findViewById(R.id.newPassEditText);
        validNewPass = findViewById(R.id.confirmPassEditText);

    }
}
