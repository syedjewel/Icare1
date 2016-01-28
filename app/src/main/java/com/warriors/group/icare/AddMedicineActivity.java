package com.warriors.group.icare;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import alarm.MyBroadcastReceiver;
import database.DBHelper;
import database.DataStorage;
import model.DoctorModel;
import model.MedicineModel;

public class AddMedicineActivity extends AppCompatActivity implements View.OnClickListener {

    EditText addMedicineNameET;
    EditText addMedicineDoseET;
    EditText addMedicineFromDateET;
    EditText addMedicineToDateET;
    EditText addMedicineRemarksET;
    CheckBox addMedicineMorningCB;
    CheckBox addMedicineAfternoonCB;
    CheckBox addMedicineEveningCB;
    CheckBox addMedicineNightCB;
    CheckBox addMedicineDailyAlarmCB;
    Button saveBtn;
    Button profileNewBTN;
    Spinner addMedicineDoctorSpinner;

    SharedPreferences preferences;
    SharedPreferences preferences1;
    String personID;
    String docId;
    DatePickerDialog dateFromPickerDialog;
    DatePickerDialog dateToPickerDialog;
    SimpleDateFormat dateFormatter;
    private DataStorage dataStorage;

    ArrayList<DoctorModel> doctorModels = new ArrayList<>();

