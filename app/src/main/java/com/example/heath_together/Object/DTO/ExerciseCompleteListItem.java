package com.example.heath_together.Object.DTO;

public class ExerciseCompleteListItem {
    private String ExerciseName;
    private String set;

    public void setExerciseName(String exerciseName) {
        ExerciseName = exerciseName;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getExerciseName() {
        return ExerciseName;
    }

    public String getSet() {
        return set;
    }

    public ExerciseCompleteListItem(String exerciseName, String set) {
        ExerciseName = exerciseName;
        this.set = set;
    }
}
