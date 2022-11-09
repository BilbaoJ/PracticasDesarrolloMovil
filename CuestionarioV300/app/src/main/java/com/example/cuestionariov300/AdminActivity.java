package com.example.cuestionariov300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    EditText etPregunta, etOpcion1, etOpcion2, etOpcionCorrecta, etPuntaje;
    Button btnGuardar, btnActualizar, btnEliminar, btnCancelar;
    ListView lvPreguntas;
    GridLayout glOpcionesDB;
    ArrayList<String> listaPreguntas = new ArrayList<>();
    ArrayAdapter<String> adapter;
    int elementoSeleccionado, idPreguntaSeleccionada;
    ArchivoPlanoPreguntas archivoPlano = new ArchivoPlanoPreguntas(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        conectar();
        escribirPreguntasPorDefecto();
        insertarPreguntasDB();
        actualizarListView();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validarCamposVacios()){
                    Toast.makeText(getApplicationContext(), "Todos los campos deben estar " +
                                    "diligenciados",
                            Toast.LENGTH_LONG).show();
                }else{
                    String enunciado = etPregunta.getText().toString();
                    String opcion1 = etOpcion1.getText().toString();
                    String opcion2 = etOpcion2.getText().toString();
                    String opcionCorrecta = etOpcionCorrecta.getText().toString();
                    int puntaje = Integer.parseInt(etPuntaje.getText().toString());

                    insertarPregunta(enunciado, opcion1, opcion2, opcionCorrecta, puntaje);
                    actualizarListView();
                    limpiarCampos();
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deshabilitarOpciones();
                limpiarCampos();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listaPreguntas.size() == 20){
                    Toast.makeText(getApplicationContext(), "Deben haber al menos 20 preguntas, " +
                                    "agregue una pregunta más antes de eliminar",
                            Toast.LENGTH_LONG).show();
                }else {
                    eliminarPregunta(idPreguntaSeleccionada);
                    limpiarCampos();
                    deshabilitarOpciones();
                    actualizarListView();
                }
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarCamposVacios()){
                    Toast.makeText(getApplicationContext(), "Todos los campos deben estar " +
                                    "diligenciados",
                            Toast.LENGTH_LONG).show();
                }else{
                    String opcion1 = etOpcion1.getText().toString();
                    String opcion2 = etOpcion2.getText().toString();
                    String opcionCorrecta = etOpcionCorrecta.getText().toString();
                    int puntaje = Integer.parseInt(etPuntaje.getText().toString());
                    actualizarPregunta(opcion1, opcion2, opcionCorrecta, puntaje, idPreguntaSeleccionada);
                    deshabilitarOpciones();
                    actualizarListView();
                    limpiarCampos();
                }
            }
        });

        lvPreguntas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                elementoSeleccionado = i;
                habilitarOpciones();
                escribirDatosPregunta();
            }
        });
    }

    private void habilitarOpciones(){
        glOpcionesDB.setVisibility(View.VISIBLE);
        btnGuardar.setVisibility(View.GONE);
        etPregunta.setEnabled(false);
    }

    private void deshabilitarOpciones(){
        glOpcionesDB.setVisibility(View.GONE);
        btnGuardar.setVisibility(View.VISIBLE);
        etPregunta.setEnabled(true);
    }

    private void limpiarCampos(){
        etPregunta.setText("");
        etOpcion1.setText("");
        etOpcion2.setText("");
        etOpcionCorrecta.setText("");
        etPuntaje.setText("");
    }

    private void actualizarListView(){

        listaPreguntas = leerPreguntas();
        adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, listaPreguntas);
        lvPreguntas.setAdapter(adapter);

    }

    private Boolean validarCamposVacios(){
        return etPregunta.getText().toString().isEmpty() || etOpcion1.getText().toString().isEmpty()
                || etOpcion2.getText().toString().isEmpty() || etOpcionCorrecta.toString().isEmpty()
                || etPuntaje.getText().toString().isEmpty();
    }

    private void escribirDatosPregunta(){
        String pregunta = listaPreguntas.get(elementoSeleccionado);
        String[] divisionPregunta = pregunta.split("-");
        int id = Integer.parseInt(divisionPregunta[0]);
        ArrayList<String> datos = buscarPregunta(id);
        idPreguntaSeleccionada = id;
        etPregunta.setText(datos.get(0));
        etOpcion1.setText(datos.get(1));
        etOpcion2.setText(datos.get(2));
        etOpcionCorrecta.setText(datos.get(3));
        etPuntaje.setText(datos.get(4));
    }

    private ArrayList<String> buscarPregunta(int id){

        DBHelper helper = new DBHelper(this, "Cuestionario", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        ArrayList<String> datosPregunta = new ArrayList<>();

        try {
            String sql = "Select * from Preguntas where id = '" + id + "'";
            Cursor c = db.rawQuery(sql, null);
            if (c.moveToFirst()){
                String enunciado = c.getString(1);
                String opcion1 = c.getString(2);
                String opcion2 = c.getString(3);
                String opcionCorrecta = c.getString(4);
                String puntaje = String.valueOf(c.getInt(5));
                datosPregunta.add(enunciado);
                datosPregunta.add(opcion1);
                datosPregunta.add(opcion2);
                datosPregunta.add(opcionCorrecta);
                datosPregunta.add(puntaje);
            }
            db.close();

        }catch (Exception e){

            Toast.makeText(this, "Ocurrió un error: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

        return datosPregunta;
    }

    private void insertarPregunta(String enunciado, String opcion1, String opcion2,
                                  String opcionCorrecta, int puntaje){
        DBHelper helper = new DBHelper(this, "Cuestionario", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();

        try {

            ContentValues cv = new ContentValues();
            cv.put("enunciado", enunciado);
            cv.put("primera_opcion", opcion1);
            cv.put("segunda_opcion", opcion2);
            cv.put("opcion_correcta", opcionCorrecta);
            cv.put("puntaje", puntaje);

            db.insert("Preguntas", null, cv);
            db.close();

            Toast.makeText(this, "Se agregó la pregunta exitosamente",
                    Toast.LENGTH_LONG).show();

        }catch (Exception e){

            Toast.makeText(this, "Ocurrió un error: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private ArrayList<String> leerPreguntas(){

        ArrayList<String> preguntasdb = new ArrayList<>();

        DBHelper helper = new DBHelper(this, "Cuestionario", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        String SQL = "select * from Preguntas";

        Cursor c = db.rawQuery(SQL, null);
        if (c.moveToFirst()){
            do {
                String registro = c.getInt(0) + "-" + c.getString(1) +"\nOpción 1: " + c.getString(2) +
                        "\nOpción 2: " + c.getString(3) + "\nOpción correcta: " + c.getString(4) +
                        "\nPuntaje: " + c.getInt(5);
                preguntasdb.add(registro);

            }while (c.moveToNext());
        }

        db.close();

        return preguntasdb;
    }

    private void eliminarPregunta(int id){
        DBHelper helper = new DBHelper(this, "Cuestionario", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();

        try {
            String sql = "Delete from Preguntas where id = '" + id + "'";
            db.execSQL(sql);

            Toast.makeText(this, "Se eliminó la pregunta exitosamente",
                    Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(this, "Ocurrió un problema: "+ e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
        db.close();

    }

    private void actualizarPregunta(String nuevaOpcion1,String nuevaOpcion2,
                                    String nuevaOpcionCorrecta, int nuevoPuntaje, int id){
        DBHelper helper = new DBHelper(this, "Cuestionario", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();

        try {
            String sql = "update Preguntas set primera_opcion = '"+ nuevaOpcion1 +"', " +
                    "segunda_opcion = '"+ nuevaOpcion2 + "', opcion_correcta = '"+ nuevaOpcionCorrecta +
                    "', puntaje = '" + nuevoPuntaje + "' where id = '"+ id + "'";
            db.execSQL(sql);
            db.close();
            Toast.makeText(getApplicationContext(), "Pregunta actualizada", Toast.LENGTH_LONG).show();

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Ocurrió un error: "+ e.getMessage(), Toast.LENGTH_LONG).show();
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

    private void insertarPreguntasDB(){
        if (leerPreguntas().size() == 0){
            DBHelper helper = new DBHelper(this, "Cuestionario", null, 1);
            SQLiteDatabase db = helper.getWritableDatabase();
            try {

                ArrayList<String> lecturas = archivoPlano.Leer();

                for (String linea: lecturas) {
                    String[] lineaPregunta = linea.split(";");
                    ContentValues cv = new ContentValues();
                    cv.put("enunciado", lineaPregunta[0]);
                    cv.put("primera_opcion", lineaPregunta[1]);
                    cv.put("segunda_opcion", lineaPregunta[2]);
                    cv.put("opcion_correcta", lineaPregunta[3]);
                    cv.put("puntaje", Integer.parseInt(lineaPregunta[4]));

                    db.insert("Preguntas", null, cv);

                }

                db.close();

            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "Ocurrió un error"+e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void conectar() {
        etPregunta = findViewById(R.id.etPregunta);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);
        etOpcion1 = findViewById(R.id.etOpcion1);
        etOpcion2 = findViewById(R.id.etOpcion2);
        etPuntaje = findViewById(R.id.etPuntaje);
        etOpcionCorrecta = findViewById(R.id.etOpcionCorrecta);
        lvPreguntas = findViewById(R.id.lvPreguntas);
        glOpcionesDB = findViewById(R.id.glOpcionesDB);
        btnCancelar = findViewById(R.id.btnCancelar);
    }
}