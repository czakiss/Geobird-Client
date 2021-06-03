package com.example.geobirdclient.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    private static final String BASE_URL = "http://vps-9187b231.vps.ovh.net:8080";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Api.getBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static String getBaseUrl() {
        return BASE_URL;
    }
    public static Retrofit getRetrofit() {
        return retrofit;
    }

}
