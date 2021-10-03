package com.example.tree.All_List

import com.example.tree.neighbor.CheckGetModel
import com.example.tree.neighbor.DeleteModel
import com.example.tree.neighbor.GetModel2
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface APIS_all {

    @GET("rank")
    fun AllSearch(
    ): Call<CheckGetAllModel>
}