package com.example.heath_together.Object.DTO;

public class ExerciseReadyListItem {
    private String ExerciseName;

    public void setExerciseName(String exerciseName) {
        ExerciseName = exerciseName;
    }

    public String getExerciseName() {
        return ExerciseName;
    }

    public ExerciseReadyListItem(String exerciseName) {
        ExerciseName = exerciseName;
    }
}
