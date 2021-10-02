package com.example.tree.Main

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

class MainData_response {
    @SerializedName("treeLevel")
    var treeLevel = 0

    @SerializedName("treeCount")
    var treeCount = 0

    @SerializedName("totalReduction")
    var totalReduction = 0.0


}