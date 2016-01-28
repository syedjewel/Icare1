package com.warriors.group.icare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import adapter.GeneralInformationListAdapter;
import model.GeneralInformationModel;

public class GeneralInformationActivity extends AppCompatActivity {

    ListView genrealLV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_information);
        genrealLV = (ListView) findViewById(R.id.genrealLV);
        ArrayList<GeneralInformationModel> al = GeneralInformationModel.getAllData();
        GeneralInformationListAdapter customAdapter = new GeneralInformationListAdapter(getApplicationContext(),al);
        genrealLV.setAdapter(customAdapter);
    }
}
