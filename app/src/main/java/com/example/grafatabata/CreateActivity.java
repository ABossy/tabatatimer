package com.example.grafatabata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CreateActivity extends AppCompatActivity {
    // création de la séance
    public static final String TABLE_KEY = "table_key";
    private CreateActivity seance;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);


    }



    public void sauvegarder(View view) {
    }

    public void valider(View view) {
    }
}
