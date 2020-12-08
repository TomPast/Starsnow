package com.example.starsnow;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Adapter.ViewPagerAdapter;
import standardclasses.Aeroport;
import standardclasses.IACO_APIService;
import standardclasses.Snowtam;
import standardclasses.VolleyCallback;
import standardclasses.VolleyCallback2;

public class codeOACI extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView FragmentOne;
    private TextView AeroportName;
    private TextView Longitude;
    private TextView Latitude;

    private Aeroport currentAeroport;
    private Aeroport[] AeroportList;
    int currentIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_o_a_c_i);

        tabLayout = findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        tabLayout.post(() -> tabLayout.setupWithViewPager(viewPager));

        ArrayList<String> aeroports = (ArrayList<String>) getIntent().getSerializableExtra("codes");
        AeroportList = new Aeroport[aeroports.size()];

        IACO_APIService API = new IACO_APIService(this.getApplicationContext());
        int i = 0;
        currentIndex = 0;
        for( String value : aeroports ) {
            AeroportList[i] = new Aeroport(value);

            int finalI = i;
            API.getSnowtam(value, new VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    AeroportList[finalI].setSnowtam(new Snowtam(value, result));
                    if(finalI == 0){
                        updateSnowtam(result);
                    }

                }
            });

            API.getAeroport(value, new VolleyCallback2() {
                @Override
                public void onSuccess(Aeroport results) {
                    AeroportList[finalI].setNom(results.getNom());
                    AeroportList[finalI].setLatitude(results.getLatitude());
                    AeroportList[finalI].setLongitude(results.getLongitude());
                    if(finalI == 0){
                        updateAirportInfo(results);
                    }

                }

                @Override
                public void onError(String results) {
                    //ERREUR CODE OACI PAS BON
                }
            });

            if(i == 0) {
                currentAeroport = AeroportList[0];
            }
            i++;
        }

        // Bouton flèche de gauche pour changer d'aéroport
        FloatingActionButton floatingButtonLeft = (FloatingActionButton) findViewById(R.id.floatingLeft);
        floatingButtonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(currentIndex);
                currentIndex = currentIndex-1;
                if(currentIndex<0){
                    currentIndex = aeroports.size()-1;
                }
                System.out.println(currentIndex);
                currentAeroport = AeroportList[currentIndex];
                updateView(currentAeroport);
            }
        });

        // Bouton flèche de droite pour changer d'aéroport
        FloatingActionButton floatingButtonRight = (FloatingActionButton) findViewById(R.id.floatingRight);
        floatingButtonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(currentIndex);
                currentIndex++;
                if(currentIndex>(aeroports.size()-1)){
                    currentIndex = 0;
                }
                System.out.println(currentIndex);
                currentAeroport = AeroportList[currentIndex];
                updateView(currentAeroport);
            }
        });

        //Bouton localisation pour afficher la page de google map
        FloatingActionButton floatingButtonLocalisation = (FloatingActionButton) findViewById(R.id.floatingButtonLocalisation);
        floatingButtonLocalisation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent localisation = new Intent(codeOACI.this, MapActivity.class);
                localisation.putExtra("aeroport",currentAeroport);
                startActivityForResult(localisation, 500);
            }
        });
    }


    //Actualisation totale de la vue (nom Aeroport, Longitude/Latitude + snowtam)
    public void updateView(Aeroport aeroport){
        FragmentOne = (TextView) findViewById(R.id.section_label);
        FragmentOne.setText(aeroport.getSnowtam().getPlainCodedSnowtam());

        AeroportName = (TextView) findViewById(R.id.aeroportName);
        AeroportName.setText(aeroport.getNom());

        Longitude = (TextView) findViewById(R.id.longitudeValue);
        Longitude.setText( String.valueOf(aeroport.getLongitude()));

        Latitude = (TextView) findViewById(R.id.latitudeValue);
        Latitude.setText( String.valueOf(aeroport.getLatitude()));

    }

    //Actualisation du snowtam dans la vue
    public void updateSnowtam(String Snowtam){
        FragmentOne = (TextView) findViewById(R.id.section_label);
        FragmentOne.setText(Snowtam);
    }

    //Actualisation des informations de l'aéroport dans la vue
    public void updateAirportInfo(Aeroport info){
        AeroportName = (TextView) findViewById(R.id.aeroportName);
        AeroportName.setText(info.getNom());

        Longitude = (TextView) findViewById(R.id.longitudeValue);
        Longitude.setText( String.valueOf(info.getLongitude()));

        Latitude = (TextView) findViewById(R.id.latitudeValue);
        Latitude.setText( String.valueOf(info.getLatitude()));
    }
}