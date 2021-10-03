package com.example.tree.All_List

import com.example.tree.neighbor.GetModel_list
import com.google.gson.annotations.SerializedName

data class GetRank(
    var memberId : Long,
    var name: String,
    var treeCount: Double
)


data class GetModel2(
    var name: String,
    var treeLevel:Int,
    var treeCount: Int,
    var followingCount: Int,
    var followerCount: Int
)