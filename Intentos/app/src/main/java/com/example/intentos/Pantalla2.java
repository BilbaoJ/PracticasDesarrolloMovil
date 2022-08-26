package com.example.intentos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class Pantalla2 extends AppCompatActivity {
    TextView tvInfo;
    ArrayList<String> informacion = new ArrayList<>();
    String infoCompleta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panalla2);

        tvInfo = findViewById(R.id.tvInfo);

        Bundle paqueteInfo = getIntent().getExtras();

        if(paqueteInfo != null)
        {
            informacion = paqueteInfo.getStringArrayList("informacion");
            infoCompleta = informacion.get(0) + " " + informacion.get(1) + " " + informacion.get(2);
            tvInfo.setText(infoCompleta);
        }
    }
}