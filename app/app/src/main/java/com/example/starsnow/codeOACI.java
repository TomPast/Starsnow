package com.example.starsnow;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Adapter.DecodeAdapter;
import Adapter.ViewPagerAdapter;
import standardclasses.Aeroport;
import standardclasses.IACO_APIService;
import standardclasses.Snowtam;
import standardclasses.SnowtamDecodeObject;
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
        viewPager =  findViewById(R.id.viewPager);

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        tabLayout.post(() -> tabLayout.setupWithViewPager(viewPager));


        //Récupération des codes OACI saisis précédemment
        @SuppressWarnings("unchecked")
        ArrayList<String> OACICode = (ArrayList<String>) getIntent().getSerializableExtra("codes");


        //Appel API pour récupération du snowtam et des informations sur les aéroports dans une ArrayList d'aéroport
        AeroportList = new Aeroport[OACICode.size()];
        int i = 0;
        currentIndex = 0;

        //On instancie le service d'appel à l'API
        IACO_APIService API = new IACO_APIService(this.getApplicationContext());

        //Pour chaque code OACI on fait 2 Appels API
        for( String value : OACICode ) {

            //On créé un nouvel objet dans notre ArrayList par code OACI
            AeroportList[i] = new Aeroport(value);

            int finalI = i;

            //APPEL API 1 : Récupération du snowtam
            API.getSnowtam(value, result -> {
                AeroportList[finalI].setSnowtam(new Snowtam(value, result));
                if(finalI == 0){
                    updateSnowtam(result);
                }
            });

            //APPEL API 2 : Récupération des informations sur l'aéroport (Nom, longitude et latitude)
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

            //Mise à jour de l'aéroport à afficher
            if(i == 0) {
                currentAeroport = AeroportList[0];
            }
            i++;
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==1) {
                    updateSnowtamDecode(currentAeroport.getSnowtam().getPlainCodedSnowtam());
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // Bouton flèche de gauche pour changer d'aéroport
        FloatingActionButton floatingButtonLeft = findViewById(R.id.floatingLeft);
        floatingButtonLeft.setOnClickListener(v -> {
            //On décrémente l'index de l'aéroport à afficher dans la liste
            currentIndex = currentIndex-1;
            if(currentIndex<0){
                currentIndex = OACICode.size()-1;
            }
            currentAeroport = AeroportList[currentIndex];
            updateView(currentAeroport);
            updateSnowtamDecode(currentAeroport.getSnowtam().getPlainCodedSnowtam());
        });

        // Bouton flèche de droite pour changer d'aéroport
        FloatingActionButton floatingButtonRight = findViewById(R.id.floatingRight);
        floatingButtonRight.setOnClickListener(v -> {
            //On incrémente l'index de l'aéroport à afficher dans la liste
            currentIndex++;
            if(currentIndex>(OACICode.size()-1)){
                currentIndex = 0;
            }
            currentAeroport = AeroportList[currentIndex];
            updateView(currentAeroport);
            updateSnowtamDecode(currentAeroport.getSnowtam().getPlainCodedSnowtam());
        });

        //Bouton localisation pour afficher la page de google map
        FloatingActionButton floatingButtonLocalisation = findViewById(R.id.floatingButtonLocalisation);
        floatingButtonLocalisation.setOnClickListener(v -> {
            Intent localisation = new Intent(codeOACI.this, MapActivity.class);
            //On passe en extra, l'aéroport courant
            localisation.putExtra("aeroport",currentAeroport);
            startActivityForResult(localisation, 500);
        });
    }

    /**
     * Actualisation totale de la vue (nom Aeroport, Longitude/Latitude + snowtam)
     */
    public void updateView(Aeroport aeroport){
        FragmentOne = findViewById(R.id.section_label);
        FragmentOne.setText(aeroport.getSnowtam().getPlainCodedSnowtam());

        AeroportName = findViewById(R.id.aeroportName);
        AeroportName.setText(aeroport.getNom());

        Longitude = findViewById(R.id.longitudeValue);
        Longitude.setText( String.valueOf(aeroport.getLongitude()));

        Latitude = findViewById(R.id.latitudeValue);
        Latitude.setText( String.valueOf(aeroport.getLatitude()));

    }

    /**
     * Actualisation du snowtam codé dans la vue
     */
    public void updateSnowtam(String Snowtam){
        FragmentOne = findViewById(R.id.section_label);
        FragmentOne.setText(Snowtam);
    }

    /**
     * Actualisation des informations de l'aéroport dans la vue
     */
    public void updateAirportInfo(Aeroport info){
        AeroportName = findViewById(R.id.aeroportName);
        AeroportName.setText(info.getNom());

        Longitude = findViewById(R.id.longitudeValue);
        Longitude.setText( String.valueOf(info.getLongitude()));

        Latitude = findViewById(R.id.latitudeValue);
        Latitude.setText( String.valueOf(info.getLatitude()));
    }

    /**
     * Mise à jour du snowtam décodé à partir du snowtam codé
     */
    public void updateSnowtamDecode(String result){
        RecyclerView recyclerView;
        recyclerView = findViewById(R.id.recyclerView);
        //définit l'agencement des cellules, ici de façon verticale, comme une ListView
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        List<SnowtamDecodeObject> letter = new ArrayList<>();
        recyclerView.setAdapter(new DecodeAdapter(letter));


        //FragmentTwo.setText(result);
        String s = result.replaceAll("\n", " ");
        System.out.println("Résultat : " + s);

        String[] snowtamSplit = s.split(" ");

        ArrayList<String> monTableau2 = new ArrayList<>();
        Collections.addAll(monTableau2, snowtamSplit);

        for(int i=0; i<monTableau2.size(); i++){
            if (monTableau2.get(i).contains("A)") || monTableau2.get(i).contains("B)") || monTableau2.get(i).contains("C)") || monTableau2.get(i).contains("D)") || monTableau2.get(i).contains("E)")
                    || monTableau2.get(i).contains("F)") || monTableau2.get(i).contains("G)") || monTableau2.get(i).equals("H)") || monTableau2.get(i).contains("J)") || monTableau2.get(i).contains("K)")
                    || monTableau2.get(i).contains("L)") || monTableau2.get(i).contains("M)") || monTableau2.get(i).contains("N)") || monTableau2.get(i).contains("P)")|| monTableau2.get(i).contains("R)")
                    || monTableau2.get(i).contains("S)") || monTableau2.get(i).contains("T)")) {

                monTableau2.add(i,"\\\n");
                i++;
            }
        }

        for (int i=0; i<monTableau2.size(); i++){
            String lettre = monTableau2.get(i);
            switch(lettre){
                case "A)" :
                    letter.add(new SnowtamDecodeObject(monTableau2.get(i+1),ContextCompat.getDrawable(this,R.drawable.a)));
                    //FragmentTwoA.setText(monTableau2.get(i+1));
                    break;

                case "B)" :
                    String monthNumber = monTableau2.get(i+1).substring(0,2);
                    String month = "";
                    switch (monthNumber){
                        case "01":
                            month = getString(R.string.month01);
                            break;
                        case "02":
                            month = getString(R.string.month02);
                            break;
                        case "03":
                            month = getString(R.string.month03);
                            break;
                        case "04":
                            month = getString(R.string.month04);
                            break;
                        case "05":
                            month = getString(R.string.month05);
                            break;
                        case "06":
                            month = getString(R.string.month06);
                            break;
                        case "07":
                            month = getString(R.string.month07);
                            break;
                        case "08":
                            month = getString(R.string.month08);
                            break;
                        case "09":
                            month = getString(R.string.month09);
                            break;
                        case "10":
                            month = getString(R.string.month10);
                            break;
                        case "11":
                            month = getString(R.string.month11);
                            break;
                        case "12":
                            month = getString(R.string.month12);
                            break;
                    }

                    String decodageB = monTableau2.get(i+1).substring(2,4) + month + getString(R.string.at) + monTableau2.get(i+1).substring(4,6) +":"+monTableau2.get(i+1).substring(6,8)+" UTC";
                    letter.add(new SnowtamDecodeObject(decodageB, ContextCompat.getDrawable(this,R.drawable.b)));
                    break;

                case "C)":
                    String decodageC = getString(R.string.runway) + monTableau2.get(i+1);
                    letter.add(new SnowtamDecodeObject(decodageC,ContextCompat.getDrawable(this,R.drawable.c)));
                    break;

                case "D)" :
                    String decodageD;
                    if(monTableau2.get(i+1).contains("L")){
                        decodageD = getString(R.string.clr_rwy_lenght) + monTableau2.get(i+1) + getString(R.string.unit_m) +getString(R.string.left);
                        letter.add(new SnowtamDecodeObject(decodageD,ContextCompat.getDrawable(this,R.drawable.d)));
                    }
                    else if (monTableau2.get(i+1).contains("R")){
                        decodageD = getString(R.string.clr_rwy_lenght) + monTableau2.get(i+1) +getString(R.string.unit_m) +getString(R.string.right);
                        letter.add(new SnowtamDecodeObject(decodageD,ContextCompat.getDrawable(this,R.drawable.d)));
                    }
                    else{
                        decodageD = getString(R.string.clr_rwy_lenght) + monTableau2.get(i+1) + getString(R.string.unit_m) ;
                        letter.add(new SnowtamDecodeObject(decodageD,ContextCompat.getDrawable(this,R.drawable.d)));
                    }
                    break;

                case "E)" :
                    String decodageE;
                    char lastChar = monTableau2.get(i+1).charAt(monTableau2.get(i+1).length());
                    if(lastChar == 'L'){
                        decodageE = getString(R.string.clr_rwy_width) + monTableau2.get(i+1).substring(0,monTableau2.get(i+1).length()-1) + getString(R.string.unit_m) +getString(R.string.left);
                        letter.add(new SnowtamDecodeObject(decodageE,ContextCompat.getDrawable(this,R.drawable.e)));
                    }
                    else if(lastChar=='R'){
                        decodageE = getString(R.string.clr_rwy_width) + monTableau2.get(i+1).substring(0,monTableau2.get(i+1).length()-1) + getString(R.string.unit_m) +getString(R.string.right);
                        letter.add(new SnowtamDecodeObject(decodageE,ContextCompat.getDrawable(this,R.drawable.e)));
                    }
                    else{
                        decodageE = getString(R.string.clr_rwy_width) + monTableau2.get(i+1) + getString(R.string.unit_m) ;
                        letter.add(new SnowtamDecodeObject(decodageE,ContextCompat.getDrawable(this,R.drawable.e)));
                    }
                    break;

                case "F)" :
                    String threshold = "", mid ="" , out = "";
                    String[] val = monTableau2.get(i+1).split("/");
                    for (int j=0; j<val.length; j++){
                        if(val[j].contains("NIL")){
                            if(j==0){
                                threshold = getString(R.string.clear_dry);
                            }
                            if(j==1){
                                mid = getString(R.string.clear_dry);
                            }
                            if(j==2){
                                out = getString(R.string.clear_dry);
                            }

                        }
                        if(val[j].contains("1")){
                            if (j==0) {
                                threshold = getString(R.string.damp);
                            }
                            if (j==1) {
                                mid = getString(R.string.damp);
                            }
                            if (j==2) {
                                out = getString(R.string.damp);
                            }
                        }
                        if(val[j].contains("2")){
                            if (j==0) {
                                threshold = getString(R.string.wet);
                            }
                            if (j==1) {
                                mid = getString(R.string.wet);
                            }
                            if (j==2) {
                                out = getString(R.string.wet);
                            }
                        }
                        if(val[j].contains("3")){
                            if (j==0) {
                                threshold = getString(R.string.rimes);
                            }
                            if (j==1) {
                                mid = getString(R.string.rimes);
                            }
                            if (j==2) {
                                out = getString(R.string.rimes);
                            }
                        }
                        if(val[j].contains("4")){
                            if (j==0) {
                                threshold = getString(R.string.dry_snow);
                            }
                            if (j==1) {
                                mid = getString(R.string.dry_snow);
                            }
                            if (j==2) {
                                out = getString(R.string.dry_snow);
                            }
                        }
                        if(val[j].contains("5")){
                            if (j==0) {
                                threshold = getString(R.string.wet_snow);
                            }
                            if (j==1) {
                                mid = getString(R.string.wet_snow);
                            }
                            if (j==2) {
                                out = getString(R.string.wet_snow);
                            }
                        }
                        if(val[j].contains("6")){
                            if (j==0) {
                                threshold = getString(R.string.slush);
                            }
                            if (j==1) {
                                mid = getString(R.string.slush);
                            }
                            if (j==2) {
                                out = getString(R.string.slush);
                            }
                        }
                        if(val[j].contains("7")){
                            if (j==0) {
                                threshold = getString(R.string.ice);
                            }
                            if (j==1) {
                                mid =  getString(R.string.ice);
                            }
                            if (j==2) {
                                out = getString(R.string.ice);
                            }
                        }
                        if(val[j].contains("8")){
                            if (j==0) {
                                threshold = getString(R.string.rolled_snow);
                            }
                            if (j==1) {
                                mid = getString(R.string.rolled_snow);
                            }
                            if (j==2) {
                                out = getString(R.string.rolled_snow);
                            }
                        }
                        if(val[j].contains("9")){
                            if (j==0) {
                                threshold = getString(R.string.ridges);
                            }
                            if (j==1) {
                                mid = getString(R.string.ridges);
                            }
                            if (j==2) {
                                out = getString(R.string.ridges);
                            }
                        }
                    }

                    String decodageF = getString(R.string.threshold) +threshold +getString(R.string.mid_rwy)+mid+getString(R.string.roll_out)+out;
                    letter.add(new SnowtamDecodeObject(decodageF,ContextCompat.getDrawable(this,R.drawable.f)));
                    break;

                case "G)":
                    String[] valG = monTableau2.get(i+1).split("/");
                    String decodageG = getString(R.string.mean_depth)+ getString(R.string.threshold) +valG[0]+getString(R.string.unit_mm) + getString(R.string.mid_rwy)  +valG[1] + getString(R.string.unit_mm)+ getString(R.string.roll_out)+valG[2]+getString(R.string.unit_mm) ;
                    letter.add(new SnowtamDecodeObject(decodageG,ContextCompat.getDrawable(this,R.drawable.g)));
                    break;

                case "H)":
                    String decodageH, equipment = "";
                    String thresholdH = "", midH = "", outH = "";
                    String[] valH1 = monTableau2.get(i+1).split(" ");
                    String[] valH2 = valH1[0].split("/");
                    for (int j=0; j<valH2.length; j++){
                        if((valH2[j].equals("2")) || ((Integer.parseInt(valH2[j]) <= 29) && (Integer.parseInt(valH2[j]) >= 26))){
                            if (j==0) {
                                thresholdH = getString(R.string.medium_poor);
                            }
                            if (j==1) {
                                midH = getString(R.string.medium_poor);
                            }
                            if (j==2) {
                                outH = getString(R.string.medium_poor);
                            }
                        }
                        else if((valH2[j].equals("3")) || ((Integer.parseInt(valH2[j]) <= 35) && (Integer.parseInt(valH2[j]) >= 30))){
                            if (j==0) {
                                thresholdH = getString(R.string.medium);
                            }
                            if (j==1) {
                                midH = getString(R.string.medium);
                            }
                            if (j==2) {
                                outH = getString(R.string.medium);
                            }
                        }
                        else if((valH2[j].equals("4")) || ((Integer.parseInt(valH2[j]) <= 39) && (Integer.parseInt(valH2[j]) >= 36))){
                            if (j==0) {
                                thresholdH = getString(R.string.medium_good);
                            }
                            if (j==1) {
                                midH = getString(R.string.medium_good);
                            }
                            if (j==2) {
                                outH = getString(R.string.medium_good);
                            }
                        }
                        else if((valH2[j].equals("5")) || (Integer.parseInt(valH2[j]) >= 40)){
                            if (j==0) {
                                thresholdH = getString(R.string.good);
                            }
                            if (j==1) {
                                midH = getString(R.string.good);
                            }
                            if (j==2) {
                                outH = getString(R.string.good);
                            }
                        }
                        else{
                            if(j==0){
                                thresholdH = getString(R.string.poor);
                            }
                            if(j==1){
                                midH = getString(R.string.poor);
                            }
                            if(j==2){
                                outH = getString(R.string.poor);
                            }
                        }
                        if (valH1.length==2){
                            switch (valH1[1]) {
                                case "BRD":
                                    equipment = getString(R.string.brd);
                                    break;
                                case "GRT":
                                    equipment = getString(R.string.grt);
                                    break;
                                case "MUM":
                                    equipment = getString(R.string.mum);
                                    break;
                                case "RFT":
                                    equipment = getString(R.string.rft);
                                    break;
                                case "SFH":
                                    equipment = getString(R.string.sfh);
                                    break;
                                case "SFL":
                                    equipment = getString(R.string.sfl);
                                    break;
                                case "SKH":
                                    equipment = getString(R.string.skh);
                                    break;
                                case "SKL":
                                    equipment = getString(R.string.skl);
                                    break;
                                case "TAP":
                                    equipment = getString(R.string.tap);
                                    break;
                                default:
                                    equipment = valH1[1];
                                    break;
                            }
                        }
                    }
                    if(!equipment.equals("")){
                        decodageH = getString(R.string.braking_act) + getString(R.string.threshold)+ thresholdH + getString(R.string.mid_rwy) + midH + getString(R.string.roll_out) +outH + getString(R.string.equipment) + equipment;
                    }
                    else{
                        decodageH = getString(R.string.braking_act)+  getString(R.string.threshold)+ thresholdH + getString(R.string.mid_rwy) + midH + getString(R.string.roll_out) +outH;
                    }
                    letter.add(new SnowtamDecodeObject(decodageH,ContextCompat.getDrawable(this,R.drawable.h)));
                    break;

                case "J)":
                    String decodageJ;
                    String[] valJ = monTableau2.get(i+1).split("/");
                    char lastCharJ = monTableau2.get(i+1).charAt(monTableau2.get(i+1).length());
                    char beforeLastCharJ = monTableau2.get(i+1).charAt(monTableau2.get(i+1).length()-1);
                    if(lastCharJ=='L'){
                        decodageJ = getString(R.string.crit_snow_bk) + valJ[0] + getString(R.string.unit_cm) + " / " + valJ[1].substring(0,valJ[i].length()-1) + getString(R.string.unit_m) + getString(R.string.left_rwy);
                        letter.add(new SnowtamDecodeObject(decodageJ,ContextCompat.getDrawable(this,R.drawable.j)));
                    }
                    else if(lastCharJ=='R' && beforeLastCharJ != 'L'){
                        decodageJ = getString(R.string.crit_snow_bk) + valJ[0]+ getString(R.string.unit_cm) + " / " + valJ[1].substring(0,valJ[i].length()-1)+ getString(R.string.unit_m) + getString(R.string.right_rwy);
                        letter.add(new SnowtamDecodeObject(decodageJ,ContextCompat.getDrawable(this,R.drawable.j)));
                    }
                    else if (lastCharJ == 'R'){
                        decodageJ = getString(R.string.crit_snow_bk) + valJ[0]+ getString(R.string.unit_cm) + " / " + valJ[1].substring(0,valJ[i].length()-2) + getString(R.string.unit_m) + getString(R.string.left_right_rwy);
                        letter.add(new SnowtamDecodeObject(decodageJ,ContextCompat.getDrawable(this,R.drawable.j)));
                    }
                    else{
                        decodageJ = getString(R.string.crit_snow_bk) + valJ[0]+ getString(R.string.unit_cm) + " / "+valJ[1]+ getString(R.string.unit_m);
                        letter.add(new SnowtamDecodeObject(decodageJ,ContextCompat.getDrawable(this,R.drawable.j)));
                    }
                    break;

                case "K)":
                    String decodageK;
                    String[] valK = monTableau2.get(i+1).split(" ");
                    switch (valK[1]) {
                        case "L":
                            decodageK = getString(R.string.light_obs) + valK[0] + getString(R.string.left_rwy);
                            letter.add(new SnowtamDecodeObject(decodageK, ContextCompat.getDrawable(this,R.drawable.k)));
                            break;
                        case "R":
                            decodageK = getString(R.string.light_obs) + valK[0] + getString(R.string.right_rwy);
                            letter.add(new SnowtamDecodeObject(decodageK, ContextCompat.getDrawable(this,R.drawable.k)));
                            break;
                        case "LR":
                            decodageK = getString(R.string.light_obs) + valK[0] + getString(R.string.left_right_rwy);
                            letter.add(new SnowtamDecodeObject(decodageK, ContextCompat.getDrawable(this,R.drawable.k)));
                            break;
                        default:
                            decodageK = getString(R.string.no_light_obs);
                            letter.add(new SnowtamDecodeObject(decodageK, ContextCompat.getDrawable(this,R.drawable.k)));

                    }
                    break;

                case "L)":
                    String decodageL;
                    if (!monTableau2.get(i+1).equals("TOTAL")){
                        String[] valL = monTableau2.get(i+1).split("/");
                        decodageL = getString(R.string.clearance)+ valL[0] + getString(R.string.unit_m) + " / " + valL[1] + getString(R.string.unit_m);
                    }
                    else{
                        decodageL = getString(R.string.clearance_tot);
                    }
                    letter.add(new SnowtamDecodeObject(decodageL,ContextCompat.getDrawable(this,R.drawable.l)));
                    break;

                case "M)":
                    String decodageM = getString(R.string.time_completion) + monTableau2.get(i+1).substring(0,2)+":"+monTableau2.get(i+1).substring(2,4)+" UTC";
                    letter.add(new SnowtamDecodeObject(decodageM,ContextCompat.getDrawable(this,R.drawable.m)));
                    break;

                case "N)":
                    String textN ="";
                    String decodageN;
                    if(monTableau2.get(i+1).contains("NIL")){
                        textN = textN + getString(R.string.taxi) + getString(R.string.clear_dry) ;
                    }
                    if(monTableau2.get(i+1).contains("1")){
                        textN = textN + getString(R.string.taxi) + monTableau2.get(i+1).substring(0,1)+": " + getString(R.string.damp);
                    }
                    if(monTableau2.get(i+1).contains("2")){
                        textN = textN + getString(R.string.taxi) +monTableau2.get(i+1).substring(0,1)+": " + getString(R.string.wet);
                    }
                    if(monTableau2.get(i+1).contains("3")){
                        textN = textN + getString(R.string.taxi) +monTableau2.get(i+1).substring(0,1)+": " + getString(R.string.rimes);
                    }
                    if(monTableau2.get(i+1).contains("4")){
                        textN = textN + getString(R.string.taxi) +monTableau2.get(i+1).substring(0,1)+": " + getString(R.string.dry_snow);
                    }
                    if(monTableau2.get(i+1).contains("5")){
                        textN = textN + getString(R.string.taxi) +monTableau2.get(i+1).substring(0,1)+": " + getString(R.string.wet_snow);
                    }
                    if(monTableau2.get(i+1).contains("6")){
                        textN = textN + getString(R.string.taxi) +monTableau2.get(i+1).substring(0,1)+": " + getString(R.string.slush);
                    }
                    if(monTableau2.get(i+1).contains("7")){
                        if (textN.equals("")){
                            textN = textN + getString(R.string.taxi) + monTableau2.get(i+1).substring(0,1)+": " + getString(R.string.ice);
                        }
                        else{
                            textN = textN + getString(R.string.top_ice);
                        }
                    }
                    if(monTableau2.get(i+1).contains("8")){
                        if (textN.equals("")){
                            textN = textN + getString(R.string.taxi) +monTableau2.get(i+1).substring(0,1)+": " + getString(R.string.rolled_snow);
                        }
                        else{
                            textN = textN + getString(R.string.top_rolled);
                        }
                    }
                    if(monTableau2.get(i+1).contains("9")){
                        textN = textN + getString(R.string.taxi) +monTableau2.get(i+1).substring(0,1)+": " + getString(R.string.ridges);
                    }
                    if(monTableau2.get(i+1).equals("NO")){
                        textN = textN + getString(R.string.no_taxi);
                    }
                    decodageN = textN;
                    letter.add(new SnowtamDecodeObject(decodageN,ContextCompat.getDrawable(this,R.drawable.n)));
                    break;

                case "P)":
                    if (monTableau2.get(i+1).contains("YES")){
                        String decodageP =  getString(R.string.snow_banks)  + monTableau2.get(i+1).substring(3)+ getString(R.string.unit_m) ;
                        letter.add(new SnowtamDecodeObject(decodageP,ContextCompat.getDrawable(this,R.drawable.p)));
                    }

                case "R)":
                    String decodageR;
                    StringBuilder textR = new StringBuilder();
                    String[] valR = monTableau2.get(i+1).split(" ");
                    for (int j=0; j<valR.length; j++){
                        if(valR[j].contains("NIL")){
                            textR.append(getString(R.string.clear_dry));
                        }
                        if(valR[j].contains("1")){
                            textR.append(getString(R.string.damp));
                        }
                        if(valR[j].contains("2")){
                            textR.append(getString(R.string.wet));
                        }
                        if(valR[j].contains("3")){
                            textR.append(getString(R.string.rimes));
                        }
                        if(valR[j].contains("4")){
                            textR.append(getString(R.string.dry_snow));
                        }
                        if(valR[j].contains("5")){
                            textR.append(getString(R.string.wet_snow));
                        }
                        if(valR[j].contains("6")){
                            textR.append(getString(R.string.slush));
                        }
                        if(valR[j].contains("7")){
                            if(textR.toString().equals("")){
                                textR.append(getString(R.string.ice));
                            }
                            else{
                                textR.append(getString(R.string.top_ice));
                            }

                        }
                        if(valR[j].contains("8")){
                            if(textR.toString().equals("")){
                                textR.append(getString(R.string.rolled_snow));
                            }
                            else{
                                textR.append(getString(R.string.top_rolled));
                            }

                        }
                        if(valR[j].contains("9")){
                            textR.append(getString(R.string.ridges));
                        }
                        if(valR[j].equals("NO")){
                            if (valR.length>=2){
                                textR.append(getString(R.string.parking)).append(valR[j - 1]).append(getString(R.string.unusable));
                            }
                            else {
                                textR.append(getString(R.string.parking_unusable));
                            }
                        }
                    }
                    decodageR = textR.toString();
                    letter.add(new SnowtamDecodeObject(decodageR,ContextCompat.getDrawable(this,R.drawable.r)));
                    break;

                case "S)":
                    String monthNumberS = monTableau2.get(i+1).substring(0,2);
                    String monthS = "";
                    switch (monthNumberS){
                        case "01":
                            monthS = getString(R.string.month01);
                            break;
                        case "02":
                            monthS = getString(R.string.month02);
                            break;
                        case "03":
                            monthS = getString(R.string.month03);
                            break;
                        case "04":
                            monthS = getString(R.string.month04);
                            break;
                        case "05":
                            monthS = getString(R.string.month05);
                            break;
                        case "06":
                            monthS = getString(R.string.month06);
                            break;
                        case "07":
                            monthS = getString(R.string.month07);
                            break;
                        case "08":
                            monthS = getString(R.string.month08);
                            break;
                        case "09":
                            monthS = getString(R.string.month09);
                            break;
                        case "10":
                            monthS = getString(R.string.month10);
                            break;
                        case "11":
                            monthS = getString(R.string.month11);
                            break;
                        case "12":
                            monthS = getString(R.string.month12);
                            break;
                    }

                    String decodageS =  getString(R.string.next_obs)  +monTableau2.get(i+1).substring(2,4) + monthS + getString(R.string.at) + monTableau2.get(i+1).substring(4,6) +":"+monTableau2.get(i+1).substring(6,8)+" UTC";
                    letter.add(new SnowtamDecodeObject(decodageS,ContextCompat.getDrawable(this,R.drawable.s)));
                    break;

                case "T)":
                    StringBuilder decodageT = new StringBuilder(getString(R.string.notes));
                    int j = i;
                    while(!monTableau2.get(j + 1).equals(")")){
                        decodageT.append(monTableau2.get(j + 1)).append(" ");
                        j++;
                    }
                    letter.add(new SnowtamDecodeObject(decodageT.toString(),ContextCompat.getDrawable(this,R.drawable.t)));
                    break;

                default:
                    break;
            }

        }
    }


}