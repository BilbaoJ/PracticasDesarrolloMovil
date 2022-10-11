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
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);
        conectar();

        Bundle paqueteInfo = getIntent().getExtras();
        if (paqueteInfo != null){
            int respuesta = paqueteInfo.getInt("promedio");
            String estaturaPromedio = respuesta + "cm";
            tvEstaturaProm.setText(estaturaPromedio);

            clientesAltos = paqueteInfo.getStringArrayList("contactosAltos");

            adapter = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_list_item_1, clientesAltos);
            lvMasAltos.setAdapter(adapter);
        }
    }

    private void conectar() {

        tvEstaturaProm = findViewById(R.id.tvEstaturaProm);
        lvMasAltos = findViewById(R.id.lvClientesAltos);

    }
}