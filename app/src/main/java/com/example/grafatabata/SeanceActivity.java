package com.example.grafatabata;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class SeanceActivity extends AppCompatActivity {

    // CONSTANTE
    private final static long INITIAL_TIME = 5000;

    // VIEW
    private Button startButton;
    private Button pauseButton;
    private TextView etapeNameValue;
    private TextView timerValue;
    private TextView cycleValue;
    private TextView tabataValue;
    int indexEtape = 0;
    int workTime;
    int cycleNb;
    int tabataNb;
    int prepareTime;
    int restTime;
    int longRestTime;


    // DATA
    private long updatedTime = INITIAL_TIME;
    private CountDownTimer timer;
    ArrayList tabata = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seance);

////////RECUPERATION DES VUES
        etapeNameValue = (TextView) findViewById(R.id.etapeNameValue);
        timerValue = (TextView) findViewById(R.id.timerValue);
        startButton = (Button) findViewById(R.id.startButton);
        pauseButton = (Button) findViewById(R.id.pauseButton);
        cycleValue = (TextView) findViewById(R.id.cycleValue);
        tabataValue = (TextView) findViewById(R.id.tabataValue);


////////RECUPERATION DES INTENTIONS
        prepareTime = getIntent().getIntExtra("prepareTime",1);
        updatedTime = workTime;

        workTime = getIntent().getIntExtra("workTime",1);
        updatedTime = workTime;

        restTime = getIntent().getIntExtra("restTime",1);
        updatedTime = workTime;

        longRestTime = getIntent().getIntExtra("longRestTime",1);
        updatedTime = workTime;

        cycleNb = getIntent().getIntExtra("cycleNb",1);
        cycleValue.setText("Cycle    "+ cycleNb);
        cycleValue.getText().toString();

        tabataNb = getIntent().getIntExtra("tabataNb",1);
        tabataValue.setText("Tabata    "+ tabataNb);
        tabataValue.getText().toString();


////////INITIALISATION DE MA SEQUENCE
        tabata.add(prepareTime);
        for(int i =0; i<tabataNb;i++){
            for(int j =0; j<cycleNb;j++){
                tabata.add(workTime);
                tabata.add(restTime);
            }
            tabata.add(longRestTime);
        }

        miseAJour();

    }

    public void onStart(View view) {

        startEtape(tabata, indexEtape);
    }

    // Mettre en pause le compteur
    public void onPause(View view) {
        if (timer != null) {
            timer.cancel(); // Arrete le CountDownTimer
        }
    }

    // Mise à jour graphique
    private void miseAJour() {
        String etapeName;
        // position dans une sequence
        // si modPos = 0, alors le dernier element de la sequence = (repos long)
        int modPos = indexEtape % (cycleNb*2 +1);

        if (indexEtape == 0 ) {
            etapeName = "Préparation";
        } else if (modPos == 0) {
            etapeName = "Repos long";
        } else if ( modPos % 2 == 0 ) {
            etapeName = "Repos";
        } else {
            etapeName = "Travail";
        }

        etapeNameValue.setText(etapeName);

        // timer de l'etape
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

        updatedTime = workTime;
        miseAJour();

    }

    public void MenuButton(View view) {
        // création de l'intention au systeme
        Intent menu = new Intent(this, TabataActivity.class);
        startActivity(menu);
    }

    public void startEtape(final ArrayList tabata, final int etape){
        timer = new CountDownTimer((int)tabata.get(etape), 10) {

            public void onTick(long millisUntilFinished) {
                updatedTime = millisUntilFinished;
                miseAJour();
            }

            public void onFinish() {
                updatedTime = 0;
                miseAJour();
                if (etape<tabata.size()-1) {
                    indexEtape = etape+1;
                    startEtape(tabata, indexEtape);
                }
            }
        }.start();
    }
}
