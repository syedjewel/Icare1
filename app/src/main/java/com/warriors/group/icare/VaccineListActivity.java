package com.warriors.group.icare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import adapter.DietListAdapter;
import adapter.VaccineListAdapter;
import database.DataStorage;
import model.DietModel;
import model.VaccineModel;

public class VaccineListActivity extends AppCompatActivity {
    ArrayList<VaccineModel> vaccineModelsCompleted = new ArrayList<>();
    ArrayList<VaccineModel> vaccineModelsUp = new ArrayList<>();
    ArrayList<VaccineModel> vaccineModelsToady = new ArrayList<>();
    DataStorage dataStorage;
    String personID;
    SharedPreferences preferences;
    ListView vaccineLVUpcoming;
    ListView vaccineLVUpCompleted;
    ListView vaccineLVToday;
    String formattedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_list);

        dataStorage = new DataStorage(getApplicationContext());
        preferences=getBaseContext().getSharedPreferences("person_id", MODE_PRIVATE);
        personID =preferences.getString("person_id", "");
         Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c.getTime());

        vaccineLVUpcoming = (ListView) findViewById(R.id.vaccineLVUpcoming);
        vaccineLVUpCompleted = (ListView) findViewById(R.id.vaccineLVUpCompleted);
        vaccineLVToday = (ListView) findViewById(R.id.vaccineLVToday);

        showVaccineCompleted();
        showVaccineUpcoming();
        showVaccineToday();

    }

    @Override
    protected void onStart() {
        super.onStart();
        showVaccineCompleted();
        showVaccineUpcoming();
        showVaccineToday();
    }

    private void showVaccineCompleted() {
        vaccineModelsCompleted = dataStorage.getVaccineModelByPersonIDCompletedUpComing(personID,formattedDate , "<");
        VaccineListAdapter vaccineListAdapter = new VaccineListAdapter(VaccineListActivity.this, vaccineModelsCompleted);
        vaccineLVUpCompleted.setAdapter(vaccineListAdapter);
        vaccineListAdapter.notifyDataSetChanged();

    }
    private void showVaccineUpcoming() {
        vaccineModelsUp= dataStorage.getVaccineModelByPersonIDCompletedUpComing(personID, formattedDate, ">");
        VaccineListAdapter vaccineListAdapterUp = new VaccineListAdapter(VaccineListActivity.this, vaccineModelsUp);
        vaccineLVUpcoming.setAdapter(vaccineListAdapterUp);
        vaccineListAdapterUp.notifyDataSetChanged();
        vaccineLVUpcoming.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final CharSequence[] items = { "Update", "Delete"};

                AlertDialog.Builder builder = new AlertDialog.Builder(VaccineListActivity.this);
                builder.setTitle("User Action");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Update")) {

                            Intent intent = new Intent(VaccineListActivity.this, AddVaccinationActivity.class);
                            String vaccine_id_update = String.valueOf((vaccineModelsUp.get(position)).getvId());

                            preferences = getBaseContext().getSharedPreferences("vaccine_id_update", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("vaccine_id_update", vaccine_id_update);
                            editor.apply();
                            editor.commit();

                            startActivity(intent);


                        } else if (items[item].equals("Delete")) {

                            new AlertDialog.Builder(VaccineListActivity.this)
                                    .setTitle("Delete Entry")
                                    .setMessage("Are you sure you want to Delete this Entry  ?")
                                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // continue with delete
                                            int vacID = vaccineModelsUp.get(position).getvId();
                                            boolean delete = dataStorage.deleteVaccine(vacID,"D");
                                            if (delete) {
                                                Toast.makeText(getApplication(), "Vaccine Information Deleted Successfully", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getApplication(), "Failed", Toast.LENGTH_LONG).show();
                                            }
                                            showVaccineUpcoming();


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
    private void showVaccineToday() {
        vaccineModelsToady= dataStorage.getVaccineModelByPersonIDCompletedUpComing(personID, formattedDate, "=");
        VaccineListAdapter vaccineListAdapterToday = new VaccineListAdapter(VaccineListActivity.this, vaccineModelsToady);
        vaccineLVToday.setAdapter(vaccineListAdapterToday);
        vaccineListAdapterToday.notifyDataSetChanged();
        vaccineLVToday.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final CharSequence[] items = { "Update", "Delete"};

                AlertDialog.Builder builder = new AlertDialog.Builder(VaccineListActivity.this);
                builder.setTitle("User Action");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Update")) {

                            Intent intent = new Intent(VaccineListActivity.this, AddVaccinationActivity.class);
                            String vaccine_id_update = String.valueOf((vaccineModelsToady.get(position)).getvId());

                            preferences = getBaseContext().getSharedPreferences("vaccine_id_update", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("vaccine_id_update", vaccine_id_update);
                            editor.apply();
                            editor.commit();

                            startActivity(intent);


                        } else if (items[item].equals("Delete")) {

                            new AlertDialog.Builder(VaccineListActivity.this)
                                    .setTitle("Delete Entry")
                                    .setMessage("Are you sure you want to Delete this Entry  ?")
                                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // continue with delete

                                            int vacID = vaccineModelsToady.get(position).getvId();
                                            boolean delete = dataStorage.deleteVaccine(vacID,"D");
                                            if (delete) {
                                                Toast.makeText(getApplication(), "Vaccine Information Deleted Successfully", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getApplication(), "Failed", Toast.LENGTH_LONG).show();
                                            }
                                            showVaccineToday();
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
        getMenuInflater().inflate(R.menu.menu_vaccine_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_add_vaccine) {
            Intent intent = new Intent(VaccineListActivity.this,AddVaccinationActivity.class);
            preferences = getBaseContext().getSharedPreferences("vaccine_id_update", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("vaccine_id_update","");
            editor.apply();
            editor.commit();
            startActivity(intent);
            return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
