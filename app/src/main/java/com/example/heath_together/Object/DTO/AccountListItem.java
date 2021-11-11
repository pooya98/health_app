package com.example.heath_together.Object.DTO;

public class AccountListItem {
    private String UserName ;
    private String UserEmail;
    private String Uid;
    private String ProfileUri;


    public AccountListItem(){

    }

    public AccountListItem(String uid, String userEmail, String userName, String profileUri) {
        UserName = userName;
        UserEmail = userEmail;
        Uid = uid;
        ProfileUri = profileUri;
    }

    public String getUid() { return Uid; }

    public void setUid(String uid) { Uid = uid; }


    public String getProfileUri() { return ProfileUri; }

    public void setProfileUri(String profileUri) { ProfileUri = profileUri; }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }
}

