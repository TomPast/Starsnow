package com.example.starsnow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import Adapter.ViewPagerAdapter;
import fragments.FragmentOACICode;
import fragments.FragmentOACIDecode;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_o_a_c_i);

        tabLayout = findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        tabLayout.post(() -> tabLayout.setupWithViewPager(viewPager));




        IACO_APIService API = new IACO_APIService(this.getApplicationContext());
        //RECUPERATION D'UN TABLEAU DE 4 STRINGS (4 code OACI) depuis intent
        //String[] aeroportNames = {"BIKF","BIKF","BIKF","BIKF"};


        //Remplissage d'un tableau de 4 aéroports
            //Remplissage des infos d'aéroport (nom, longitude, latitude) + SNOWTAM (codé, décodé)
        //Lien avec la vue, voir comment faire (fragment ??)


        API.getSnowtam("ENGM", new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                FragmentOne = (TextView) findViewById(R.id.section_label);
                FragmentOne.setText(result);
            }
        });

        API.getAeroport("ENGM", new VolleyCallback2() {
            @Override
            public void onSuccess(Aeroport results) {
                System.out.println(results.getNom());
                AeroportName = (TextView) findViewById(R.id.aeroportName);
                AeroportName.setText(results.getNom());

                Longitude = (TextView) findViewById(R.id.longitudeValue);
                Longitude.setText( String.valueOf(results.getLongitude()));

                Latitude = (TextView) findViewById(R.id.latitudeValue);
                Latitude.setText( String.valueOf(results.getLatitude()));
            }
        });


        System.out.println("FIN APPEL");

        //System.out.println(aeroportList[0]);

    }


}