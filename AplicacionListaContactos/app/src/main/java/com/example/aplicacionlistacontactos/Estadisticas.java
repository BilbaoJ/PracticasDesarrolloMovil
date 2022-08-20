package com.example.aplicacionlistacontactos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Estadisticas extends AppCompatActivity {

    TextView tvEstaturaProm;
    ListView lvMasAltos;
    ArrayList<String> clientesAltos = new ArrayList<>();
    ArrayList<String> clientes = new ArrayList<>();
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);
        conectar();

        Bundle paqueteInfo = getIntent().getExtras();
        if (paqueteInfo != null){
            int res = paqueteInfo.getInt("promedio");
            tvEstaturaProm.setText(String.valueOf(res));
        }
    }

    private void conectar() {

        tvEstaturaProm = findViewById(R.id.tvEstaturaProm);
        lvMasAltos = findViewById(R.id.lvClientesAltos);

    }
}