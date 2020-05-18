package com.manas.avtobeketkg.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.manas.avtobeketkg.Api.ApiServiceHelper;
import com.manas.avtobeketkg.Model.Route;
import com.manas.avtobeketkg.Model.Search;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseSeatViewModel extends ViewModel {

    public ChooseSeatViewModel(){ super(); }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public LiveData<List<Route>> getRoute(String token, Search search){

        MutableLiveData<List<Route>> liveRouteListResponse = new MutableLiveData<>();

        Call<List<Route>> getRouteCall = ApiServiceHelper.getInstance()
                .getApiService().getRoute(token, search);
        getRouteCall.enqueue(new Callback<List<Route>>() {
            @Override
            public void onResponse(Call<List<Route>> call, Response<List<Route>> response) {
                if(response.isSuccessful()){
                    Log.d("TAG", "onResponse: getRoute succes");
                    liveRouteListResponse.setValue(response.body());
                }
                else  Log.d("TAG", "onResponse: getRoute unsucces: " + response.code());
            }
            @Override
            public void onFailure(Call<List<Route>> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });
        return liveRouteListResponse;
    }

}
