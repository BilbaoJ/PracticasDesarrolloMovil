package com.example.cuestionario;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10,
            btn11, btn12, btn13, btn14, btn15, btn16, btn17, btn18, btn19, btn20,
            btnOpc1, btnOpc2, btnOpc3, btnActual;
    TextView tvPregunta;
    ArrayList<Pregunta> listaPreguntas = new ArrayList<>();
    ArrayList<Button> listaBotones = new ArrayList<>();
    Pregunta preguntaActual;
    String respuesta = "";
    Integer puntaje = 0;
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        conectar();
        llenarListaPreguntas();
        llenarListaBotones();

        for (Button btn: listaBotones) {
            asignarPregunta(btn);
        }

        comprobarRespuesta(btnOpc1);
        comprobarRespuesta(btnOpc2);
        comprobarRespuesta(btnOpc3);

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
                    Toast.makeText(getApplicationContext(), "Respuesta correcta", Toast.LENGTH_SHORT).show();
                    if (listaBotones.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Terminaste, tu puntaje es: " + puntaje, Toast.LENGTH_LONG).show();
                        resetear();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Pierdes tus puntos, vuelve a empezar", Toast.LENGTH_LONG).show();
                    resetear();
                }

            }
        });
    }

    private void comprobarFinal(){
        if (listaBotones.isEmpty()){
            Toast.makeText(getApplicationContext(), "Terminaste, tu puntaje es: " + puntaje, Toast.LENGTH_LONG).show();
            resetear();
        }
    }

    private void resetear() {
        llenarListaBotones();
        llenarListaPreguntas();
        tvPregunta.setText("Pregunta");
        btnOpc1.setText("A");
        btnOpc2.setText("B");
        btnOpc3.setText("C");
        puntaje = 0;
        for (Button btn: listaBotones) {
            btn.setEnabled(true);
            btn.setBackgroundColor(Color.parseColor("#FFBB86FC"));
        }
    }

    private void llenarListaBotones() {
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

    private void asignarPregunta(Button btn){

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnActual = findViewById(view.getId());
                btnActual.setEnabled(false);
                listaBotones.remove(btnActual);

                int preguntaRandom = random.nextInt(listaPreguntas.size());
                preguntaActual = listaPreguntas.get(preguntaRandom);
                listaPreguntas.remove(preguntaActual);
                tvPregunta.setText(preguntaActual.getPregunta());

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
            }
        });
    }

    private void llenarListaPreguntas() {
        listaPreguntas.add(new Pregunta("??Cu??l es el pa??s con menos habitantes del mundo?",
                "Venezuela", "China","Vaticano", 10));

        listaPreguntas.add(new Pregunta("??En que a??o lleg?? Cristobal Col??n a Am??rica?",
                "1429", "1420","1492", 30));

        listaPreguntas.add(new Pregunta("??Cu??l es el planeta m??s grande de nuestro sistema solar?",
                "Mercurio", "Saturno","J??piter", 30));

        listaPreguntas.add(new Pregunta("??Cuantos huesos tiene el cuerpo humano?",
                "150", "50","206", 20));

        listaPreguntas.add(new Pregunta("??Cual es el pa??s m??s poblado del mundo?",
                "Estados Unidos", "Rusia","China", 20));

        listaPreguntas.add(new Pregunta("??Cuando termin?? la II guerra mundial?",
                "1935", "1940","1945", 30));

        listaPreguntas.add(new Pregunta("??Cual es el libro sagrado de los musulmanes?",
                "Talmud", "Biblia","Cor??n", 10));

        listaPreguntas.add(new Pregunta("??Quien escribi?? La Odisea?",
                "Virgilio", "Cervantes","Homero", 20));

        listaPreguntas.add(new Pregunta("??Qui??n pint?? el famoso cuadro La ??ltima cena?",
                "Rembrandt", "Velazquez","Leonardo da Vinci", 10));

        listaPreguntas.add(new Pregunta("??Qui??n escribi?? Cien a??os de soledad?",
                "Camilo Jos?? Cela", "Mario Vargas Llosa","Gabriel Garc??a M??rquez", 10));

        listaPreguntas.add(new Pregunta("??Cu??l es el elemento qu??mico m??s abundante en la corteza terrestre?",
                "Nitr??geno", "Carbono","Ox??geno", 10));

        listaPreguntas.add(new Pregunta("??Cu??l fue el primer metal que emplearon los seres humanos?",
                "Bronce", "Acero","Cobre", 20));

        listaPreguntas.add(new Pregunta("??Cu??l es el primero de los n??meros primos?",
                "0", "1","2", 10));

        listaPreguntas.add(new Pregunta(" ??Cu??l es el primer elemento de la Tabla peri??dica?",
                "Helio", "Arg??n","Hidr??geno", 30));

        listaPreguntas.add(new Pregunta(" ??Cu??ntos litros de sangre tiene una persona adulta?",
                "Tiene entre 2 y 4 litros", "Tiene 7 litros","Tiene entre 4 y 6 litros", 30));

        listaPreguntas.add(new Pregunta("??Cu??ntos jugadores por equipo participan en un partido de voleibol?",
                "4 jugadores", "5 jugadores","6 jugadores", 20));

        listaPreguntas.add(new Pregunta("??Cu??nto tiempo tarda la luz del Sol en llegar a la Tierra?",
                "12 minutos", "8 segundos","8 minutos", 20));

        listaPreguntas.add(new Pregunta("??Cu??l es el grupo de palabras escritas correctamente?",
                "Metamorfosis, extrangero, dibersidad, equilibrio", "Metam??rfosis, eztranjero, div??rsidad, ecuilibrio",
                "Metamorfosis, extranjero, diversidad, equilibrio", 10));

        listaPreguntas.add(new Pregunta("??Cu??les son los nombres de los tres reyes magos?",
                "Gaspar, Nicolas y Nataniel", "Abraham, No?? y Moises",
                "Gaspar, Melchor y Baltazar", 10));

        listaPreguntas.add(new Pregunta("??Cu??l es la religi??n monote??sta que cuenta con el mayor n??mero de adeptos en el mundo?",
                "Islamismo", "Hinduismo", "Cristianismo", 10));

        listaPreguntas.add(new Pregunta("??Cu??l es la capital de Colombia?",
                "Medell??n", "Cali", "Bogot??", 10));

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
        tvPregunta = findViewById(R.id.tvPregunta);
    }
}