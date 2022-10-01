package com.example.presistenciadedatos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SharedPreferencesActivity extends AppCompatActivity {

    Button btnGuardar;
    TextView tvSalida;
    EditText etValor, etEstatura, etEdad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla2);
        conectar();


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("Archivo", Context.MODE_PRIVATE);

                String valor = etValor.getText().toString();
                Float estatura = Float.valueOf(etEstatura.getText().toString());
                Integer edad = Integer.valueOf(etEdad.getText().toString());

                SharedPreferences.Editor editor = sp.edit();

                editor.putString("Nombre", valor).apply();
                editor.putFloat("Estatura", estatura).apply();
                editor.putInt("Edad", edad).apply();

                String nombre = sp.getString("Nombre", valor);

                Toast.makeText(getApplicationContext(), nombre, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void conectar() {
        btnGuardar = findViewById(R.id.btnGuardar);
        tvSalida = findViewById(R.id.tvSalida);
        etValor = findViewById(R.id.etValor);
        etEstatura = findViewById(R.id.etEstatura);
        etEdad = findViewById(R.id.etEdad);
    }
}