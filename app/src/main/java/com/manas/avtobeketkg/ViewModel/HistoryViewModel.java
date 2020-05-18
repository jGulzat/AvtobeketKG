package com.manas.avtobeketkg.ViewModel;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.manas.avtobeketkg.Api.ApiServiceHelper;
import com.manas.avtobeketkg.Model.History;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryViewModel extends ViewModel {
    public HistoryViewModel() { super(); }

    @Override
    protected void onCleared() { super.onCleared(); }

    public LiveData<List<History>> getLiveDataHistory(String token){
        MutableLiveData<List<History>> liveDataHistory = new MutableLiveData<>();
        Call<List<History>> getHistoryCall = ApiServiceHelper.getInstance().getApiService().getHistory(token);
        getHistoryCall.enqueue(new Callback<List<History>>() {
            @Override
            public void onResponse(Call<List<History>> call, Response<List<History>> response) {
                if(response.isSuccessful()){
                    liveDataHistory.setValue(response.body());
                    Log.d("TAG", "onResponse:history succes");
                }
                else Log.d("TAG", "onResponse: history unsucces");
            }

            @Override
            public void onFailure(Call<List<History>> call, Throwable t) {
                Log.d("TAG", "onFailure:history " + t.getMessage());

            }
        });
        return  liveDataHistory;
    }
}
