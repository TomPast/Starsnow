package com.example.starsnow;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private CardView cardAerop1, cardAerop2, cardAerop3, cardAerop4;
    private Button ajoutAerop;
    private ImageButton suppr1, suppr2, suppr3, suppr4;

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

        suppr1 = findViewById((R.id.icone_supp1));
        suppr2 = findViewById((R.id.icone_supp2));
        suppr3 = findViewById((R.id.icone_supp3));
        suppr4 = findViewById((R.id.icone_supp4));
        suppr1.setOnClickListener(handlerSuppr1);
        suppr2.setOnClickListener(handlerSuppr2);
        suppr3.setOnClickListener(handlerSuppr3);
        suppr4.setOnClickListener(handlerSuppr4);


        ajoutAerop = (Button) findViewById(R.id.ajoutAerop);
        ajoutAerop.setOnClickListener(handlerAjout);

    }

    View.OnClickListener handlerSuppr1 = new View.OnClickListener() {
        public void onClick(View v) {
            cardAerop1.setVisibility(View.GONE);
            affichageAjoutAerop();
        }
    };
    View.OnClickListener handlerSuppr2 = new View.OnClickListener() {
        public void onClick(View v) {
            cardAerop2.setVisibility(View.GONE);
            affichageAjoutAerop();
        }
    };
    View.OnClickListener handlerSuppr3 = new View.OnClickListener() {
        public void onClick(View v) {
            cardAerop3.setVisibility(View.GONE);
            affichageAjoutAerop();
        }
    };
    View.OnClickListener handlerSuppr4 = new View.OnClickListener() {
        public void onClick(View v) {
            cardAerop4.setVisibility(View.GONE);
            affichageAjoutAerop();
        }
    };

    View.OnClickListener handlerAjout = new View.OnClickListener() {
        public void onClick(View v) {
            if (cardAerop1.getVisibility() == View.GONE) {
                cardAerop1.setVisibility(View.VISIBLE);
            } else {
                if (cardAerop2.getVisibility() == View.GONE) {
                    cardAerop2.setVisibility(View.VISIBLE);
                } else {
                    if (cardAerop3.getVisibility() == View.GONE) {
                        cardAerop3.setVisibility(View.VISIBLE);
                    } else {
                        if (cardAerop4.getVisibility() == View.GONE) {
                            cardAerop4.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
            affichageAjoutAerop();
        }

    };
    private void affichageAjoutAerop() {
        if ((cardAerop1.getVisibility() == View.VISIBLE) && (cardAerop2.getVisibility() == View.VISIBLE) && (cardAerop3.getVisibility() == View.VISIBLE) && (cardAerop4.getVisibility() == View.VISIBLE)) {
            ajoutAerop.setVisibility(View.GONE);
        } else {
            ajoutAerop.setVisibility(View.VISIBLE);
        }
    }

}