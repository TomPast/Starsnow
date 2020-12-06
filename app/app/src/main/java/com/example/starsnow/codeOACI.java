package com.example.starsnow;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
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
import standardclasses.SnowtamDecodeObject;
import standardclasses.VolleyCallback;
import standardclasses.VolleyCallback2;

public class codeOACI extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView FragmentOne;
    private TextView AeroportName;
    private TextView Longitude;
    private TextView Latitude;
    private RecyclerView recyclerView;
    private List<SnowtamDecodeObject> letter = new ArrayList<>();
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
            @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
            @Override
            public void onSuccess(String result) {
               //Partie codé
                System.out.println(result);
                FragmentOne = (TextView) findViewById(R.id.section_label);
                FragmentOne.setText(result);

                //Partie décodé

                recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                //définit l'agencement des cellules, ici de façon verticale, comme une ListView
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
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
                            letter.add(new SnowtamDecodeObject(monTableau2.get(i+1),getDrawable(R.drawable.a)));
                            //FragmentTwoA.setText(monTableau2.get(i+1));
                            break;

                        case "B)" :
                             String decodageB = monTableau2.get(i+1).substring(2,4)+ "/" + monTableau2.get(i+1).substring(0,2) + " at " + monTableau2.get(i+1).substring(4,6) +":"+monTableau2.get(i+1).substring(6,8)+" UTC";
                            letter.add(new SnowtamDecodeObject(decodageB,getDrawable(R.drawable.b)));
                            break;

                        case "C)":
                            String decodageC = "Runway " + monTableau2.get(i+1);
                            letter.add(new SnowtamDecodeObject(decodageC,getDrawable(R.drawable.c)));
                            break;

                        case "D)" :
                            String decodageD;
                            if(monTableau2.get(i+1).contains("L")){
                                decodageD = "Cleared runway lenght "+ monTableau2.get(i+1) + "m Left";
                                letter.add(new SnowtamDecodeObject(decodageD,getDrawable(R.drawable.d)));
                            }
                            else if (monTableau2.get(i+1).contains("R")){
                                decodageD = "Cleared runway lenght "+ monTableau2.get(i+1) + "m Right";
                                letter.add(new SnowtamDecodeObject(decodageD,getDrawable(R.drawable.d)));
                            }
                            else{
                                decodageD = "Cleared runway lenght "+ monTableau2.get(i+1) + "m";
                                letter.add(new SnowtamDecodeObject(decodageD,getDrawable(R.drawable.d)));
                            }
                            break;

                        case "E)" :
                           String decodageE;
                           char lastChar = monTableau2.get(i+1).charAt(monTableau2.get(i+1).length());
                           if(lastChar == 'L'){
                               decodageE = "Cleared runway width " + monTableau2.get(i+1).substring(0,monTableau2.get(i+1).length()-1) + "m Left";
                               letter.add(new SnowtamDecodeObject(decodageE,getDrawable(R.drawable.e)));
                           }
                           else if(lastChar=='R'){
                               decodageE = "Cleared runway width " + monTableau2.get(i+1).substring(0,monTableau2.get(i+1).length()-1) + "m Right";
                               letter.add(new SnowtamDecodeObject(decodageE,getDrawable(R.drawable.e)));
                           }
                           else{
                               decodageE = "Cleared runway width " + monTableau2.get(i+1) + "m";
                               letter.add(new SnowtamDecodeObject(decodageE,getDrawable(R.drawable.e)));
                           }
                            break;

                        case "F)" :
                            String threshold = "", mid ="" , out = "";
                            String[] val = monTableau2.get(i+1).split("/");
                            for (int j=0; j<val.length; j++){
                                if(val[j].contains("NIL")){
                                    if(j==0){
                                        threshold = " Cleared and dry";
                                    }
                                    if(j==1){
                                        mid = " Cleared and dry";
                                    }
                                    if(j==2){
                                        out = " Cleared and dry";
                                    }

                                }
                                if(val[j].contains("1")){
                                    if (j==0) {
                                        threshold = " Damp";
                                    }
                                    if (j==1) {
                                        mid = " Damp";
                                    }
                                    if (j==2) {
                                        out = " Damp";
                                    }
                                }
                                if(val[j].contains("2")){
                                    if (j==0) {
                                        threshold = " Wet or water patches";
                                    }
                                    if (j==1) {
                                        mid = " Wet or water patches";
                                    }
                                    if (j==2) {
                                        out = " Wet or water patches";
                                    }
                                }
                                if(val[j].contains("3")){
                                    if (j==0) {
                                        threshold = " Rime or frost covered";
                                    }
                                    if (j==1) {
                                        mid = " Rime or frost covered";
                                    }
                                    if (j==2) {
                                        out = " Rime or frost covered";
                                    }
                                }
                                if(val[j].contains("4")){
                                    if (j==0) {
                                        threshold = " Dry snow";
                                    }
                                    if (j==1) {
                                        mid = " Dry snow";
                                    }
                                    if (j==2) {
                                        out = " Dry snow";
                                    }
                                }
                                if(val[j].contains("5")){
                                    if (j==0) {
                                        threshold = " Wet snow";
                                    }
                                    if (j==1) {
                                        mid = " Wet snow";
                                    }
                                    if (j==2) {
                                        out = " Wet snow";
                                    }
                                }
                                if(val[j].contains("6")){
                                    if (j==0) {
                                        threshold = " Slush";
                                    }
                                    if (j==1) {
                                        mid = " Slush";
                                    }
                                    if (j==2) {
                                        out = " Slush";
                                    }
                                }
                                if(val[j].contains("7")){
                                    if (j==0) {
                                        threshold = " Ice";
                                    }
                                    if (j==1) {
                                        mid =  " Ice";
                                    }
                                    if (j==2) {
                                        out = " Ice";
                                    }
                                }
                                if(val[j].contains("8")){
                                    if (j==0) {
                                        threshold = " Compact or rolled snow";
                                    }
                                    if (j==1) {
                                        mid =" Compact or rolled snow";
                                    }
                                    if (j==2) {
                                        out = " Compact or rolled snow";
                                    }
                                }
                                if(val[j].contains("9")){
                                    if (j==0) {
                                        threshold = " Frozen ruts or ridges";
                                    }
                                    if (j==1) {
                                        mid = " Frozen ruts or ridges";
                                    }
                                    if (j==2) {
                                        out = " Frozen ruts or ridges";
                                    }
                                }
                            }

                            String decodageF = "Threshold: "+threshold +" \nMid runway: "+mid+" \nRoll out: "+out;
                            letter.add(new SnowtamDecodeObject(decodageF,getDrawable(R.drawable.f)));
                            break;

                        case "G)":
                            String[] valG = monTableau2.get(i+1).split("/");
                            String decodageG = "Mean depth Threshold: "+valG[0]+"mm \nMid runway: "+valG[1]+"mm \nRoll out: "+valG[2]+"mm";
                            letter.add(new SnowtamDecodeObject(decodageG,getDrawable(R.drawable.g)));
                            break;

                        case "H)":
                            String decodageH, equipment = "";
                            String thresholdH = "", midH = "", outH = "";
                            String[] valH1 = monTableau2.get(i+1).split(" ");
                            String[] valH2 = valH1[0].split("/");
                            for (int j=0; j<valH2.length; j++){
                                if((valH2[j].equals("2")) || ((Integer.parseInt(valH2[j]) <= 29) && (Integer.parseInt(valH2[j]) >= 26))){
                                    if (j==0) {
                                        thresholdH = " Medium, Poor";
                                    }
                                    if (j==1) {
                                        midH = " Medium, Poor";
                                    }
                                    if (j==2) {
                                        outH = " Medium, Poor";
                                    }
                                }
                                else if((valH2[j].equals("3")) || ((Integer.parseInt(valH2[j]) <= 35) && (Integer.parseInt(valH2[j]) >= 30))){
                                    if (j==0) {
                                        thresholdH = " Medium";
                                    }
                                    if (j==1) {
                                        midH = " Medium";
                                    }
                                    if (j==2) {
                                        outH = " Medium";
                                    }
                                }
                                else if((valH2[j].equals("4")) || ((Integer.parseInt(valH2[j]) <= 39) && (Integer.parseInt(valH2[j]) >= 36))){
                                    if (j==0) {
                                        thresholdH = " Medium, Good";
                                    }
                                    if (j==1) {
                                        midH = " Medium, Good";
                                    }
                                    if (j==2) {
                                        outH = " Medium, Good";
                                    }
                                }
                                else if((valH2[j].equals("5")) || (Integer.parseInt(valH2[j]) >= 40)){
                                    if (j==0) {
                                        thresholdH = " Good";
                                    }
                                    if (j==1) {
                                        midH = " Good";
                                    }
                                    if (j==2) {
                                        outH = " Good";
                                    }
                                }
                                else{
                                    if(j==0){
                                        thresholdH = " Poor";
                                    }
                                    if(j==1){
                                        midH = " Poor";
                                    }
                                    if(j==2){
                                        outH = " Poor";
                                    }
                                }
                                if (valH1.length==2){
                                    switch (valH1[1]) {
                                        case "BRD":
                                            equipment = "Brakemeter-Dynometer";
                                            break;
                                        case "GRT":
                                            equipment = "Grip-Tester";
                                            break;
                                        case "MUM":
                                            equipment = "Mu-meter";
                                            break;
                                        case "RFT":
                                            equipment = "Runway Friction Tester";
                                            break;
                                        case "SFH":
                                            equipment = "Surface Friction Tester (High pressure tires)";
                                            break;
                                        case "SFL":
                                            equipment = "Surface Friction Tester (Low pressure tires)";
                                            break;
                                        case "SKH":
                                            equipment = "Skiddometer (High pressure tires)";
                                            break;
                                        case "SKL":
                                            equipment = "Skiddometer (Low pressure tires)";
                                            break;
                                        case "TAP":
                                            equipment = "Tapleymeter";
                                            break;
                                        default:
                                            equipment = valH1[1];
                                            break;
                                    }
                                }
                            }
                            if(equipment != ""){
                                decodageH = "Treshold: "+thresholdH+ " \nMid runway: "+midH + " \nRoll out: "+outH + " \nequipment: "+equipment;
                            }
                            else{
                                decodageH = "Treshold: "+thresholdH+ " \nMid runway: "+midH + " \nRoll out: "+outH;
                            }

                            letter.add(new SnowtamDecodeObject(decodageH,getDrawable(R.drawable.h)));
                            break;

                        case "J)":
                            String decodageJ;
                            String[] valJ = monTableau2.get(i+1).split("/");
                            char lastCharJ = monTableau2.get(i+1).charAt(monTableau2.get(i+1).length());
                            char beforeLastCharJ = monTableau2.get(i+1).charAt(monTableau2.get(i+1).length()-1);
                            if(lastCharJ=='L'){
                                decodageJ = "Critical snow bank "+valJ[0]+"cm / "+valJ[1].substring(0,valJ[i].length()-1)+"m Left of runway";
                                letter.add(new SnowtamDecodeObject(decodageJ,getDrawable(R.drawable.j)));
                            }
                            else if(lastCharJ=='R' && beforeLastCharJ != 'L'){
                                decodageJ = "Critical snow bank "+valJ[0]+"cm / "+valJ[1].substring(0,valJ[i].length()-1)+"m Right of runway";
                                letter.add(new SnowtamDecodeObject(decodageJ,getDrawable(R.drawable.j)));
                            }
                            else if (lastCharJ=='R' && beforeLastCharJ == 'L'){
                                decodageJ = "Critical snow bank "+valJ[0]+"cm / "+valJ[1].substring(0,valJ[i].length()-2)+"m Left and Right of runway";
                                letter.add(new SnowtamDecodeObject(decodageJ,getDrawable(R.drawable.j)));
                            }
                            else{
                                decodageJ = "Critical snow bank "+valJ[0]+"cm / "+valJ[1]+"meters";
                                letter.add(new SnowtamDecodeObject(decodageJ,getDrawable(R.drawable.j)));
                            }
                            break;

                        case "K)":
                            String decodageK;
                            String[] valK = monTableau2.get(i+1).split(" ");
                            if (valK[1].equals("L")){
                                decodageK = "Lights obscured: "+valK[0]+" Left of runway";
                                letter.add(new SnowtamDecodeObject(decodageK,getDrawable(R.drawable.k)));
                            }
                            else if (valK[1].equals("R")){
                                decodageK = "Lights obscured: "+valK[0]+" Right of runway";
                                letter.add(new SnowtamDecodeObject(decodageK,getDrawable(R.drawable.k)));
                            }
                            else if (valK[1].equals("LR")){
                                decodageK = "Lights obscured: "+valK[0]+" Left and Right of runway";
                                letter.add(new SnowtamDecodeObject(decodageK,getDrawable(R.drawable.k)));
                            }
                            break;

                        case "L)":
                            String decodageL;
                            if (!monTableau2.get(i+1).equals("TOTAL")){
                                String[] valL = monTableau2.get(i+1).split("/");
                                decodageL = "Further clearance "+valL[0]+"m / "+valL[1]+"m";
                                letter.add(new SnowtamDecodeObject(decodageL,getDrawable(R.drawable.l)));
                            }
                            else{
                                decodageL = "Further clearance total";
                                letter.add(new SnowtamDecodeObject(decodageL,getDrawable(R.drawable.l)));
                            }
                            break;

                        case "M)":
                            String decodageM = "Anticipated time of completion "+monTableau2.get(i+1).substring(0,2)+":"+monTableau2.get(i+1).substring(2,4)+" UTC";
                            letter.add(new SnowtamDecodeObject(decodageM,getDrawable(R.drawable.m)));
                            break;

                        case "N)":
                            String textN ="";
                            String decodageN;
                            if(monTableau2.get(i+1).contains("NIL")){
                                textN = textN +"Taxiway cleared and dry";
                                //letter.add(new SnowtamDecodeObject(decodageN,getDrawable(R.drawable.n)));
                            }
                            if(monTableau2.get(i+1).contains("1")){
                                textN = textN +"Taxiway "+monTableau2.get(i+1).substring(0,1)+": Damp";
                                //letter.add(new SnowtamDecodeObject(decodageN,getDrawable(R.drawable.n)));
                            }
                            if(monTableau2.get(i+1).contains("2")){
                                textN = textN +"Taxiway "+monTableau2.get(i+1).substring(0,1)+": Wet or water patches";
                                //letter.add(new SnowtamDecodeObject(decodageN,getDrawable(R.drawable.n)));
                            }
                            if(monTableau2.get(i+1).contains("3")){
                                textN = textN +"Taxiway "+monTableau2.get(i+1).substring(0,1)+": Rimes or frost covered";
                               // letter.add(new SnowtamDecodeObject(decodageN,getDrawable(R.drawable.n)));
                            }
                            if(monTableau2.get(i+1).contains("4")){
                                textN = textN +"Taxiway "+monTableau2.get(i+1).substring(0,1)+": Dry snow";
                                //letter.add(new SnowtamDecodeObject(decodageN,getDrawable(R.drawable.n)));
                            }
                            if(monTableau2.get(i+1).contains("5")){
                                textN = textN +"Taxiway "+monTableau2.get(i+1).substring(0,1)+": Wet snow";
                                //letter.add(new SnowtamDecodeObject(decodageN,getDrawable(R.drawable.n)));
                            }
                            if(monTableau2.get(i+1).contains("6")){
                                textN = textN +"Taxiway "+monTableau2.get(i+1).substring(0,1)+": Slush";
                                //letter.add(new SnowtamDecodeObject(decodageN,getDrawable(R.drawable.n)));
                            }
                            if(monTableau2.get(i+1).contains("7")){
                                if (textN.equals("")){
                                    textN = textN +"Taxiway "+monTableau2.get(i+1).substring(0,1)+": Ice";
                                }
                                else{
                                    textN = textN +"on top of Ice";
                                }
                                //letter.add(new SnowtamDecodeObject(decodageN,getDrawable(R.drawable.n)));
                            }
                            if(monTableau2.get(i+1).contains("8")){
                                if (textN.equals("")){
                                    textN = textN +"Taxiway "+monTableau2.get(i+1).substring(0,1)+": Compacted or rolled snow";
                                }
                                else{
                                    textN = textN +"on top of compacted snow";
                                }
                                //letter.add(new SnowtamDecodeObject(decodageN,getDrawable(R.drawable.n)));
                            }
                            if(monTableau2.get(i+1).contains("9")){
                                textN = textN +"Taxiway "+monTableau2.get(i+1).substring(0,1)+": Frozen ruts or ridges";
                               // letter.add(new SnowtamDecodeObject(decodageN,getDrawable(R.drawable.n)));
                            }
                            if(monTableau2.get(i+1).equals("NO")){
                                textN = textN +"No taxiway available";
                                //letter.add(new SnowtamDecodeObject(decodageN,getDrawable(R.drawable.n)));
                            }
                            decodageN = textN;
                            letter.add(new SnowtamDecodeObject(decodageN,getDrawable(R.drawable.n)));
                            break;

                        case "P)":
                            if (monTableau2.get(i+1).contains("YES")){
                                String decodageP = "Snow banks: YES Space " + monTableau2.get(i+1).substring(3)+"m";
                                letter.add(new SnowtamDecodeObject(decodageP,getDrawable(R.drawable.p)));
                            }

                        case "R)":
                            String decodageR;
                            String textR = "";
                            String[] valR = monTableau2.get(i+1).split(" ");
                            for (int j=0; j<valR.length; j++){
                                if(valR[j].contains("NIL")){
                                    textR +=  "Cleared and dry";
                                }
                                if(valR[j].contains("1")){
                                    textR += "Damp";
                                }
                                if(valR[j].contains("2")){
                                    textR += "Wet or water patches";
                                }
                                if(valR[j].contains("3")){
                                    textR +=" Rime or frost covered";
                                }
                                if(valR[j].contains("4")){
                                    textR +=  "Dry snow";
                                }
                                if(valR[j].contains("5")){
                                    textR += "Wet snow";
                                }
                                if(valR[j].contains("6")){
                                    textR += "Slush";
                                }
                                if(valR[j].contains("7")){
                                    if(textR.equals("")){
                                        textR += "Ice";
                                    }
                                    else{
                                        textR += " on top of Ice";
                                    }

                                }
                                if(valR[j].contains("8")){
                                    if(textR.equals("")){
                                        textR += " Compact or rolled snow";
                                    }
                                    else{
                                        textR += " on top of compact or rolled snow";
                                    }

                                }
                                if(valR[j].contains("9")){
                                    textR += " Frozen ruts or ridges";
                                }
                                if(valR[j].equals("NO")){
                                    if (valR.length>=2){
                                        textR += "Parking "+valR[j-1]+" Unusable";
                                    }
                                    else {
                                        textR += "Parkings unusable";
                                    }
                                }
                            }
                            decodageR = textR;
                            letter.add(new SnowtamDecodeObject(decodageR,getDrawable(R.drawable.r)));
                            break;

                        case "S)":
                            String decodageS = "Next observation "+monTableau2.get(i+1).substring(2,4)+ "/" + monTableau2.get(i+1).substring(0,2) + " at " + monTableau2.get(i+1).substring(4,6) +":"+monTableau2.get(i+1).substring(6,8)+" UTC";
                            letter.add(new SnowtamDecodeObject(decodageS,getDrawable(R.drawable.s)));
                            break;

                        case "T)":
                            String decodageT = "";
                            int j = i;
                            while(!monTableau2.get(j + 1).equals(")")){
                                decodageT = decodageT + monTableau2.get(j+1)+ " ";
                                j++;
                            }
                            letter.add(new SnowtamDecodeObject(decodageT,getDrawable(R.drawable.t)));
                            break;

                        default:
                            break;
                    }
                    System.out.println(monTableau2.get(i));
                }
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