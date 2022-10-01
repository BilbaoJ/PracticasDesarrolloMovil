package com.example.presistenciadedatos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnSP, btnAP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        conectar();

        btnSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pantallaSP = new Intent(getApplicationContext(), SharedPreferencesActivity.class);
                startActivity(pantallaSP);
            }
        });

        btnAP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pantallaAP = new Intent(getApplicationContext(), ArchivosPlanosActivity.class);
                startActivity(pantallaAP);
            }
        });
    }

    private void conectar() {
        btnSP = findViewById(R.id.btnSP);
        btnAP = findViewById(R.id.btnAP);
    }
}