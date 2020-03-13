package com.manas.avtobeketkg.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.manas.avtobeketkg.Api.ApiService;
import com.manas.avtobeketkg.Api.ApiServiceHelper;
import com.manas.avtobeketkg.Model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private User userResponce;
    private MutableLiveData<User> liveUserResponse;

    public LoginViewModel(){ super();}

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d("######", "onCleared called!");
    }

    public LiveData<User> getLoginResult(){
        String mail = "gulzat", password = "123456789";

        User user = new User(mail,password);
        liveUserResponse = new MutableLiveData<>();
        Call<User> userLoginCall = ApiServiceHelper.getInstance()
                .getApiService().userLogin(user);
        userLoginCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                liveUserResponse.setValue(response.body());
                Log.d("11111111111", "onResponse: " + "succes");
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("000000000", "onResponse: " + "unsucces: " + t.getMessage());
            }
        });
        if (null == liveUserResponse.getValue()) {
            Log.e("######", "Data newly loaded");
        } else {
            Log.e("@@@@@@@", "Old Data retrieved");
        }
        return liveUserResponse;

    }



}
