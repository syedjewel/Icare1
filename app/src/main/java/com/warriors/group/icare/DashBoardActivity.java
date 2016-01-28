package com.warriors.group.icare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import database.DataStorage;
import model.ProfileModel;
import util.CircleTransform;
import util.Utility;

public class DashBoardActivity extends AppCompatActivity {

    String personID;
    SharedPreferences preferences;
    ImageView dashBoardProfilePicIV;
    TextView dashBoardNameTV;
    TextView dashBoardGenderTV;
    TextView dashBoardBirthDateTV;
    TextView dashBoardAgeTV;
    TextView dashBoardEmailTV;
    TextView dashBoardMobileTV;
    TextView dashBoardHeightTV;
    TextView dashBoardWeightTV;
    TextView dashBoardBloodGroupTV;
    TextView dashBoardDiseaseTV;

    DataStorage dataStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        preferences=getBaseContext().getSharedPreferences("person_id", MODE_PRIVATE);
        personID =preferences.getString("person_id", "");
        dataStorage = new DataStorage(getApplicationContext());
        initializer();
        showData();

    }

    private void showData() {
        ArrayList<ProfileModel> profileModels = new ArrayList<>();
        profileModels = dataStorage.getProfileModelByPersonID(personID);
        String name = (profileModels.get(0)).getProfileName();
        String bloodGroup = (profileModels.get(0)).getBloodGroup();
        String gender = (profileModels.get(0)).getGender();
        String birthDt = (profileModels.get(0)).getDateOfBirth();
        String email = (profileModels.get(0)).getEmail();
        String mobile = (profileModels.get(0)).getContactNo();
        String height = (profileModels.get(0)).getHeight();
        String weight = (profileModels.get(0)).getWeight();
        String profileImagePath = (profileModels.get(0)).getImagePath();
        String majorDisease = (profileModels.get(0)).getMajorDisease();

        ///////////////////////////////////////////
        //Bitmap bmImg= ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(profileImagePath), 96, 90);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = null;
        try {
            birthDate=sdf.parse(birthDt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String ageOfPerson= Utility.calculateAge(birthDate);
        String bDate = sdf.format(birthDate);


        /////////////////////////////////////////
        if(profileImagePath!= null)
        {
            Uri uri = Uri.fromFile(new File(profileImagePath));
            Picasso.with(DashBoardActivity.this).load(uri)
                    .resize(225,225).centerCrop().transform(new CircleTransform()).into(dashBoardProfilePicIV);
        }
        dashBoardNameTV.setText(name);
        dashBoardGenderTV.setText(gender);
        dashBoardBloodGroupTV.setText("Blood group: "+bloodGroup);
        dashBoardBirthDateTV.setText("Birth Date: "+bDate);
        dashBoardAgeTV.setText("Age: "+ageOfPerson);
        dashBoardEmailTV.setText("Email:"+email);
        dashBoardMobileTV.setText("Contact no :"+mobile);
        dashBoardHeightTV.setText("Height: "+height+" Inch");
        dashBoardWeightTV.setText("Weight: "+weight+" kg");
        dashBoardDiseaseTV.setText("Major Disease: "+majorDisease);

    }

    private void initializer() {
        dashBoardDiseaseTV = (TextView) findViewById(R.id.dashBoardDiseaseTV);
        dashBoardNameTV = (TextView) findViewById(R.id.dashBoardNameTV);
        dashBoardGenderTV = (TextView) findViewById(R.id.dashBoardGenderTV);
        dashBoardBloodGroupTV = (TextView) findViewById(R.id.dashBoardBloodGroupTV);
        dashBoardBirthDateTV = (TextView) findViewById(R.id.dashBoardBirthDateTV);
        dashBoardAgeTV = (TextView) findViewById(R.id.dashBoardAgeTV);
        dashBoardEmailTV = (TextView) findViewById(R.id.dashBoardEmailTV);
        dashBoardMobileTV = (TextView) findViewById(R.id.dashBoardMobileTV);
        dashBoardHeightTV = (TextView) findViewById(R.id.dashBoardHeightTV);
        dashBoardWeightTV = (TextView) findViewById(R.id.dashBoardWeightTV);
        dashBoardProfilePicIV = (ImageView) findViewById(R.id.dashBoardProfilePicIV);
    }




    public void onClickDiet(View view)
    {
        Intent intent = new Intent(DashBoardActivity.this,DietListActivity.class);
        startActivity(intent);
    }
    public void onClickVaccination(View view)
    {
        Intent intent = new Intent(DashBoardActivity.this,VaccineListActivity.class);
        startActivity(intent);
    }
    public void onClickMedicalHistory(View view)
    {
        Intent intent = new Intent(DashBoardActivity.this,MedicalHistoryListActivity.class);
        startActivity(intent);
    }
    public void onClickDoctors(View view)
    {
        Intent intent = new Intent(DashBoardActivity.this,DoctorsListActivity.class);
        startActivity(intent);
    }
    public void onClickGeneralInfo(View view)
    {
        Intent intent = new Intent(DashBoardActivity.this,GeneralInformationActivity.class);
        startActivity(intent);
    }

    public void onClickAppointment(View view) {
        Intent intent = new Intent(DashBoardActivity.this,AppointmentListActivity.class);
        startActivity(intent);
    }

    public void onClickMedicineDose(View view) {
        Intent intent = new Intent(DashBoardActivity.this,MedicineListActivity.class);
        startActivity(intent);
    }

    public void onClickHospital(View view) {
        Intent intent = new Intent(DashBoardActivity.this,HospitalListActivity.class);
        startActivity(intent);
    }
    
}
