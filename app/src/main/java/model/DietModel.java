package model;

/**
 * Created by Mobile App Develop on 7-1-16.
 */
public class DietModel {
    private int dietId;
    private String diteType;
    private String dietMenu;
    private String dietDate;
    private String dietTime;
    private String reminderState;
    private String alarmState;
    private String personId;
    private String flag;
    private String alarmCode;
    private String notificationCode;

    public DietModel() {
    }

    public DietModel(int dietId, String diteType, String dietMenu, String dietDate, String dietTime, String reminderState, String alarmState, String personId,String flag,String alarmCode,String notificationCode) {
        this.dietId = dietId;
        this.diteType = diteType;
        this.dietMenu = dietMenu;
        this.dietDate = dietDate;
        this.dietTime = dietTime;
        this.reminderState = reminderState;
        this.alarmState = alarmState;
        this.personId = personId;
        this.flag=flag;
        this.alarmCode=alarmCode;
        this.notificationCode =notificationCode;
    }

    public DietModel(String diteType, String dietMenu, String dietDate, String dietTime, String reminderState, String alarmState, String personId,String flag,String alarmCode,String notificationCode) {
        this.diteType = diteType;
        this.dietMenu = dietMenu;
        this.dietDate = dietDate;
        this.dietTime = dietTime;
        this.reminderState = reminderState;
        this.alarmState = alarmState;
        this.personId = personId;
        this.flag=flag;
        this.alarmCode=alarmCode;
        this.notificationCode =notificationCode;
    }

    public int getDietId() {
        return dietId;
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

    public String getDiteType() {
        return diteType;
    }

    public void setDiteType(String diteType) {
        this.diteType = diteType;
    }

    public String getDietMenu() {
        return dietMenu;
    }

    public void setDietMenu(String dietMenu) {
        this.dietMenu = dietMenu;
    }

    public String getDietDate() {
        return dietDate;
    }

    public void setDietDate(String dietDate) {
        this.dietDate = dietDate;
    }

    public String getDietTime() {
        return dietTime;
    }

    public void setDietTime(String dietTime) {
        this.dietTime = dietTime;
    }

    public String getReminderState() {
        return reminderState;
    }

    public void setReminderState(String reminderState) {
        this.reminderState = reminderState;
    }

    public String getAlarmState() {
        return alarmState;
    }

    public void setAlarmState(String alarmState) {
        this.alarmState = alarmState;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }
}
