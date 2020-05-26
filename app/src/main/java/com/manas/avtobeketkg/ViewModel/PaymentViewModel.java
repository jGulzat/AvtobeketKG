package com.manas.avtobeketkg.ViewModel;

import android.app.ProgressDialog;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.common.api.Api;
import com.manas.avtobeketkg.Api.ApiService;
import com.manas.avtobeketkg.Api.ApiServiceHelper;
import com.manas.avtobeketkg.Model.Answer;
import com.manas.avtobeketkg.Model.PassengerInfo;
import com.manas.avtobeketkg.UI.PaymentOptionActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentViewModel extends ViewModel {

    public PaymentViewModel(){ super(); }

    @Override
    protected void onCleared() { super.onCleared(); }

    public LiveData<Answer>buyOrBook(String token,PassengerInfo passengerInfo){
        MutableLiveData<Answer> liveAnswerResponse = new MutableLiveData<>();
        Log.d("TAG", "buyOrBook: viewModel");
        Call<Answer> buyORbookCall = ApiServiceHelper.getInstance()
                .getApiService().buyOrBook(token, passengerInfo);

        buyORbookCall.enqueue(new Callback<Answer>() {
            @Override
            public void onResponse(Call<Answer> call, Response<Answer> response) {

                if(response.isSuccessful()){
                    Log.d("TAG", "onResponse: payment succes" + response.body().toString());
                    liveAnswerResponse.setValue(response.body());
                }
                else Log.d("TAG", "onResponse: payment unsucces: " + response.code());
            }

            @Override
            public void onFailure(Call<Answer> call, Throwable t) {
                Log.d("TAG", "Failure: " + t.getMessage());
            }
        });

        return liveAnswerResponse;
    }

    public LiveData<Answer> buyOrBookTO(String token, PassengerInfo passengerInfo){

        MutableLiveData<Answer> liveDataAnswer = new MutableLiveData<>();
        Call<Answer>buyOrBookTOCall = ApiServiceHelper.getInstance()
                .getApiService().buyOrBookTO(token, passengerInfo);
        buyOrBookTOCall.enqueue(new Callback<Answer>() {
            @Override
            public void onResponse(Call<Answer> call, Response<Answer> response) {

                if(response.isSuccessful()){
                    liveDataAnswer.setValue(response.body());
                }
                else Log.d("TAG", "onResponse: payment unsuccesTO: " + response.code());
            }

            @Override
            public void onFailure(Call<Answer> call, Throwable t) {
                Log.d("TAG", "Failure TO: " + t.getMessage());
            }
        });

        return  liveDataAnswer;
    }
}
