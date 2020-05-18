package com.manas.avtobeketkg.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;
import com.manas.avtobeketkg.MainActivity;
import com.manas.avtobeketkg.Model.Answer;
import com.manas.avtobeketkg.Model.Passenger;
import com.manas.avtobeketkg.Model.PassengerInfo;
import com.manas.avtobeketkg.R;
import com.manas.avtobeketkg.ViewModel.PaymentViewModel;

import java.util.ArrayList;

public class PaymentOptionActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Token = "token";

    PassengerInfo passengerInfo;
    TextView infoRoute,summaTv;
    String routeway, token;
    int size;
    PaymentViewModel paymentViewModel;

    CardForm cardForm;
    Button buyBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_option);

        Intent intent = getIntent();
        routeway = intent.getStringExtra("routeWay");
        passengerInfo =(PassengerInfo) intent.getSerializableExtra("passengerInfo");

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        if (sharedpreferences.contains(Token))
        {  Log.d("RouteActivity", "run: tokennnnnnnnnnn" + sharedpreferences.getString(Token, ""));
            token = sharedpreferences.getString(Token,""); }

        size = passengerInfo.getPlaces_to_book_or_buy().size();
        Log.d("TAG", "onCreate: " + passengerInfo.getAction()
         + "\nfullname:  " + passengerInfo.getPlaces_to_book_or_buy().get(0).getFullname());
        paymentViewModel = ViewModelProviders.of(this).get(PaymentViewModel.class);

        /*infoRoute = findViewById(R.id.infoRoute);
        summaTv = findViewById(R.id.summaTV);
        summaTv.setText("Сумма: " + 500*size + " сом");
        infoRoute.setText(routeway + " на " + size  + " человека ");*/

        initUI();

    }

    private void initUI() {
        cardForm = findViewById(R.id.card_form);
        buyBtn = findViewById(R.id.btnBuy);

        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("SMS is required on this number")
                .setup(PaymentOptionActivity.this);

        cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);

        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cardForm.isValid()) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(PaymentOptionActivity.this);
                    alertBuilder.setTitle("Confirm before purchase");
                    alertBuilder.setMessage("Card number: " + cardForm.getCardNumber() + "\n" +
                            "Card expiry date: " + cardForm.getExpirationDateEditText().getText().toString() + "\n" +
                            "Card CVV: " + cardForm.getCvv() + "\n" +
                            "Postal code: " + cardForm.getPostalCode() + "\n" +
                            "Phone number: " + cardForm.getMobileNumber());
                    alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            Toast.makeText(PaymentOptionActivity.this, "Thank you for purchase", Toast.LENGTH_LONG).show();
                            final ProgressDialog progressDoalog;
                            progressDoalog = new ProgressDialog(PaymentOptionActivity.this);
                            progressDoalog.setMessage("Its loading....");
                            progressDoalog.setTitle("Routes");
                            progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            // show it
                            progressDoalog.show();
                            paymentViewModel.buyOrBook("Token " + token, passengerInfo)
                                    .observe(PaymentOptionActivity.this, new Observer<Answer>() {
                                @Override
                                public void onChanged(Answer answer) {

                                }
                            });

                        }
                    });
                    alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = alertBuilder.create();
                    alertDialog.show();

                }else {
                    Toast.makeText(PaymentOptionActivity.this, "Please complete the form", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void choosecard(View view) {
        Toast.makeText(this,"Credit card",Toast.LENGTH_SHORT).show();
    }
}
