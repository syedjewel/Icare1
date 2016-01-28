package model;

/**
 * Created by Rubayet on 10-Jan-16.
 */
public class AppointmentModel {
    private int apptId;
    private String reminderState;
    private String apptDate;
    private String apptTime;
    private String remarks;
    private String doctorId;
    private String personId;
    private String flag;
    private String alarmCode;
    private String notificationCode;

    public AppointmentModel() {
    }

    public AppointmentModel(int apptId, String apptDate, String apptTime, String remarks, String doctorId, String personId,String reminderState,String flag,String alarmCode,String notificationCode) {
        this.apptId = apptId;
        this.apptDate = apptDate;
        this.apptTime = apptTime;
        this.remarks = remarks;
        this.doctorId = doctorId;
        this.personId = personId;
        this.reminderState =reminderState;
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

    public AppointmentModel(String apptDate, String apptTime, String remarks, String doctorId, String personId, String reminderState,String flag,String alarmCode,String notificationCode) {
        this.apptDate = apptDate;
        this.apptTime = apptTime;
        this.remarks = remarks;
        this.doctorId = doctorId;
        this.personId = personId;
        this.reminderState =reminderState;
        this.flag=flag;
        this.alarmCode=alarmCode;
        this.notificationCode =notificationCode;
    }
    public String getReminderState() {
        return reminderState;
    }

    public void setReminderState(String reminderState) {
        this.reminderState = reminderState;
    }

    public int getApptId() {
        return apptId;
    }

    public String getApptDate() {
        return apptDate;
    }

    public void setApptDate(String apptDate) {
        this.apptDate = apptDate;
    }

    public String getApptTime() {
        return apptTime;
    }

    public void setApptTime(String apptTime) {
        this.apptTime = apptTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }
}
