package com.groep4.mindfulness.services;

import com.groep4.mindfulness.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Retrofit service of HTTP API om te zetten naar Java interface
 * https://square.github.io/retrofit/
 * */
public interface DbService {


    @GET("users")
    Call<List<User>> getUsers();

    @POST("users/register")
    Call<User> createUser(@Body User user);


    @POST("users/register")
    @FormUrlEncoded
    Call<User> saveUser(@Field("email") String email,
                        @Field("token") String token);

}
