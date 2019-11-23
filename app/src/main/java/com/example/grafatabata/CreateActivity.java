package com.example.grafatabata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grafatabata.db.DatabaseClient;
import com.example.grafatabata.db.Tabata;




public class CreateActivity extends AppCompatActivity {
////// CONSTANTE
    Tabata tabata = new Tabata();
    int tempsTravail[] = {0,0,0,0,0,0};
    String tabataCycle[] = {"tabata","preparation", "cycle", "travail", "repos", "repos long"};
    TextView afficherTempsTravail[] = {null,null,null,null,null,null};



//////// views
    private LinearLayout createLayout;
    private LinearLayout linearTMP;
    private LinearLayout button;
    private DatabaseClient mDb;
    private Button saveView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);


///////////Récupération du DatabaseClient
            mDb = DatabaseClient.getInstance(getApplicationContext());


/////////Recuperation vue
            createLayout = (LinearLayout) findViewById(R.id.linear);
            button = (LinearLayout) findViewById(R.id.linearButton);


////////// Boucle pour la création de mes lignes workout.
            for (int i = 0; i < tabataCycle.length; i++) {
                linearTMP = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_exercice, null);
                afficherTempsTravail[i] = (TextView) linearTMP.findViewById(R.id.afficherTempsTravail);
                afficherTempsTravail[i].setText(String.valueOf(tempsTravail[i]));
                TextView Categorie = (TextView) linearTMP.findViewById(R.id.categorie);
                Categorie.setText(tabataCycle[i]);
                Categorie.getText().toString();


                ////////////Ajout au linear principal
                //createLayout.addView(linearTMP);
                button.addView(linearTMP);


///////////Recuperation id boutons
                ((Button) linearTMP.findViewById(R.id.ButtonAdd)).setTag(i);
                ((Button) linearTMP.findViewById(R.id.ButtonLess)).setTag(i);
                saveView = findViewById(R.id.SauvegarderCreation);
                // Associer un événement au bouton save
                saveView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        saveTask();

                    }
                });

            }
        }



/////////Sauver en base de données
    private void saveTask() {
        final String nomSeance = ((EditText)findViewById(R.id.nom)).getText().toString();

        class SaveTask extends AsyncTask<Void, Void, Tabata> {

            @Override
            protected Tabata doInBackground(Void... voids) {
                tabata.setName(nomSeance);
                tabata.setTabataNb(tempsTravail[0]);
                tabata.setPrepareTime(tempsTravail[1]*1000);
                tabata.setCycleNb(tempsTravail[2]);
                tabata.setWorkTime(tempsTravail[3] *1000);
                tabata.setRestTime(tempsTravail[4]*1000);
                tabata.setLongRestTime(tempsTravail[5] *1000);
                // adding to database
                mDb.getAppDatabase()
                        .tabataDao()
                        .insert(tabata);

                return tabata;
            }

            @Override
            protected void onPostExecute(Tabata tabata) {
                super.onPostExecute(tabata);

                // Quand la tache est créée, on arrête l'activité AddTaskActivity (on l'enleve de la pile d'activités)
                setResult(RESULT_OK);
                finish();
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveTask st = new SaveTask();
        st.execute();
    }



//// Decrementation du temps de travail
    public void ButtonLess(View view) {
        int index = (int)view.getTag();
        tempsTravail[index] = tempsTravail[index]-1;
        afficherTempsTravail[index].setText(String.valueOf(tempsTravail[index]));
    }



/////// Incrementation du temps de travail
    public void ButtonAdd(View view) {
        int index = (int)view.getTag();
        tempsTravail[index] = tempsTravail[index]+1;
        afficherTempsTravail[index].setText(String.valueOf(tempsTravail[index]));

    }


    // Permet de sauvegarder une creation workout
    public void sauvegarder(View view) {
        finish();
    }

    // Permet de valider la creation et de la jouer dans le Chrono
    public void valider(View view) {
        tabata.setTabataNb(tempsTravail[0]);
        tabata.setPrepareTime(tempsTravail[1]*1000);
        tabata.setCycleNb(tempsTravail[2]);
        tabata.setWorkTime(tempsTravail[3] *1000);
        tabata.setRestTime(tempsTravail[4]*1000);
        tabata.setLongRestTime(tempsTravail[5] *1000);
        Intent goToChrono = new Intent(this, SeanceActivity.class);
        goToChrono.putExtra("tabata",tabata);
        startActivity(goToChrono);

    }


}
