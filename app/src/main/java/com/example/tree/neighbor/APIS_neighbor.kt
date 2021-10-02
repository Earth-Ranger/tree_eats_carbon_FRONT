package com.example.tree.neighbor

import retrofit2.Call
import retrofit2.http.*


interface APIS_neighbor {
    // 이웃 삭제
    @DELETE("follow/{memberId}")
    fun neighborDelete(@Path("memberId") memberId: Long
    ): Call<DeleteModel>

    // 전체 이웃 조회
    @GET("follow")
    fun AllneighborSearch(
    ): Call<CheckGetModel>

}