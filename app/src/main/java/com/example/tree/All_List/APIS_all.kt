package com.example.tree.All_List

import com.example.tree.neighbor.CheckGetModel
import com.example.tree.neighbor.DeleteModel
import com.example.tree.neighbor.GetModel2
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface APIS_all {
    @DELETE("follow/{memberId}")
    fun allDelete(@Path("memberId") memberId: Long
    ): Call<DeleteModel>

    // 전체 이웃 조회
    @GET("follow")
    fun Allranking(
    ): Call<CheckGetModel>

    // 이웃트리 상세정보 확인 (다이얼로그)
    @GET("plant/{memberId}")
    fun peopletreeView(@Path("memberId") memberId: Long
    ): Call<GetModel2>

}