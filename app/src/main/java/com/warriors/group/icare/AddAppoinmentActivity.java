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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

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
import model.AppointmentModel;
import model.DoctorModel;
import model.VaccineModel;

public class AddAppoinmentActivity extends AppCompatActivity implements View.OnClickListener {
    Spinner addApptDoctorSpinner;
    EditText addAppointmentDateET;
    EditText addAppointmentTimeET;
    EditText addAppointmentRemarksET;
    CheckBox addAppointmentReminderCB;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;
    String reminderState;
    SharedPreferences preferences;
    SharedPreferences preferences1;
    String personID;
    DataStorage dataStorage;;
    ArrayList<DoctorModel> doctorModels = new ArrayList<>();
    String docId;
    Button saveBtn;
    Button profileNewBTN;
    String appointment_id_update;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        dataStorage = new DataStorage(getApplicationContext());
        preferences = getBaseContext().getSharedPreferences("person_id", MODE_PRIVATE);
        personID =preferences.getString("person_id", "");
        doctorModels=dataStorage.getAllDoctorModel();
        initializer();
        timePicker();
        datePicker();
        loadSpinnerData();
        preferences1=getBaseContext().getSharedPreferences("appointment_id_update", MODE_PRIVATE);
        appointment_id_update =preferences1.getString("appointment_id_update", "");
        saveBtn= (Button) findViewById(R.id.addApptSaveBTN);
        profileNewBTN=(Button) findViewById(R.id.addApptNewBTN);
        if (appointment_id_update.equalsIgnoreCase(""))
        {
            saveBtn.setText("SAVE");
        }
        else
        {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Update Appointment");
            saveBtn.setText("UPDATE");
            id = Integer.valueOf(appointment_id_update);
            showDataforUpdate();
            profileNewBTN.setVisibility(View.INVISIBLE);

        }
    }

    private void showDataforUpdate()
    {
        String apptDate = dataStorage.getAppointmentModelByAppointmentId(id).get(0).getApptDate();
        String apptTime = dataStorage.getAppointmentModelByAppointmentId(id).get(0).getApptTime();
        String remarks = dataStorage.getAppointmentModelByAppointmentId(id).get(0).getRemarks();
        String doctorId = dataStorage.getAppointmentModelByAppointmentId(id).get(0).getDoctorId();
        String doctorName =dataStorage.getDoctorModelByDoctorID(Integer.valueOf(doctorId)).get(0).getDocName();
        String reminderState = dataStorage.getAppointmentModelByAppointmentId(id).get(0).getReminderState();
        addApptDoctorSpinner.setSelection(getIndex(addApptDoctorSpinner, doctorName));
        addAppointmentDateET.setText(apptDate);
        addAppointmentTimeET.setText(apptTime);
        addAppointmentRemarksET.setText(remarks);
        if(reminderState == null || reminderState.equalsIgnoreCase("0"))
        {
            addAppointmentReminderCB.setChecked(false);
        }
        else
        {
            addAppointmentReminderCB.setChecked(true);
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

    private void initializer()
    {
        addApptDoctorSpinner = (Spinner) findViewById(R.id.addApptDoctorSpinner);
        addAppointmentDateET = (EditText) findViewById(R.id.addAppointmentDateET);
        addAppointmentTimeET = (EditText) findViewById(R.id.addAppointmentTimeET);
        addAppointmentRemarksET= (EditText) findViewById(R.id.addAppointmentRemarksET);
        addAppointmentReminderCB = (CheckBox) findViewById(R.id.addAppointmentReminderCB);
    }
    private void timePicker() {
        addAppointmentTimeET.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddAppoinmentActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        addAppointmentTimeET.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, false);//true= 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }

    private void datePicker()   {
        addAppointmentDateET.setOnClickListener(AddAppoinmentActivity.this);


        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(AddAppoinmentActivity.this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                addAppointmentDateET.setText(dateFormatter.format(newDate.getTime()));

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public void onClick(View v) {
        if(v == addAppointmentDateET) {
            datePickerDialog.show();
        }

    }

    public void onApptReminderBoxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.addAppointmentReminderCB:
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

    private void loadSpinnerData() {

        List<String> doctorNames=new ArrayList<>();
        String value1="";

        for(int i=0; i<doctorModels.size(); i++){

            value1=doctorModels.get(i).getDocName();
            doctorNames.add(value1);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, doctorNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addApptDoctorSpinner.setAdapter(dataAdapter);
        addApptDoctorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    public void onClickAppointmentSave (View view)
    {


        String saveUpdate=saveBtn.getText().toString();
        String profileName = dataStorage.getProfileModelByPersonID(personID).get(0).getProfileName();
        String docName1=dataStorage.getDoctorModelByDoctorID(Integer.valueOf(docId)).get(0).getDocName();

        if (saveUpdate.equalsIgnoreCase("SAVE")) {
            String flag = "A";
            int pmaxID = dataStorage.getProfileModelMaxID(DBHelper.TABLE_NAME_APPOINTMENT);

            int alarmC=0;
            alarmC=alarmC+3000+pmaxID;

            String alarmCode = String.valueOf(alarmC);
            String notificationCode = String.valueOf(alarmC);

            String date = addAppointmentDateET.getText().toString();
            String time = addAppointmentTimeET.getText().toString();
            String remarks = addAppointmentRemarksET.getText().toString();

            AppointmentModel appointmentModel = new AppointmentModel(date, time
                    , remarks, docId, personID, reminderState, flag, alarmCode, notificationCode);

            boolean insert = dataStorage.insertAppointment(appointmentModel);
            if (insert) {

                if(addAppointmentReminderCB.isChecked()){
                    reminderAlarm(date+":"+time + ": Doctor: " + docName1, alarmC, "Doctor Appoinment: " + profileName + " ");
                }


                Toast.makeText(getApplication(), "Appointment Added Successfully", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplication(), "Failed", Toast.LENGTH_LONG).show();
            }
        }
        else if(saveUpdate.equalsIgnoreCase("UPDATE"))
        {
            String flag = "A";
            String alarmCode = dataStorage.getVaccineModelByVaccineId(id).get(0).getAlarmCode();
            String notificationCode = dataStorage.getVaccineModelByVaccineId(id).get(0).getNotificationCode();

            String date = addAppointmentDateET.getText().toString();
            String time = addAppointmentTimeET.getText().toString();
            String remarks = addAppointmentRemarksET.getText().toString();

            AppointmentModel appointmentModel = new AppointmentModel(date, time
                    , remarks, docId, personID, reminderState, flag, alarmCode, notificationCode);

            boolean update = dataStorage.updateAppointment(id,appointmentModel);
            if (update) {

                if(addAppointmentReminderCB.isChecked()) {

                    reminderAlarm(date+":"+time + ": Doctor: " + docName1, Integer.valueOf(alarmCode), "Doctor Appoinment: " + profileName + " ");

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



                Toast.makeText(getApplication(), "Appointment Updated Successfully", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplication(), "Failed", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onClickAppointmentNew(View view) {

        addAppointmentDateET.setText("");
        addAppointmentTimeET.setText("");
        addAppointmentRemarksET.setText("");

        if(addAppointmentReminderCB.isChecked())
        {
            addAppointmentReminderCB.toggle();
        }

    }


    //Set Daily Alarm
    private void reminderAlarm(String alarmMsg, int RequestCode, String notificationTitle)
    {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        Date dateOfAlarm=null;

        try {
            dateOfAlarm = sdf1.parse(addAppointmentDateET.getText().toString()+" "+addAppointmentTimeET.getText().toString());

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

        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        Toast.makeText(this, alarmMsg + dateOfAlarm + " ",Toast.LENGTH_LONG).show();

    }


}
