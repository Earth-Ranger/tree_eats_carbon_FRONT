package com.example.tree.Main;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.Map;

public class MainData_response {
    @SerializedName("treeLevel")
    public int treeLevel;

    @SerializedName("treeCount")
    public int treeCount;

    @SerializedName("totalReduction")
    public double totalReduction;

    @SerializedName("carbonMap")
    public Map<LocalDate, Double> carbonMap;




    public int getTreeLevel() {
        return treeLevel;
    }

    public void setCarbonMap(Map<LocalDate, Double> carbonMap) {
        this.carbonMap = carbonMap;
    }

    public int getTreeCount() {
        return treeCount;
    }

    public Map<LocalDate, Double> getCarbonMap() {
        return carbonMap;
    }

    public void setTotalReduction(double totalReduction) {
        this.totalReduction = totalReduction;
    }

    public double getTotalReduction() {
        return totalReduction;
    }

    public void setTreeCount(int treeCount) {
        this.treeCount = treeCount;
    }

    public void setTreeLevel(int treeLevel) {
        this.treeLevel = treeLevel;
    }
}
