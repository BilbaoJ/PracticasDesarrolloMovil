package com.example.mycrud;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class RegistrosActivity extends AppCompatActivity {

    ListView lvContactos;
    ArrayList<String> listaContactos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros);

        lvContactos = findViewById(R.id.lvContactos);
        listaContactos = leerRegistros();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, listaContactos);
        lvContactos.setAdapter(adapter);

    }

    private ArrayList<String> leerRegistros(){

        ArrayList<String> contactos = new ArrayList<>();

        DBHelper helper = new DBHelper(this, "Mis_Contactos", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        String SQL = "select * from Contactos";

        Cursor c = db.rawQuery(SQL, null);
        if (c.moveToFirst()){
            do {
                String registro = c.getInt(0) + " " + c.getString(1) + " " + c.getString(2);
                contactos.add(registro);

            }while (c.moveToNext());
        }

        db.close();

        return contactos;
    }

}