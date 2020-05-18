package com.manas.avtobeketkg.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.manas.avtobeketkg.Api.ApiServiceHelper;
import com.manas.avtobeketkg.Model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    public LoginViewModel() {
        super();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d("######", "onCleared called!");
    }

    public LiveData<User> getLoginResult(String username,String password) {
        MutableLiveData<User> liveUserResponse;

        User user = new User(username, password);
        liveUserResponse = new MutableLiveData<>();

        Call<User> userLoginCall = ApiServiceHelper.getInstance()
                .getApiService().userLogin(user);
        userLoginCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    liveUserResponse.setValue(response.body());
                    Log.d("aaa", "onResponse: " + "succes: " + response.body().getToken());
                }
                else Log.d("aaa", "onResponse: " + "unsucces: "  + response.code());
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("aaa", "onFailure: " + "unsucces: " + t.getMessage());
            }
        });
//        if (null == liveUserResponse.getValue()) {
//            Log.e("######", "Data newly loaded");
//        } else {
//            Log.e("@@@@@@@", "Old Data retrieved");
//        }
        return liveUserResponse;
    }
}
