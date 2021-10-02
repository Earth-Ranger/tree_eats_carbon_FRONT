package com.example.tree.Main

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