    String mAlarmState;
    String mMorning;
    String mAfterNoon;
    String mEvening;
    String mNight;
    String medicine_id_update;

    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        dataStorage = new DataStorage(getApplicationContext());
        doctorModels=dataStorage.getAllDoctorModel();
        preferences = getBaseContext().getSharedPreferences("person_id", MODE_PRIVATE);
        personID =preferences.getString("person_id", "");
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        initializer();
        datePicker();
        loadSpinnerData();
        //Toast.makeText(AddMedicineActivity.this, personID, Toast.LENGTH_LONG).show();
        preferences1=getBaseContext().getSharedPreferences("medicine_id_update", MODE_PRIVATE);
        medicine_id_update =preferences1.getString("medicine_id_update", "");
        saveBtn= (Button) findViewById(R.id.addMedicineSaveBTN);
        profileNewBTN=(Button) findViewById(R.id.addMedicineNewBTN);
        if (medicine_id_update.equalsIgnoreCase("")) {

            saveBtn.setText("SAVE");

        }
        else
        {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Update Medicine Dose");
            saveBtn.setText("UPDATE");
            id = Integer.valueOf(medicine_id_update);
            showDataforUpdate();
            profileNewBTN.setVisibility(View.INVISIBLE);

        }
    }

    private void showDataforUpdate()
    {
        String medicineName = dataStorage.getMedicineModelByMedicineID(id).get(0).getMedicineName();
        String medicineDose = dataStorage.getMedicineModelByMedicineID(id).get(0).getMedicineDose();
        String fromDate = dataStorage.getMedicineModelByMedicineID(id).get(0).getmFromDate();
        String toDate = dataStorage.getMedicineModelByMedicineID(id).get(0).getmToDate();
        String remarks = dataStorage.getMedicineModelByMedicineID(id).get(0).getmRemarks();
        String doctorId = dataStorage.getMedicineModelByMedicineID(id).get(0).getDoctorId();
        String doctorName =dataStorage.getDoctorModelByDoctorID(Integer.valueOf(doctorId)).get(0).getDocName();

        String morning = dataStorage.getMedicineModelByMedicineID(id).get(0).getmMorning();
        String afterNoon = dataStorage.getMedicineModelByMedicineID(id).get(0).getmAfterNoon();
        String evening = dataStorage.getMedicineModelByMedicineID(id).get(0).getmEvening();
        String night = dataStorage.getMedicineModelByMedicineID(id).get(0).getmNight();
        String alarmState = dataStorage.getMedicineModelByMedicineID(id).get(0).getmAlarmState();



        addMedicineNameET.setText(medicineName);
        addMedicineDoseET.setText(medicineDose);
        addMedicineFromDateET.setText(fromDate);
        addMedicineToDateET.setText(toDate);
        addMedicineRemarksET.setText(remarks);


        if(morning == null || morning.equalsIgnoreCase("0"))
        {
            addMedicineMorningCB.setChecked(false);
        }
        else
        {
            addMedicineMorningCB.setChecked(true);
        }


        if(afterNoon == null || afterNoon.equalsIgnoreCase("0"))
        {
            addMedicineAfternoonCB.setChecked(false);
        }
        else
        {
            addMedicineAfternoonCB.setChecked(true);
        }


        if(evening == null || evening.equalsIgnoreCase("0"))
        {
            addMedicineEveningCB.setChecked(false);
        }
        else
        {
            addMedicineEveningCB.setChecked(true);
        }

        if(night == null || night.equalsIgnoreCase("0"))
        {
            addMedicineNightCB.setChecked(false);
        }
        else
        {
            addMedicineNightCB.setChecked(true);
        }

        if(alarmState == null || alarmState.equalsIgnoreCase("0"))
        {
            addMedicineDailyAlarmCB.setChecked(false);
        }
        else
        {
            addMedicineDailyAlarmCB.setChecked(true);
        }

        //addMedicineDoctorSpinner

        addMedicineDoctorSpinner.setSelection(getIndex(addMedicineDoctorSpinner, doctorName));

    }

    private void initializer(){
        addMedicineNameET = (EditText) findViewById(R.id.addMedicineNameET);
        addMedicineDoseET = (EditText) findViewById(R.id.addMedicineDoseET);
        addMedicineFromDateET = (EditText) findViewById(R.id.addMedicineFromDateET);
        addMedicineToDateET = (EditText) findViewById(R.id.addMedicineToDateET);
        addMedicineRemarksET = (EditText) findViewById(R.id.addMedicineRemarks);
        addMedicineMorningCB = (CheckBox) findViewById(R.id.addMedicineMorningCB);
        addMedicineAfternoonCB = (CheckBox) findViewById(R.id.addMedicineAfternoonCB);
        addMedicineEveningCB = (CheckBox) findViewById(R.id.addMedicineEveningCB);
        addMedicineNightCB = (CheckBox) findViewById(R.id.addMedicineNightCB);
        addMedicineDailyAlarmCB = (CheckBox) findViewById(R.id.addMedicineDailyAlarmCB);
        addMedicineDoctorSpinner= (Spinner) findViewById(R.id.addMedicineDoctorSpinner);

    }

    private void datePicker()  {
        addMedicineFromDateET.setOnClickListener(AddMedicineActivity.this);
        addMedicineToDateET.setOnClickListener(AddMedicineActivity.this);

        Calendar newCalendar = Calendar.getInstance();
        dateFromPickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                addMedicineFromDateET.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        dateToPickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                addMedicineToDateET.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }


    @Override
    public void onClick(View v) {
        if (v == addMedicineFromDateET) {
            dateFromPickerDialog.show();
        } else if (v == addMedicineToDateET) {
            dateToPickerDialog.show();
        }
    }

    private void loadSpinnerData() {

        List<String> doctorNames=new ArrayList<>();
        List<Integer> doctorId=new ArrayList<>();
        String value1="";

        for(int i=0; i<doctorModels.size(); i++){

            value1=doctorModels.get(i).getDocName();
            doctorNames.add(value1);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, doctorNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addMedicineDoctorSpinner.setAdapter(dataAdapter);
        addMedicineDoctorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int dID = (doctorModels.get(position)).getDoctorId();
                docId = String.valueOf(dID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void onClickMedicineSave(View view) {
        String saveUpdate=saveBtn.getText().toString();
        if (saveUpdate.equalsIgnoreCase("SAVE")) {
        String flag="A";


            int pmaxID = dataStorage.getProfileModelMaxID(DBHelper.TABLE_NAME_MEDICINE);

            String profileName = dataStorage.getProfileModelByPersonID(personID).get(0).getProfileName();

            int alarmC=0;
            alarmC=alarmC+2000+pmaxID;
            String alarmCode = String.valueOf(alarmC);
            String notificationCode = String.valueOf(alarmC);


        String medicineName = addMedicineNameET.getText().toString();
        String medicineDose= addMedicineDoseET.getText().toString();
        String mFromDate = addMedicineFromDateET.getText().toString();
        String mToDate = addMedicineToDateET.getText().toString();
        String mRemarks = addMedicineRemarksET.getText().toString();

        MedicineModel medicineModel = new MedicineModel(medicineName,medicineDose,mFromDate,
                mToDate,docId,personID,mAlarmState,mMorning,mAfterNoon,mEvening,
                mNight,mRemarks,flag,alarmCode,notificationCode);

        boolean insert = dataStorage.insertMedicine(medicineModel);
        if (insert) {


            if(addMedicineMorningCB.isChecked())
            {
                dailyRepetingAlarm(medicineName + ": Morning "+medicineDose, alarmC+6, "Daily-Medicine Take: " + profileName+ " ","06:00");


            }
            if(addMedicineAfternoonCB.isChecked())
            {
                dailyRepetingAlarm1(medicineName + ": AfterNoon "+medicineDose, alarmC+12, "Daily-Medicine Take: " + profileName + " ","13:00");

            }

            if(addMedicineEveningCB.isChecked())
            {
                dailyRepetingAlarm2(medicineName + ": Evening "+medicineDose, alarmC+18, "Daily-Medicine Take: " + profileName + " ","18:00");

            }

            if(addMedicineNightCB.isChecked())
            {
                dailyRepetingAlarm3(medicineName + ": Night "+medicineDose, alarmC+24, "Daily-Medicine Take: " + profileName + " ","23:59");

            }

            if(addMedicineDailyAlarmCB.isChecked())
            {
                //dailyRepetingAlarm(medicineName + ": Morning:AfterNoon:Evening:Night " + medicineDose, alarmC, "Daily-Medicine Take: " + profileName);
                  //Toast.makeText(getApplicationContext(),"Set Alarm",Toast.LENGTH_LONG).show();
            }



//            if(addDietReminderCB.isChecked())
//            {
//                reminderAlarm(dietType + ": " + dietMenu, alarmC, "Diet Menu: " + profileName + " ");
//            }

//            Toast.makeText(getApplication(), "Your Daily Alarm Set for Medicine", Toast.LENGTH_LONG).show();

            Toast.makeText(getApplication(), "Medicine Dose Added Successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplication(), "Failed", Toast.LENGTH_LONG).show();
        }
        }
        else if(saveUpdate.equalsIgnoreCase("UPDATE"))
        {
            String flag="A";
            String alarmCode="";
            String notificationCode="";

            String medicineName = addMedicineNameET.getText().toString();
            String medicineDose= addMedicineDoseET.getText().toString();
            String mFromDate = addMedicineFromDateET.getText().toString();
            String mToDate = addMedicineToDateET.getText().toString();
            String mRemarks = addMedicineRemarksET.getText().toString();

            MedicineModel medicineModel = new MedicineModel(medicineName,medicineDose,mFromDate,
                    mToDate,docId,personID,mAlarmState,mMorning,mAfterNoon,mEvening,
                    mNight,mRemarks,flag,alarmCode,notificationCode);

            boolean updated = dataStorage.updateMedicine(id,medicineModel);
            if (updated) {
                Toast.makeText(getApplication(), "Medicine Dose Updated Successfully", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplication(), "Failed", Toast.LENGTH_LONG).show();
            }
        }

        }



    public void onBoxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.addMedicineDailyAlarmCB:
                if (checked)
                {
                    mAlarmState ="1";
                }
                // Put some meat on the sandwich
                else{
                    mAlarmState ="0";
                }
                // Remove the meat
                break;
            case R.id.addMedicineMorningCB:
                if (checked)
                {
                    mMorning ="1";
                }
                // Cheese me
                else
                {
                    mMorning ="0";
                }
                // I'm lactose intolerant
                break;
            case R.id.addMedicineAfternoonCB:
                if (checked)
                {
                    mAfterNoon ="1";
                }
                // Cheese me
                else
                {
                    mAfterNoon ="0";
                }
                // I'm lactose intolerant
                break;
            case R.id.addMedicineEveningCB:
                if (checked)
                {
                    mEvening ="1";
                }
                // Cheese me
                else
                {
                    mEvening ="0";
                }
                // I'm lactose intolerant
                break;
            case R.id.addMedicineNightCB:
                if (checked)
                {
                    mNight ="1";
                }
                // Cheese me
                else
                {
                    mNight ="0";
                }
                // I'm lactose intolerant
                break;

        }
    }
    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){

            // String strBG=spinner.getItemAtPosition(i).toString();

            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){

                //  if(strBG.equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }

    public void onClickMedicineNew(View view) {

        addMedicineNameET.setText("");
        addMedicineDoseET.setText("");
        addMedicineFromDateET.setText("");
        addMedicineToDateET.setText("");
        addMedicineRemarksET.setText("");

        if (addMedicineMorningCB.isChecked()){
            addMedicineMorningCB.toggle();
        }

        if (addMedicineAfternoonCB.isChecked()){
            addMedicineAfternoonCB.toggle();
        }

        if (addMedicineEveningCB.isChecked()){
            addMedicineEveningCB.toggle();
        }

        if (addMedicineNightCB.isChecked()){
            addMedicineNightCB.toggle();
        }

        if (addMedicineDailyAlarmCB.isChecked()){
            addMedicineDailyAlarmCB.toggle();
        }

        //addMedicineDoctorSpinner= (Spinner) findViewById(R.id.addMedicineDoctorSpinner);
    }



    //Set Daily Alarm
    private void dailyRepetingAlarm(String alarmMsg, int RequestCode, String notificationTitle, String DailyDoseTime)
    {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        Date dateOfAlarm=null;

        try {
            dateOfAlarm = sdf1.parse(addMedicineFromDateET.getText().toString()+" "+DailyDoseTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(dateOfAlarm.getTime());
//
//        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
//        int month = cal.get(Calendar.MONTH);
//        int year = cal.get(Calendar.YEAR);
//        int hour=cal.get(Calendar.HOUR_OF_DAY);
//        int minutes=cal.get(Calendar.MINUTE);
//
//        cal.set(Calendar.DATE,dayOfMonth);
//        cal.set(Calendar.MONTH,month);
//        cal.set(Calendar.YEAR,year);
//        cal.set(Calendar.HOUR, hour);
//        cal.set(Calendar.MINUTE, minutes);

        Intent intent = new Intent(this , MyBroadcastReceiver.class);

        intent.putExtra("TITLE_NOTIFI",notificationTitle);
        intent.putExtra("ID_NOTIFI", String.valueOf(RequestCode));
        intent.putExtra("NOTIFI_MSG",alarmMsg);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), RequestCode+2, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 86400000, pendingIntent);
        Toast.makeText(this, alarmMsg + dateOfAlarm + " ",Toast.LENGTH_LONG).show();

    }


    private void dailyRepetingAlarm1(String alarmMsg, int RequestCode, String notificationTitle, String DailyDoseTime)
    {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        Date dateOfAlarm=null;

        try {
            dateOfAlarm = sdf1.parse(addMedicineFromDateET.getText().toString()+" "+DailyDoseTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(dateOfAlarm.getTime());
//
//        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
//        int month = cal.get(Calendar.MONTH);
//        int year = cal.get(Calendar.YEAR);
//        int hour=cal.get(Calendar.HOUR_OF_DAY);
//        int minutes=cal.get(Calendar.MINUTE);
//
//        cal.set(Calendar.DATE,dayOfMonth);
//        cal.set(Calendar.MONTH,month);
//        cal.set(Calendar.YEAR,year);
//        cal.set(Calendar.HOUR, hour);
//        cal.set(Calendar.MINUTE, minutes);

        Intent intent = new Intent(this , MyBroadcastReceiver.class);

        intent.putExtra("TITLE_NOTIFI",notificationTitle);
        intent.putExtra("ID_NOTIFI", String.valueOf(RequestCode));
        intent.putExtra("NOTIFI_MSG",alarmMsg);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), RequestCode+3, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager1.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 86400000, pendingIntent);
        Toast.makeText(this, alarmMsg + dateOfAlarm + " ",Toast.LENGTH_LONG).show();

    }

    private void dailyRepetingAlarm2(String alarmMsg, int RequestCode, String notificationTitle, String DailyDoseTime)
    {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        Date dateOfAlarm=null;

        try {
            dateOfAlarm = sdf1.parse(addMedicineFromDateET.getText().toString()+" "+DailyDoseTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(dateOfAlarm.getTime());
//
//        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
//        int month = cal.get(Calendar.MONTH);
//        int year = cal.get(Calendar.YEAR);
//        int hour=cal.get(Calendar.HOUR_OF_DAY);
//        int minutes=cal.get(Calendar.MINUTE);
//
//        cal.set(Calendar.DATE,dayOfMonth);
//        cal.set(Calendar.MONTH,month);
//        cal.set(Calendar.YEAR,year);
//        cal.set(Calendar.HOUR, hour);
//        cal.set(Calendar.MINUTE, minutes);

        Intent intent = new Intent(this , MyBroadcastReceiver.class);

        intent.putExtra("TITLE_NOTIFI",notificationTitle);
        intent.putExtra("ID_NOTIFI", String.valueOf(RequestCode));
        intent.putExtra("NOTIFI_MSG",alarmMsg);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), RequestCode+5, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager2 = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager2.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 86400000, pendingIntent);
        Toast.makeText(this, alarmMsg + dateOfAlarm + " ",Toast.LENGTH_LONG).show();

    }

    private void dailyRepetingAlarm3(String alarmMsg, int RequestCode, String notificationTitle, String DailyDoseTime)
    {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        Date dateOfAlarm=null;

        try {
            dateOfAlarm = sdf1.parse(addMedicineFromDateET.getText().toString()+" "+DailyDoseTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(dateOfAlarm.getTime());
//
//        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
//        int month = cal.get(Calendar.MONTH);
//        int year = cal.get(Calendar.YEAR);
//        int hour=cal.get(Calendar.HOUR_OF_DAY);
//        int minutes=cal.get(Calendar.MINUTE);
//
//        cal.set(Calendar.DATE,dayOfMonth);
//        cal.set(Calendar.MONTH,month);
//        cal.set(Calendar.YEAR,year);
//        cal.set(Calendar.HOUR, hour);
//        cal.set(Calendar.MINUTE, minutes);

        Intent intent = new Intent(this , MyBroadcastReceiver.class);

        intent.putExtra("TITLE_NOTIFI",notificationTitle);
        intent.putExtra("ID_NOTIFI", String.valueOf(RequestCode));
        intent.putExtra("NOTIFI_MSG",alarmMsg);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), RequestCode+8, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager3 = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager3.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 86400000, pendingIntent);
        Toast.makeText(this, alarmMsg + dateOfAlarm + " ",Toast.LENGTH_LONG).show();

    }



////    Set Reminder
//    private void reminderAlarm(String alarmMsg, int RequestCode, String notificationTitle, String DailyTime)
//    {
//        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
//        Date dateOfAlarm=null;
//
//        try {
////            dateOfAlarm = sdf1.parse(addDietDateET.getText().toString()+" "+addDietTimeET.getText().toString());
//
//            dateOfAlarm = sdf1.parse(addMedicineFromDateET.getText().toString()+" "+DailyTime);
//
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(dateOfAlarm.getTime());
////
////        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
////        int month = cal.get(Calendar.MONTH);
////        int year = cal.get(Calendar.YEAR);
////        int hour=cal.get(Calendar.HOUR_OF_DAY);
////        int minutes=cal.get(Calendar.MINUTE);
////
////        cal.set(Calendar.DATE,dayOfMonth);
////        cal.set(Calendar.MONTH,month);
////        cal.set(Calendar.YEAR,year);
////        cal.set(Calendar.HOUR, hour);
////        cal.set(Calendar.MINUTE, minutes);
//
//        Intent intent = new Intent(this , MyBroadcastReceiver.class);
//        intent.putExtra("TITLE_NOTIFI",notificationTitle);
//        intent.putExtra("ID_NOTIFI", String.valueOf(RequestCode));
//        intent.putExtra("NOTIFI_MSG",alarmMsg);
//
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), RequestCode, intent, 0);
//        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
//        Toast.makeText(this, alarmMsg + dateOfAlarm + " ",Toast.LENGTH_LONG).show();
//
//    }

    //Set Daily Alarm---DailyDose Will be 6+12+18+24
