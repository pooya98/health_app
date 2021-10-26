package com.example.heath_together.Object.DTO;

public class ProfileListItem {
    private String exercise_title;
    private String exercise_count;

    public ProfileListItem(String exercise_title, String exercise_count) {
        this.exercise_title = exercise_title;
        this.exercise_count = exercise_count;
    }

    public String getExercise_title() {
        return exercise_title;
    }

    public void setExercise_title(String exercise_title) {
        this.exercise_title = exercise_title;
    }

    public String getExercise_count() { return exercise_count; }

    public void setExercise_count(String exercise_count) { this.exercise_count = exercise_count; }
}
