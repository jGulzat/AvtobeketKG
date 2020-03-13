package com.manas.avtobeketkg.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceHelper {

    public static ApiServiceHelper getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public ApiService getApiService() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://avtobeket.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ApiService.class);

    }

    private static class InstanceHolder {
        private static final ApiServiceHelper INSTANCE = new ApiServiceHelper();
    }
}
