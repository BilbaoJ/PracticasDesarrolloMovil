package com.example.intentos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Pantalla1 extends AppCompatActivity {

    TextView tvInformacion;
    String Mistery2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla1);

        tvInformacion = findViewById(R.id.tvInformacion);

        Bundle paqueteInfo = getIntent().getExtras();

        if(paqueteInfo != null)
        {
            Mistery2 = paqueteInfo.getString("mensaje");
            tvInformacion.setText(Mistery2);
        }
    }
}