package com.example.azar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etApuesta, etNumeroApuesta;
    Button btnJuego, btnReiniciar;
    TextView tvPuntaje, tvGanancia;
    int numeroAleatorio, puntaje = 0, ganancia = 0, numeroApuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        conectar();

        btnJuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generarNumeroAleatorio();

                numeroApuesta = Integer.parseInt(etNumeroApuesta.getText().toString());

                if(numeroApuesta == numeroAleatorio){
                    puntaje += 500;
                }else{
                    if(numeroApuesta <= numeroAleatorio+5 && numeroApuesta >= numeroAleatorio-5){
                        puntaje += 200;
                    }else if(numeroApuesta <= numeroAleatorio+10 && numeroApuesta >= numeroAleatorio-10){
                        puntaje += 100;
                    }
                    comprobarCombinado();
                    if ((numeroApuesta%100) == (numeroAleatorio%100)){
                        puntaje += 200;
                    }
                }

                tvPuntaje.setText(puntaje+"");
                ganancia = puntaje * 5000;
                tvGanancia.setText(ganancia+"");
            }
        });

        btnReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etApuesta.setText("");
                etNumeroApuesta.setText("");
                btnJuego.setText("Empezar a jugar");
                tvPuntaje.setText("0");
                tvGanancia.setText("0");
                puntaje = 0;
                ganancia = 0;

            }
        });
    }

    private void comprobarCombinado() {
        int primeraNumeroAp = Integer.valueOf(numeroApuesta/100);
        int segundaNumeroAp = Integer.valueOf((numeroApuesta%100)/10);
        int ultimaNumeroAp = numeroApuesta%10;

        int primeraNumeroAlt = Integer.valueOf(numeroAleatorio/100);
        int segundaNumeroAlt = Integer.valueOf((numeroAleatorio%100)/10);
        int ultimaNumeroAlt = numeroAleatorio%10;

        ArrayList<Integer> arrayNumeroAlt = new ArrayList<>();
        arrayNumeroAlt.add(primeraNumeroAlt);
        arrayNumeroAlt.add(segundaNumeroAlt);
        arrayNumeroAlt.add(ultimaNumeroAlt);

        if (arrayNumeroAlt.contains(primeraNumeroAp) &&
                arrayNumeroAlt.contains(segundaNumeroAp) &&
                arrayNumeroAlt.contains(ultimaNumeroAp)){
            puntaje += 300;
        }
    }

    private void generarNumeroAleatorio() {
        numeroAleatorio = (int) Math.floor(Math.random()*(100-999+1)+999);
        btnJuego.setText(numeroAleatorio+"");
    }

    private void conectar() {
        etApuesta = findViewById(R.id.etApuesta);
        etNumeroApuesta = findViewById(R.id.etNumeroApuesta);
        btnJuego = findViewById(R.id.btnJuego);
        btnReiniciar = findViewById(R.id.btnReiniciar);
        tvPuntaje = findViewById(R.id.tvPuntaje);
        tvGanancia = findViewById(R.id.tvGanancia);
    }
}