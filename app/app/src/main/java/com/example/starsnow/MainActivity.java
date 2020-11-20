package com.example.starsnow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private CardView cardAerop1, cardAerop2, cardAerop3, cardAerop4;
    private Button ajoutAerop;
    private int nbAerop = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardAerop1 = (CardView) findViewById(R.id.card_aerop1);
        cardAerop2 = (CardView) findViewById(R.id.card_aerop2);
        cardAerop3 = (CardView) findViewById(R.id.card_aerop3);
        cardAerop4 = (CardView) findViewById(R.id.card_aerop4);

        cardAerop1.setVisibility(View.GONE);
        cardAerop2.setVisibility(View.GONE);
        cardAerop3.setVisibility(View.GONE);
        cardAerop4.setVisibility(View.GONE);

        ajoutAerop = (Button) findViewById(R.id.ajoutAerop);

        ajoutAerop.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(nbAerop == 0)
        {
            cardAerop1.setVisibility(View.VISIBLE);
            nbAerop++;
        }
        else if (nbAerop == 1) {
            cardAerop2.setVisibility(View.VISIBLE);
            nbAerop++;
        }
        else if (nbAerop == 2) {
            cardAerop3.setVisibility(View.VISIBLE);
            nbAerop++;
        }
        else if (nbAerop == 3) {
            cardAerop4.setVisibility(View.VISIBLE);
            ajoutAerop.setVisibility(View.GONE);
            nbAerop = 4;
        }
        else {
            //affiche popup 
        }
    }
}