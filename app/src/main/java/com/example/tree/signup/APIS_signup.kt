package com.example.tree.signup

import retrofit2.Call
import retrofit2.http.*

interface APIS_signup {
    // 회원가입
    @POST("member/signup")
    fun register(
        @Body signup: Signup
    ): Call<Signup>

    // 닉네임 중복 여부
    @GET("member")
    fun get_nickname(@Query("name") name: String): Call<CheckGetModel2>

}