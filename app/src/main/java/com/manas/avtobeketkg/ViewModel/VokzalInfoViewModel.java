package com.manas.avtobeketkg.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.manas.avtobeketkg.Api.ApiService;
import com.manas.avtobeketkg.Api.ApiServiceHelper;
import com.manas.avtobeketkg.Model.VokzalInfo;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VokzalInfoViewModel extends ViewModel {
    public VokzalInfoViewModel() { super(); }

    @Override
    protected void onCleared() { super.onCleared(); }

    public LiveData<List<VokzalInfo>>getVokzalInfo(String token)
    {
        MutableLiveData<List<VokzalInfo>> liveVokzalListResponse = new MutableLiveData<>();
        Call<List<VokzalInfo>> getVokzalInfo = ApiServiceHelper.getInstance()
                .getApiService().getVokzalInfo(token);
        getVokzalInfo.enqueue(new Callback<List<VokzalInfo>>() {
            @Override
            public void onResponse(Call<List<VokzalInfo>> call, Response<List<VokzalInfo>> response) {
                if(response.isSuccessful())
                {
                    liveVokzalListResponse.setValue(response.body());
                    Log.d("TAG", "onResponse:vokzalInfo succes");
                }
                else Log.d("TAG", "onResponse:vokzalInfo unsucces");
            }

            @Override
            public void onFailure(Call<List<VokzalInfo>> call, Throwable t) {

            }
        });

        return liveVokzalListResponse;
    }
}
