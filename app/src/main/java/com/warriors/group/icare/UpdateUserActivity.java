package com.warriors.group.icare;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateUserActivity extends AppCompatActivity {
    EditText userNameUET;
    EditText passwordUET;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        userNameUET = (EditText) findViewById(R.id.userNameUET);
        passwordUET = (EditText) findViewById(R.id.passwordUET);
    }
    public void updateUser(View view) {

        final String usernameETx = userNameUET.getText().toString();
        final String passwordETx = passwordUET.getText().toString();

        if(usernameETx.matches("") || passwordETx.matches(""))
        {
            Toast.makeText(this, "Username and Password can not be Blank !", Toast.LENGTH_LONG).show();
        }
        else {



            new AlertDialog.Builder(UpdateUserActivity.this)
                    .setTitle("Change User Name & Password")
                    .setMessage("Are you sure you want to Change User Name & Password  ?")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            preferences = getBaseContext().getSharedPreferences("userInfo", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("userName", usernameETx);
                            editor.putString("password", passwordETx);
                            editor.apply();
                            editor.commit();
                            Toast.makeText(UpdateUserActivity.this, "Username and Password Changed !", Toast.LENGTH_LONG).show();
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
}
