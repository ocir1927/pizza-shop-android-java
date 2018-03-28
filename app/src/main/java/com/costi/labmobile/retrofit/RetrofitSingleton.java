package com.costi.labmobile.retrofit;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Costi  on 14.01.2018 . All rights reserved.
 */

public class RetrofitSingleton {

    public static final String BASE_URL = "http://192.168.43.123:8080";

    private static Retrofit instance;

    private RetrofitSingleton() {

        instance = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public static Retrofit getInstance() {
        if (instance == null) {
            new RetrofitSingleton();
        }
        return instance;
    }

}
