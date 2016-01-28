package com.warriors.group.icare;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import adapter.DoctorsListAdapter;
import adapter.MedicalHistroyListAdapter;
import database.DataStorage;
import model.MedicalHistoryModel;

public class MedicalHistoryListActivity extends AppCompatActivity {
    ListView medicalHistoryLV;
    DataStorage dataStorage;
    ArrayList<MedicalHistoryModel> medicalHistoryModels = new ArrayList<>();
    String personID;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_history_list);
        medicalHistoryLV = (ListView) findViewById(R.id.medicalHistoryLV);
        preferences=getBaseContext().getSharedPreferences("person_id", MODE_PRIVATE);
        dataStorage = new DataStorage(getApplicationContext());
        personID =preferences.getString("person_id", "");
        showMedicalHistoryList();
    }
    @Override
    protected void onStart() {
        super.onStart();
        showMedicalHistoryList();
    }
    private void showMedicalHistoryList() {
        medicalHistoryModels = dataStorage.getMedicalHistoryModelByPersonID(personID);
        MedicalHistroyListAdapter medicalHistroyListAdapter = new MedicalHistroyListAdapter(MedicalHistoryListActivity.this,medicalHistoryModels);
        medicalHistoryLV.setAdapter(medicalHistroyListAdapter);
        medicalHistroyListAdapter.notifyDataSetChanged();
        medicalHistoryLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                final CharSequence[] items = { "Update", "Delete"};

                AlertDialog.Builder builder = new AlertDialog.Builder(MedicalHistoryListActivity.this);
                builder.setTitle("User Action");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Update")) {

                            Intent intent = new Intent(MedicalHistoryListActivity.this, AddMedicalHistoryActivity.class);
                            String medical_history_id_update = String.valueOf((medicalHistoryModels.get(position)).getMhId());

                            preferences = getBaseContext().getSharedPreferences("medical_history_id_update", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("medical_history_id_update", medical_history_id_update);
                            editor.apply();
                            editor.commit();

                            startActivity(intent);


                        } else if (items[item].equals("Delete")) {

                            new AlertDialog.Builder(MedicalHistoryListActivity.this)
                                    .setTitle("Delete Entry")
                                    .setMessage("Are you sure you want to Delete this entry  ?")
                                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            int mhID = medicalHistoryModels.get(position).getMhId();
                                            boolean delete = dataStorage.deleteMedicalHistory(mhID,"D");
                                            if (delete) {
                                                Toast.makeText(getApplication(), "Information Deleted Successfully", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getApplication(), "Failed", Toast.LENGTH_LONG).show();
                                            }
                                            showMedicalHistoryList();
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
        getMenuInflater().inflate(R.menu.menu_medical_history_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_add_medical_history) {

            Intent intent=new Intent(MedicalHistoryListActivity.this,AddMedicalHistoryActivity.class);
            preferences = getBaseContext().getSharedPreferences("medical_history_id_update", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("medical_history_id_update","");
            editor.apply();
            editor.commit();
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
