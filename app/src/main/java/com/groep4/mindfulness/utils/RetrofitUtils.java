package com.groep4.mindfulness.utils;

import com.groep4.mindfulness.services.DbService;

public class RetrofitUtils   {

    private RetrofitUtils(){}

    public static final String BASE_URL = "http://10.0.2.2:3000/";
    public static DbService getDbService(){
        return RetrofitClient.getClient(BASE_URL).create(DbService.class);
    }
}