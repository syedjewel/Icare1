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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import adapter.DietListAdapter;
import adapter.ProfileListAdapter;
import database.DataStorage;
import model.DietModel;

public class DietListActivity extends AppCompatActivity {
    ArrayList<DietModel> dietModels = new ArrayList<>();
    ArrayList<DietModel> dietModelsUp = new ArrayList<>();
    ArrayList<DietModel> dietModelsPrev = new ArrayList<>();
    DataStorage dataStorage;
    String personID;
    SharedPreferences preferences;
    SharedPreferences preferences1;
    ListView dietTodaysChartLV;
    ListView dietUpcommingChartLV;
    ListView dietPrevChart;
    String formattedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_list);

        dataStorage = new DataStorage(getApplicationContext());
        preferences=getBaseContext().getSharedPreferences("person_id", MODE_PRIVATE);
        personID =preferences.getString("person_id", "");

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c.getTime());

        dietTodaysChartLV = (ListView) findViewById(R.id.dietTodaysChart);
        dietUpcommingChartLV = (ListView) findViewById(R.id.dietUpcommingChart);
        dietPrevChart = (ListView) findViewById(R.id.dietPrevChart);

        showDietTodays();
        showDietUpcoming();
        showDietPrevious();

    }

    @Override
    protected void onStart() {
        super.onStart();
        showDietTodays();
        showDietUpcoming();
        showDietPrevious();
    }



    private void showDietTodays() {
        dietModels = dataStorage.getDietModelByPersonIDandDietDate(personID,formattedDate , "=");
        DietListAdapter dietListAdapter = new DietListAdapter(DietListActivity.this, dietModels);
        dietTodaysChartLV.setAdapter(dietListAdapter);
        dietListAdapter.notifyDataSetChanged();
        dietTodaysChartLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final CharSequence[] items = { "Update", "Delete"};

                AlertDialog.Builder builder = new AlertDialog.Builder(DietListActivity.this);
                builder.setTitle("User Action");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Update")) {

                            Intent intent = new Intent(DietListActivity.this, AddDietActivity.class);
                            String diet_id_update = String.valueOf((dietModels.get(position)).getDietId());

                            preferences = getBaseContext().getSharedPreferences("diet_id_update", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("diet_id_update", diet_id_update);
                            editor.apply();
                            editor.commit();

                            startActivity(intent);


                        } else if (items[item].equals("Delete")) {

                            new AlertDialog.Builder(DietListActivity.this)
                                    .setTitle("Delete Entry")
                                    .setMessage("Are you sure you want to Delete This Entry ?")
                                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            int dietId = dietModels.get(position).getDietId();
                                            boolean delete = dataStorage.deleteDiet(dietId,"D");
                                            if (delete) {
                                                Toast.makeText(getApplication(), "Diet Information Deleted Successfully", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getApplication(), "Failed", Toast.LENGTH_LONG).show();
                                            }
                                            showDietTodays();
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
    private void showDietUpcoming() {
        dietModelsUp= dataStorage.getDietModelByPersonIDandDietDate(personID, formattedDate, ">");
        DietListAdapter dietListAdapter = new DietListAdapter(DietListActivity.this, dietModelsUp);
        dietUpcommingChartLV.setAdapter(dietListAdapter);
        dietListAdapter.notifyDataSetChanged();
        dietUpcommingChartLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final CharSequence[] items = { "Update", "Delete"};

                AlertDialog.Builder builder = new AlertDialog.Builder(DietListActivity.this);
                builder.setTitle("User Action");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Update")) {

                            Intent intent = new Intent(DietListActivity.this, AddDietActivity.class);
                            String diet_id_update = String.valueOf((dietModelsUp.get(position)).getDietId());

                            preferences = getBaseContext().getSharedPreferences("diet_id_update", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("diet_id_update", diet_id_update);
                            editor.apply();
                            editor.commit();

                            startActivity(intent);


                        } else if (items[item].equals("Delete")) {

                            new AlertDialog.Builder(DietListActivity.this)
                                    .setTitle("Delete Entry")
                                    .setMessage("Are you sure you want to Delete This Entry ?")
                                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            int dietId = dietModelsUp.get(position).getDietId();
                                            boolean delete = dataStorage.deleteDiet(dietId,"D");
                                            if (delete) {
                                                Toast.makeText(getApplication(), "Diet Information Deleted Successfully", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getApplication(), "Failed", Toast.LENGTH_LONG).show();
                                            }
                                            showDietUpcoming();
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
    private void showDietPrevious() {
        dietModelsPrev= dataStorage.getDietModelByPersonIDandDietDate(personID, formattedDate, "<");
        DietListAdapter dietListAdapter = new DietListAdapter(DietListActivity.this, dietModelsPrev);
        dietPrevChart.setAdapter(dietListAdapter);
        dietListAdapter.notifyDataSetChanged();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_diet_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_diet_menu) {
            preferences1 = getBaseContext().getSharedPreferences("diet_id_update", MODE_PRIVATE);
            SharedPreferences.Editor editor1 = preferences1.edit();
            editor1.putString("diet_id_update", "");
            editor1.apply();
            editor1.commit();
            Intent intent=new Intent(DietListActivity.this,AddDietActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
