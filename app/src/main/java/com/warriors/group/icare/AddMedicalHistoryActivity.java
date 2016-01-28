package com.warriors.group.icare;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import database.DataStorage;
import model.DoctorModel;
import model.MedicalHistoryModel;
import model.VaccineModel;
import util.CircleTransform;

public class AddMedicalHistoryActivity extends AppCompatActivity implements View.OnClickListener {
    Spinner addHistoryDoctorSpinner;
    EditText addMedicalHistoryDateET;
    EditText addMedicalHistoryDetailsET;
    ImageView addMedicalHistoryIV;
    Button saveBtn;
    Button profileNewBTN;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;
    SharedPreferences preferences;
    SharedPreferences preferences1;
    String personID;
    DataStorage dataStorage;;
    ArrayList<DoctorModel> doctorModels = new ArrayList<>();
    String docId;
    String selectedImagePath;

    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    String selectedImagePathOfCamera;
    String medical_history_id_update;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medical_history);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        dataStorage = new DataStorage(getApplicationContext());
        preferences = getBaseContext().getSharedPreferences("person_id", MODE_PRIVATE);
        personID =preferences.getString("person_id", "");
        doctorModels=dataStorage.getAllDoctorModel();
        initializer();
        datePicker();
        loadSpinnerData();
        preferences1=getBaseContext().getSharedPreferences("medical_history_id_update", MODE_PRIVATE);
        medical_history_id_update =preferences1.getString("medical_history_id_update", "");
        saveBtn= (Button) findViewById(R.id.addMedicalHistorySaveBTN);
        profileNewBTN=(Button) findViewById(R.id.addMedicalHistoryNewBTN);
        if (medical_history_id_update.equalsIgnoreCase("")) {

            saveBtn.setText("SAVE");

        }
        else
        {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Update Medical History");
            saveBtn.setText("UPDATE");
            id = Integer.valueOf(medical_history_id_update);
            showDataforUpdate();


            profileNewBTN.setVisibility(View.INVISIBLE);
        }
    }

    private void showDataforUpdate()
    {
        String mhDate = dataStorage.getMedicalHistoryModelByID(id).get(0).getMhDate();
        String details = dataStorage.getMedicalHistoryModelByID(id).get(0).getDetails();
        String prescriptionPath = dataStorage.getMedicalHistoryModelByID(id).get(0).getPrescriptionPath();
        String doctorId = dataStorage.getMedicalHistoryModelByID(id).get(0).getDoctorId();
        String doctorName =dataStorage.getDoctorModelByDoctorID(Integer.valueOf(doctorId)).get(0).getDocName();


        /////////////////////////////////////////
        if(prescriptionPath!= null)
        {
            Uri uri = Uri.fromFile(new File(prescriptionPath));
            Picasso.with(AddMedicalHistoryActivity.this).load(uri)
                    .resize(225, 225).centerCrop().into(addMedicalHistoryIV);
        }


       // prescriptionPath=

        addMedicalHistoryDateET.setText(mhDate);
        addMedicalHistoryDetailsET.setText(details);
        selectedImagePathOfCamera=prescriptionPath;
        addHistoryDoctorSpinner.setSelection(getIndex(addHistoryDoctorSpinner, doctorName));
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

    private void initializer(){
        addHistoryDoctorSpinner = (Spinner) findViewById(R.id.addHistoryDoctorSpinner);
        addMedicalHistoryDateET = (EditText) findViewById(R.id.addMedicalHistoryDateET);
        addMedicalHistoryDetailsET = (EditText) findViewById(R.id.addMedicalHistoryDetailsET);
        addMedicalHistoryIV = (ImageView) findViewById(R.id.addMedicalHistoryIV);
    }

    private void datePicker()   {
        addMedicalHistoryDateET.setOnClickListener(AddMedicalHistoryActivity.this);


        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(AddMedicalHistoryActivity.this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                addMedicalHistoryDateET.setText(dateFormatter.format(newDate.getTime()));

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public void onClick(View v) {
        if(v == addMedicalHistoryDateET) {
            datePickerDialog.show();
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
        addHistoryDoctorSpinner.setAdapter(dataAdapter);
        addHistoryDoctorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    //------------------------------------------------------------------------------------------//
    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(AddMedicalHistoryActivity.this);
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

        addMedicalHistoryIV.setImageBitmap(thumbnail);
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

        addMedicalHistoryIV.setImageBitmap(bm);
    }

    public void onClickbtnTakePrescription(View view) {

        selectImage();

    }

    public void onClickMedicalHistorySave(View view)
    {

        String saveUpdate=saveBtn.getText().toString();
        if (saveUpdate.equalsIgnoreCase("SAVE")) {
        String flag="A";

        String date = addMedicalHistoryDateET.getText().toString();
        String details = addMedicalHistoryDetailsET.getText().toString();

        MedicalHistoryModel medicalHistoryModel = new MedicalHistoryModel(docId,date,personID,selectedImagePathOfCamera,details,flag);
        boolean insert = dataStorage.insertMedicalHistory(medicalHistoryModel);
        if (insert) {
            Toast.makeText(getApplication(), "Medical History Added Successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplication(), "Failed", Toast.LENGTH_LONG).show();
        }
        }
        else if (saveUpdate.equalsIgnoreCase("UPDATE")) {
            String flag="A";

            String date = addMedicalHistoryDateET.getText().toString();
            String details = addMedicalHistoryDetailsET.getText().toString();

            MedicalHistoryModel medicalHistoryModel = new MedicalHistoryModel(docId,date,personID,selectedImagePathOfCamera,details,flag);
            boolean update = dataStorage.updateMedicalHistory(id,medicalHistoryModel);
            if (update) {
                Toast.makeText(getApplication(), "Medical History Updated Successfully", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplication(), "Failed", Toast.LENGTH_LONG).show();
            }
        }

    }

    public void onClickMedicalHistoryNew(View view) {
        addMedicalHistoryDateET.setText("");
        addMedicalHistoryDetailsET.setText("");
        addMedicalHistoryIV.setImageDrawable(null);
    }
}
