package com.groep4.mindfulness.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Retrofit service of HTTP API om te zetten naar Java interface
 * https://square.github.io/retrofit/
 * */
public interface DbService {


    @GET("users/list")
    Call<List<String>> groupList(@Path("token") int token);

//    @POST("register")
   // Call<User> createUser(@Body User user);

}
