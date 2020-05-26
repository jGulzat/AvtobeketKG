package com.manas.avtobeketkg.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.manas.avtobeketkg.R;
import com.squareup.picasso.Picasso;

public class VokzalInfoActivity extends AppCompatActivity {

    String vokzalName, phoneNumber, vokzalInfo,image;
    TextView vokzalNameTV, phoneNumberTV, vokzalInfoTV;
    Button callBtn;
    ImageView vokzalImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vokzal_info);
        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("phoneNumber");
        vokzalName = intent.getStringExtra("name");
        vokzalInfo = intent.getStringExtra("vokzalInfo");
        image = intent.getStringExtra("image");



        initUI();

    }

    private void initUI() {
        vokzalNameTV = findViewById(R.id.vokzalNameTV);
        vokzalInfoTV = findViewById(R.id.vokzalInfoTV);
        phoneNumberTV = findViewById(R.id.phoneNumberTV);
        vokzalImage = findViewById(R.id.vokzalIV);
        callBtn = findViewById(R.id.callBtn);

        Picasso.with(vokzalImage.getContext()).load("https://avtobeketkg.herokuapp.com" + image)
                .into(vokzalImage);

        phoneNumberTV.setText(phoneNumber);
        vokzalNameTV.setText(vokzalName);
        vokzalInfoTV.setText(vokzalInfo);
    }

    public void call(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        Log.d("TAG", "call: calliiiiiiiiiiing" + phoneNumber );
        startActivity(intent);
    }
}
