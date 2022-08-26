package com.example.intentos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnP1, btnP2, btnP3;
    EditText etNombre, etApellido, etTelefono;
    String strNombre, strApellido, strTelefono;
    ArrayList<String> informacion = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        conectar();
        String Mistery = "Despues del ultimo no hay nadie";

        //Implementar Intent
        btnP1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent IP1 = new Intent(getApplicationContext(), Pantalla1.class);
                IP1.putExtra("mensaje", Mistery);
                startActivity(IP1);
            }
        });

        btnP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent IP2 = new Intent(getApplicationContext(), Pantalla2.class);
                strNombre = etNombre.getText().toString();
                informacion.add(strNombre);
                strApellido = etApellido.getText().toString();
                informacion.add(strApellido);
                strTelefono = etTelefono.getText().toString();
                informacion.add(strTelefono);
                IP2.putExtra("informacion", informacion);
                startActivity(IP2);
            }
        });

        btnP3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent IP3 = new Intent(getApplicationContext(), Pantalla3.class);
                startActivity(IP3);
            }
        });

    }

    private void conectar() {
        btnP1 = findViewById(R.id.btnP1);
        btnP2 = findViewById(R.id.btnP2);
        btnP3 = findViewById(R.id.btnP3);
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etTelefono = findViewById(R.id.etTelefono);
    }
}