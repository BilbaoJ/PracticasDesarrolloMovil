package com.example.mycrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.BlockingDeque;

public class MainActivity extends AppCompatActivity {

    EditText etNombre, etTelefono;
    Button btnGuardar, btnVerContactos, btnActualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        conectar();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = etNombre.getText().toString();
                String telefono = etTelefono.getText().toString();
                agregarRegistro(nombre, telefono);
            }
        });

        btnVerContactos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent verContactos = new Intent(getApplicationContext(), RegistrosActivity.class);
                startActivity(verContactos);
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actualizarContactos = new Intent(getApplicationContext(), ActualizarActivity.class);
                startActivity(actualizarContactos);
            }
        });


    }

    private void conectar() {
        etNombre = findViewById(R.id.etNombre);
        etTelefono = findViewById(R.id.etTelefono);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnVerContactos = findViewById(R.id.btnVerContactos);
        btnActualizar = findViewById(R.id.btnActualizar);
    }

    private void agregarRegistro(String nombre, String telefono){

        DBHelper helper = new DBHelper(this, "Mis_Contactos", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();

        try {

            ContentValues cv = new ContentValues();
            cv.put("nombre", nombre);
            cv.put("telefono", telefono);
            db.insert("Contactos", null, cv);
            db.close();

            Toast.makeText(getApplicationContext(), "Contacto guardado", Toast.LENGTH_LONG).show();

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
}