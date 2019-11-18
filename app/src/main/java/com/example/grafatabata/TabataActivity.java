package com.example.grafatabata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.grafatabata.db.DatabaseClient;
import com.example.grafatabata.db.Tabata;

import java.util.List;

public class TabataActivity extends AppCompatActivity {
    private DatabaseClient mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabata);

        mDb = DatabaseClient.getInstance(getApplicationContext());

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
////////////Mettre à jour l'adapter avec la liste de taches
            @Override
            protected void onPostExecute(List<Tabata> tabata) {
                super.onPostExecute(tabata);
                for (Tabata t : tabata)
                {
                       LinearLayout linearlist = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_listseance, null);
                       TextView tabata_list_name = (TextView) linearlist.findViewById(R.id.tabata_list_name);
                       TextView tabata_list_work_time = (TextView)linearlist.findViewById(R.id.tabata_list_work_time);
                       TextView tabata_list_rest_time = (TextView)linearlist.findViewById(R.id.tabata_list_rest_time);
                       TextView tabata_list_cylce_nb = (TextView)linearlist.findViewById(R.id.tabata_list_cylce_nb);
                       TextView tabata_list_tabata_nb = (TextView)linearlist.findViewById(R.id.tabata_list_tabata_nb);
                       tabata_list_name.setText("Exercice" + t.getId());
                       tabata_list_work_time.setText(String.valueOf(t.getWorkTime()/ 1000));
                       tabata_list_rest_time.setText(String.valueOf(t.getRestTime()/1000));
                       tabata_list_cylce_nb.setText(String.valueOf(t.getCycleNb()));
                       tabata_list_tabata_nb.setText(String.valueOf(t.getTabataNb()));
                    ((LinearLayout) findViewById(R.id.list_tabata)).addView(linearlist);

                   }

            }
        }

////////Création d'un objet de type GetTasks et execution de la demande asynchrone
        GetTabata gt = new GetTabata();
        gt.execute();
    }

////Va nous permettre de selectionner directement une séance
    public void playSeance(View view) {
        // création de l'intention au systeme
        Intent play = new Intent(this, SeanceActivity.class);
        Tabata tabata = new Tabata();
        play.putExtra("tabata", tabata);
        startActivity(play);



    }

    // permet de renvoyer sur la CreateActivity
    public void createSeance(View view) {
        // création de l'intention au systeme
        Intent create = new Intent(this, CreateActivity.class);
        startActivity(create);
    }

}
