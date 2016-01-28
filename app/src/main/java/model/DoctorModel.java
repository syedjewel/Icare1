package model;

/**
 * Created by Mobile App Develop on 7-1-16.
 */
public class DoctorModel {
    private int doctorId;
    private String docName;
    private String docSpecialist;
    private String docAddress;
    private String docEmail;
    private String docContactNo;
    private String flag;

    public DoctorModel() {
    }

    public DoctorModel(int doctorId, String docName, String docSpecialist, String docAddress, String docEmail, String docContactNo,String flag) {
        this.doctorId = doctorId;
        this.docName = docName;
        this.docSpecialist = docSpecialist;
        this.docAddress = docAddress;
        this.docEmail = docEmail;
        this.docContactNo = docContactNo;
        this.flag=flag;
    }

    public DoctorModel(String docName, String docSpecialist, String docAddress, String docEmail, String docContactNo,String flag) {
        this.docName = docName;
        this.docSpecialist = docSpecialist;
        this.docAddress = docAddress;
        this.docEmail = docEmail;
        this.docContactNo = docContactNo;
        this.flag=flag;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocSpecialist() {
        return docSpecialist;
    }

    public void setDocSpecialist(String docSpecialist) {
        this.docSpecialist = docSpecialist;
    }

    public String getDocAddress() {
        return docAddress;
    }

    public void setDocAddress(String docAddress) {
        this.docAddress = docAddress;
    }

    public String getDocEmail() {
        return docEmail;
    }

    public void setDocEmail(String docEmail) {
        this.docEmail = docEmail;
    }

    public String getDocContactNo() {
        return docContactNo;
    }

    public void setDocContactNo(String docContactNo) {
        this.docContactNo = docContactNo;
    }
}
