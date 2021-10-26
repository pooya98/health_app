package com.example.heath_together.Object.DTO;

public class HealthItem {
    private String id;
    private String name;
    private String type;
    private boolean flag_count;
    private boolean flag_time;
    private boolean flag_weight;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public HealthItem(String healthName) {
        this.name = healthName;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HealthItem(String id, String name, String type, boolean flag_count, boolean flag_time, boolean flag_weight) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.flag_count = flag_count;
        this.flag_time = flag_time;
        this.flag_weight = flag_weight;
    }
}
