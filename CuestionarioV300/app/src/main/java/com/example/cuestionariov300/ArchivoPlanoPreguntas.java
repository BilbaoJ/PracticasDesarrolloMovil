package com.example.cuestionariov300;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ArchivoPlanoPreguntas {
    String archivo = "preguntas.txt";
    Context ctx;
    FileOutputStream fos;
    FileInputStream fis;

    public ArchivoPlanoPreguntas(Context ctx) {
        this.ctx = ctx;
    }

    public void Escribir(String text) throws IOException {
        try {
            fos = ctx.openFileOutput(archivo, Context.MODE_APPEND);
            fos.write(text.getBytes());
            fos.close();

        }catch (FileNotFoundException e){
            Log.e("", e.getMessage());

        }catch (IOException e){
            Log.e("", e.getMessage());
        }
    }

    public ArrayList<String> Leer(){
        ArrayList<String> listaLecturas = new ArrayList<>();
        String lectura = "";
        try{
            fis = ctx.openFileInput(archivo);
            int i;
            char caracter = 'a';

            do {
                i = fis.read();

                if (i != '\n'){

                    caracter = (char) i;
                    lectura += caracter;

                }else{
                    lectura = new String(lectura.getBytes("ISO-8859-1"),"UTF-8");
                    listaLecturas.add(lectura);
                    lectura = "";
                }

            }while (i>0);

        }catch (Exception e){

            e.printStackTrace();
        }

        return listaLecturas;
    }
}
