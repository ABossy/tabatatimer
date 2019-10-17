package com.example.grafatabata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class TabataActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabata);
    }

    // va nous permettre de selectionner directement une séance
    public void playSeance(View view) {
        // création de l'intention au systeme
        Intent play = new Intent(this, SeanceActivity.class);
        startActivity(play);

    }

    // permet de renvoyer sur la CreateActivity
    public void createSeance(View view) {
        // création de l'intention au systeme
        Intent create = new Intent(this, CreateActivity.class);
        startActivity(create);
    }

}
