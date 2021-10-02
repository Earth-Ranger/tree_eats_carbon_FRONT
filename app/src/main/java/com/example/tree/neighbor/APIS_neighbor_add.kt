package com.example.tree.neighbor

import retrofit2.Call
import retrofit2.http.*


interface APIS_neighbor_add {
    // 이웃 검색
    @GET("follow/search")
    fun neighborSearch(
        @Query("name") name: String
    ): Call<GetModel>

    // 이웃 추가
    @POST("follow/{memberId}")
    fun neighborAdd(@Path("memberId") memberId: Long
    ): Call<PostModel>



}
