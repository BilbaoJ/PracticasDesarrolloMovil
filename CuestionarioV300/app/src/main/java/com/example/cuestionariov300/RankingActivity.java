package com.example.cuestionariov300;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class RankingActivity extends AppCompatActivity {

    ListView lvRanking;
    ArrayList<String> listajugadores = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        lvRanking = findViewById(R.id.lvRanking);
        listajugadores = cargarRanking();

        adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, listajugadores);
        lvRanking.setAdapter(adapter);
    }

    private ArrayList<String> cargarRanking(){
        ArrayList<String> jugadores = new ArrayList<>();

        DBHelper helper = new DBHelper(this, "Cuestionario", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select * from Jugadores order by puntaje desc";

        try {
            Cursor c = db.rawQuery(sql, null);
            if (c.moveToFirst()){
                do {
                    String registro = "Jugador: " + c.getString(1) + " Puntaje: " + c.getInt(2);
                    jugadores.add(registro);

                }while (c.moveToNext());
            }

        }catch (Exception e){
            Toast.makeText(this, "Ocurrió un problema: "+ e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

        db.close();

        return jugadores;
    }
}