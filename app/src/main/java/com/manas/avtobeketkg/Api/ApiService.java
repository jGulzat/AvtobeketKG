package com.manas.avtobeketkg.Api;

import com.manas.avtobeketkg.Model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/api/login/")
    Call<User> userLogin(@Body User user);
}
