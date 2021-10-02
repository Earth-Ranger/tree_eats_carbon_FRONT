package com.example.tree.signup




data class Signup (
    var email : String? = null,
    var name : String? = null,
    var password : String? = null
)

data class CheckGetModel2 (
    var message : Boolean? = null
)