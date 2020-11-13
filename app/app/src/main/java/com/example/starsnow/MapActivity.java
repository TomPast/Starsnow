package com.example.starsnow;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MapActivity extends AppCompatActivity {

        private MapFragment myMapFragment;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_map);

            FragmentManager fragmentManager = this.getSupportFragmentManager();
            this.myMapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.fragment_map);

        }
    }
