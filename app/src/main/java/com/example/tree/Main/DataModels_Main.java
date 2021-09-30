package com.example.tree.Main;


import java.lang.reflect.Member;

public class DataModels_Main {
    private double distance;
    private int transport;
    private String tree;
    private String info;

    public double getDistance() {
        return distance;
    }

    public int getTransport() {
        return transport;
    }

    public String getTree() {
        return tree;
    }

    public String getInfo() {
        return info;
    }

    public void setDistance(double s){
        distance = s;
    }
    public void setTransport(int s){
        transport = s;
    }
}