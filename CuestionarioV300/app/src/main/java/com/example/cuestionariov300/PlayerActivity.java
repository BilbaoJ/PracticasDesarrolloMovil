package com.example.cuestionariov300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class PlayerActivity extends AppCompatActivity {

    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10,
            btn11, btn12, btn13, btn14, btn15, btn16, btn17, btn18, btn19, btn20,
            btnOpc1, btnOpc2, btnOpc3, btnActual, btnReiniciar, btnRanking, btnEmpezarJuego;
    TextView tvPregunta;
    EditText etJugador;
    ArrayList<Button> listaBotones = new ArrayList<>();
    ArrayList<Pregunta> listaPreguntas = new ArrayList<>();
    Boolean juegoEmpezado = false;
    Integer puntaje = 0, penalizaciones = 0;
    Random random = new Random();
    Pregunta preguntaActual;
    String respuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        conectar();
        llenarListaBotones();
        llenarListaPreguntas();
        bloquearBotones(false);

        for (Button btn: listaBotones) {
            asignarPregunta(btn);
        }

        comprobarRespuesta(btnOpc1);
        comprobarRespuesta(btnOpc2);
        comprobarRespuesta(btnOpc3);

        btnEmpezarJuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etJugador.getText().toString().isEmpty()){
                    resetear();
                    bloquearBotones(true);
                    juegoEmpezado = true;
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Por favor escriba su nombre para empezar",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btnReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetear();
            }
        });

        btnRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ranking = new Intent(getApplicationContext(), RankingActivity.class);
                startActivity(ranking);
            }
        });

    }

    private void guardarJugador(String nombre, int puntaje){
        DBHelper helper = new DBHelper(this, "Cuestionario", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();

        try {

            ContentValues cv = new ContentValues();
            cv.put("nombre", nombre);
            cv.put("puntaje", puntaje);
            db.insert("Jugadores", null, cv);

            Toast.makeText(this, "Se guardó tu puntaje",
                    Toast.LENGTH_LONG).show();

        }catch (Exception e){

            Toast.makeText(this, "Ocurrió un error: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
        db.close();
    }

    private void comprobarRespuesta(Button btn){

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Button botonRespuesta = findViewById(view.getId());
                respuesta = botonRespuesta.getText().toString();

                if (respuesta.equals(preguntaActual.getOpcionCorrecta())){

                    listaPreguntas.remove(preguntaActual);
                    btnActual.setBackgroundColor(Color.GREEN);
                    puntaje += preguntaActual.getPuntaje();
                    Toast.makeText(getApplicationContext(), "Respuesta correcta",
                            Toast.LENGTH_SHORT).show();

                    if (listaBotones.isEmpty()){
                        puntaje -= penalizaciones;
                        penalizaciones = 0;
                        Toast.makeText(getApplicationContext(),
                                "Terminaste, tu puntaje es: " + puntaje, Toast.LENGTH_LONG).show();
                        String nombreJugador = etJugador.getText().toString();
                        guardarJugador(nombreJugador, puntaje);
                        bloquearBotones(false);
                        btnEmpezarJuego.setEnabled(false);
                        juegoEmpezado = false;
                    }

                }else {
                    Toast.makeText(getApplicationContext(), "Pierdes tus puntos, vuelve a empezar", Toast.LENGTH_LONG).show();
                    penalizaciones += 15;
                    resetear();
                }

            }
        });
    }

    private void asignarPregunta(Button btn){

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    btnActual = findViewById(view.getId());
                    btnActual.setEnabled(false);
                    listaBotones.remove(btnActual);

                    int preguntaRandom = random.nextInt(listaPreguntas.size());
                    preguntaActual = listaPreguntas.get(preguntaRandom);
                    tvPregunta.setText(preguntaActual.getEnunciado());

                    ArrayList<String> opcionesRespuesta = new ArrayList<>();
                    opcionesRespuesta.add(preguntaActual.opcion1);
                    opcionesRespuesta.add(preguntaActual.opcion2);
                    opcionesRespuesta.add(preguntaActual.opcionCorrecta);

                    ArrayList<Button> opcionesBotones = new ArrayList<>();
                    opcionesBotones.add(btnOpc1);
                    opcionesBotones.add(btnOpc2);
                    opcionesBotones.add(btnOpc3);

                    for (int i = 3; i > 0; i--) {
                        int boton = random.nextInt(i);
                        int opcion = random.nextInt(i);
                        Button botonOpcion = opcionesBotones.get(boton);
                        String respuesta = opcionesRespuesta.get(opcion);
                        botonOpcion.setText(respuesta);
                        opcionesBotones.remove(botonOpcion);
                        opcionesRespuesta.remove(respuesta);
                    }

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Ocurrió un error asignando " + e.getMessage() +
                            "Size" + listaPreguntas.size(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void bloquearBotones(Boolean activar){
        for (Button boton: listaBotones) {
            boton.setEnabled(activar);
        }
        if(activar){
            tvPregunta.setVisibility(View.VISIBLE);
            btnOpc1.setVisibility(View.VISIBLE);
            btnOpc2.setVisibility(View.VISIBLE);
            btnOpc3.setVisibility(View.VISIBLE);
        }else {
            tvPregunta.setVisibility(View.GONE);
            btnOpc1.setVisibility(View.GONE);
            btnOpc2.setVisibility(View.GONE);
            btnOpc3.setVisibility(View.GONE);
        }
    }

    private void resetear() {
        llenarListaBotones();
        llenarListaPreguntas();
        tvPregunta.setText("Pregunta");
        btnOpc1.setText("A");
        btnOpc2.setText("B");
        btnOpc3.setText("C");
        btnEmpezarJuego.setEnabled(true);
        puntaje = 0;
        for (Button btn: listaBotones) {
            btn.setEnabled(juegoEmpezado);
            btn.setBackgroundColor(Color.parseColor("#FFBB86FC"));
        }
    }

    private void llenarListaPreguntas(){

        DBHelper helper = new DBHelper(this, "Cuestionario", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();

        try {
            listaPreguntas.clear();
            String sql = "select * from Preguntas";
            Cursor c = db.rawQuery(sql, null);
            if (c.moveToFirst()){
                do {
                    String enunciado = c.getString(1);
                    String opcion1 = c.getString(2);
                    String opcion2 = c.getString(3);
                    String opcionCorrecta = c.getString(4);
                    int puntaje = c.getInt(5);

                    Pregunta nuevaPregunta = new Pregunta(enunciado, opcion1, opcion2,
                            opcionCorrecta, puntaje);
                    listaPreguntas.add(nuevaPregunta);

                }while (c.moveToNext());
            }

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Ocurrió un error llenando la lista" +
                    e.getMessage(), Toast.LENGTH_LONG).show();
        }
        db.close();
    }

    private void llenarListaBotones() {
        listaBotones.clear();
        listaBotones.add(btn1);
        listaBotones.add(btn2);
        listaBotones.add(btn3);
        listaBotones.add(btn4);
        listaBotones.add(btn5);
        listaBotones.add(btn6);
        listaBotones.add(btn7);
        listaBotones.add(btn8);
        listaBotones.add(btn9);
        listaBotones.add(btn10);
        listaBotones.add(btn11);
        listaBotones.add(btn12);
        listaBotones.add(btn13);
        listaBotones.add(btn14);
        listaBotones.add(btn15);
        listaBotones.add(btn16);
        listaBotones.add(btn17);
        listaBotones.add(btn18);
        listaBotones.add(btn19);
        listaBotones.add(btn20);

    }

    private void conectar() {
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btn10 = findViewById(R.id.btn10);
        btn11 = findViewById(R.id.btn11);
        btn12 = findViewById(R.id.btn12);
        btn13 = findViewById(R.id.btn13);
        btn14 = findViewById(R.id.btn14);
        btn15 = findViewById(R.id.btn15);
        btn16 = findViewById(R.id.btn16);
        btn17 = findViewById(R.id.btn17);
        btn18 = findViewById(R.id.btn18);
        btn19 = findViewById(R.id.btn19);
        btn20 = findViewById(R.id.btn20);
        btnOpc1 = findViewById(R.id.btnOpc1);
        btnOpc2 = findViewById(R.id.btnOpc2);
        btnOpc3 = findViewById(R.id.btnOpc3);
        btnReiniciar = findViewById(R.id.btnReiniciar);
        btnRanking = findViewById(R.id.btnRanking);
        tvPregunta = findViewById(R.id.tvPregunta);
        etJugador = findViewById(R.id.etJugador);
        btnEmpezarJuego = findViewById(R.id.btnEmpezarJuego);
    }
}