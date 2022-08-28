package rw.ac.utb.garbagemanagementapp;

public class Feedback {

    private  String message,senderName,senderPhone,date;


    public Feedback() {
    }

    public Feedback(String message, String senderName, String senderPhone, String date) {
        this.message = message;
        this.senderName = senderName;
        this.senderPhone = senderPhone;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
