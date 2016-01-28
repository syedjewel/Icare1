package com.warriors.group.icare;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import database.DataStorage;
import model.AppointmentModel;
import model.DoctorModel;

public class AppointmentDetailsActivity extends AppCompatActivity {
    DataStorage dataStorage;
    SharedPreferences preferences1;
    String appointment_id_show;
    ArrayList<AppointmentModel> appointmentModels = new ArrayList<>();
    ArrayList<DoctorModel> doctorModels = new ArrayList<>();
    String doctorMobile="";
    String doctorEmail="";

    TextView aaptShowDoctorTV;
    TextView aaptShowDateTV;
    TextView aaptShowTimeTV;
    TextView aaptShowRemarksTV;
    TextView aaptShowAddressTV;
    TextView aaptContactTV;
    TextView aaptShowEmailTV;
    TextView aaptShowSpecialistTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);
        dataStorage = new DataStorage(getApplicationContext());
        preferences1=getBaseContext().getSharedPreferences("appointment_id_show", MODE_PRIVATE);
        appointment_id_show =preferences1.getString("appointment_id_show", "");
        appointmentModels =dataStorage.getAppointmentModelByAppointmentId(Integer.valueOf(appointment_id_show));
        initializer();
        showData();

    }

    private void showData()
    {
        String aaptDate = appointmentModels.get(0).getApptDate();
        String aaptTime = appointmentModels.get(0).getApptTime();
        String doctorID = appointmentModels.get(0).getDoctorId();
        String remarks = appointmentModels.get(0).getRemarks();
        int docID=0;
        String doctorName="";
        String doctorSpecialist="";
        String doctorAddress="";

        if (doctorID!=null) {
            docID = Integer.valueOf(doctorID);
        }
        doctorModels = dataStorage.getDoctorModelByDoctorID(docID);
        if(doctorModels.size()>0) {
            doctorName = doctorModels.get(0).getDocName();
            doctorSpecialist = doctorModels.get(0).getDocSpecialist();
            doctorAddress = doctorModels.get(0).getDocAddress();
            doctorMobile = doctorModels.get(0).getDocContactNo();
            doctorEmail = doctorModels.get(0).getDocEmail();
        }
        aaptShowDoctorTV.setText(doctorName);
        aaptShowDateTV.setText(aaptDate);
        aaptShowTimeTV.setText(aaptTime);
        aaptShowRemarksTV.setText(remarks);
        aaptShowAddressTV.setText(doctorAddress);
        aaptContactTV.setText(doctorMobile);
        aaptShowEmailTV.setText(doctorEmail);
        aaptShowSpecialistTV.setText(doctorSpecialist);
        aaptContactTV.setPaintFlags(aaptContactTV.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        aaptShowEmailTV.setPaintFlags(aaptShowEmailTV.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    private void initializer()
    {
        aaptShowDoctorTV = (TextView) findViewById(R.id.aaptShowDoctorTV);
        aaptShowDateTV = (TextView) findViewById(R.id.aaptShowDateTV);
        aaptShowTimeTV = (TextView) findViewById(R.id.aaptShowTimeTV);
        aaptShowRemarksTV = (TextView) findViewById(R.id.aaptShowRemarksTV);
        aaptShowAddressTV = (TextView) findViewById(R.id.aaptShowAddressTV);
        aaptContactTV = (TextView) findViewById(R.id.aaptContactTV);
        aaptShowEmailTV = (TextView) findViewById(R.id.aaptShowEmailTV);
        aaptShowSpecialistTV = (TextView) findViewById(R.id.aaptShowSpecialistTV);
    }

    public void onClickCall(View view)
    {
        new AlertDialog.Builder(AppointmentDetailsActivity.this)
                .setTitle("User Action")
                .setMessage("What you want to do?")
                .setPositiveButton("CALL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        String number;
                        number = "tel:" + doctorMobile;
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse(number));
                        if (ActivityCompat.checkSelfPermission(AppointmentDetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        startActivity(callIntent);

                    }
                })
                .setNegativeButton("SEND SMS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms",doctorMobile, null)));
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void onClickEmail(View view)
    {
        new AlertDialog.Builder(AppointmentDetailsActivity.this)
                .setTitle("User Action")
                .setMessage("What you want to do?")
                .setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("SEND Email", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("message/rfc822");
                        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{doctorEmail});
                        i.putExtra(Intent.EXTRA_SUBJECT, "");
                        i.putExtra(Intent.EXTRA_TEXT   , "");
                        try
                        {
                            startActivity(Intent.createChooser(i, "Send mail..."));
                        }
                        catch (android.content.ActivityNotFoundException ex)
                        {
                            Toast.makeText(AppointmentDetailsActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }



}
