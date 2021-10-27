package com.example.heath_together.Object.DTO;

import java.util.ArrayList;

public class ExerciseRecord {
    private String exerciseId;
    private String exerciseName;
    private ArrayList<AmountInfo> amountList;

    public String getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public ArrayList<AmountInfo> getAmountList() {
        return amountList;
    }

    public void setAmountList(ArrayList<AmountInfo> amountList) {
        this.amountList = amountList;
    }

    public ExerciseRecord() {
    }

    public ExerciseRecord(String exerciseId, String exerciseName, ArrayList<AmountInfo> amountList) {
        this.exerciseId = exerciseId;
        this.exerciseName = exerciseName;
        this.amountList = amountList;
    }
}
