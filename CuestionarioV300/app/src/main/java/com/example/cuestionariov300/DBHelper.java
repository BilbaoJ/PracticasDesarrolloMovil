package com.example.cuestionariov300;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    String crear_tabla_preguntas = "create table Preguntas (id integer primary key autoincrement, " +
            "enunciado text not null, " +
            "primera_opcion text not null," +
            "segunda_opcion text not null," +
            "opcion_correcta text not null," +
            "puntaje integer not null)";

    String crear_tabla_jugadores = "create table Jugadores (id integer primary key autoincrement, " +
            "nombre text not null, " +
            "puntaje integer not null)";

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(crear_tabla_preguntas);
        db.execSQL(crear_tabla_jugadores);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("Drop table Preguntas");
        db.execSQL("Drop table Jugadores");
        db.execSQL(crear_tabla_preguntas);
        db.execSQL(crear_tabla_jugadores);

    }
}
