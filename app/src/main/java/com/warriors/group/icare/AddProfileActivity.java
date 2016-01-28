package com.warriors.group.icare;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import database.DBHelper;
import database.DataStorage;
import model.ProfileModel;
import util.CircleTransform;
import util.Utility;

public class AddProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private DataStorage dataStorage;

    int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    EditText profileNameET;
    EditText profileBirthDateET;
    ImageView profilePictureIMG;
    Spinner profileGenderSpinner;
    Spinner profileBloodGroupSpinner;
    Spinner profileRelationSpinner;
    EditText profileHeightET;
    EditText profileWeightET;
    EditText profileEmailET;
    EditText profileMobileNoET;
    RadioGroup idRadioGroup;
    EditText profileNIDBIDET;
    EditText profileDiseaseET;
    EditText profileRelationshipET;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    String gender;
    String bloodGroup;
    String selectedImagePath;

    String selectedImagePathOfCamera;

    private RadioButton radioNIDBCNButton;

    SharedPreferences preferences;
    String personIDUpdate;
    int profileID=0;
    Button saveBtn;
    Button profileNewBTN;
    String relation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);
        dataStorage = new DataStorage(getApplicationContext());
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        preferences=getBaseContext().getSharedPreferences("person_id_update", MODE_PRIVATE);
        personIDUpdate =preferences.getString("person_id_update", "");
        saveBtn= (Button) findViewById(R.id.addProfileSaveBtn);
        profileNewBTN=(Button) findViewById(R.id.profileNewBTN);
        initializer();
        genderSpinner();
        bloodGroupSpinner();
        relationSpinner();
        setDateTimeField();
        if (personIDUpdate.equalsIgnoreCase(""))
        {
            saveBtn.setText("SAVE");
        }
        else
        {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Update Profile");
            profileID=Integer.valueOf(personIDUpdate.toString());
            showDataforUpdate();
            saveBtn.setText("UPDATE");
            profileNewBTN.setVisibility(View.INVISIBLE);
        }
    }

    private void initializer(){

        profileNameET=(EditText) findViewById(R.id.profileNameET);
        profileBirthDateET=(EditText) findViewById(R.id.profileBirthDateET);
        profilePictureIMG=(ImageView) findViewById(R.id.profilePictureIMG);
        profileGenderSpinner=(Spinner) findViewById(R.id.profileGenderSpinner);
        profileBloodGroupSpinner=(Spinner) findViewById(R.id.profileBloodGroupSpinner);
        profileHeightET=(EditText) findViewById(R.id.profileHeightET);
        profileWeightET=(EditText) findViewById(R.id.profileWeightET);
        profileEmailET=(EditText) findViewById(R.id.profileEmailET);
        profileMobileNoET= (EditText) findViewById(R.id.profileMobileNoET);
        profileNIDBIDET= (EditText) findViewById(R.id.profileNIDBIDET);
        idRadioGroup= (RadioGroup) findViewById(R.id.idRadioGroup);
        profileDiseaseET = (EditText) findViewById(R.id.profileDiseaseET);
        profileRelationSpinner = (Spinner) findViewById(R.id.profileRelationSpinner);
    }

    public void onClickProfileSave(View view)
    {

    String saveUpdate=saveBtn.getText().toString();
    if (saveUpdate.equalsIgnoreCase("SAVE")) {

        int selectedId = idRadioGroup.getCheckedRadioButtonId();
        radioNIDBCNButton = (RadioButton) findViewById(selectedId);

        Utility utility = new Utility();
        String personId = utility.personIdGenerate(profileNIDBIDET.getText().toString(), radioNIDBCNButton.getText().toString());

        int pmaxID = dataStorage.getProfileModelMaxID(DBHelper.TABLE_NAME_PROFILE);

        ProfileModel profileModel = new ProfileModel();
        profileModel.setProfileName(profileNameET.getText().toString());
        profileModel.setDateOfBirth(profileBirthDateET.getText().toString());
        profileModel.setImagePath(selectedImagePathOfCamera);
        profileModel.setGender(gender);
        profileModel.setBloodGroup(bloodGroup);
        profileModel.setHeight(profileHeightET.getText().toString());
        profileModel.setWeight(profileWeightET.getText().toString());
        profileModel.setEmail(profileEmailET.getText().toString());
        profileModel.setContactNo(profileMobileNoET.getText().toString());
        profileModel.setIdType(radioNIDBCNButton.getText().toString());
        profileModel.setMajorDisease(profileDiseaseET.getText().toString());
        profileModel.setPersonId(personId + String.valueOf(pmaxID));
        profileModel.setNid_bcn(profileNIDBIDET.getText().toString());
        profileModel.setRelation(relation);
        profileModel.setProfile_flag("A");

        boolean insert = dataStorage.insertProfile(profileModel);
        if (insert) {
            Toast.makeText(getApplication(), "Profile Added Successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplication(), "Failed or This profile has been Exists", Toast.LENGTH_LONG).show();
        }
    }

    else if (saveUpdate.equalsIgnoreCase("UPDATE"))
            {
                //personIDUpdate

                int selectedId = idRadioGroup.getCheckedRadioButtonId();
                radioNIDBCNButton = (RadioButton) findViewById(selectedId);

                Utility utility = new Utility();
                String personId = utility.personIdGenerate(profileNIDBIDET.getText().toString(), radioNIDBCNButton.getText().toString());

                int pmaxID = profileID;

                ProfileModel profileModel = new ProfileModel();
                profileModel.setProfileName(profileNameET.getText().toString());
                profileModel.setDateOfBirth(profileBirthDateET.getText().toString());
                profileModel.setImagePath(selectedImagePathOfCamera);

                profileModel.setGender(gender);
                profileModel.setBloodGroup(bloodGroup);
                profileModel.setHeight(profileHeightET.getText().toString());
                profileModel.setWeight(profileWeightET.getText().toString());
                profileModel.setEmail(profileEmailET.getText().toString());
                profileModel.setContactNo(profileMobileNoET.getText().toString());
                profileModel.setIdType(radioNIDBCNButton.getText().toString());
                profileModel.setMajorDisease(profileDiseaseET.getText().toString());
                profileModel.setPersonId(personId + String.valueOf(pmaxID));
                profileModel.setNid_bcn(profileNIDBIDET.getText().toString());
                profileModel.setRelation(relation);
                //profileModel.setProfile_flag("A");

                boolean updated = dataStorage.updateProfile(profileID,profileModel);
                if (updated) {

                    String profile_id = personId + String.valueOf(pmaxID);

                    preferences=getBaseContext().getSharedPreferences("person_id", MODE_PRIVATE);
                    String oldpersonId =preferences.getString("person_id", "");

                    //String personId = profileModels.get(position).getPersonId();
                    boolean updatedPersonid = dataStorage.updateAllActivitiesByPersonId(oldpersonId,profile_id);
                    if (updatedPersonid) {

                    } else {

                    }


                    preferences = getBaseContext().getSharedPreferences("person_id", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("person_id", profile_id);
                    editor.apply();
                    editor.commit();

                    //Update All Activitites by Person ID



                    Toast.makeText(getApplication(), "Profile Updated Successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplication(), "Failed or This profile has been Exists", Toast.LENGTH_LONG).show();
                }

            }

        //Toast.makeText(AddProfileActivity.this, radioNIDBCNButton.getText(), Toast.LENGTH_SHORT).show();
    }

    public void onClickProfileNew(View view)
    {
        profileNameET.setText("");
        profileBirthDateET.setText("");
        profilePictureIMG.setImageDrawable(null);
        profileHeightET.setText("");
        profileWeightET.setText("");
        profileEmailET.setText("");
        profileMobileNoET.setText("");
        profileNIDBIDET.setText("");
        profileDiseaseET.setText("");
    }


    private void genderSpinner()
    {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profileGenderSpinner.setAdapter(adapter);
        profileGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void bloodGroupSpinner()
    {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.blood_group_spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profileBloodGroupSpinner.setAdapter(adapter);
        profileBloodGroupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bloodGroup = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void relationSpinner()
    {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.relation_spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profileRelationSpinner.setAdapter(adapter);
        profileRelationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                relation = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(AddProfileActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        selectedImagePathOfCamera= String.valueOf(destination);



        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        profilePictureIMG.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

         selectedImagePath = cursor.getString(column_index);

        selectedImagePathOfCamera=selectedImagePath;

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        profilePictureIMG.setImageBitmap(bm);
    }


    public void onClickbtnTakePhoto(View view) {

        selectImage();

    }


    private void setDateTimeField()   {
        profileBirthDateET.setOnClickListener(this);


        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                profileBirthDateET.setText(dateFormatter.format(newDate.getTime()));

                String birthdateET=profileBirthDateET.getText().toString();

                Date birthDate = null;

                Utility utility=new Utility();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                try {
                    birthDate=sdf.parse(birthdateET);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String ageOfPerson=utility.calculateAge(birthDate);

               // ageCalcTV.setText(ageOfPerson);


            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }



    private void showDataforUpdate()
    {
        ArrayList<ProfileModel> profileModels = new ArrayList<>();
        profileModels = dataStorage.getProfileModelByProfileID(profileID);
        String name = (profileModels.get(0)).getProfileName();
        String bloodGroup = (profileModels.get(0)).getBloodGroup();
        String gender = (profileModels.get(0)).getGender();
        String birthDt = (profileModels.get(0)).getDateOfBirth();
        String email = (profileModels.get(0)).getEmail();
        String mobile = (profileModels.get(0)).getContactNo();
        String height = (profileModels.get(0)).getHeight();
        String weight = (profileModels.get(0)).getWeight();
        String PersonID=(profileModels.get(0)).getPersonId();
        String profileImagePath = (profileModels.get(0)).getImagePath();
        selectedImagePathOfCamera=profileImagePath;
        String majorDisease = (profileModels.get(0)).getMajorDisease();
        String nIdBcn=(profileModels.get(0)).getNid_bcn();
        String relation=(profileModels.get(0)).getRelation();

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
            Picasso.with(AddProfileActivity.this).load(uri)
                    .resize(225,225).centerCrop().into(profilePictureIMG);
        }
        profileNameET.setText(name);

        profileBloodGroupSpinner.setSelection(getIndex(profileBloodGroupSpinner, bloodGroup));
        profileGenderSpinner.setSelection(getIndex(profileGenderSpinner, gender));
        profileBirthDateET.setText(bDate);
        profileEmailET.setText(email);
        profileMobileNoET.setText(mobile);
        profileHeightET.setText(height);
        profileWeightET.setText(weight);
        profileDiseaseET.setText(majorDisease);
        profileNIDBIDET.setText(nIdBcn);
        profileRelationSpinner.setSelection(getIndex(profileRelationSpinner, relation));


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

    @Override
    public void onClick(View v) {
        if(v == profileBirthDateET) {
            datePickerDialog.show();
        }

    }
}