//    private void dailyRepetingAlarm(String alarmMsg, int RequestCode, String notificationTitle)//,String DailyDoseTime)
//    {
//        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
//
//        Date dateOfAlarm=null;
//
//        try {
//            dateOfAlarm = sdf1.parse(addMedicineFromDateET.getText().toString()+" "+"06:12");
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        Calendar cal = Calendar.getInstance();
//
//        cal.setTimeInMillis(dateOfAlarm.getTime());
//
////        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
////        int month = cal.get(Calendar.MONTH);
////        int year = cal.get(Calendar.YEAR);
////        int hour=cal.get(Calendar.HOUR_OF_DAY);
////        int minutes=cal.get(Calendar.MINUTE);
//
////        cal.set(Calendar.DATE,dayOfMonth);
////        cal.set(Calendar.MONTH,month);
////        cal.set(Calendar.YEAR,year);
////        cal.set(Calendar.HOUR, hour);
////        cal.set(Calendar.MINUTE, minutes);
//
//        Intent intent = new Intent(this , MyBroadcastReceiver.class);
//        intent.putExtra("TITLE_NOTIFI",notificationTitle);
//        intent.putExtra("ID_NOTIFI", String.valueOf(RequestCode));
//        intent.putExtra("NOTIFI_MSG",alarmMsg);
//
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), RequestCode, intent, 0);
//        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//
//        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 5000, pendingIntent);
//        Toast.makeText(this, alarmMsg + dateOfAlarm + " ",Toast.LENGTH_LONG).show();
//
//    }



}
