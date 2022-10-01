package com.example.archivosplanos;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ArchivosPlanos {

    String archivo = "datos.txt";
    Context ctx;
    FileOutputStream fos;
    FileInputStream fis;

    public ArchivosPlanos(Context ctx) {
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

    public String Leer(){
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
                }

                if (caracter == '.' || caracter == '?'){
                    lectura += '\n';
                }

            }while (i>0);

            lectura += '\n';

        }catch (Exception e){

            e.printStackTrace();
        }

        return lectura;
    }

}
