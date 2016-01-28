package model;

/**
 * Created by Mobile App Develop on 7-1-16.
 */
public class VaccineModel {
    private int vId;
    private String vaccineName;
    private String vaccineDate;
    private String vaccineTime;
    private String vReminderState;
    private String vDetails;
    private String personId;
    private String flag;
    private String alarmCode;
    private String notificationCode;

    public VaccineModel() {
    }

    public VaccineModel(int vId, String vaccineName, String vaccineDate,
                        String vaccineTime, String vReminderState, String vDetails, String personId,String flag,String alarmCode,String notificationCode) {
        this.vId = vId;
        this.vaccineName = vaccineName;
        this.vaccineDate = vaccineDate;
        this.vaccineTime = vaccineTime;
        this.vReminderState = vReminderState;
        this.vDetails = vDetails;
        this.personId = personId;
        this.flag=flag;
        this.alarmCode=alarmCode;
        this.notificationCode =notificationCode;
    }

    public VaccineModel(String vaccineName, String vaccineDate,
                        String vaccineTime, String vReminderState, String vDetails, String personId,String flag,String alarmCode,String notificationCode) {
        this.vaccineName = vaccineName;
        this.vaccineDate = vaccineDate;
        this.vaccineTime = vaccineTime;
        this.vReminderState = vReminderState;
        this.vDetails = vDetails;
        this.personId = personId;
        this.flag=flag;
        this.alarmCode=alarmCode;
        this.notificationCode =notificationCode;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getAlarmCode() {
        return alarmCode;
    }

    public void setAlarmCode(String alarmCode) {
        this.alarmCode = alarmCode;
    }

    public String getNotificationCode() {
        return notificationCode;
    }

    public void setNotificationCode(String notificationCode) {
        this.notificationCode = notificationCode;
    }

    public int getvId() {
        return vId;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public String getVaccineDate() {
        return vaccineDate;
    }

    public void setVaccineDate(String vaccineDate) {
        this.vaccineDate = vaccineDate;
    }

    public String getVaccineTime() {
        return vaccineTime;
    }

    public void setVaccineTime(String vaccineTime) {
        this.vaccineTime = vaccineTime;
    }

    public String getvReminderState() {
        return vReminderState;
    }

    public void setvReminderState(String vReminderState) {
        this.vReminderState = vReminderState;
    }

    public String getvDetails() {
        return vDetails;
    }

    public void setvDetails(String vDetails) {
        this.vDetails = vDetails;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }
}
