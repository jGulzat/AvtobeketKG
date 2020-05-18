package com.manas.avtobeketkg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;

import com.manas.avtobeketkg.Model.Route;
import com.manas.avtobeketkg.UI.ChooseSeatActivity;
import com.manas.avtobeketkg.UI.LoginActivity;
import com.manas.avtobeketkg.UI.NavigationActivity;
import com.manas.avtobeketkg.UI.PaymentOptionActivity;
import com.manas.avtobeketkg.UI.Route2Activity;
import com.manas.avtobeketkg.UI.RouteActivity;
import com.manas.avtobeketkg.UI.RouteMapActivity;
import com.manas.avtobeketkg.UI.ServiceActivity;

import java.util.prefs.Preferences;

import maes.tech.intentanim.CustomIntent;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Token = "token";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                if (sharedpreferences.contains(Token)) {
                    Log.d("TAG", "run: tokennnnnnnnnnn" + sharedpreferences.getString(Token, ""));
                    final Intent mainIntent = new Intent(MainActivity.this, NavigationActivity.class);
                    MainActivity.this.startActivity(mainIntent);
                    MainActivity.this.finish();
                    CustomIntent.customType(MainActivity.this,"fadein-to-fadeout");
                }else{
                    final Intent mainIntent = new Intent(MainActivity.this, LoginActivity.class);
                    MainActivity.this.startActivity(mainIntent);
                    MainActivity.this.finish();
                    CustomIntent.customType(MainActivity.this,"fadein-to-fadeout");

                }
                    }
        }, 2000);

    }
}
