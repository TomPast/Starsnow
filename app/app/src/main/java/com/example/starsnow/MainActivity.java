package com.example.starsnow;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    private CardView cardAerop1, cardAerop2, cardAerop3, cardAerop4;
    private Button ajoutAerop, btn_valider, rechercherCode;
    private ImageButton suppr1, suppr2, suppr3, suppr4;
    private ConstraintLayout ajoutCode;
    private TextView code_aeroport1, code_aeroport2, code_aeroport3, code_aeroport4;
    private EditText otp_textbox1, otp_textbox2, otp_textbox3, otp_textbox4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Recupere l'id des elements graphiques
        cardAerop1 = findViewById(R.id.card_aerop1);
        cardAerop2 = findViewById(R.id.card_aerop2);
        cardAerop3 = findViewById(R.id.card_aerop3);
        cardAerop4 = findViewById(R.id.card_aerop4);
        suppr1 = findViewById((R.id.icone_supp1));
        suppr2 = findViewById((R.id.icone_supp2));
        suppr3 = findViewById((R.id.icone_supp3));
        suppr4 = findViewById((R.id.icone_supp4));
        ajoutAerop = findViewById(R.id.ajoutAerop);
        btn_valider = findViewById(R.id.btn_valider);
        ajoutCode = findViewById(R.id.ajoutCode);
        rechercherCode = findViewById(R.id.rechercherCode);

        code_aeroport1 = findViewById(R.id.code_aeroport1);
        code_aeroport2 = findViewById(R.id.code_aeroport2);
        code_aeroport3 = findViewById(R.id.code_aeroport3);
        code_aeroport4 = findViewById(R.id.code_aeroport4);
        otp_textbox1 = findViewById(R.id.otp_edit_box1);
        otp_textbox2 = findViewById(R.id.otp_edit_box2);
        otp_textbox3 = findViewById(R.id.otp_edit_box3);
        otp_textbox4 = findViewById(R.id.otp_edit_box4);

        // Rend invisibles certains elements
        cardAerop1.setVisibility(View.GONE);
        cardAerop2.setVisibility(View.GONE);
        cardAerop3.setVisibility(View.GONE);
        cardAerop4.setVisibility(View.GONE);
        ajoutCode.setVisibility(View.GONE);


        suppr1.setOnClickListener(handlerSuppr1);
        suppr2.setOnClickListener(handlerSuppr2);
        suppr3.setOnClickListener(handlerSuppr3);
        suppr4.setOnClickListener(handlerSuppr4);
        ajoutAerop.setOnClickListener(handlerAjout);
        rechercherCode.setOnClickListener(handlerRecherche);

        EditText[] edit = {otp_textbox1, otp_textbox2, otp_textbox3, otp_textbox4};

        otp_textbox1.addTextChangedListener(new GenericTextWatcher(otp_textbox1, edit));
        otp_textbox2.addTextChangedListener(new GenericTextWatcher(otp_textbox2, edit));
        otp_textbox3.addTextChangedListener(new GenericTextWatcher(otp_textbox3, edit));
        otp_textbox4.addTextChangedListener(new GenericTextWatcher(otp_textbox4, edit));

    }

    /**
     * Supprime la card correspondante
     */
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

    /**
     * Recherche le code et ajoute la bonne card
     */
    View.OnClickListener handlerRecherche = new View.OnClickListener() {
        public void onClick(View v) {
            String lettre1 = otp_textbox1.getText().toString();
            String lettre2 = otp_textbox2.getText().toString();
            String lettre3 = otp_textbox3.getText().toString();
            String lettre4 = otp_textbox4.getText().toString();
            String codeOACI = lettre1+lettre2+lettre3+lettre4;
            if (cardAerop1.getVisibility() == View.GONE) {
                code_aeroport1.setText(codeOACI.toUpperCase());
                cardAerop1.setVisibility(View.VISIBLE);
            } else {
                if (cardAerop2.getVisibility() == View.GONE) {
                    code_aeroport2.setText(codeOACI.toUpperCase());
                    cardAerop2.setVisibility(View.VISIBLE);
                } else {
                    if (cardAerop3.getVisibility() == View.GONE) {
                        code_aeroport3.setText(codeOACI.toUpperCase());
                        cardAerop3.setVisibility(View.VISIBLE);
                    } else {
                        if (cardAerop4.getVisibility() == View.GONE) {
                            code_aeroport4.setText(codeOACI.toUpperCase());
                            cardAerop4.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
            affichageAjoutAerop();
            ajoutCode.setVisibility(View.GONE);
            btn_valider.setVisibility(View.VISIBLE);
        }
    };

    /**
     * Ouvre la zone de recherche d'aeroport
     */
    View.OnClickListener handlerAjout = new View.OnClickListener() {
        public void onClick(View v) {
            ajoutCode.setVisibility(View.VISIBLE);
            btn_valider.setVisibility(View.GONE);
            ajoutAerop.setVisibility(View.GONE);
        }
    };

    /**
     * Affiche ou non le bouton d'ajout d'aéroport en fonction du nombre d'aéroport deja presents
     */
    private void affichageAjoutAerop() {
        if ((cardAerop1.getVisibility() == View.VISIBLE) && (cardAerop2.getVisibility() == View.VISIBLE) && (cardAerop3.getVisibility() == View.VISIBLE) && (cardAerop4.getVisibility() == View.VISIBLE)) {
            ajoutAerop.setVisibility(View.GONE);
        } else {
            ajoutAerop.setVisibility(View.VISIBLE);
        }
    }
}