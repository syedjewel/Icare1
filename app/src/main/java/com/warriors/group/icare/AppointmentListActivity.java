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
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import adapter.AppointmentListAdapter;
import database.DataStorage;
import model.AppointmentModel;
import model.DietModel;

public class AppointmentListActivity extends AppCompatActivity {

    ArrayList<AppointmentModel> appointmentModelsUP = new ArrayList<>();
    ArrayList<AppointmentModel> appointmentModelsPrev = new ArrayList<>();
    ArrayList<AppointmentModel> appointmentModelsNow= new ArrayList<>();

    DataStorage dataStorage;
    String personID;
    SharedPreferences preferences;
    String formattedDate;
    ListView aaptTodayChart;
    ListView aaptUpcomingChart;
    ListView apptPrevChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoinment_list);

        dataStorage = new DataStorage(getApplicationContext());
        preferences=getBaseContext().getSharedPreferences("person_id", MODE_PRIVATE);
        personID =preferences.getString("person_id", "");

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c.getTime());
        initializer();
        showUpcomingAppointment();
        showTodayAppointment();
        showPreviousAppointment();

    }
    @Override
    protected void onStart() {
        super.onStart();
        showUpcomingAppointment();
        showTodayAppointment();
        showPreviousAppointment();
    }

    private void showPreviousAppointment()
    {
        appointmentModelsPrev = dataStorage.getAppointmentModelByPersonIDandDate(personID,formattedDate,"<");
        AppointmentListAdapter appointmentListAdapter = new AppointmentListAdapter(AppointmentListActivity.this,appointmentModelsPrev);
        apptPrevChart.setAdapter(appointmentListAdapter);
        appointmentListAdapter.notifyDataSetChanged();
        apptPrevChart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(AppointmentListActivity.this, AppointmentDetailsActivity.class);
                String appointment_id_show = String.valueOf((appointmentModelsPrev.get(position)).getApptId());

                preferences = getBaseContext().getSharedPreferences("appointment_id_show", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("appointment_id_show", appointment_id_show);
                editor.apply();
                editor.commit();

                startActivity(intent);

            }
        });

    }
    private void showUpcomingAppointment()
    {
        appointmentModelsUP = dataStorage.getAppointmentModelByPersonIDandDate(personID,formattedDate,">");
        AppointmentListAdapter appointmentListAdapter = new AppointmentListAdapter(AppointmentListActivity.this,appointmentModelsUP);
        aaptUpcomingChart.setAdapter(appointmentListAdapter);
        appointmentListAdapter.notifyDataSetChanged();
        aaptUpcomingChart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final CharSequence[] items = { "Update", "Delete"};

                AlertDialog.Builder builder = new AlertDialog.Builder(AppointmentListActivity.this);
                builder.setTitle("User Action");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Update")) {

                            Intent intent = new Intent(AppointmentListActivity.this, AddAppoinmentActivity.class);
                            String appointment_id_update = String.valueOf((appointmentModelsUP.get(position)).getApptId());

                            preferences = getBaseContext().getSharedPreferences("appointment_id_update", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("appointment_id_update", appointment_id_update);
                            editor.apply();
                            editor.commit();

                            startActivity(intent);


                        } else if (items[item].equals("Delete")) {

                            new AlertDialog.Builder(AppointmentListActivity.this)
                                    .setTitle("Delete Entry")
                                    .setMessage("Are you sure you want to Delete This Entry  ?")
                                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            int apptId = appointmentModelsUP.get(position).getApptId();
                                            boolean delete = dataStorage.deleteAppointment(apptId,"D");
                                            if (delete) {
                                                Toast.makeText(getApplication(), "Appointment Information Deleted Successfully", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getApplication(), "Failed", Toast.LENGTH_LONG).show();
                                            }
                                            showUpcomingAppointment();
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
        aaptUpcomingChart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(AppointmentListActivity.this, AppointmentDetailsActivity.class);
                String appointment_id_show = String.valueOf((appointmentModelsUP.get(position)).getApptId());

                preferences = getBaseContext().getSharedPreferences("appointment_id_show", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("appointment_id_show", appointment_id_show);
                editor.apply();
                editor.commit();

                startActivity(intent);

            }
        });
    }
    private void showTodayAppointment()
    {
        appointmentModelsNow = dataStorage.getAppointmentModelByPersonIDandDate(personID,formattedDate,"=");
        AppointmentListAdapter appointmentListAdapter = new AppointmentListAdapter(AppointmentListActivity.this,appointmentModelsNow);
        aaptTodayChart.setAdapter(appointmentListAdapter);
        appointmentListAdapter.notifyDataSetChanged();
        aaptTodayChart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final CharSequence[] items = { "Update", "Delete"};

                AlertDialog.Builder builder = new AlertDialog.Builder(AppointmentListActivity.this);
                builder.setTitle("User Action");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Update")) {

                            Intent intent = new Intent(AppointmentListActivity.this, AddAppoinmentActivity.class);
                            String appointment_id_update = String.valueOf((appointmentModelsNow.get(position)).getApptId());

                            preferences = getBaseContext().getSharedPreferences("appointment_id_update", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("appointment_id_update", appointment_id_update);
                            editor.apply();
                            editor.commit();

                            startActivity(intent);


                        } else if (items[item].equals("Delete")) {

                            new AlertDialog.Builder(AppointmentListActivity.this)
                                    .setTitle("Delete Entry")
                                    .setMessage("Are you sure you want to Delete This Entry  ?")
                                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // continue with delete
                                            int apptId = appointmentModelsNow.get(position).getApptId();
                                            boolean delete = dataStorage.deleteAppointment(apptId,"D");
                                            if (delete) {
                                                Toast.makeText(getApplication(), "Appointment Information Deleted Successfully", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getApplication(), "Failed", Toast.LENGTH_LONG).show();
                                            }
                                            showTodayAppointment();

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
        aaptTodayChart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(AppointmentListActivity.this, AppointmentDetailsActivity.class);
                String appointment_id_show = String.valueOf((appointmentModelsNow.get(position)).getApptId());

                preferences = getBaseContext().getSharedPreferences("appointment_id_show", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("appointment_id_show", appointment_id_show);
                editor.apply();
                editor.commit();

                startActivity(intent);

            }
        });

    }
    private void initializer()
    {
        aaptTodayChart = (ListView) findViewById(R.id.aaptTodayChart);
        aaptUpcomingChart = (ListView) findViewById(R.id.aaptUpcomingChart);
        apptPrevChart = (ListView) findViewById(R.id.apptPrevChart);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_appoinment_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_add_appointment) {
            Intent intent = new Intent(AppointmentListActivity.this,AddAppoinmentActivity.class);
            preferences = getBaseContext().getSharedPreferences("appointment_id_update", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("appointment_id_update","");
            editor.apply();
            editor.commit();
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
