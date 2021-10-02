package com.example.tree.mypage

import com.example.tree.neighbor.GetModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIS_mypage {

    // 개인정보 조회
    @GET("mypage")
    fun mydataSearch(
    ): Call<GetModel2>

}

