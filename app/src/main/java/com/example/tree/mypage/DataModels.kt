package com.example.tree.mypage

data class GetModel2 (
    var name : String,
    var email : String,
    var follower : Int,
    var following : Int
)

data class PUTModel (
    var code : Int,
    var message : String
)
