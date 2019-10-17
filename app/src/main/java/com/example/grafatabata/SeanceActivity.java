package com.example.grafatabata;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SeanceActivity extends AppCompatActivity {

    // CONSTANTE
    private final static long INITIAL_TIME = 5000;


    // VIEW
    private Button startButton;
    private Button pauseButton;
    private TextView timerValue;
    int tempsChrono;

    // DATA
    private long updatedTime = INITIAL_TIME;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seance);

        // Récupérer les views
        timerValue = (TextView) findViewById(R.id.timerValue);
        startButton = (Button) findViewById(R.id.startButton);
        pauseButton = (Button) findViewById(R.id.pauseButton);

        // la valeur du chrono va prendre pour valeur la valeur passée.
        tempsChrono = getIntent().getIntExtra("TABLE_KEY",1);
        updatedTime = tempsChrono;
        miseAJour();
        

    }

      public void onStart(View view) {

        timer = new CountDownTimer(updatedTime, 10) {

            public void onTick(long millisUntilFinished) {
                updatedTime = millisUntilFinished;
                miseAJour();
            }

            public void onFinish() {
                updatedTime = 0;
                miseAJour();
            }
        }.start();

    }

    // Mettre en pause le compteur
    public void onPause(View view) {
        if (timer != null) {
            timer.cancel(); // Arrete le CountDownTimer
        }
    }


    // Mise à jour graphique
    private void miseAJour() {

        // Décompositions en secondes et minutes
        int secs = (int) (updatedTime / 1000);
        int mins = secs / 60;
        secs = secs % 60;
        int milliseconds = (int) (updatedTime % 1000);

        // Affichage du "timer"
        timerValue.setText("" + mins + ":"
                + String.format("%02d", secs) + ":"
                + String.format("%03d", milliseconds));

    }

    // Remettre à le compteur à la valeur initiale
    public void onReset(View view) {

        // Mettre en pause
        if (timer != null) {
            timer.cancel();
        }

        // Réinitialiser
        updatedTime = INITIAL_TIME;

        // Mise à jour graphique

        updatedTime = tempsChrono;
        miseAJour();

    }

    public void MenuButton(View view) {
        // création de l'intention au systeme
        Intent menu = new Intent(this, TabataActivity.class);
        startActivity(menu);
    }
}
