package rw.ac.utb.garbagemanagementapp;

public class Client {
    private String names,nid,phone,MainLocation,SubLocation,StreetCode,Password;

    public Client() {
    }


    public Client(String names, String nid, String phone, String mainLocation, String subLocation, String streetCode, String password) {
        this.names = names;
        this.nid = nid;
        this.phone = phone;
        MainLocation = mainLocation;
        SubLocation = subLocation;
        StreetCode = streetCode;
        Password = password;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMainLocation() {
        return MainLocation;
    }

    public void setMainLocation(String mainLocation) {
        MainLocation = mainLocation;
    }

    public String getSubLocation() {
        return SubLocation;
    }

    public void setSubLocation(String subLocation) {
        SubLocation = subLocation;
    }

    public String getStreetCode() {
        return StreetCode;
    }

    public void setStreetCode(String streetCode) {
        StreetCode = streetCode;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
