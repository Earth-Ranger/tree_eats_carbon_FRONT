package com.example.tree.Main;


import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Member;
import java.time.LocalDate;
import java.util.Map;

public class DataModels_Main {
    @SerializedName("distance")
    private double distance;
    @SerializedName("transport")
    private int transport;

    @SerializedName("treeLevel")
    public int treeLevel;

    @SerializedName("treeCount")
    public int treeCount;

    @SerializedName("carbonMap")
    public Map<LocalDate, Double> carbonMap;

    public DataModels_Main(double distance, int transport){
        this.distance=distance;
        this.transport=transport;
    }
    public double getDistance() {
        return distance;
    }

    public int getTransport() {
        return transport;
    }


    public int getTreeLevel() {
        return treeLevel;
    }

    public int getTreeCount() {
        return treeCount;
    }

    public Map<LocalDate, Double> getCarbonMap() {
        return carbonMap;
    }

    public void setTreeCount(int treeCount) {
        this.treeCount = treeCount;
    }

    public void setTreeLevel(int treeLevel) {
        this.treeLevel = treeLevel;
    }

    public void setCarbonMap(Map<LocalDate, Double> carbonMap) {
        this.carbonMap = carbonMap;
    }
}