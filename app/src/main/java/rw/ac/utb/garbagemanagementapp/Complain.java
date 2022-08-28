package rw.ac.utb.garbagemanagementapp;

public class Complain {
    private String clientNames,ClientLocation,ClientPhone,date,status;

    public Complain() {
    }

    public Complain(String clientNames, String clientLocation, String clientPhone, String date, String status) {
        this.clientNames = clientNames;
        ClientLocation = clientLocation;
        ClientPhone = clientPhone;
        this.date = date;
        this.status = status;
    }

    public String getClientNames() {
        return clientNames;
    }

    public void setClientNames(String clientNames) {
        this.clientNames = clientNames;
    }

    public String getClientLocation() {
        return ClientLocation;
    }

    public void setClientLocation(String clientLocation) {
        ClientLocation = clientLocation;
    }

    public String getClientPhone() {
        return ClientPhone;
    }

    public void setClientPhone(String clientPhone) {
        ClientPhone = clientPhone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
