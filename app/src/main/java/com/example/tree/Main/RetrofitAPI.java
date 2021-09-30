package com.example.tree.Main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitAPI {

    //distance
    @POST("carbon")
    Call<Double> post_distance(@Body DataModels_Main distance);

    @POST("carbon")
    Call<Integer> post_transport();

    @GET("carbon")
    Call<String> get_tree();

    @GET("carbon")
    Call<String> get_info();

}
