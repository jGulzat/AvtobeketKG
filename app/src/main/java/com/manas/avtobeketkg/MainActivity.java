package com.manas.avtobeketkg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

import com.manas.avtobeketkg.UI.LoginActivity;

import java.util.prefs.Preferences;

import maes.tech.intentanim.CustomIntent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    final Intent mainIntent = new Intent(MainActivity.this, LoginActivity.class);
                    MainActivity.this.startActivity(mainIntent);
                    MainActivity.this.finish();
                    CustomIntent.customType(MainActivity.this,"fadein-to-fadeout");
            }
        }, 2000);

    }
}
