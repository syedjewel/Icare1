package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mobile App Develop on 7-1-16.
 */
public class DBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME="icare.db";
    public static final int DATABASE_VERSION=1;
    public static final String TABLE_NAME_PROFILE="profile";
    public static final String TABLE_NAME_DIET="diet";
    public static final String TABLE_NAME_DOCTOR="doctor";
    public static final String TABLE_NAME_APPOINTMENT="appointment";
    public static final String TABLE_NAME_MEDICAL_HISTORY="medicalHistory";
    public static final String TABLE_NAME_VACCINATION="vaccination";
    public static final String TABLE_NAME_MEDICINE="medicine";
    public static final String TABLE_NAME_HOSPITAL="hospital";


    //TABLE OF PROFILE


    public static final String PROFILE_COL_ID="id";
    public static final String PROFILE_COL_PROFILE_NAME="profileName";
    public static final String PROFILE_COL_DATE_OF_BIRTH="dateOfBirth";
    public static final String PROFILE_COL_GENDER="gender";
    public static final String PROFILE_COL_BLOOD_GROUP="bloodGroup";
    public static final String PROFILE_COL_HEIGHT="height";
    public static final String PROFILE_COL_WEIGHT="weight";
    public static final String PROFILE_COL_EMAIL="email";
    public static final String PROFILE_COL_CONTACT_NO="contactNo";
    public static final String PROFILE_COL_PERSON_ID="personId";
    public static final String PROFILE_COL_ID_TYPE="idType";
    public static final String PROFILE_COL_IMAGE_PATH ="imagePath";
    public static final String PROFILE_COL_MAJOR_DISEASE ="majorDisease";
    public static final String PROFILE_COL_NID_BCN ="nid_bcn";
    public static final String PROFILE_COL_RELATIONSHIP ="relation";
    public static final String COL_FLAG ="flag";
    public static final String COL_ALARM ="alarmCode";
    public static final String COL_NOTIFICATION ="notificationCode";



    static final String CREATE_TABLE_PROFILE="CREATE TABLE "+TABLE_NAME_PROFILE +" ( " +
            PROFILE_COL_ID+" INTEGER PRIMARY KEY,"+
            PROFILE_COL_PROFILE_NAME+" TEXT NOT NULL,"+
            PROFILE_COL_DATE_OF_BIRTH+" TEXT NOT NULL,"+
            PROFILE_COL_GENDER+" TEXT,"+
            PROFILE_COL_BLOOD_GROUP+" TEXT,"+
            PROFILE_COL_HEIGHT+" TEXT,"+
            PROFILE_COL_WEIGHT+" TEXT,"+
            PROFILE_COL_EMAIL+" TEXT,"+
            PROFILE_COL_CONTACT_NO+" TEXT,"+
            PROFILE_COL_PERSON_ID+" TEXT,"+
            PROFILE_COL_ID_TYPE+" TEXT,"+
            PROFILE_COL_IMAGE_PATH+" TEXT,"+
            PROFILE_COL_MAJOR_DISEASE+" TEXT,"+
            PROFILE_COL_NID_BCN+" TEXT,"+
            PROFILE_COL_RELATIONSHIP+" TEXT,"+
            COL_FLAG +" TEXT, "+ "UNIQUE( " +PROFILE_COL_PROFILE_NAME+", "+ PROFILE_COL_DATE_OF_BIRTH+" ))";


    //CREATE TABLE contacts(name CHAR(10) NOT NULL,    address INTEGER,    phone INTEGER NOT NULL,    song VARCHAR(255),    PRIMARY KEY (name, phone)    )


    //////////////////////////////////////////////////

    public static final String DIET_COL_ID="id";
    public static final String DIET_COL_DIET_TYPE="diteType";
    public static final String DIET_COL_DIET_MENU="dietMenu";
    public static final String DIET_COL_DIET_DATE="dietDate";
    public static final String DIET_COL_DIET_TIME="dietTime";
    public static final String DIET_COL_DIET_REMINDER_STATE="reminderState";
    public static final String DIET_COL_DIET_ALARM_STATE="alarmState";
    public static final String DIET_COL_DIET_PERSON_ID="personId";



    static final String CREATE_TABLE_DIET="CREATE TABLE "+TABLE_NAME_DIET +" ( " +
            DIET_COL_ID+" INTEGER PRIMARY KEY,"+
            DIET_COL_DIET_TYPE+" TEXT,"+
            DIET_COL_DIET_MENU+" TEXT,"+
            DIET_COL_DIET_DATE+" TEXT,"+
            DIET_COL_DIET_TIME+" TEXT,"+
            DIET_COL_DIET_REMINDER_STATE+" TEXT,"+
            DIET_COL_DIET_ALARM_STATE+" TEXT,"+
            DIET_COL_DIET_PERSON_ID+" TEXT,"+
            COL_FLAG+" TEXT,"+
            COL_ALARM+" TEXT,"+
            COL_NOTIFICATION+" TEXT)";


