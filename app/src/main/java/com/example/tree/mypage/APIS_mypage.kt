package com.example.tree.mypage

import com.example.tree.neighbor.GetModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface APIS_mypage {

    // 개인정보 조회
    @GET("mypage")
    fun mydataSearch(
    ): Call<GetModel2>

    // 개인 닉네임 변경
    @PUT("mypage")
    fun mydataChange(@Path("name") name: String
    ): Call<PUTModel>

}

