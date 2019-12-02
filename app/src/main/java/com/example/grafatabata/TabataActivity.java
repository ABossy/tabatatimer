package com.example.grafatabata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.grafatabata.db.DatabaseClient;
import com.example.grafatabata.db.Tabata;
import java.util.List;

import android.util.Log;

public class TabataActivity extends AppCompatActivity {

    private DatabaseClient mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabata);

        mDb = DatabaseClient.getInstance(getApplicationContext());
        getTabata();


    }

    @Override
    protected void onResume() {
        super.onResume();
        getTabata();
    }

    //////Classe asynchrone permettant de récupérer des taches et de mettre à jour le listView de l'activité
    private void getTabata() {
        class GetTabata extends AsyncTask<Void, Void, List<Tabata>> {

            @Override
            protected List<Tabata> doInBackground(Void... voids) {

                List<Tabata> tabataList = mDb.getAppDatabase()
                        .tabataDao()
                        .getAll();
                return tabataList;
            }
////////////Mettre à jour la vue avec la liste des seances
            @Override
            protected void onPostExecute(List<Tabata> tabata) {
                super.onPostExecute(tabata);
                ((LinearLayout) findViewById(R.id.list_tabata)).removeAllViews();
                for (Tabata t : tabata)
                {
                    LinearLayout linearlist = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_listseance, null);
                    LinearLayout idSeance = (LinearLayout)linearlist.findViewById(R.id.jouer);
                    idSeance.setTag(t.getId());
                    ((TextView) linearlist.findViewById(R.id.tabata_list_name)).setText(t.getName());
                    ((TextView)linearlist.findViewById(R.id.tabata_list_tabata_nb)).setText("Séquence: "+t.getTabataNb());
                    ((TextView)linearlist.findViewById(R.id.tabata_list_cylce_nb)).setText("Cycle: "+ t.getCycleNb());
                    ((TextView) linearlist.findViewById(R.id.tabata_prepare_time)).setText("Préparation: " + t.getPrepareTime()/1000 + "sec");
                    ((TextView)linearlist.findViewById(R.id.tabata_list_work_time)).setText("Travail: " + t.getWorkTime()/1000 + "sec");
                    ((TextView)linearlist.findViewById(R.id.tabata_list_rest_time)).setText("Repos: " + t.getRestTime()/1000 + "sec");
                    ((TextView)linearlist.findViewById(R.id.tabata_list_long_rest_time)).setText("Repos long: "+ t.getLongRestTime()/1000 + "sec");
                    ((ImageButton)linearlist.findViewById(R.id.removeButton)).setTag(t.getId());
                    ((LinearLayout) findViewById(R.id.list_tabata)).addView(linearlist);

                }

            }
        }

////////Création d'un objet de type GetTasks et execution de la demande asynchrone
        GetTabata gt = new GetTabata();
        gt.execute();
    }

/////PERMET DE RENVOYER sur la CreateActivity
    public void createSeance(View view) {
        // création de l'intention au systeme
        Intent create = new Intent(this, CreateActivity.class);
        startActivity(create);
    }

/////PERMET DE JOUER UNE SEANCE DE LA LISTE EN FONCTION DE SON ID
    public void findById(final long id, final String mode){
        class FindById extends AsyncTask<Void, Void, Tabata>{

            @Override
            protected Tabata doInBackground(Void... voids) {
                Tabata tabata = mDb.getAppDatabase()
                        .tabataDao()
                        .findById(id);

                return tabata;
            }

            @Override
            protected void onPostExecute(Tabata tabata) {
                super.onPostExecute(tabata);
                if (mode == "start"){

                    startTimer(tabata);
                } else if(mode == "update"){

                    updateTimer(tabata);
                }
            }
        }
        FindById findbyid = new FindById();
        findbyid.execute();
    }

    private void updateTimer(Tabata tabata) {
        Intent update = new Intent(this, CreateActivity.class);
        update.putExtra("tabata", tabata);
        startActivity(update);
    }

    /////////Va nous permettre de selectionner directement une séance via son id dans la liste
    public void playSeance(View view) {
        findById(Long.parseLong(view.getTag().toString()), "start");

    }

    public void onUpdate(View view){
        findById(Long.parseLong(view.getTag().toString()),"update");
    }

    private void startTimer(Tabata tabata) {
        Intent play = new Intent(this, SeanceActivity.class);
        play.putExtra("tabata", tabata);
        Log.d("t1", String.valueOf(tabata.getTabataNb()));
        startActivity(play);
    }

/////////////GESTION DE LA SUPPRESSION DE LA LISTE
    private void removeTabata(final long id) {
        class RemoveTabata extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                mDb.getAppDatabase()
                        .tabataDao()
                        .delete(id);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                getTabata();
            }
        }
        RemoveTabata removeTabata = new RemoveTabata();
        removeTabata.execute();
    }

/////ON Click qui permet de supprimer la seance de la liste.
    public void onRemove(View view) {
        removeTabata(Long.parseLong(view.getTag().toString()));
        Log.d("ii", view.getTag().toString());
    }


}
