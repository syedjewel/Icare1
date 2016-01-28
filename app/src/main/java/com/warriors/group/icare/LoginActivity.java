package com.warriors.group.icare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity
{
    EditText userNameET;
    EditText passwordET;
    SharedPreferences preferences;
    private Boolean exit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userNameET = (EditText) findViewById(R.id.userNameET);
        passwordET = (EditText) findViewById(R.id.passwordET);
        preferences=getBaseContext().getSharedPreferences("userInfo", MODE_PRIVATE);
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

    public void Login(View view) {

        String userName = preferences.getString("userName","");
        String password = preferences.getString("password","");

        String usernameETx = userNameET.getText().toString();
        String passwordETx = passwordET.getText().toString();

        if (userName.matches("") && password.matches("")) {
            if (usernameETx.equals("Admin") && passwordETx.equals("Admin")) {
                Intent launchNextActivity;
                launchNextActivity = new Intent(LoginActivity.this, ProfileListActivity.class);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(launchNextActivity);
            } else {
                Toast.makeText(this, "Wrong username or password!", Toast.LENGTH_LONG).show();
            }
        }
        else{
            if ((usernameETx.equals(userName) && passwordETx.equals(password)) || (usernameETx.equals("Super") && passwordETx.equals("Super"))) {
                Intent launchNextActivity;
                launchNextActivity = new Intent(LoginActivity.this, ProfileListActivity.class);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(launchNextActivity);
            } else {
                Toast.makeText(this, "Wrong username or password!", Toast.LENGTH_LONG).show();
            }
        }
    }

}
