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

    // 이웃트리 상세정보 확인 (다이얼로그)
    @GET("plant/{memberId}")
    fun neighbortreeView(@Path("memberId") memberId: Long
    ): Call<GetModel2>

}