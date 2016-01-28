package com.warriors.group.icare;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import alarm.MyBroadcastReceiver;
import database.DBHelper;
import database.DataStorage;
import model.VaccineModel;

public class AddVaccinationActivity extends AppCompatActivity implements View.OnClickListener {
    EditText addVaccinationVaccineNameET;
    EditText addVaccinationDateET;
    EditText addVaccinationTimeET;
    EditText addVaccinationDetailsET;
    CheckBox addVaccinationReminderCB;
    Button saveBtn;
    Button profileNewBTN;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;
    String reminderState;
    SharedPreferences preferences;
    SharedPreferences preferences1;
    String personID;
    String vaccineIDUpdate;
    private DataStorage dataStorage;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vaccination);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        dataStorage = new DataStorage(getApplicationContext());
        preferences = getBaseContext().getSharedPreferences("person_id", MODE_PRIVATE);
        personID =preferences.getString("person_id", "");
        initializer();
        timePicker();
        datePicker();
//        Toast.makeText(AddVaccinationActivity.this, personID, Toast.LENGTH_LONG).show();
        preferences1=getBaseContext().getSharedPreferences("vaccine_id_update", MODE_PRIVATE);
        vaccineIDUpdate =preferences1.getString("vaccine_id_update", "");
        saveBtn= (Button) findViewById(R.id.addVaccinationSaveBTN);
        profileNewBTN=(Button) findViewById(R.id.addVaccinationNewBTN);
        if (vaccineIDUpdate.equalsIgnoreCase("")) {

            saveBtn.setText("SAVE");

        }
        else
        {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Update Vaccination");
            saveBtn.setText("UPDATE");
            id = Integer.valueOf(vaccineIDUpdate);
            showDataforUpdate();
            profileNewBTN.setVisibility(View.INVISIBLE);
        }

    }

    private void showDataforUpdate()
    {

        String vaccineName = dataStorage.getVaccineModelByVaccineId(id).get(0).getVaccineName();
        String vaccineDate = dataStorage.getVaccineModelByVaccineId(id).get(0).getVaccineDate();
        String vaccineTime = dataStorage.getVaccineModelByVaccineId(id).get(0).getVaccineTime();
        String details = dataStorage.getVaccineModelByVaccineId(id).get(0).getvDetails();
        String reminderState = dataStorage.getVaccineModelByVaccineId(id).get(0).getvReminderState();

        addVaccinationVaccineNameET.setText(vaccineName);
        addVaccinationDateET.setText(vaccineDate);
        addVaccinationTimeET.setText(vaccineTime);
        addVaccinationDetailsET.setText(details);
        if(reminderState == null || reminderState.equalsIgnoreCase("0"))
        {
            addVaccinationReminderCB.setChecked(false);
        }
        else
        {
            addVaccinationReminderCB.setChecked(true);
        }
    }

    private void initializer(){
        addVaccinationVaccineNameET = (EditText) findViewById(R.id.addVaccinationVaccineNameET);
        addVaccinationDateET = (EditText) findViewById(R.id.addVaccinationDateET);
        addVaccinationTimeET = (EditText) findViewById(R.id.addVaccinationTimeET);
        addVaccinationDetailsET = (EditText) findViewById(R.id.addVaccinationDetailsET);
        addVaccinationReminderCB = (CheckBox) findViewById(R.id.addVaccinationReminderCB);
    }

    private void timePicker() {
        addVaccinationTimeET.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddVaccinationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        addVaccinationTimeET.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, false);//true= 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }

    private void datePicker()   {
        addVaccinationDateET.setOnClickListener(AddVaccinationActivity.this);


        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(AddVaccinationActivity.this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                addVaccinationDateET.setText(dateFormatter.format(newDate.getTime()));

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public void onClick(View v) {
        if(v == addVaccinationDateET) {
            datePickerDialog.show();
        }

    }

    public void onVaccineReminderBoxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.addVaccinationReminderCB:
                if (checked) {
                    reminderState = "1";
                }
                // Put some meat on the sandwich
                else {
                    reminderState = "0";
                }
                // Remove the meat
                break;
        }
    }

    public void onClickVaccineSave(View view)
    {

        String profileName = dataStorage.getProfileModelByPersonID(personID).get(0).getProfileName();

        String saveUpdate=saveBtn.getText().toString();
        if (saveUpdate.equalsIgnoreCase("SAVE")) {
            String flag = "A";

            int pmaxID = dataStorage.getProfileModelMaxID(DBHelper.TABLE_NAME_VACCINATION);



            int alarmC=0;
            alarmC=alarmC+3000+pmaxID;

            String alarmCode = String.valueOf(alarmC);
            String notificationCode = String.valueOf(alarmC);

            String vaccineName = addVaccinationVaccineNameET.getText().toString();
            String date = addVaccinationDateET.getText().toString();
            String time = addVaccinationTimeET.getText().toString();
            String details = addVaccinationDetailsET.getText().toString();

            VaccineModel vaccineModel = new VaccineModel(vaccineName, date, time, reminderState, details, personID, flag, alarmCode, notificationCode);
            boolean insert = dataStorage.insertVaccine(vaccineModel);
            if (insert) {

                if(addVaccinationReminderCB.isChecked()){
                    reminderAlarm(vaccineName + ": " + details, alarmC, "Vaccine: " + profileName + " ");
                }

                Toast.makeText(getApplication(), "Vaccination Information Added Successfully", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplication(), "Failed", Toast.LENGTH_LONG).show();
            }
        }
        else if(saveUpdate.equalsIgnoreCase("UPDATE"))
        {
            String flag = "A";
            String alarmCode = dataStorage.getVaccineModelByVaccineId(id).get(0).getAlarmCode();
            String notificationCode = dataStorage.getVaccineModelByVaccineId(id).get(0).getNotificationCode();
            String vaccineName = addVaccinationVaccineNameET.getText().toString();
            String date = addVaccinationDateET.getText().toString();
            String time = addVaccinationTimeET.getText().toString();
            String details = addVaccinationDetailsET.getText().toString();

            VaccineModel vaccineModel = new VaccineModel(vaccineName, date, time, reminderState, details, personID, flag, alarmCode, notificationCode);
            boolean update = dataStorage.updateVaccine(id,vaccineModel);
            if (update) {


                if(addVaccinationReminderCB.isChecked()) {

                    reminderAlarm(vaccineName + ": " + details, Integer.valueOf(alarmCode) , "Vaccine: " + profileName + " ");

                }
                else {
                    Intent intent = new Intent(this, MyBroadcastReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(),
                            Integer.valueOf(alarmCode), intent, 0);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    if (alarmManager != null) {
                        alarmManager.cancel(pendingIntent);
                        Toast.makeText(getApplicationContext(), "Your Alarm Cancel", Toast.LENGTH_LONG).show();
                    }
                }

                Toast.makeText(getApplication(), "Vaccination Information Updated Successfully", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplication(), "Failed", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onClickVaccinationNew(View view) {

        addVaccinationVaccineNameET.setText("");
        addVaccinationDateET.setText("");
        addVaccinationTimeET.setText("");
        addVaccinationDetailsET.setText("");
        if(addVaccinationReminderCB.isChecked()){addVaccinationReminderCB.toggle();}

    }

    //Set Daily Alarm
    private void reminderAlarm(String alarmMsg, int RequestCode, String notificationTitle)
    {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        Date dateOfAlarm=null;

        try {
            dateOfAlarm = sdf1.parse(addVaccinationDateET.getText().toString()+" "+addVaccinationTimeET.getText().toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(dateOfAlarm.getTime());

        Intent intent = new Intent(this , MyBroadcastReceiver.class);

        intent.putExtra("TITLE_NOTIFI",notificationTitle);
        intent.putExtra("ID_NOTIFI", String.valueOf(RequestCode));
        intent.putExtra("NOTIFI_MSG",alarmMsg);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), RequestCode, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),pendingIntent);
        Toast.makeText(this, alarmMsg + dateOfAlarm + " ",Toast.LENGTH_LONG).show();

    }

}
