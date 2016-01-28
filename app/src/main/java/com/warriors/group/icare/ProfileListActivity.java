package com.warriors.group.icare;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import adapter.ProfileListAdapter;
import database.DataStorage;
import model.ProfileModel;

public class ProfileListActivity extends AppCompatActivity {

    ListView profileLV;
    ArrayList<ProfileModel> profileModels;
    DataStorage dataStorage;
    SharedPreferences preferences;
    SharedPreferences preferences1;
    private Boolean exit = false;
    EditText profileSearchET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_list);

        profileLV = (ListView) findViewById(R.id.profileLV);
        profileSearchET=(EditText) findViewById(R.id.profileSearchET);
        dataStorage = new DataStorage(getApplicationContext());



        showProfile();

        profileSearchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showProfileSearch(profileSearchET.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }
    @Override
    protected void onStart() {
        super.onStart();
        showProfile();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_profile_menu) {


            preferences1 = getBaseContext().getSharedPreferences("person_id_update", MODE_PRIVATE);
            SharedPreferences.Editor editor1 = preferences1.edit();
            editor1.putString("person_id_update", "");
            editor1.apply();
            editor1.commit();

            Intent intent=new Intent(ProfileListActivity.this,AddProfileActivity.class);
            startActivity(intent);




            return true;
        }
        if (id == R.id.menu_change_user_info) {

            Intent intent=new Intent(ProfileListActivity.this,UpdateUserActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }

    private void showProfile() {
        profileModels = dataStorage.getAllProfile();

        ProfileListAdapter profileListAdapter = new ProfileListAdapter(ProfileListActivity.this, profileModels);
        profileLV.setAdapter(profileListAdapter);
        profileListAdapter.notifyDataSetChanged();
        profileLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ProfileListActivity.this, DashBoardActivity.class);
                String profile_id = (profileModels.get(position)).getPersonId();
                preferences = getBaseContext().getSharedPreferences("person_id", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("person_id", profile_id);
                editor.apply();
                editor.commit();
                startActivity(intent);

                preferences1 = getBaseContext().getSharedPreferences("person_id_update", MODE_PRIVATE);
                SharedPreferences.Editor editor1 = preferences1.edit();
                editor1.putString("person_id_update", "");
                editor1.apply();
                editor1.commit();


            }
        });

        profileLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final CharSequence[] items = { "Update", "Delete"};

                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileListActivity.this);
                builder.setTitle("User Action");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Update")) {
                            Intent intent = new Intent(ProfileListActivity.this, AddProfileActivity.class);
                            String profile_id_update = String.valueOf((profileModels.get(position)).getId());

                            preferences = getBaseContext().getSharedPreferences("person_id_update", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("person_id_update", profile_id_update);
                            editor.apply();
                            editor.commit();

                            startActivity(intent);


                        } else if (items[item].equals("Delete")) {

                            new AlertDialog.Builder(ProfileListActivity.this)
                                    .setTitle("Delete Profile")
                                    .setMessage("Are you sure you want to Delete The Profile  ?")
                                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            String personId = profileModels.get(position).getPersonId();
                                            boolean delete = dataStorage.deleteAllActivitiesByPersonId(personId,"D");
                                            if (delete) {
                                                Toast.makeText(getApplication(), "Profile Deleted Successfully", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getApplication(), "Failed", Toast.LENGTH_LONG).show();
                                            }
                                            showProfile();

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

    private void showProfileSearch(String prName) {
        profileModels = dataStorage.getProfileModelBySearchbyName(prName);

        ProfileListAdapter profileListAdapter = new ProfileListAdapter(ProfileListActivity.this, profileModels);
        profileLV.setAdapter(profileListAdapter);
        profileListAdapter.notifyDataSetChanged();
        profileLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ProfileListActivity.this, DashBoardActivity.class);
                String profile_id = (profileModels.get(position)).getPersonId();
                preferences = getBaseContext().getSharedPreferences("person_id", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("person_id", profile_id);
                editor.apply();
                editor.commit();
                startActivity(intent);

                preferences1 = getBaseContext().getSharedPreferences("person_id_update", MODE_PRIVATE);
                SharedPreferences.Editor editor1 = preferences1.edit();
                editor1.putString("person_id_update", "");
                editor1.apply();
                editor1.commit();


            }
        });
    }

}
