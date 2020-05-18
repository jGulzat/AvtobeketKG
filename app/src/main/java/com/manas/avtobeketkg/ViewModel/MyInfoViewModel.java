package com.manas.avtobeketkg.ViewModel;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.manas.avtobeketkg.Api.ApiServiceHelper;
import com.manas.avtobeketkg.Model.Passenger;
import com.manas.avtobeketkg.Model.Password;
import com.manas.avtobeketkg.Model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyInfoViewModel extends ViewModel {

    public MyInfoViewModel(){ super(); }
    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public LiveData<User> getUserInfo(String token){
        MutableLiveData<User> liveUserResponse = new MutableLiveData<>();

        Call<User> getUserInfocall = ApiServiceHelper.getInstance()
                .getApiService().userProfile(token);
        getUserInfocall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    Log.d("TAG", "onResponse: " + response.body().getFirstname());
                    liveUserResponse.setValue(response.body());
                }
                else  Log.d("TAG", "onResponse: unsucces:  " + response.code());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());

            }
        });
        return liveUserResponse;
    }

    public LiveData<User> editProfile(String token,User user){
        MutableLiveData<User> liveProfileResponse = new MutableLiveData<>();
        Call<User> editProfilecall = ApiServiceHelper.getInstance()
                .getApiService().editProfile(token,user);
        editProfilecall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    Log.d("TAG", "onResponse: " + response.body().getFirstname());
                    liveProfileResponse.setValue(response.body());
                }
                else  Log.d("TAG", "onResponse: unsucces:  " + response.code());
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });

        return liveProfileResponse;
    }

    public LiveData<Password> changePass(String token, Password password){
        MutableLiveData<Password>livePasswordResponse = new MutableLiveData<>();
        Call<Password>changePassword = ApiServiceHelper.getInstance().getApiService()
                .changePassword(token,password);
        changePassword.enqueue(new Callback<Password>() {
            @Override
            public void onResponse(Call<Password> call, Response<Password> response) {
                if(response.isSuccessful()){
                    livePasswordResponse.setValue(response.body());
                    Log.d("ChangePassword", "Пароль успешно изменен!");
                }
                else Log.d("ChangePassword", "eeeeerror:  " + response.code());
            }
            @Override
            public void onFailure(Call<Password> call, Throwable t) {
                Log.d("ChangePassword", "onFailureeeeeeeee: " + t.getMessage());
            }
        });

        return livePasswordResponse;
    }
}
