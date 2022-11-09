package com.example.asyncapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMensaje = findViewById(R.id.btnMensaje);
        TareaAsincrona tarea = new TareaAsincrona();
        tarea.execute(30);
    }

    public class TareaAsincrona extends AsyncTask<Integer, Integer, String>{

        @Override
        protected String doInBackground(Integer... integers) {
            int tope = integers[0];

            for (int i = 1; i <= tope; i++) {
                try {
                    Thread.sleep(500);
                }catch (Exception e){
                    e.printStackTrace();
                }
                publishProgress(i);
            }

            return "Fin de la ejecución";
        }

        @Override
        protected void onPostExecute(String s){
            btnMensaje.append(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values){
            int contador = values[0];
            String texto = "Contador " + contador;
            btnMensaje.setText(texto);
            btnMensaje.setTextSize(contador);
            super.onProgressUpdate(values);
        }
    }
}