package com.manas.avtobeketkg.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.manas.avtobeketkg.Api.ApiServiceHelper;
import com.manas.avtobeketkg.Model.Stations;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewModel extends ViewModel {

    public SearchViewModel(){ super(); }
    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public LiveData<List<Stations>> getListStations(String token){
        MutableLiveData <List<Stations>> livestationsResponce = new MutableLiveData<>();

        Call<List<Stations>> stationCall = ApiServiceHelper.getInstance()
                .getApiService().getStations("Token " + token);
        stationCall.enqueue(new Callback<List<Stations>>() {
            @Override
            public void onResponse(Call<List<Stations>> call, Response<List<Stations>> response) {
                if (response.isSuccessful()){
                    Log.d("aaa", "onResponse: " + "succes: " + response.body().get(0).getName());

                    livestationsResponce.setValue(response.body());

                }
                else Log.d("aaa", "onResponse: " + "unsucces: "  + response.body());
            }
            @Override
            public void onFailure(Call<List<Stations>> call, Throwable t) {
                Log.d("aaa", "onFailure: " + "unsucces: " + t.getMessage());
            }
        });
        return livestationsResponce;
    }
}
