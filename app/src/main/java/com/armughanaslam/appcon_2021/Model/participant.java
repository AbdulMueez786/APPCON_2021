package com.armughanaslam.appcon_2021.Model;

public class participant {
    private String id;
    private String goal_id;
    private String donor_id;
    private String goal_title;
    private String donor_name;
    private String price;

    public participant(){

    }

    public participant(String id, String goal_id, String donor_id, String goal_title, String donor_name, String price) {
        this.id = id;
        this.goal_id = goal_id;
        this.donor_id = donor_id;
        this.goal_title = goal_title;
        this.donor_name = donor_name;
        this.price = price;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGoal_id(String goal_id) {
        this.goal_id = goal_id;
    }

    public void setDonor_id(String donor_id) {
        this.donor_id = donor_id;
    }

    public void setGoal_title(String goal_title) {
        this.goal_title = goal_title;
    }

    public void setDonor_name(String donor_name) {
        this.donor_name = donor_name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getGoal_id() {
        return goal_id;
    }

    public String getDonor_id() {
        return donor_id;
    }

    public String getGoal_title() {
        return goal_title;
    }

    public String getDonor_name() {
        return donor_name;
    }

    public String getPrice() {
        return price;
    }

}
