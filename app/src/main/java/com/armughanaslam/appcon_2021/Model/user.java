package com.armughanaslam.appcon_2021.Model;

public class user {

    private String id;
    private String email;
    private String app_id;
    private String name;
    private String status;
    private String user_profile;
    private String balance;
    private String account_suspended;// In case user account is proved span

    public user(){

    }

    public user(String id,String email ,String app_id, String name, String status, String user_profile, String balance, String account_suspended) {
        this.id = id;
        this.email=email;
        this.app_id = app_id;
        this.name = name;
        this.status = status;
        this.user_profile = user_profile;
        this.balance = balance;
        this.account_suspended = account_suspended;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getApp_id() {
        return app_id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getUser_profile() {
        return user_profile;
    }

    public String getBalance() {
        return balance;
    }

    public String getAccount_suspended() {
        return account_suspended;
    }

    public void setId(String id) {
        this.id = id;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUser_profile(String user_profile) {
        this.user_profile = user_profile;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public void setAccount_suspended(String account_suspended) {
        this.account_suspended = account_suspended;
    }
}
