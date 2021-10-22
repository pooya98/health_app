package com.example.heath_together.Object.DTO;

public class HealthListItem {
    private String healthListName;
    private int healthCount;

    // Healthimage;

    public String getHealthListName() {
        return healthListName;
    }

    public void setHealthListName(String healthListName) {
        this.healthListName = healthListName;
    }
    public int getHealthCount() {
        return healthCount;
    }

    public void setHealthCount(int healthCount) {
        this.healthCount = healthCount;
    }



    public HealthListItem(String healthListName, int healthCount) {
        this.healthListName = healthListName;

    }


}
