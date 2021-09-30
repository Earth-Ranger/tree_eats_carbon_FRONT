package com.example.tree.signup

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface APIS_signup {
    // 회원가입
    @POST("member/signup")
    fun register(
        @Body signup: Signup
    ): Call<Signup>
}