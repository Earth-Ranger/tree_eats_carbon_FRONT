package com.example.tree.login


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface APIS_login {
    // 로그인
    @POST("member/login")
    fun signin(
        @Body login: Login
    ): Call<user_token>


//    companion object { // static 처럼 공유객체로 사용가능함. 모든 인스턴스가 공유하는 객체로서 동작함.
//        private const val BASE_URL = "http://192.168.56.1:8080/" // 주소
//
//        fun create(): APIS_login {
//
//
//            val gson : Gson =   GsonBuilder().setLenient().create();
//
//            return Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build()
//                .create(APIS_login::class.java)
//        }
//    }

}