package model;

/**
 * Created by Rubayet on 13-Jan-16.
 */
public class HospitalModel {
    private int hospitalID;
    private String hospitalName;
    private String roadName;
    private String cityName;
    private String countryName;
    private String contactNumber;
    private String emailAddress;
    private String flag;

    public HospitalModel() {
    }

    public HospitalModel(int hospitalID, String hospitalName, String roadName, String cityName, String countryName, String contactNumber, String emailAddress,String flag) {
        this.hospitalID = hospitalID;
        this.hospitalName = hospitalName;
        this.roadName = roadName;
        this.cityName = cityName;
        this.countryName = countryName;
        this.contactNumber = contactNumber;
        this.emailAddress = emailAddress;
        this.flag=flag;
    }

    public HospitalModel(String hospitalName, String roadName, String cityName, String countryName, String contactNumber, String emailAddress,String flag) {
        this.hospitalName = hospitalName;
        this.roadName = roadName;
        this.cityName = cityName;
        this.countryName = countryName;
        this.contactNumber = contactNumber;
        this.emailAddress = emailAddress;
        this.flag=flag;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getHospitalID() {
        return hospitalID;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
