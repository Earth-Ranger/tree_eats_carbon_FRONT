package com.example.tree.neighbor

import com.google.gson.annotations.SerializedName



// 이웃 삭제
data class DeleteModel(
    var code : Int,
    var message: String
)


// 이웃 검색
data class GetModel(
    var id : Long,
    var name: String,
    var followerCount: Int,
    var treeCount: Int
)



// 이웃 목록
data class GetModel_list(
    var id : Long,
    var name: String,
    var followerCount: Int,
    var treeCount: Int
)
class CheckGetModel(
    @SerializedName("followList")
    val checkRoomList : List<GetModel_list>
)



// 이웃 요청
data class PostModel(
    var code : Int,
    var message: String
)

// 이웃 트리 상세정보
data class GetModel2(
    var name: String,
    var treeLevel:Int,
    var treeCount: Int,
    var followingCount: Int,
    var followerCount: Int
)
