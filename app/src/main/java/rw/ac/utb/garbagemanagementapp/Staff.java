package rw.ac.utb.garbagemanagementapp;

public class Staff {

    private String names,nid,phone,MainLocation,SubLocation,Password,LicenseNumber,PlateNumber;

    public Staff() {
    }


    public Staff(String names, String nid, String phone, String mainLocation, String subLocation, String password, String licenseNumber, String plateNumber) {
        this.names = names;
        this.nid = nid;
        this.phone = phone;
        MainLocation = mainLocation;
        SubLocation = subLocation;
        Password = password;
        LicenseNumber = licenseNumber;
        PlateNumber = plateNumber;
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

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getLicenseNumber() {
        return LicenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        LicenseNumber = licenseNumber;
    }

    public String getPlateNumber() {
        return PlateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        PlateNumber = plateNumber;
    }
}
