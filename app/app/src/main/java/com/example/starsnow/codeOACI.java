package com.example.starsnow;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Collections;

import Adapter.ViewPagerAdapter;
import standardclasses.Aeroport;
import standardclasses.IACO_APIService;
import standardclasses.VolleyCallback;
import standardclasses.VolleyCallback2;

public class codeOACI extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView FragmentOne;
    private TextView FragmentTwoA;
    private TextView FragmentTwoB;
    private TextView FragmentTwoC;
    private TextView AeroportName;
    private TextView Longitude;
    private TextView Latitude;
    //private String[] snowtamSplit;

    private Aeroport currentAeroport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_o_a_c_i);

        tabLayout = findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        tabLayout.post(() -> tabLayout.setupWithViewPager(viewPager));


        IACO_APIService API = new IACO_APIService(this.getApplicationContext());

        API.getSnowtam("BIAR", new VolleyCallback() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(String result) {
               //Partie codé
                System.out.println(result);
                FragmentOne = (TextView) findViewById(R.id.section_label);
                FragmentOne.setText(result);

                //Partie décodé
                FragmentTwoA = (TextView) findViewById(R.id.textView_decodeA);
                FragmentTwoB = (TextView) findViewById(R.id.textView_decodeB);
                FragmentTwoC = (TextView) findViewById(R.id.textView_decodeC);



                /*String[] snowtamAdjustment = result.split("");

                ArrayList<String> monTableau = new ArrayList<String>();
                for (String s : snowtamAdjustment) {
                    if (s.equals("\n")) {
                        s.replaceAll("[\\t\\n\\r]+"," ");
                        monTableau.add(s);
                    } else {
                        monTableau.add(s);
                    }
                }

                for(int i=1; i<monTableau.size(); i++){
                    System.out.println("Position "+ i +" : " + monTableau.get(i));
                }

                String str, str2 ="";
                for(int i=1; i<monTableau.size(); i++){
                    if(i==1){
                        str = monTableau.get(i-1);
                        str2 = str.concat(monTableau.get(i));
                    }
                    else{
                        str2 = str2.concat(monTableau.get(i));
                    }
                }

                System.out.println("ici la chaine : " +str2);*/

                //FragmentTwo.setText(result);
                String s = result.replaceAll("\n", " ");
                System.out.println("Résultat : " + s);

                String[] snowtamSplit = s.split(" ");

                ArrayList<String> monTableau2 = new ArrayList<>();
                Collections.addAll(monTableau2, snowtamSplit);

                for(int i=0; i<monTableau2.size(); i++){
                    if (monTableau2.get(i).contains("A)") || monTableau2.get(i).contains("B)") || monTableau2.get(i).contains("C)") || monTableau2.get(i).contains("D)") || monTableau2.get(i).contains("F)")
                            || monTableau2.get(i).contains("G)") || monTableau2.get(i).equals("H)") || monTableau2.get(i).contains("J)") || monTableau2.get(i).contains("K)") || monTableau2.get(i).contains("L)")
                            || monTableau2.get(i).contains("M)") || monTableau2.get(i).contains("N)") || monTableau2.get(i).contains("P)")|| monTableau2.get(i).contains("R)") || monTableau2.get(i).contains("S)")
                            || monTableau2.get(i).contains("T)")) {

                        monTableau2.add(i,"\\\n");
                        i++;
                    }
                }

                for (int i=0; i<monTableau2.size(); i++){
                    String lettre = monTableau2.get(i);
                    switch(lettre){
                        case "A)" :
                            FragmentTwoA.setText(monTableau2.get(i+1));
                            break;

                        case "B)" :
                            FragmentTwoB.setText(monTableau2.get(i+1).substring(2,4)+ "/" + monTableau2.get(i+1).substring(0,2) + " à " + monTableau2.get(i+1).substring(4,6) +":"+monTableau2.get(i+1).substring(6,8)+" UTC");
                            break;

                        case "C)":
                            FragmentTwoC.setText("Piste " + monTableau2.get(i+1));
                            break;

                        default:
                            break;
                    }
                    System.out.println(monTableau2.get(i));
                }


                //String[] ligneSplit = snowtamSplit[2].split(" ");

                //FragmentTwoA.setText(snowtamSplit[1]);


               // FragmentTwoA.setText(ligneSplit[1]);
                //FragmentTwoB.setText(snowtamSplit[3]);
               // FragmentTwoC.setText(snowtamSplit[4]);


            }
        });

        API.getAeroport("ENBO", new VolleyCallback2() {
            @Override
            public void onSuccess(Aeroport results) {
                currentAeroport = results;
                System.out.println(results.getNom());
                AeroportName = (TextView) findViewById(R.id.aeroportName);
                AeroportName.setText(results.getNom());

                Longitude = (TextView) findViewById(R.id.longitudeValue);
                Longitude.setText( String.valueOf(results.getLongitude()));

                Latitude = (TextView) findViewById(R.id.latitudeValue);
                Latitude.setText( String.valueOf(results.getLatitude()));
            }
        });

        currentAeroport = new Aeroport("OKIE", "Charles de gaulles", 11.08388888888888, 60.2027777777777);

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


}