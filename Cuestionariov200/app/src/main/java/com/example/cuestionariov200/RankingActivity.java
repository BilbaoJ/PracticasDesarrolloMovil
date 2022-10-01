package com.example.cuestionariov200;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

public class RankingActivity extends AppCompatActivity {

    ListView lvRanking;
    ArrayList<String> jugadores = new ArrayList<>();
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        lvRanking = findViewById(R.id.lvRanking);

        Bundle paqueteInfo = getIntent().getExtras();

        if(paqueteInfo != null)
        {
            jugadores = paqueteInfo.getStringArrayList("jugadores");
        }


        adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, jugadores);
        lvRanking.setAdapter(adapter);

    }
}