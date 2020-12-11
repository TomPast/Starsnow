package com.example.starsnow;

import com.example.starsnow.model.Aeroport;
import com.example.starsnow.model.Snowtam;


public class sampleOACI {

    public static final String SNOWTAM_BIRK = "SWBI2304 BIRK 12101400\n(SNOWTAM 2304\nA) BIRK\nB) 12101400 C) 01 F) 1/1/1 G) XX/XX/XX H) 5/5/5 N) 1\nB) 12101400 C) 13 F) 1/1/1 G) XX/XX/XX H) 5/5/5 N) 1\nR) 1\nS) 12102200\nT)\nRWY 01 CONTAMINATION 100 PERCENT SANDED. RWY 13 CONTAMINATION 100 PERCENT SANDED. TWYS AND APRONS CONTAMINATION 100 PERCENT SANDED\n)\nCREATED: 10 Dec 2020 14:28:00 \nSOURCE: BIRKYNYX";
    public static final String SNOWTAM_BGJN = "SWBG0096 BGJN 12101110\n(SNOWTAM 0096\nA) BGJN\nB) 12101110 C) 07 F) 8/8/8 G) 2/2/2 H) 5/5/5 N) 1\nR) 1\nT)\nRWY 01 CONTAMINATION 100 PERCENT RWY 10 CONTAMINATION 100 PERCENT TW\n)\nCREATED: 10 Dec 2020 11:16:00 \nSOURCE: EUECYIYN";
    public static final String SNOWTAM_BIKF = "SWBI2301 BIKF 12101325\n(SNOWTAM 2301\nA) BIKF\nB) 12101325 C) 01 F) 1/1/1 G) XX/XX/XX H) 5/5/5 N) 1\nB) 12101330 C) 10 F) 1/1/1 G) XX/XX/XX H) 5/5/5 N) 1\nR) 1\nT)\nRWY 01 CONTAMINATION 100 PERCENT RWY 10 CONTAMINATION 100 PERCENT TW\n Y B/A GOOD\n)\nCREATED: 10 Dec 2020 13:31:00 \nSOURCE: BIRKYNYX";
    public static final String SNOWTAM_BIAR = "SWBI2305 BIAR 12101430\n(SNOWTAM 2305\nA) BIAR\nB) 12101430 C) 01 F) 7/7/7 H) 5/5/5\nS) 12102200\nT)\nRWY 01 SANDED\n)\nCREATED: 10 Dec 2020 14:36:00 \nSOURCE: BIRKYNYX";


    public Aeroport[] aeroports =  new Aeroport[4];


    public sampleOACI(){
        aeroports[0] = new Aeroport("BIRK", "Reykjavik", 64.13, -21.940555);
        aeroports[0].setSnowtam(new Snowtam("BIRK", SNOWTAM_BIRK));
        aeroports[1] = new Aeroport("BIAR", "Akureyri", 65.65666666666667, -18.071944444444444);
        aeroports[1].setSnowtam(new Snowtam("BIAR", SNOWTAM_BIAR));
        aeroports[2] = new Aeroport("BGJN", "Ilulissat", 69.24321, -51.057111);
        aeroports[2].setSnowtam(new Snowtam("BGJN", SNOWTAM_BGJN));
        aeroports[3] = new Aeroport("BIKF", "Keflavik",  63.985, -22.605555);
        aeroports[3].setSnowtam(new Snowtam("BIKF", SNOWTAM_BIKF));
    }

    public Aeroport[] getAirports(){
        return aeroports;
    }

}
