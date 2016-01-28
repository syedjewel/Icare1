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

import adapter.HospitalListAdapter;
import database.DataStorage;
import model.HospitalModel;

public class HospitalListActivity extends AppCompatActivity {
    ListView hospitalLV;
    DataStorage dataStorage;
    ArrayList<HospitalModel> hospitalModels = new ArrayList<>();
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);
        hospitalLV = (ListView) findViewById(R.id.hospitalLV);
        dataStorage = new DataStorage(getApplicationContext());
        hospitalList();
    }
    @Override
    protected void onStart() {
        super.onStart();
        hospitalList();
    }

    private void hospitalList()
    {
        hospitalModels = dataStorage.getAllHospitalModel();
        HospitalListAdapter hospitalListAdapter = new HospitalListAdapter(HospitalListActivity.this,hospitalModels);
        hospitalLV.setAdapter(hospitalListAdapter);
        hospitalListAdapter.notifyDataSetChanged();
        hospitalLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HospitalListActivity.this);
                builder.setTitle("User Action");
                final CharSequence[] items = { "Call", "SMS","Email","Show on Map"};

                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Call")) {
                            String receiveNumber = hospitalModels.get(position).getContactNumber();
                            String number;
                            number = "tel:" + receiveNumber;
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse(number));
                            if (ActivityCompat.checkSelfPermission(HospitalListActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
                            String receiveNumber = hospitalModels.get(position).getContactNumber();
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms",receiveNumber, null)));
                        }
                        else if (items[item].equals("Email")) {
                            String reciever = hospitalModels.get(position).getEmailAddress();
                            Intent i = new Intent(Intent.ACTION_SEND);
                            i.setType("message/rfc822");
                            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{reciever});
                            i.putExtra(Intent.EXTRA_SUBJECT, "");
                            i.putExtra(Intent.EXTRA_TEXT   , "");
                            try {
                                startActivity(Intent.createChooser(i, "Send mail..."));
                            } catch (android.content.ActivityNotFoundException ex) {
                                Toast.makeText(HospitalListActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if (items[item].equals("Show on Map")) {
                            String hospitalName = hospitalModels.get(position).getHospitalName();
                            String roadName = hospitalModels.get(position).getRoadName();
                            String cityName = hospitalModels.get(position).getCityName();
                            String countryName = hospitalModels.get(position).getCountryName();
                            String address = hospitalName+","+cityName+","+countryName;

                            Intent intent = new Intent(HospitalListActivity.this,MapsActivity.class);
                            intent.putExtra("address",address);
                            startActivity(intent);
                        }
                    }
                });
                builder.show();

            }
        });
        hospitalLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final CharSequence[] items = { "Update", "Delete"};

                AlertDialog.Builder builder = new AlertDialog.Builder(HospitalListActivity.this);
                builder.setTitle("User Action");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Update")) {

                            Intent intent = new Intent(HospitalListActivity.this, AddHospitalActivity.class);
                            String hospital_id_update = String.valueOf((hospitalModels.get(position)).getHospitalID());

                            preferences = getBaseContext().getSharedPreferences("hospital_id_update", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("hospital_id_update", hospital_id_update);
                            editor.apply();
                            editor.commit();

                            startActivity(intent);


                        } else if (items[item].equals("Delete")) {

                            new AlertDialog.Builder(HospitalListActivity.this)
                                    .setTitle("Delete Entry")
                                    .setMessage("Are you sure you want to Delete this Entry  ?")
                                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // continue with delete

                                            int hospitalID = hospitalModels.get(position).getHospitalID();
                                            boolean delete = dataStorage.deleteHospital(hospitalID,"D");
                                            if (delete) {
                                                Toast.makeText(getApplication(), "Hospital Information Deleted Successfully", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getApplication(), "Failed", Toast.LENGTH_LONG).show();
                                            }
                                            hospitalList();

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
        getMenuInflater().inflate(R.menu.menu_hospital_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_add_hospital) {

            Intent intent=new Intent(HospitalListActivity.this, AddHospitalActivity.class);
            preferences = getBaseContext().getSharedPreferences("hospital_id_update", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("hospital_id_update", "");
            editor.apply();
            editor.commit();

            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
