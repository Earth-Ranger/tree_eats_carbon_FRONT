package com.example.tree.Main

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitAPI {
    @POST("carbon")
    fun createPost(@Body post: Data_Request): Call<Data_Response>

    @GET("carbon")
    fun get_TreeTip (): Call<Get_Response>


    @GET("plant")
    fun get_plant (): Call<TreeDTO>
}