package com.warriors.group.icare;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import adapter.DietListAdapter;
import adapter.DoctorsListAdapter;
import database.DataStorage;
import model.DoctorModel;

public class DoctorsListActivity extends AppCompatActivity {

    ArrayList<DoctorModel> doctorModels = new ArrayList<>();
    DataStorage dataStorage;
    ListView doctorListView;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_list);

        dataStorage = new DataStorage(getApplicationContext());
        doctorListView = (ListView) findViewById(R.id.doctorListView);

        showDoctorList();
    }

    @Override
    protected void onStart() {
        super.onStart();
        showDoctorList();
    }

    private void showDoctorList() {
        doctorModels = dataStorage.getAllDoctorModel();
        DoctorsListAdapter doctorsListAdapter = new DoctorsListAdapter(DoctorsListActivity.this, doctorModels);
        doctorListView.setAdapter(doctorsListAdapter);
        doctorsListAdapter.notifyDataSetChanged();
        doctorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DoctorsListActivity.this);
                builder.setTitle("User Action");
                final CharSequence[] items = {"Call", "SMS", "Email"};

                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Call")) {

                            String receiveNumber = doctorModels.get(position).getDocContactNo();
                            String number;
                            number = "tel:" + receiveNumber;
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse(number));
                            if (ActivityCompat.checkSelfPermission(DoctorsListActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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

                        } else if (items[item].equals("SMS")) {
                            String receiveNumber = doctorModels.get(position).getDocContactNo();
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms",receiveNumber, null)));
                        }
                        else if (items[item].equals("Email")) {
                            String reciever = doctorModels.get(position).getDocEmail();
                            Intent i = new Intent(Intent.ACTION_SEND);
                            i.setType("message/rfc822");
                            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{reciever});
                            i.putExtra(Intent.EXTRA_SUBJECT, "");
                            i.putExtra(Intent.EXTRA_TEXT   , "");
                            try {
                                startActivity(Intent.createChooser(i, "Send mail..."));
                            } catch (android.content.ActivityNotFoundException ex) {
                                Toast.makeText(DoctorsListActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
                builder.show();
            }
        });
        doctorListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                final CharSequence[] items = { "Update", "Delete"};

                AlertDialog.Builder builder = new AlertDialog.Builder(DoctorsListActivity.this);
                builder.setTitle("User Action");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Update")) {

                            Intent intent = new Intent(DoctorsListActivity.this, AddDoctorActivity.class);
                            String doctor_id_update = String.valueOf((doctorModels.get(position)).getDoctorId());

                            preferences = getBaseContext().getSharedPreferences("hospital_id_update", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("hospital_id_update", doctor_id_update);
                            editor.apply();
                            editor.commit();

                            startActivity(intent);


                        } else if (items[item].equals("Delete")) {

                            new AlertDialog.Builder(DoctorsListActivity.this)
                                    .setTitle("Delete Entry")
                                    .setMessage("Are you sure you want to Delete This Entry ?")
                                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // continue with delete
                                            int doctorID = doctorModels.get(position).getDoctorId();
                                            boolean delete = dataStorage.deleteDoctor(doctorID,"D");
                                            if (delete) {
                                                Toast.makeText(getApplication(), "Doctor Information Deleted Successfully", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getApplication(), "Failed", Toast.LENGTH_LONG).show();
                                            }
                                            showDoctorList();
                                        }
                                    })
                                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // do nothing
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }
                    }
                });
                builder.show();





                return true;
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_doctors_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_add_doctor) {
            Intent intent=new Intent(DoctorsListActivity.this,AddDoctorActivity.class);
            preferences = getBaseContext().getSharedPreferences("hospital_id_update", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("hospital_id_update","");
            editor.apply();
            editor.commit();
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
