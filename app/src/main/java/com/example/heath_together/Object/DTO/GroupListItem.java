package com.example.heath_together.Object.DTO;

public class GroupListItem {
    private String groupName;
    private String groupType;
    private String leaderName;
    // leaderimage;
    private int mem_num;
    private int mem_limit;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public int getMem_num() {
        return mem_num;
    }

    public void setMem_num(int mem_num) {
        this.mem_num = mem_num;
    }

    public int getMem_limit() {
        return mem_limit;
    }

    public void setMem_limit(int mem_limit) {
        this.mem_limit = mem_limit;
    }

    public GroupListItem(String groupName, String groupType, String leaderName, int mem_num, int mem_limit) {
        this.groupName = groupName;
        this.groupType = groupType;
        this.leaderName = leaderName;
        this.mem_num = mem_num;
        this.mem_limit = mem_limit;
    }
}
