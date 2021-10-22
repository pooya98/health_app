package com.example.heath_together.Object.DTO;

public class AccountListItem {
    private String accountName;


    public String getAccountName(){
        return accountName;
    }

    public void setAccountName(String accountName) {
        this. accountName= accountName;
    }

    public AccountListItem(String accountName){
        this.accountName = accountName;
    }
}
