package model;

/**
 * Created by Mobile App Develop on 7-1-16.
 */
public class MedicineModel {

    private int mId;
    private String medicineName;
    private String medicineDose;
    private String mFromDate;
    private String mToDate;
    private String doctorId; //Prescribe by
    private String personId;
    private String mAlarmState;
    private String mMorning;
    private String mAfterNoon;
    private String mEvening;
    private String mNight;
    private String mRemarks;
    private String flag;
    private String alarmCode;
    private String notificationCode;

    public MedicineModel() {
    }

    public MedicineModel(int mId, String medicineName, String medicineDose, String mFromDate,
                         String mToDate, String doctorId, String personId, String mAlarmState,
                         String mMorning, String mAfterNoon, String mEvening, String mNight,
                         String mRemarks,String flag,String alarmCode,String notificationCode) {
        this.mId = mId;
        this.medicineName = medicineName;
        this.medicineDose = medicineDose;
        this.mFromDate = mFromDate;
        this.mToDate = mToDate;
        this.doctorId = doctorId;
        this.personId = personId;
        this.mAlarmState = mAlarmState;
        this.mMorning = mMorning;
        this.mAfterNoon = mAfterNoon;
        this.mEvening = mEvening;
        this.mNight = mNight;
        this.mRemarks = mRemarks;
        this.flag=flag;
        this.alarmCode=alarmCode;
        this.notificationCode =notificationCode;
    }

    public MedicineModel(String medicineName, String medicineDose, String mFromDate, String mToDate,
                         String doctorId, String personId, String mAlarmState,
                         String mMorning, String mAfterNoon, String mEvening, String mNight,
                         String mRemarks,String flag,String alarmCode,String notificationCode) {
        this.medicineName = medicineName;
        this.medicineDose = medicineDose;
        this.mFromDate = mFromDate;
        this.mToDate = mToDate;
        this.doctorId = doctorId;
        this.personId = personId;
        this.mAlarmState = mAlarmState;
        this.mMorning = mMorning;
        this.mAfterNoon = mAfterNoon;
        this.mEvening = mEvening;
        this.mNight = mNight;
        this.mRemarks = mRemarks;
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

    public int getmId() {
        return mId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getMedicineDose() {
        return medicineDose;
    }

    public void setMedicineDose(String medicineDose) {
        this.medicineDose = medicineDose;
    }

    public String getmFromDate() {
        return mFromDate;
    }

    public void setmFromDate(String mFromDate) {
        this.mFromDate = mFromDate;
    }

    public String getmToDate() {
        return mToDate;
    }

    public void setmToDate(String mToDate) {
        this.mToDate = mToDate;
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

    public String getmAlarmState() {
        return mAlarmState;
    }

    public void setmAlarmState(String mAlarmState) {
        this.mAlarmState = mAlarmState;
    }

    public String getmMorning() {
        return mMorning;
    }

    public void setmMorning(String mMorning) {
        this.mMorning = mMorning;
    }

    public String getmAfterNoon() {
        return mAfterNoon;
    }

    public void setmAfterNoon(String mAfterNoon) {
        this.mAfterNoon = mAfterNoon;
    }

    public String getmEvening() {
        return mEvening;
    }

    public void setmEvening(String mEvening) {
        this.mEvening = mEvening;
    }

    public String getmNight() {
        return mNight;
    }

    public void setmNight(String mNight) {
        this.mNight = mNight;
    }

    public String getmRemarks() {
        return mRemarks;
    }

    public void setmRemarks(String mRemarks) {
        this.mRemarks = mRemarks;
    }
}
