package com.warriors.group.icare;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import database.DataStorage;
import model.DoctorModel;

public class AddDoctorActivity extends AppCompatActivity {
    EditText addDoctorNameET;
    EditText addDoctorAddressET;
    EditText addDoctorEmailAddressET;
    EditText addDoctorMobileNumberET;
    Spinner addDoctorSpecialistSpinner;
    DataStorage dataStorage;
    //addDoctorSaveBTN
    SharedPreferences preferences1;
    Button saveBtn;
    Button profileNewBTN;
    String doctor_id_update;
    String specialist;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);
        initializer();
        specialistSpinner();
        dataStorage = new DataStorage(getApplicationContext());
        preferences1=getBaseContext().getSharedPreferences("hospital_id_update", MODE_PRIVATE);
        doctor_id_update =preferences1.getString("hospital_id_update", "");



        saveBtn= (Button) findViewById(R.id.addDoctorSaveBTN);
        profileNewBTN=(Button) findViewById(R.id.addDoctorNewBTN);
        if (doctor_id_update.equalsIgnoreCase("")) {

            saveBtn.setText("SAVE");

        }
        else
        {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Update Doctor");
            saveBtn.setText("UPDATE");
            profileNewBTN.setVisibility(View.INVISIBLE);
            id = Integer.valueOf(doctor_id_update);
            showDataforUpdate();

        }


    }
    private void initializer(){
        addDoctorNameET = (EditText) findViewById(R.id.addDoctorNameET);
        addDoctorAddressET = (EditText) findViewById(R.id.addDoctorAddressET);
        addDoctorEmailAddressET = (EditText) findViewById(R.id.addDoctorEmailAddressET);
        addDoctorMobileNumberET = (EditText) findViewById(R.id.addDoctorMobileNumberET);
        addDoctorSpecialistSpinner = (Spinner) findViewById(R.id.addDoctorSpecialistSpinner);

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

    private void showDataforUpdate()
    {
        String docName = dataStorage.getDoctorModelByDoctorID(id).get(0).getDocName();
        String docSpecialist = dataStorage.getDoctorModelByDoctorID(id).get(0).getDocSpecialist();
        String docAddress = dataStorage.getDoctorModelByDoctorID(id).get(0).getDocAddress();
        String docEmail = dataStorage.getDoctorModelByDoctorID(id).get(0).getDocEmail();
        String docContactNo = dataStorage.getDoctorModelByDoctorID(id).get(0).getDocContactNo();
        addDoctorSpecialistSpinner.setSelection(getIndex(addDoctorSpecialistSpinner, docSpecialist));

        addDoctorNameET.setText(docName);
        addDoctorAddressET.setText(docAddress);
        addDoctorEmailAddressET.setText(docEmail);
        addDoctorMobileNumberET.setText(docContactNo);
    }

    public void onClickSaveDoctor(View view)
    {
        String saveUpdate=saveBtn.getText().toString();

        if (saveUpdate.equalsIgnoreCase("SAVE"))
        {
            String flag = "A";
            String doctorName = addDoctorNameET.getText().toString();
            String doctorSpecialist=specialist;
            String doctorAddress = addDoctorAddressET.getText().toString();
            String doctorEmailAddress = addDoctorEmailAddressET.getText().toString();
            String doctorMobileNumber = addDoctorMobileNumberET.getText().toString();

            DoctorModel doctorModel = new DoctorModel(doctorName, doctorSpecialist, doctorAddress, doctorEmailAddress, doctorMobileNumber, flag);
            boolean insert = dataStorage.insertDoctor(doctorModel);
            if (insert) {
                Toast.makeText(getApplication(), "Doctor Added Successfully", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplication(), "Failed or This Doctor has been Exists", Toast.LENGTH_LONG).show();
            }
        }
        else if (saveUpdate.equalsIgnoreCase("UPDATE"))

        {
            String flag = "A";
            String doctorName = addDoctorNameET.getText().toString();
            String doctorSpecialist=specialist;
            String doctorAddress = addDoctorAddressET.getText().toString();
            String doctorEmailAddress = addDoctorEmailAddressET.getText().toString();
            String doctorMobileNumber = addDoctorMobileNumberET.getText().toString();

            DoctorModel doctorModel = new DoctorModel(doctorName, doctorSpecialist, doctorAddress, doctorEmailAddress, doctorMobileNumber, flag);
            boolean updated = dataStorage.updateDoctor(id, doctorModel);
            if (updated) {
                Toast.makeText(getApplication(), "Doctor Updated Successfully", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplication(), "Failed or This Doctor has been Exists", Toast.LENGTH_LONG).show();
            }

        }

    }

    public void onClickDoctorNew(View view) {

        addDoctorNameET.setText("");
        addDoctorAddressET.setText("");
        addDoctorEmailAddressET.setText("");
        addDoctorMobileNumberET.setText("");

    }
    private void specialistSpinner()
    {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.doctor_specialist_spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addDoctorSpecialistSpinner.setAdapter(adapter);
        addDoctorSpecialistSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                specialist = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
