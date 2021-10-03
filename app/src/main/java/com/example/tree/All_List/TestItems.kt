package com.example.tree.All_List
import com.example.tree.neighbor.GetModel_list
import com.google.gson.annotations.SerializedName


data class GetModel_all_list(
    var name: String,
    var treeCount: Int,
    var rank: Int
)
class CheckGetAllModel(
    @SerializedName("content")
    val content : List<GetModel_all_list>
)