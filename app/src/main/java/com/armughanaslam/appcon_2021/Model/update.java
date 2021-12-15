package com.armughanaslam.appcon_2021.Model;

public class update {
    private String id;
    private String goal_id;
    private String type;
    private String data;
    private String time;

    public update(){

    }

    public update(String id, String goal_id, String type, String data, String time) {
        this.id = id;
        this.goal_id = goal_id;
        this.type = type;
        this.data = data;
        this.time = time;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGoal_id(String goal_id) {
        this.goal_id = goal_id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getGoal_id() {
        return goal_id;
    }

    public String getType() {
        return type;
    }
    public String getData() {
        return data;
    }

    public String getTime() {
        return time;
    }


}
