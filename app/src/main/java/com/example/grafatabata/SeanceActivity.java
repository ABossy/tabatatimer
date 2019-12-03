package com.example.grafatabata;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.media.MediaPlayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.example.grafatabata.db.Tabata;

import java.util.ArrayList;


public class SeanceActivity extends AppCompatActivity {

    // VIEW
    private Button startButton;
    private Button pauseButton;
    private TextView etapeNameValue;
    private TextView timerValue;
    private TextView cycleValue;
    private TextView tabataValue;
    private Tabata tabata;
    private RelativeLayout chrono;
    private MediaPlayer mediaPlayer;
    // initialisation de l'etat du chrono.
    private int playState = -1;




    // DATA
    private long updatedTime ;
    private CountDownTimer timer;
    private int indexCycle = 0;
    private int indexTabata = 0;
    private int etapeCourrante = -1;
    ArrayList tabataArr = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seance);

        tabata = new Tabata();

////////RECUPERATION DES VUES
        etapeNameValue = (TextView) findViewById(R.id.etapeNameValue);
        timerValue = (TextView) findViewById(R.id.timerValue);
        startButton = (Button) findViewById(R.id.startButton);
        pauseButton = (Button) findViewById(R.id.pauseButton);
        cycleValue = (TextView) findViewById(R.id.cycleValue);
        tabataValue = (TextView) findViewById(R.id.tabataValue);
        chrono = (RelativeLayout) findViewById(R.id.activity_main);
///////On cache le bouton pause
        pauseButton.setAlpha(0f);

////////RECUPERATION DES INTENTIONS

        tabata = getIntent().getExtras().getParcelable("tabata");

        cycleValue.setText("Cycle    "+ tabata.getCycleNb());
        cycleValue.getText().toString();

        tabataValue.setText("Tabata    "+ tabata.getTabataNb());
        tabataValue.getText().toString();
        updatedTime = tabata.getPrepareTime();

////////INITIALISATION DE MA SEQUENCE
        tabataArr.add(tabata.getPrepareTime());
        for(int i =0; i<tabata.getTabataNb();i++){
            for(int j =0; j<tabata.getCycleNb();j++){
                tabataArr.add(tabata.getWorkTime());
                tabataArr.add(tabata.getRestTime());
            }
            tabataArr.add(tabata.getLongRestTime());
        }

        miseAJour();
        if(savedInstanceState !=null){
            this.etapeCourrante = savedInstanceState.getInt("etape courante");
            tabata.setIndexEtape(etapeCourrante);
            this.updatedTime = savedInstanceState.getLong("time");
            onStart(new View(this));
        }

    }

////////// OnClick du bouton start
    public void onStart(View view) {
        if (playState==-1){
            playState = 1;
            startButton.setAlpha(0f);
            pauseButton.setAlpha(1f);
            startEtape(tabataArr, tabata.getIndexEtape(), updatedTime);
        }
    }

//////// Mettre en pause le compteur
    public void onPause(View view) {
        if (playState==1) {
            playState = 0;
            pauseButton.setText("Resume");

            if (timer != null) {
                timer.cancel(); // Arrete le CountDownTimer
            }
            return;
        }
        if (playState==0) {
            playState = 1;
            pauseButton.setText("Pause");
            startEtape(tabataArr, tabata.getIndexEtape(), updatedTime);
            return;
        }
    }

/////// Acceder au dossier raw
    private int getRawResIdByName(String id) {
        String pkgName = this.getPackageName();
        // Return 0 if not found.
        int resID = this.getResources().getIdentifier(id, "raw", pkgName);
        return resID;
    }

/////// Gestion de la partie Son
    private void joueSon(String id) {
        if (this.mediaPlayer!= null) {
            this.mediaPlayer.stop();
            this.mediaPlayer.release();
            this.mediaPlayer = null;
        }
        this.mediaPlayer=   MediaPlayer.create(this, this.getRawResIdByName(id));
        this.mediaPlayer.start();
    }

/////// Mise à jour graphique
    private void miseAJour() {
        boolean joueSon = false;
        if (this.etapeCourrante != tabata.getIndexEtape()){
            this.etapeCourrante = tabata.getIndexEtape();
            joueSon = true;
        }
        String etapeName;
        // position dans une sequence
        // si modPos = 0, alors le dernier element de la sequence = (repos long)
        int modPos = tabata.getIndexEtape() % (tabata.getCycleNb()*2 +1);
        Log.d( "r", String.valueOf(Math.floor(((tabata.getIndexEtape()-1) % (tabata.getCycleNb()*2 +1)) / tabata.getCycleNb())));
        this.indexTabata = (int) Math.floor((tabata.getIndexEtape()-1) / (tabata.getCycleNb()*2 +1));
        this.indexCycle = (int) Math.floor(((tabata.getIndexEtape()-1) % (tabata.getCycleNb()*2 +1)) / 2);
        if (tabata.getIndexEtape() == 0 ) {
            etapeName = "Préparation";
            chrono.setBackgroundResource(R.drawable.create);
        } else if (modPos == 0) {
            etapeName = "Repos long";
            chrono.setBackgroundResource(R.drawable.longrest);
            if(joueSon) this.joueSon("longrest");
        } else if ( modPos % 2 == 0 ) {
            etapeName = "Repos";
            chrono.setBackgroundResource(R.drawable.rest);
            if(joueSon) this.joueSon("repos");
        } else {
            etapeName = "Travail";
            chrono.setBackgroundResource(R.drawable.background);
            if(joueSon) this.joueSon("work");

        }

        etapeNameValue.setText(etapeName);
        cycleValue.setText("Cycle    "+ String.valueOf(tabata.getCycleNb() - this.indexCycle));
        tabataValue.setText("Tabata    "+ String.valueOf(tabata.getTabataNb() - this.indexTabata));


/////////// timer de l'etape
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
        startButton.setAlpha(1f);
        pauseButton.setAlpha(0f);
        playState = -1;
        updatedTime = tabata.getPrepareTime();

        // Mise à jour graphique
        tabata.setIndexEtape(0);
        miseAJour();

    }

    public void MenuButton(View view) {
        // création de l'intention au systeme
        Intent menu = new Intent(this, TabataActivity.class);
        startActivity(menu);
    }

    public void startEtape(final ArrayList tabataArr, final int etape, final long remainingTime){
        long time = (int)tabataArr.get(etape);
        if (remainingTime > 0 && remainingTime < time ) {
            time = remainingTime;
        }
        timer = new CountDownTimer(time, 10) {

            public void onTick(long millisUntilFinished) {
                updatedTime = millisUntilFinished;
                miseAJour();
            }

            public void onFinish() {
                updatedTime = 0;
                miseAJour();
                if (etape<tabataArr.size()-1) {
                    tabata.setIndexEtape(etape+1);
                    startEtape(tabataArr, tabata.getIndexEtape(), 0);
                }
            }
        }.start();
    }

/// Permet de conserver les donner de l'etape chrono
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
      super.onSaveInstanceState(outState);
        outState.putInt("etape courante", etapeCourrante);
        timer.cancel();
        outState.putLong("time", updatedTime);
    }

////Gestion du retour sur la home page (stop de la seance)
    @Override
    public void onBackPressed(){
        if (this.mediaPlayer != null) {
            this.mediaPlayer.stop();
            this.mediaPlayer.release();
            this.mediaPlayer = null;
        }

        if(timer != null){
            timer.cancel();
        }
        timer = null;
        finish();
    }
}
