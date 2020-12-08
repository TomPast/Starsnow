package com.example.starsnow;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

import standardclasses.Aeroport;
import standardclasses.IACO_APIService;
import standardclasses.VolleyCallback2;

public class MainActivity extends AppCompatActivity{

    private static String MSG_STATUS;
    private String nomAerop;
    private ArrayList codes;
    private IACO_APIService API;
    private ConstraintLayout ajoutCode;
    private CardView cardAerop1, cardAerop2, cardAerop3, cardAerop4;
    private Button btn_valider, ajoutAerop;
    private TextView code_aeroport1, code_aeroport2, code_aeroport3, code_aeroport4, aeroport_titre1, aeroport_titre2, aeroport_titre3, aeroport_titre4;
    private EditText otp_textbox1, otp_textbox2, otp_textbox3, otp_textbox4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MSG_STATUS = this.getResources().getString(R.string.popup_error);

        // Init liste des codes qui sera envoyé dans la prochaine activité
        codes = new ArrayList();

        // Recupere l'id des elements graphiques
        cardAerop1 = findViewById(R.id.card_aerop1);
        cardAerop2 = findViewById(R.id.card_aerop2);
        cardAerop3 = findViewById(R.id.card_aerop3);
        cardAerop4 = findViewById(R.id.card_aerop4);
        ImageButton suppr1 = findViewById((R.id.icone_supp1));
        ImageButton suppr2 = findViewById((R.id.icone_supp2));
        ImageButton suppr3 = findViewById((R.id.icone_supp3));
        ImageButton suppr4 = findViewById((R.id.icone_supp4));
        ajoutAerop = findViewById(R.id.ajoutAerop);
        btn_valider = findViewById(R.id.btn_valider);
        ajoutCode = findViewById(R.id.ajoutCode);
        Button rechercherCode = findViewById(R.id.rechercherCode);

        code_aeroport1 = findViewById(R.id.code_aeroport1);
        code_aeroport2 = findViewById(R.id.code_aeroport2);
        code_aeroport3 = findViewById(R.id.code_aeroport3);
        code_aeroport4 = findViewById(R.id.code_aeroport4);

        aeroport_titre1 = findViewById(R.id.aeroport_titre1);
        aeroport_titre2= findViewById(R.id.aeroport_titre2);
        aeroport_titre3= findViewById(R.id.aeroport_titre3);
        aeroport_titre4= findViewById(R.id.aeroport_titre4);

        otp_textbox1 = findViewById(R.id.otp_edit_box1);
        otp_textbox2 = findViewById(R.id.otp_edit_box2);
        otp_textbox3 = findViewById(R.id.otp_edit_box3);
        otp_textbox4 = findViewById(R.id.otp_edit_box4);

        //Cache les éléments que l'on ne souhaite pas voir lors de la création
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
        btn_valider.setOnClickListener(handlerValider);

        //Initialise la zone de texte customisée pour l'ajout du code
        EditText[] edit = {otp_textbox1, otp_textbox2, otp_textbox3, otp_textbox4};
        otp_textbox1.addTextChangedListener(new GenericTextWatcher(otp_textbox1, edit));
        otp_textbox2.addTextChangedListener(new GenericTextWatcher(otp_textbox2, edit));
        otp_textbox3.addTextChangedListener(new GenericTextWatcher(otp_textbox3, edit));
        otp_textbox4.addTextChangedListener(new GenericTextWatcher(otp_textbox4, edit));

