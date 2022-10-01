package com.example.cuestionariov200;

public class Jugador {
    String nombre;
    Integer puntaje;

    public Jugador(String nombre, Integer puntaje) {
        this.nombre = nombre;
        this.puntaje = puntaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Integer puntaje) {
        this.puntaje = puntaje;
    }

    @Override
    public String toString() {
        return "Jugador " + nombre + " Puntaje: " + puntaje;
    }
}
