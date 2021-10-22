package com.example.heath_together.Object.DTO;

public class GroupListItem {
    private String groupId;
    private String groupName;
    private String leaderName;
    private String leaderUid;
    private String groupIntro;
    private String groupTag;
    // leaderimage;
    private int mem_num;
    private int mem_limit;
    private boolean groupOpen;




    public String getGroupIntro() {
        return groupIntro;
    }

    public void setGroupIntro(String groupIntro) {
        this.groupIntro = groupIntro;
    }

    public GroupListItem() {
    }

    public boolean isGroupOpen() {
        return groupOpen;
    }

    public void setGroupOpen(boolean groupOpen) {
        this.groupOpen = groupOpen;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

    public String getLeaderUid() {
        return leaderUid;
    }

    public void setLeaderUid(String leaderUid) {
        this.leaderUid = leaderUid;
    }

    public String getGroupTag() {
        return groupTag;
    }

    public void setGroupTag(String groupTag) {
        this.groupTag = groupTag;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public GroupListItem(String groupId, String groupName, String leaderName, String leaderUid, String groupIntro, String groupTag, int mem_num, int mem_limit, boolean groupOpen) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.leaderName = leaderName;
        this.leaderUid = leaderUid;
        this.groupIntro = groupIntro;
        this.groupTag = groupTag;
        this.mem_num = mem_num;
        this.mem_limit = mem_limit;
        this.groupOpen = groupOpen;
    }
}
