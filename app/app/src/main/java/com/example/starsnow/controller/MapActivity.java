package com.example.starsnow.controller;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;

import com.example.starsnow.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.example.starsnow.model.Aeroport;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    //private MapFragment myMapFragment;
    private GoogleMap googleMap;
    private Aeroport aeroport;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_map);

            //Récupération information aéroport
            aeroport = (Aeroport) getIntent().getSerializableExtra("aeroport");

            Button button = findViewById(R.id.imageButton);
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                       if(googleMap.getMapType() == GoogleMap.MAP_TYPE_HYBRID){
                           googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                           button.setBackground(getDrawable(R.drawable.satellite));
                       }
                       else{
                           googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                           button.setBackground(getDrawable(R.drawable.norm));
                       }
                    }
                });

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);
            mapFragment.getMapAsync(this);
        }


    public void backFunction(View view){
        setResult(500);
        finish();
    }

    @Override
    public void onMapReady(GoogleMap gMap) {
        this.googleMap = gMap;

        LatLng Airport = new LatLng(aeroport.getLatitude(), aeroport.getLongitude());
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        this.googleMap.addMarker(new MarkerOptions().position(Airport).title(aeroport.getNom()));
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Airport,14));
    }
}
