package com.example.cuestionariov200;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Random;

public class PlayerActivity extends AppCompatActivity {

    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10,
            btn11, btn12, btn13, btn14, btn15, btn16, btn17, btn18, btn19, btn20,
            btnOpc1, btnOpc2, btnOpc3, btnActual, btnReiniciar, btnRanking, btnEmpezarJuego;
    TextView tvPregunta;
    EditText etJugador;
    ArrayList<Pregunta> listaPreguntas = new ArrayList<>();
    ArrayList<Button> listaBotones = new ArrayList<>();
    Pregunta preguntaActual;
    String respuesta;
    Integer puntaje = 0;
    Integer penalizaciones = 0;
    Random random = new Random();
    ArchivoPlanoPreguntas archivoPlano = new ArchivoPlanoPreguntas(this);
    ArrayList<Jugador> jugadores = new ArrayList<>();
    ArrayList<String> listajugadores = new ArrayList<>();
    Boolean juegoEmpezado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        conectar();
        escribirPreguntasPorDefecto();
        llenarListaBotones();
        llenarListaPreguntas();
        cargarRanking();
        bloquearBotones(false);

        Toast.makeText(getApplicationContext(),
                "Por favor escriba su nombre para guardar el puntaje",
                Toast.LENGTH_LONG).show();

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

                Collections.sort(jugadores, new Comparator<Jugador>() {
                    @Override
                    public int compare(Jugador jugador1, Jugador jugador2) {
                        return jugador1.getPuntaje().compareTo(jugador2.getPuntaje());
                    }
                });

                Collections.reverse(jugadores);

                listajugadores.clear();

                for (Jugador j: jugadores) {
                    listajugadores.add(j.toString());
                }

                ranking.putExtra("jugadores", listajugadores);
                startActivity(ranking);
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

    private void cargarRanking(){

        SharedPreferences sp = getSharedPreferences("Ranking", Context.MODE_PRIVATE);
        jugadores.clear();
        Map<String, ?> SPJugadores = sp.getAll();
        for (Map.Entry<String, ?> jugador: SPJugadores.entrySet()) {
            String nombre = jugador.getKey();
            Integer puntaje = (Integer) jugador.getValue();
            Jugador nuevoJugador = new Jugador(nombre, puntaje);
            jugadores.add(nuevoJugador);
        }

    }

    private void guardarPuntaje(){
        String nombreJugador = etJugador.getText().toString();
        SharedPreferences sp = getSharedPreferences("Ranking", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(nombreJugador, puntaje).apply();
        cargarRanking();
    }

    private void llenarListaPreguntas(){
        try {

            listaPreguntas.clear();
            ArrayList<String> lecturas = archivoPlano.Leer();

            for (String linea: lecturas) {
                String[] lineaPregunta = linea.split(";");
                String enunciado = lineaPregunta[0];
                String opcion1 = lineaPregunta[1];
                String opcion2 = lineaPregunta[2];
                String opcionCorrecta = lineaPregunta[3];
                int puntaje = Integer.parseInt(lineaPregunta[4]);
                Pregunta preguntaCompleta = new Pregunta(enunciado, opcion1, opcion2, opcionCorrecta, puntaje);
                listaPreguntas.add(preguntaCompleta);
            }

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    private void escribirPreguntasPorDefecto(){
        try {
            if (archivoPlano.Leer().size() == 0){
                String texto = "¿Cuál es el país con menos habitantes del mundo?;Venezuela;China;Vaticano;10\n" +
                        "¿En que año llegó Cristobal Colón a América?;1429;1420;1492;30\n" +
                        "¿Cuál es el planeta más grande de nuestro sistema solar?;Mercurio;Saturno;Júpiter;30\n" +
                        "¿Cuantos huesos tiene el cuerpo humano?;150;50;206;20\n" +
                        "¿Cual es el país más poblado del mundo?;Estados Unidos;Rusia;China;20\n" +
                        "¿Cuando terminó la II guerra mundial?;1935;1940;1945;30\n" +
                        "¿Cual es el libro sagrado de los musulmanes?;Talmud;Biblia;Corán;10\n" +
                        "¿Quien escribió La Odisea?;Virgilio;Cervantes;Homero;20\n" +
                        "¿Quién pintó el famoso cuadro La última cena?;Rembrandt;Velazquez;Leonardo da Vinci;10\n" +
                        "¿Quién escribió Cien años de soledad?;Camilo José Cela;Mario Vargas Llosa;Gabriel García Márquez;10\n" +
                        "¿Cuál es el elemento químico más abundante en la corteza terrestre?;Nitrógeno;Carbono;Oxígeno;10\n" +
                        "¿Cuál fue el primer metal que emplearon los seres humanos?;Bronce;Acero;Cobre;20\n" +
                        "¿Cuál es el primero de los números primos?;0;1;2;10\n" +
                        "¿Cuál es el primer elemento de la Tabla periódica?;Helio;Argón;Hidrógeno;30\n" +
                        "¿Cuántos litros de sangre tiene una persona adulta?;Tiene entre 2 y 4 litros;Tiene 7 litros;Tiene entre 4 y 6 litros;30\n" +
                        "¿Cuántos jugadores por equipo participan en un partido de voleibol?;4 jugadores;5 jugadores;6 jugadores;20\n" +
                        "¿Cuánto tiempo tarda la luz del Sol en llegar a la Tierra?\";12 minutos;8 segundos;8 minutos;20\n" +
                        "¿Cuál es el grupo de palabras escritas correctamente?;Metamorfosis, extrangero, dibersidad, equilibrio;Metamórfosis, eztranjero, divérsidad, ecuilibrio;Metamorfosis, extranjero, diversidad, equilibrio;10\n" +
                        "¿Cuáles son los nombres de los tres reyes magos?;Gaspar, Nicolas y Nataniel;Abraham, Noé y Moises;Gaspar, Melchor y Baltazar;10\n" +
                        "¿Cuál es la religión monoteísta que cuenta con el mayor número de adeptos en el mundo?;Islamismo;Hinduismo;Cristianismo;10\n" +
                        "¿Cuál es la capital de Colombia?;Medellín;Cali;Bogotá;10\n";
                archivoPlano.Escribir(texto);
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void comprobarRespuesta(Button btn){

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Button botonRespuesta = findViewById(view.getId());
                respuesta = botonRespuesta.getText().toString();

                if (respuesta.equals(preguntaActual.getOpcionCorrecta())){

                    btnActual.setBackgroundColor(Color.GREEN);
                    puntaje += preguntaActual.getPuntaje();
                    Toast.makeText(getApplicationContext(), "Respuesta correcta",
                            Toast.LENGTH_SHORT).show();

                    if (listaBotones.isEmpty()){
                        puntaje -= penalizaciones;
                        penalizaciones = 0;
                        Toast.makeText(getApplicationContext(),
                                "Terminaste, tu puntaje es: " + puntaje, Toast.LENGTH_LONG).show();
                        guardarPuntaje();
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
                    listaPreguntas.remove(preguntaActual);
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
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
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