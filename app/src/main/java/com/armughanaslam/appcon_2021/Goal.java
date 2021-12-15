package com.armughanaslam.appcon_2021;

public class Goal {
    String codeValue;
    String name;
    String description;
    int totalAmount;
    int amountCollected;
    String goalHeader;


    public String getGoalHeader() {
        return goalHeader;
    }

    public void setGoalHeader(String goalHeader) {
        this.goalHeader = goalHeader;
    }

    public Goal(String codeValue, String name, String description, int totalAmount, int amountCollected, String goalHeader) {
        this.codeValue = codeValue;
        this.name = name;
        this.description = description;
        this.totalAmount = totalAmount;
        this.amountCollected = amountCollected;
        this.goalHeader = goalHeader;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getAmountCollected() {
        return amountCollected;
    }

    public void setAmountCollected(int amountCollected) {
        this.amountCollected = amountCollected;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }
}
