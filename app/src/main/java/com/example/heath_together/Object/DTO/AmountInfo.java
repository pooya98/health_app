package com.example.heath_together.Object.DTO;

public class AmountInfo {
    private int setNum;
    private int count;
    private double time;
    private double weight;

    public int getSetNum() {
        return setNum;
    }

    public void setSetNum(int setNum) {
        this.setNum = setNum;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public AmountInfo(int setNum, int count, double time, double weight) {
        this.setNum = setNum;
        this.count = count;
        this.time = time;
        this.weight = weight;
    }
}