        API = new IACO_APIService(this.getApplicationContext());

    }

    /**
     * Supprime la card correspondante
     */
    View.OnClickListener handlerSuppr1 = new View.OnClickListener() {
        public void onClick(View v) {
            cardAerop1.setVisibility(View.GONE);
            codes.remove(0);
            affichageAjoutAerop();
        }
    };
    View.OnClickListener handlerSuppr2 = new View.OnClickListener() {
        public void onClick(View v) {
            cardAerop2.setVisibility(View.GONE);
            codes.remove(1);
            affichageAjoutAerop();
        }
    };
    View.OnClickListener handlerSuppr3 = new View.OnClickListener() {
        public void onClick(View v) {
            cardAerop3.setVisibility(View.GONE);
            codes.remove(2);
            affichageAjoutAerop();
        }
    };
    View.OnClickListener handlerSuppr4 = new View.OnClickListener() {
        public void onClick(View v) {
            cardAerop4.setVisibility(View.GONE);
            codes.remove(3);
            affichageAjoutAerop();
        }
    };

    /**
     * Appel de l'api pour chercher le nom de l'aéroort
     * Si code valide affiche une card avec le nom de l'aéroport
     * Sinon popup erreur
     */
    View.OnClickListener handlerRecherche = new View.OnClickListener() {
        public void onClick(View v) {
            String lettre1 = otp_textbox1.getText().toString();
            String lettre2 = otp_textbox2.getText().toString();
            String lettre3 = otp_textbox3.getText().toString();
            String lettre4 = otp_textbox4.getText().toString();
            String codeOACI = lettre1+lettre2+lettre3+lettre4;

            API.getAeroport(codeOACI, new VolleyCallback2() {
                @Override
                public void onSuccess(Aeroport results) {
                    Log.d("APPELAPI","API SUCCESS result = "+results);
                    nomAerop = results.getNom();
                    if (cardAerop1.getVisibility() == View.GONE) {
                        code_aeroport1.setText(codeOACI.toUpperCase());
                        codes.add(0, codeOACI);
                        aeroport_titre1.setText(nomAerop);
                        cardAerop1.setVisibility(View.VISIBLE);
                    } else {
                        if (cardAerop2.getVisibility() == View.GONE) {
                            code_aeroport2.setText(codeOACI.toUpperCase());
                            codes.add(1, codeOACI);
                            aeroport_titre2.setText(nomAerop);
                            cardAerop2.setVisibility(View.VISIBLE);
                        } else {
                            if (cardAerop3.getVisibility() == View.GONE) {
                                code_aeroport3.setText(codeOACI.toUpperCase());
                                codes.add(2, codeOACI);
                                aeroport_titre3.setText(nomAerop);
                                cardAerop3.setVisibility(View.VISIBLE);
                            } else {
                                if (cardAerop4.getVisibility() == View.GONE) {
                                    code_aeroport4.setText(codeOACI.toUpperCase());
                                    codes.add(3, codeOACI);
                                    aeroport_titre4.setText(nomAerop);
                                    cardAerop4.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                    affichageAjoutAerop();
                }
                public void onError(String error){
                    Log.d("APPELAPI","API ONERROR result = "+error);
                    popUp();
                }
            });
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
            ajoutCode.setZ(18);
            btn_valider.setZ(0);
            cardAerop3.setZ(1);
            ajoutAerop.setZ(2);
        }
    };

    /**
     * Affiche ou non le bouton d'ajout d'aéroport en fonction du nombre d'aéroports deja presents
     */
    private void affichageAjoutAerop() {
        if ((cardAerop1.getVisibility() == View.VISIBLE) && (cardAerop2.getVisibility() == View.VISIBLE) && (cardAerop3.getVisibility() == View.VISIBLE) && (cardAerop4.getVisibility() == View.VISIBLE)) {
            ajoutAerop.setVisibility(View.GONE);
        } else {
            ajoutAerop.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Changement d'activité avec envoi de la liste des codes
     */
    View.OnClickListener handlerValider = new View.OnClickListener() {
        public void onClick(View v) {
            Log.d("MSG",codes.toString());
            Intent codeOACI = new Intent(MainActivity.this, codeOACI.class);
            codeOACI.putExtra("codes",codes);
            startActivity(codeOACI);
        }
    };

    /**
     * Affiche pop up erreur
     */
    public void popUp() {
        Toast toast = Toast.makeText(this, MSG_STATUS, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}