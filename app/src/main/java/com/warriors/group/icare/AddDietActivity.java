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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import alarm.MyBroadcastReceiver;
import database.DBHelper;
import database.DataStorage;
import model.DietModel;

public class AddDietActivity extends AppCompatActivity implements View.OnClickListener {

    EditText addDietMenuET;
    EditText addDietDateET;
    EditText addDietTimeET;
    CheckBox addDietReminderCB;
    CheckBox addDietAlarmCB;
    Spinner addDietTypeSP;

    String dietType;
    String dietIDUpdate;

    SharedPreferences preferences;
    SharedPreferences preferences1;
    String personID;
    String reminderState;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;
    SimpleDateFormat sdfs;
    private DataStorage dataStorage;
    int id;


    String alarmState;
    Button saveBtn;
    Button profileNewBTN;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diet);

        preferences=getBaseContext().getSharedPreferences("person_id", MODE_PRIVATE);
        personID =preferences.getString("person_id", "");
//        Toast.makeText(AddDietActivity.this,personID,Toast.LENGTH_LONG).show();
        dataStorage = new DataStorage(getApplicationContext());
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        preferences1=getBaseContext().getSharedPreferences("diet_id_update", MODE_PRIVATE);
        dietIDUpdate =preferences1.getString("diet_id_update", "");

        saveBtn= (Button) findViewById(R.id.addDietSaveBTN);
        profileNewBTN=(Button) findViewById(R.id.addDietNewBTN);

        initialize();
        datePicker();
        timePicker();
        dietTypeSpinner();
        if (dietIDUpdate.equalsIgnoreCase("")) {
            saveBtn.setText("SAVE");
        }
        else
        {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Update Diet");

            saveBtn.setText("UPDATE");
            id = Integer.valueOf(dietIDUpdate);



            showDataforUpdate();
            profileNewBTN.setVisibility(View.INVISIBLE);
        }


    }
    private void showDataforUpdate()
    {
        String menu = dataStorage.getDietModelByDietID(id).get(0).getDietMenu();
        String date = dataStorage.getDietModelByDietID(id).get(0).getDietDate();
        String time = dataStorage.getDietModelByDietID(id).get(0).getDietTime();
        String type = dataStorage.getDietModelByDietID(id).get(0).getDiteType();
        String alarm = dataStorage.getDietModelByDietID(id).get(0).getAlarmState();
        String reminder = dataStorage.getDietModelByDietID(id).get(0).getReminderState();
        if(alarm == null || alarm.equalsIgnoreCase("0"))
        {
            addDietAlarmCB.setChecked(false);
        }
        else
        {
            addDietAlarmCB.setChecked(true);
        }

        if(reminder == null || reminder.equalsIgnoreCase("0"))
        {
            addDietReminderCB.setChecked(false);
        }
        else
        {
            addDietReminderCB.setChecked(true);
        }
        addDietTypeSP.setSelection(getIndex(addDietTypeSP, type));
        addDietMenuET.setText(menu);
        addDietDateET.setText(date);
        addDietTimeET.setText(time);
    }
    //private method of your class
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


    public void OnclickSaveDiet(View view)
    {
        String saveUpdate=saveBtn.getText().toString();
        if (saveUpdate.equalsIgnoreCase("SAVE")) {
            String flag = "A";

            int pmaxID = dataStorage.getProfileModelMaxID(DBHelper.TABLE_NAME_DIET);

            String profileName = dataStorage.getProfileModelByPersonID(personID).get(0).getProfileName();

            int alarmC=0;
            alarmC=alarmC+1000+pmaxID;

            String alarmCode = String.valueOf(alarmC);
            String notificationCode = String.valueOf(alarmC);
            String date = addDietDateET.getText().toString();
            String time = addDietTimeET.getText().toString();
            String dietMenu = addDietMenuET.getText().toString();
            DietModel dietModel = new DietModel(dietType, dietMenu, date, time, reminderState, alarmState, personID, flag, alarmCode, notificationCode);
            boolean insert = dataStorage.insertDiet(dietModel);

            if (insert) {

                //Set Alarm and Reminder

                //String menu = dataStorage.getDietModelByDietID(id).get(0).getDietMenu();

                if(addDietAlarmCB.isChecked())
                {
                    dailyRepetingAlarm(dietType + ": " + dietMenu, alarmC, "Daily-Diet Menu: " + profileName + " ");

                }


                if(addDietReminderCB.isChecked())
                {
                    reminderAlarm(dietType + ": " + dietMenu, alarmC, "Diet Menu: " + profileName + " ");
                }



                Toast.makeText(getApplication(), "Diet Added Successfully", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(getApplication(), "Failed", Toast.LENGTH_LONG).show();
            }
        }
        else if(saveUpdate.equalsIgnoreCase("UPDATE"))
        {
            String flag = "A";
            String alarmCode = dataStorage.getDietModelByDietID(id).get(0).getAlarmCode();
            String notificationCode = dataStorage.getDietModelByDietID(id).get(0).getNotificationCode();
            String date = addDietDateET.getText().toString();
            String time = addDietTimeET.getText().toString();
            String dietMenu = addDietMenuET.getText().toString();
            DietModel dietModel = new DietModel(dietType, dietMenu, date, time, reminderState, alarmState, personID, flag, alarmCode, notificationCode);
            boolean insert = dataStorage.updateDiet(id,dietModel);
            if (insert) {


                if(addDietAlarmCB.isChecked()) {

                }
                    else
                {
                    Intent intent = new Intent(this , MyBroadcastReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(),
                            Integer.valueOf(alarmCode), intent, 0);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    if (alarmManager!= null) {
                      alarmManager.cancel(pendingIntent);
                        Toast.makeText(getApplicationContext(),"Your Alarm Cancel",Toast.LENGTH_LONG).show();
                }
            }
                Toast.makeText(getApplication(), "Diet Updated Successfully", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplication(), "Failed", Toast.LENGTH_LONG).show();
            }
        }
        /*Toast.makeText(AddDietActivity.this,dietType+" "+dietMenu+" "+date+" "+time+" Reminder: "+reminderState+" Alarm: "+alarmState,Toast.LENGTH_LONG).show();*/

    }
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.addDietReminderCB:
                if (checked)
                {
                    reminderState ="1";
                }
                // Put some meat on the sandwich
                else{
                    reminderState ="0";
                }
                // Remove the meat
                break;
            case R.id.addDietAlarmCB:
                if (checked)
                {
                    alarmState ="1";
                }
                // Cheese me
                else
                {
                    alarmState ="0";
                }
                // I'm lactose intolerant
                break;

        }
    }

    private void dietTypeSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.diet_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addDietTypeSP.setAdapter(adapter);
        addDietTypeSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dietType = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void timePicker() {
        addDietTimeET.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddDietActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        addDietTimeET.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, false);//true= 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
    }

    private void initialize()
    {
        addDietMenuET = (EditText) findViewById(R.id.addDietMenuET);
        addDietDateET = (EditText) findViewById(R.id.addDietDateET);
        addDietTimeET = (EditText) findViewById(R.id.addDietTimeET);
        addDietReminderCB = (CheckBox) findViewById(R.id.addDietReminderCB);
        addDietAlarmCB = (CheckBox) findViewById(R.id.addDietAlarmCB);
        addDietTypeSP = (Spinner) findViewById(R.id.addDietTypeSP);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        sdfs = new SimpleDateFormat("hh:mm a");
    }

    private void datePicker()   {
        addDietDateET.setOnClickListener(AddDietActivity.this);


        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(AddDietActivity.this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                addDietDateET.setText(dateFormatter.format(newDate.getTime()));

                }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public void onClick(View v) {
        if(v == addDietDateET) {
            datePickerDialog.show();
        }

    }


    public void onClickDietNew(View view) {

        addDietMenuET.setText("");
        addDietDateET.setText("");
        addDietTimeET.setText("");
        if(addDietReminderCB.isChecked())
        {
            addDietReminderCB.toggle();
        }
        if(addDietAlarmCB.isChecked())
        {
            addDietAlarmCB.toggle();
        }

    }


    //Set Reminder
    private void reminderAlarm(String alarmMsg, int RequestCode, String notificationTitle)
    {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date dateOfAlarm=null;

        try {
            dateOfAlarm = sdf1.parse(addDietDateET.getText().toString()+" "+addDietTimeET.getText().toString());

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

            PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), RequestCode, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
            Toast.makeText(this, alarmMsg + dateOfAlarm + " ",Toast.LENGTH_LONG).show();

        }

    //Set Daily Alarm
    private void dailyRepetingAlarm(String alarmMsg, int RequestCode, String notificationTitle)
    {
      SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        Date dateOfAlarm=null;

        try {
            dateOfAlarm = sdf1.parse(addDietDateET.getText().toString()+" "+addDietTimeET.getText().toString());

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

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), RequestCode, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 86400000, pendingIntent);
        Toast.makeText(this, alarmMsg + dateOfAlarm + " ",Toast.LENGTH_LONG).show();

    }

}
