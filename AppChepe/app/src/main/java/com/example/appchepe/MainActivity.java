package com.example.appchepe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    Button btnValidar;
    EditText etPlaca;
    TextView tvMensaje;
    ArrayList<Integer> arrayNumeros = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        conectar();

        btnValidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                arrayNumeros.clear();

                int numeroPlaca = Integer.parseInt(etPlaca.getText().toString());

                descomponerNumero(numeroPlaca);

                int numeroMayor = encontrarMayor();

                Toast.makeText(getApplicationContext(), "Numero mayor "+ numeroMayor, Toast.LENGTH_LONG).show();

                int porcentaje = validarCalidad(numeroPlaca, numeroMayor);

                String calidad = "";

                if (porcentaje > 75){
                    btnValidar.setBackgroundColor(Color.GREEN);
                    calidad = "buena";
                }else if(porcentaje >= 45){
                    btnValidar.setBackgroundColor(Color.BLUE);
                    calidad = "regular";
                }else{
                    btnValidar.setBackgroundColor(Color.RED);
                    calidad = "mala";
                }

                tvMensaje.setText("El vehiculo es de calidad "+ calidad);

            }
        });
    }

    private Integer validarCalidad(int placa, int numeroMayor) {

        int porcentaje = (placa*100)/numeroMayor;

        return porcentaje;
    }

    private Integer encontrarMayor() {

        Collections.sort(arrayNumeros, Collections.reverseOrder());
        String numeroMayor = String.valueOf(arrayNumeros.get(0)) +
                String.valueOf(arrayNumeros.get(1)) + String.valueOf(arrayNumeros.get(2));

        return Integer.valueOf(numeroMayor);
    }

    private void descomponerNumero(int numeroPlaca) {
        int primeraCifra = Integer.valueOf(numeroPlaca/100);
        arrayNumeros.add(primeraCifra);

        int segundaCifra = Integer.valueOf((numeroPlaca%100)/10);
        arrayNumeros.add(segundaCifra);

        int ultimaCifra = numeroPlaca%10;
        arrayNumeros.add(ultimaCifra);
    }


    private void conectar() {

        btnValidar = findViewById(R.id.btnValidar);
        etPlaca = findViewById(R.id.etPlaca);
        tvMensaje = findViewById(R.id.tvMensaje);
    }
}