//////////////////////////////////////

    public static final String DOCTOR_COL_ID="id";
    public static final String DOCTOR_COL_NAME="docName";
    public static final String DOCTOR_COL_SPECIALIST="docSpecialist";
    public static final String DOCTOR_COL_ADDRESS="docAddress";
    public static final String DOCTOR_COL_EMAIL="docEmail";
    public static final String DOCTOR_COL_CONTACT_NO="docContactNo";



    static final String CREATE_TABLE_DOCTOR="CREATE TABLE "+TABLE_NAME_DOCTOR +" ( " +
            DOCTOR_COL_ID+" INTEGER PRIMARY KEY,"+
            DOCTOR_COL_NAME+" TEXT NOT NULL,"+
            DOCTOR_COL_SPECIALIST+" TEXT,"+
            DOCTOR_COL_ADDRESS+" TEXT,"+
            DOCTOR_COL_EMAIL+" TEXT,"+
            DOCTOR_COL_CONTACT_NO+" TEXT NOT NULL,"+
            COL_FLAG +" TEXT, "+ "UNIQUE( " +DOCTOR_COL_NAME+", "+ DOCTOR_COL_CONTACT_NO+" ))";






    /////////////////////////////////

    public static final String APPOINTMENT_COL_ID="id";
    public static final String APPOINTMENT_COL_APPT_DATE="apptDate";
    public static final String APPOINTMENT_COL_APPT_TIME="apptTime";
    public static final String APPOINTMENT_COL_REMARKS="remarks";
    public static final String APPOINTMENT_COL_DOCTOR_ID="doctorId";
    public static final String APPOINTMENT_COL_PERSON_ID="personId";
    public static final String APPOINTMENT_COL_REMINDER_STATE="reminderState";




    static final String CREATE_TABLE_APPOINTMENT="CREATE TABLE "+TABLE_NAME_APPOINTMENT +" ( " +
            APPOINTMENT_COL_ID+" INTEGER PRIMARY KEY,"+
            APPOINTMENT_COL_APPT_DATE+" TEXT,"+
            APPOINTMENT_COL_APPT_TIME+" TEXT,"+
            APPOINTMENT_COL_REMARKS+" TEXT,"+
            APPOINTMENT_COL_DOCTOR_ID+" TEXT,"+
            APPOINTMENT_COL_PERSON_ID+" TEXT,"+
            APPOINTMENT_COL_REMINDER_STATE+" TEXT,"+
            COL_FLAG+" TEXT,"+
            COL_ALARM+" TEXT,"+
            COL_NOTIFICATION+" TEXT)";



    /////////////////////////////////


    public static final String MEDICAL_HISTORY_COL_ID="id";
    public static final String MEDICAL_HISTORY_COL_DOC_ID="doctorId";
    public static final String MEDICAL_HISTORY_COL_DATE="mhDate";
    public static final String MEDICAL_HISTORY_COL_PERSON_ID="personId";
    public static final String MEDICAL_HISTORY_COL_PRESCRIPTION_PATH="prescriptionPath";
    public static final String MEDICAL_HISTORY_COL_DETAILS="details";


    static final String CREATE_TABLE_MEDICAL_HISTORY="CREATE TABLE "+TABLE_NAME_MEDICAL_HISTORY +" ( " +
            MEDICAL_HISTORY_COL_ID+" INTEGER PRIMARY KEY,"+
            MEDICAL_HISTORY_COL_DOC_ID+" TEXT,"+
            MEDICAL_HISTORY_COL_DATE+" TEXT,"+
            MEDICAL_HISTORY_COL_PERSON_ID+" TEXT,"+
            MEDICAL_HISTORY_COL_PRESCRIPTION_PATH+" TEXT,"+
            MEDICAL_HISTORY_COL_DETAILS+" TEXT,"+
            COL_FLAG+" TEXT)";


    ////////////////////////////////////
    public static final String MEDICINE_COL_ID="id";
    public static final String MEDICINE_COL_MEDICINE_NAME="medicineName";
    public static final String MEDICINE_COL_MEDICINE_DOSE="medicineDose";
    public static final String MEDICINE_COL_FROM_DATE="mFromDate";
    public static final String MEDICINE_COL_TO_DATE="mToDate";
    public static final String MEDICINE_COL_DOC_ID="doctorId"; //Prescribe by
    public static final String MEDICINE_COL_PERSON_ID="personId";
    public static final String MEDICINE_ALARM_STATE="mAlarmState";
    public static final String MEDICINE_TAKE_MORNING_STATE="mMorning";
    public static final String MEDICINE_TAKE_AFTERNOON_STATE="mAfterNoon";
    public static final String MEDICINE_TAKE_EVENING_STATE="mEvening";
    public static final String MEDICINE_TAKE_NIGHT_STATE="mNight";
    public static final String MEDICINE_TAKE_REMARKS="mRemarks";

    static final String CREATE_TABLE_MEDICINE="CREATE TABLE "+TABLE_NAME_MEDICINE +" ( " +
            MEDICINE_COL_ID+" INTEGER PRIMARY KEY,"+
            MEDICINE_COL_MEDICINE_NAME+" TEXT NOT NULL,"+
            MEDICINE_COL_MEDICINE_DOSE+" TEXT,"+
            MEDICINE_COL_FROM_DATE+" TEXT NOT NULL,"+
            MEDICINE_COL_TO_DATE+" TEXT,"+
            MEDICINE_COL_DOC_ID+" TEXT,"+
            MEDICINE_COL_PERSON_ID+" TEXT,"+
            MEDICINE_ALARM_STATE+" TEXT,"+
            MEDICINE_TAKE_MORNING_STATE+" TEXT,"+
            MEDICINE_TAKE_AFTERNOON_STATE+" TEXT,"+
            MEDICINE_TAKE_EVENING_STATE+" TEXT,"+
            MEDICINE_TAKE_NIGHT_STATE+" TEXT,"+
            MEDICINE_TAKE_REMARKS+" TEXT,"+
            COL_FLAG+" TEXT,"+
            COL_ALARM+" TEXT,"+
            COL_NOTIFICATION+" TEXT, "+ "UNIQUE( " +MEDICINE_COL_PERSON_ID+", "+ MEDICINE_COL_MEDICINE_NAME+", "+ MEDICINE_COL_FROM_DATE  +" ))";
    //COL_FLAG +" TEXT, "+ "UNIQUE( " +DOCTOR_COL_NAME+", "+ DOCTOR_COL_CONTACT_NO+" ))";



    ///////////////////////////////////////////////////////


    public static final String VACCINATION_COL_ID="id";
    public static final String VACCINATION_COL_NAME="vaccineName";
    public static final String VACCINATION_COL_DATE="vaccineDate";
    public static final String VACCINATION_COL_TIME="vaccineTime";
    public static final String VACCINATION_REMINDER_STATE="vReminderState";
    public static final String VACCINATION_DETAILS="vDetails";
    public static final String VACCINATION_COL_PERSON_ID="personId";



    static final String CREATE_TABLE_VACCINATION="CREATE TABLE "+TABLE_NAME_VACCINATION +" ( " +
            VACCINATION_COL_ID+" INTEGER PRIMARY KEY,"+
            VACCINATION_COL_NAME+" TEXT,"+
            VACCINATION_COL_DATE+" TEXT,"+
            VACCINATION_COL_TIME+" TEXT,"+
            VACCINATION_REMINDER_STATE+" TEXT,"+
            VACCINATION_DETAILS+" TEXT,"+
            VACCINATION_COL_PERSON_ID+" TEXT,"+
            COL_FLAG+" TEXT,"+
            COL_ALARM+" TEXT,"+
            COL_NOTIFICATION+" TEXT)";

    ///////////////////////////////////////////////////////

    public static final String HOSPITAL_COL_ID = "id";
    public static final String HOSPITAL_COL_NAME = "hospitalName";
    public static final String HOSPITAL_COL_ROAD_NAME = "roadName";
    public static final String HOSPITAL_COL_CITY_NAME = "cityName";
    public static final String HOSPITAL_COL_COUNTRY_NAME = "countryName";
    public static final String HOSPITAL_COL_CONTACT_NO = "hospitalContactNo";
    public static final String HOSPITAL_COL_EMAIL = "hospitalEmail";

    static final String CREATE_TABLE_HOSPITAL="CREATE TABLE "+TABLE_NAME_HOSPITAL +" ( " +
            HOSPITAL_COL_ID+" INTEGER PRIMARY KEY,"+
            HOSPITAL_COL_NAME+" TEXT,"+
            HOSPITAL_COL_ROAD_NAME+" TEXT,"+
            HOSPITAL_COL_CITY_NAME+" TEXT,"+
            HOSPITAL_COL_COUNTRY_NAME+" TEXT,"+
            HOSPITAL_COL_CONTACT_NO+" TEXT,"+
            HOSPITAL_COL_EMAIL+" TEXT,"+
            COL_FLAG+" TEXT)";


    ///////////////////////////////////////////////////////
    private Context context;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_PROFILE);
        db.execSQL(CREATE_TABLE_DIET);
        db.execSQL(CREATE_TABLE_DOCTOR);
        db.execSQL(CREATE_TABLE_APPOINTMENT);
        db.execSQL(CREATE_TABLE_MEDICAL_HISTORY);
        db.execSQL(CREATE_TABLE_MEDICINE);
        db.execSQL(CREATE_TABLE_VACCINATION);
        db.execSQL(CREATE_TABLE_HOSPITAL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST "+TABLE_NAME_PROFILE);
        db.execSQL("DROP TABLE IF EXIST "+TABLE_NAME_DIET);
        db.execSQL("DROP TABLE IF EXIST "+TABLE_NAME_DOCTOR);
        db.execSQL("DROP TABLE IF EXIST "+TABLE_NAME_APPOINTMENT);
        db.execSQL("DROP TABLE IF EXIST "+TABLE_NAME_MEDICAL_HISTORY);
        db.execSQL("DROP TABLE IF EXIST "+TABLE_NAME_MEDICINE);
        db.execSQL("DROP TABLE IF EXIST "+TABLE_NAME_VACCINATION);
        db.execSQL("DROP TABLE IF EXIST "+CREATE_TABLE_HOSPITAL);

    }
}
