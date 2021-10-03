package com.example.tree.Main

import com.example.tree.neighbor.GetModel_list
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Data_Request(
    val distance: Double,
    val transport: Int
):Serializable


data class Data_Response(
    var treeLevel : Int,
    var treeCount : Int,
    var totalReduction : Double,
    var levelReduction : Double
):Serializable

data class Get_Response(
    var tree : String,
    var info : String



):Serializable

data class GetModel_alllist(
    var memberId : Long,
    var treeCount: Int,
    var name: String
)

class getAllList(
    @SerializedName("allList")
    val getallList : List<GetModel_alllist>

)