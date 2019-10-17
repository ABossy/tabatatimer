package com.example.grafatabata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CreateActivity extends AppCompatActivity {
    // CONSTANTE
    private int tempsTravail = 10;
    TextView afficherTempsTravail;
    public static final String TABLE_KEY = "table_key";

    // views
    private LinearLayout createLayout;
    private LinearLayout linearTMP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        //Recuperation vue
        createLayout = (LinearLayout) findViewById(R.id.linear);


        // Boucle pour la création de mes lignes workout.
        for (int i =1; i <6;i++) {
            linearTMP = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_exercice, null);
            afficherTempsTravail = (TextView)linearTMP.findViewById(R.id.afficherTempsTravail);
            afficherTempsTravail.setText(String.valueOf(tempsTravail));
            TextView Categorie = (TextView) linearTMP.findViewById(R.id.categorie);
            Categorie.setText("");
            Categorie.getText().toString();

            //ajout au linear principal
            createLayout.addView(linearTMP);


        }
    }

    // decrementation du chrono
    public void ButtonLess(View view) {
        tempsTravail = tempsTravail -1;
        Log.d("text", String.valueOf(tempsTravail));
        afficherTempsTravail.setText(String.valueOf(tempsTravail));

    }

    // incrementation du chrono
    public void ButtonAdd(View view) {
        tempsTravail = tempsTravail +1;
        Log.d("text", String.valueOf(tempsTravail));
        afficherTempsTravail.setText(String.valueOf(tempsTravail));


    }

    // permet de sauvegarder une creation workout
    public void sauvegarder(View view) {
        finish();
    }

    // permet de valider la creation et de la jouer dans le chrono
    public void valider(View view) {
        Intent goToChrono = new Intent(this, SeanceActivity.class);
        goToChrono.putExtra("TABLE_KEY",tempsTravail * 1000);
        startActivity(goToChrono);

    }



}
