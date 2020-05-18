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

public class RegisterViewModel  extends ViewModel {

    public RegisterViewModel() {
        super();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d("######", "onCleared called!");
    }

    public LiveData<User> getRegisterResult(User user) {
        MutableLiveData<User> livenewUserResponse;

        livenewUserResponse = new MutableLiveData<>();

        Call<User> newUserCall = ApiServiceHelper.getInstance()
                .getApiService().createUser(user.getUsername(),user.getEmail(),user.getFirstname(),user.getLast_name(),user.getPassword());
        newUserCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    livenewUserResponse.setValue(response.body());
                    Log.d("aaa", "onResponse: " + "succes: " + response.body().getToken());
                }
                Log.d("aaa", "onResponse: " + "unsucces: "  + response.code()
                        +"\n " + response.errorBody().toString());
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
        return livenewUserResponse;
    }

}
