package com.example.heath_together.Object.DTO;

public class ExerciseReadyListItem {
    private String ExerciseName;
    private boolean flag_count;
    private boolean flag_time;
    private boolean flag_weight;


    public void setExerciseName(String exerciseName) {
        ExerciseName = exerciseName;
    }

    public String getExerciseName() {
        return ExerciseName;
    }


    public boolean isFlag_count() {
        return flag_count;
    }

    public void setFlag_count(boolean flag_count) {
        this.flag_count = flag_count;
    }

    public boolean isFlag_time() {
        return flag_time;
    }

    public void setFlag_time(boolean flag_time) {
        this.flag_time = flag_time;
    }

    public boolean isFlag_weight() {
        return flag_weight;
    }

    public void setFlag_weight(boolean flag_weight) {
        this.flag_weight = flag_weight;
    }

    public ExerciseReadyListItem(String exerciseName, boolean flag_count, boolean flag_time, boolean flag_weight) {
        ExerciseName = exerciseName;
        this.flag_count = flag_count;
        this.flag_time = flag_time;
        this.flag_weight = flag_weight;
    }
}
