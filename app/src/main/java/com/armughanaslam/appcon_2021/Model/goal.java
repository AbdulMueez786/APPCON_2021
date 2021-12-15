package com.armughanaslam.appcon_2021.Model;

public class goal {

    String id;
    String creater_id;
    String image;
    String title;
    String description;
    String categroy;
    String Target_Amount;
    String Raised_Amount;
    String No_of_participants;
    String status; // Activitive or closed
    String QrCode;

    public goal(){
        this.id = "";
        this.creater_id = "";
        this.image = "";
        this.title = "";
        this.description = "";
        this.categroy = "";
        Target_Amount = "";
        Raised_Amount = "";
        No_of_participants = "";
        this.status = "";
        QrCode = "";
    }

    public goal(String id, String creater_id, String image, String title, String description, String categroy, String target_Amount, String raised_Amount, String no_of_participants, String status, String qrCode) {
        this.id = id;
        this.creater_id = creater_id;
        this.image = image;
        this.title = title;
        this.description = description;
        this.categroy = categroy;
        Target_Amount = target_Amount;
        Raised_Amount = raised_Amount;
        No_of_participants = no_of_participants;
        this.status = status;
        QrCode = qrCode;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreater_id(String creater_id) {
        this.creater_id = creater_id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategroy(String categroy) {
        this.categroy = categroy;
    }

    public void setTarget_Amount(String target_Amount) {
        Target_Amount = target_Amount;
    }

    public void setRaised_Amount(String raised_Amount) {
        Raised_Amount = raised_Amount;
    }

    public void setNo_of_participants(String no_of_participants) {
        No_of_participants = no_of_participants;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setQrCode(String qrCode) {
        QrCode = qrCode;
    }

    public String getId() {
        return id;
    }

    public String getCreater_id() {
        return creater_id;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategroy() {
        return categroy;
    }

    public String getTarget_Amount() {
        return Target_Amount;
    }

    public String getRaised_Amount() {
        return Raised_Amount;
    }

    public String getNo_of_participants() {
        return No_of_participants;
    }

    public String getStatus() {
        return status;
    }

    public String getQrCode() {
        return QrCode;
    }

}
