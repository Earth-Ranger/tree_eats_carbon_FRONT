package com.example.tree.login

import java.io.Serializable


data class Login (
    var email : String? = null,
    var password : String? = null
): Serializable

data class user_token (
    var token : String? = null
):Serializable