package com.example.tree.All_List

import com.example.tree.neighbor.GetModel_list
import com.google.gson.annotations.SerializedName

data class GetModel_list(
    var id : Long,
    var name: String,
    var allCount: Int,
    var treeCount: Int
)
class CheckGetModel(
    @SerializedName("allList")
    val checkRoomList : List<GetModel_list>
)


data class GetModel2(
    var name: String,
    var treeLevel:Int,
    var treeCount: Int,
    var followingCount: Int,
    var followerCount: Int
)