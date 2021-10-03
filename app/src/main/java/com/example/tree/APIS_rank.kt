package com.example.tree

import com.example.tree.Main.getAllList
import com.example.tree.neighbor.CheckGetModel
import com.example.tree.neighbor.DeleteModel
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIS_Rank {

    // 전체 이웃 조회
    @GET("rank")
    fun get_All(
    ): Call<getAllList>

    //이웃 조회
    @GET("rank")
    fun get_Follow(
    ): Call<getAllList>

}