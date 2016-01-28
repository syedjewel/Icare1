package model;

/**
 * Created by Mobile App Develop on 7-1-16.
 */
public class MedicalHistoryModel {

    private int mhId;
    private String doctorId;
    private String mhDate;
    private String personId;
    private String prescriptionPath;
    private String details;
    private String flag;


    public MedicalHistoryModel() {
    }

    public MedicalHistoryModel(int mhId, String doctorId, String mhDate, String personId, String prescriptionPath,String details,String flag) {
        this.mhId = mhId;
        this.doctorId = doctorId;
        this.mhDate = mhDate;
        this.personId = personId;
        this.prescriptionPath = prescriptionPath;
        this.details = details;
        this.flag=flag;

    }

    public MedicalHistoryModel(String doctorId, String mhDate, String personId, String prescriptionPath,String details,String flag) {
        this.doctorId = doctorId;
        this.mhDate = mhDate;
        this.personId = personId;
        this.prescriptionPath = prescriptionPath;
        this.details = details;
        this.flag=flag;

    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }


    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getMhId() {
        return mhId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getMhDate() {
        return mhDate;
    }

    public void setMhDate(String mhDate) {
        this.mhDate = mhDate;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPrescriptionPath() {
        return prescriptionPath;
    }

    public void setPrescriptionPath(String prescriptionPath) {
        this.prescriptionPath = prescriptionPath;
    }
}
