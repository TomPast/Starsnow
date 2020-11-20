package com.example.starsnow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView liste_aeroports;
    private String[] aeroports = new String[]{
            "New York", "Paris", "Dubai", "Londres"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        liste_aeroports = (ListView) findViewById(R.id.liste_aeroports);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, aeroports);
        liste_aeroports.setAdapter(adapter); */
    }
}