package com.example.grafatabata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CreateActivity extends AppCompatActivity {

    // CONSTANTE
    private int tempsTravail = 10;
    TextView afficherTempsTravail;

    // création de la séance
    public static final String TABLE_KEY = "table_key";
    private CreateActivity seance;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        // recuperation des objets
        TextView category = (TextView)findViewById(R.id.Categorie);

        // affichage texte
        afficherTempsTravail = (TextView)findViewById(R.id.afficherTempsTravail);
        afficherTempsTravail.setText(String.valueOf(tempsTravail));

        int valeur = getIntent().getIntExtra(TABLE_KEY, 1);
        setContentView(R.layout.activity_create);
        LinearLayout linear = findViewById(R.id.linear);
        //seance = new SeanceActivity(toto);


        for (int i =1; i <4;i++){
            //création de la ligne temporaire
            LinearLayout linearTMP=(LinearLayout)getLayoutInflater().inflate(R.layout.activity_create, null);
            TextView toto = (TextView) linearTMP.findViewById(R.id.afficherTempsTravail);
            // création du texte décrivant l'operation
            toto.setText(seance.getText(i));
            //ajout au linear principal
            linear.addView(linearTMP);



        }


    }

    public void ButtonLess(View view) {
        tempsTravail = tempsTravail -1;
        Log.d("text", String.valueOf(tempsTravail));
        afficherTempsTravail.setText(String.valueOf(tempsTravail));

    }

    public void ButtonAdd(View view) {
        tempsTravail = tempsTravail +1;
        Log.d("text", String.valueOf(tempsTravail));
        afficherTempsTravail.setText(String.valueOf(tempsTravail));


    }


    public void sauvegarder(View view) {
    }

    public void valider(View view) {
        Intent goToChrono = new Intent(this, SeanceActivity.class);
        goToChrono.putExtra("TABLE_KEY",tempsTravail * 1000);
        startActivity(goToChrono);

    }



}
