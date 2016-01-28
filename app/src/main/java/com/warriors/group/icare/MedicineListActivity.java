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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import adapter.MedicineListAdapter;
import database.DataStorage;
import model.MedicineModel;

public class MedicineListActivity extends AppCompatActivity
{
    ListView medicineLV;
    ListView medicinePrevLV;
    DataStorage dataStorage;
    String personID;
    SharedPreferences preferences;
    String formattedDate;
    ArrayList<MedicineModel> medicineModels = new ArrayList<>();
    ArrayList<MedicineModel> medicineModelsPrev = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_list);
        medicineLV = (ListView) findViewById(R.id.medicineLV);
        medicinePrevLV = (ListView) findViewById(R.id.medicinePrevLV);
        dataStorage = new DataStorage(getApplicationContext());
        preferences=getBaseContext().getSharedPreferences("person_id", MODE_PRIVATE);
        personID =preferences.getString("person_id", "");

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c.getTime());
        showMedicineList();
        showMedicinePrevList();
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        showMedicineList();
        showMedicinePrevList();
    }


    private void showMedicineList()
    {
        medicineModels = dataStorage.getMedicineModelByPersonID(personID,formattedDate,">");
        MedicineListAdapter medicineListAdapter = new MedicineListAdapter(MedicineListActivity.this,medicineModels);
        medicineLV.setAdapter(medicineListAdapter);
        medicineListAdapter.notifyDataSetChanged();
        medicineLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final CharSequence[] items = { "Update", "Delete"};

                AlertDialog.Builder builder = new AlertDialog.Builder(MedicineListActivity.this);
                builder.setTitle("User Action");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Update")) {

                            Intent intent = new Intent(MedicineListActivity.this, AddMedicineActivity.class);
                            String medicine_id_update = String.valueOf((medicineModels.get(position)).getmId());

                            preferences = getBaseContext().getSharedPreferences("medicine_id_update", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("medicine_id_update", medicine_id_update);
                            editor.apply();
                            editor.commit();

                            startActivity(intent);


                        } else if (items[item].equals("Delete")) {

                            new AlertDialog.Builder(MedicineListActivity.this)
                                    .setTitle("Delete Entry")
                                    .setMessage("Are you sure you want to Delete this Entry  ?")
                                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            int mID = medicineModels.get(position).getmId();
                                            boolean delete = dataStorage.deleteMedicine(mID,"D");
                                            if (delete) {
                                                Toast.makeText(getApplication(), "Information Deleted Successfully", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getApplication(), "Failed", Toast.LENGTH_LONG).show();
                                            }
                                            showMedicineList();
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

    private void showMedicinePrevList() {
        medicineModelsPrev = dataStorage.getMedicineModelByPersonID(personID, formattedDate, "<");
        MedicineListAdapter medicineListAdapter = new MedicineListAdapter(MedicineListActivity.this, medicineModelsPrev);
        medicinePrevLV.setAdapter(medicineListAdapter);
        medicineListAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_medicine_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_add_medicine) {
            Intent intent=new Intent(MedicineListActivity.this,AddMedicineActivity.class);
            preferences = getBaseContext().getSharedPreferences("medicine_id_update", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("medicine_id_update", "");
            editor.apply();
            editor.commit();

            startActivity(intent);


            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
