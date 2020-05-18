package com.manas.avtobeketkg.Api;

import com.manas.avtobeketkg.Model.Answer;
import com.manas.avtobeketkg.Model.History;
import com.manas.avtobeketkg.Model.MapResponse;
import com.manas.avtobeketkg.Model.Passenger;
import com.manas.avtobeketkg.Model.PassengerInfo;
import com.manas.avtobeketkg.Model.Password;
import com.manas.avtobeketkg.Model.Route;
import com.manas.avtobeketkg.Model.Search;
import com.manas.avtobeketkg.Model.User;
import com.manas.avtobeketkg.Model.Stations;
import com.manas.avtobeketkg.Model.VokzalInfo;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiService {
    @POST("/api/login/")
    Call<User> userLogin(@Body User user);

    @Headers("Content-Type: application/json")
    @POST("/api/register/")
    Call<User>userSignUp(@Body User user);

    @GET("/api/stations/")
    Call<List<Stations>> getStations(@Header("Authorization") String token);

    @Headers("Content-Type: application/json")
    @POST("/api/route/")
    Call<List<Route>> getRoute(@Header("Authorization") String token, @Body Search search );

    @FormUrlEncoded
    @Headers("Content-Type: application/json")
    @POST("api/register/")
    Call<User> createUser(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password")  String password,
            @Field("first_name") String firstName,
            @Field("last_name") String lastName

    );

    @GET("/api/profile/")
    Call<User>userProfile(@Header("Authorization") String token);

    @PUT("/api/profile/")
    Call<User>editProfile(@Header("Authorization") String token, @Body User user);

    @POST("/api/buy_or_book_places/")
    Call<Answer> buyOrBook(@Header("Authorization") String token, @Body PassengerInfo passenger);
    /*Call<String> buyOrBook(
            @Header("Authorization") String token,
            @Field("trans_id") int id,
            @Field("places_to_book_or_buy") List<Passenger>passengers,
            @Field("action") String action
    );*/

    @PUT("/api/change_pass/")
    Call<Password>changePassword(@Header("Authorization") String token, @Body Password passwords);

    @POST("/maps/api/directions/json")
    Call<MapResponse>getDirection(@Query("origin") String origin,
                                  @Query("destination") String destination,
                                  @Query("key") String key);
    @GET("/api/history/")
    Call<List<History>> getHistory(@Header("Authorization") String token);

    @GET("/api/infoBusStations/")
    Call<List<VokzalInfo>> getVokzalInfo(@Header("Authorization") String token);






  /*  https://maps.googleapis.com/maps/api/directions/json?origin=42.9191833,74.5467319
    &destination=41.2674528,74.9501034
    &key=AIzaSyC0QHkHmtE9PHzWR-*/


}
