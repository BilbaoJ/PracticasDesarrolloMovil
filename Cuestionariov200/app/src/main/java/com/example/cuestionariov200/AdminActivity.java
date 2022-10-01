package com.example.cuestionariov200;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    EditText etPregunta, etOpcion1, etOpcion2, etOpcionCorrecta, etPuntaje;
    Button btnGuardar;
    String pregunta, opcion1, opcion2, opcionCorrecta, textoPreguntaCompleta, puntaje;
    ArchivoPlanoPreguntas archivoPlano = new ArchivoPlanoPreguntas(this);
    ListView lvPreguntas;
    ArrayList<String> preguntas = new ArrayList<>();
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        conectar();
        escribirPreguntasPorDefecto();
        actualizarListView();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pregunta = etPregunta.getText().toString();
                opcion1 = etOpcion1.getText().toString();
                opcion2 = etOpcion2.getText().toString();
                opcionCorrecta = etOpcionCorrecta.getText().toString();
                puntaje = etPuntaje.getText().toString();

                textoPreguntaCompleta = pregunta + ";" + opcion1 + ";" + opcion2 + ";" +
                        opcionCorrecta + ";" + puntaje + "\n";

                etPregunta.setText("");
                etOpcion1.setText("");
                etOpcion2.setText("");
                etOpcionCorrecta.setText("");
                etPuntaje.setText("");

                try {
                    archivoPlano.Escribir(textoPreguntaCompleta);
                    actualizarListView();
                    Toast.makeText(getApplicationContext(), "Pregunta guardada", Toast.LENGTH_LONG).show();

                }catch (IOException e){

                    e.getMessage();
                }
            }
        });
    }

    private void conectar() {
        etPregunta = findViewById(R.id.etPregunta);
        btnGuardar = findViewById(R.id.btnGuardar);
        etOpcion1 = findViewById(R.id.etOpcion1);
        etOpcion2 = findViewById(R.id.etOpcion2);
        etPuntaje = findViewById(R.id.etPuntaje);
        etOpcionCorrecta = findViewById(R.id.etOpcionCorrecta);
        lvPreguntas = findViewById(R.id.lvPreguntas);
    }

    private void actualizarListView(){
        try {

            preguntas.clear();
            ArrayList<String> lecturas = archivoPlano.Leer();
            String preguntaLista = "";

            for (String linea: lecturas) {
                String[] lineaPregunta = linea.split(";");
                String enunciado = lineaPregunta[0];
                String opcion1 = lineaPregunta[1];
                String opcion2 = lineaPregunta[2];
                String opcionCorrecta = lineaPregunta[3];
                int puntaje = Integer.parseInt(lineaPregunta[4]);
                preguntaLista = enunciado + "\n" +  "Opción 1: " + opcion1 + "\n" + "Opción 2: " +
                        opcion2 + "\n" + "Opción correcta: " + opcionCorrecta + "\n"
                        +  "Puntaje: " + puntaje;
                preguntas.add(preguntaLista);
            }

            adapter = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_list_item_1, preguntas);
            lvPreguntas.setAdapter(adapter);

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
}