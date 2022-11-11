package com.example.firebasecrud;

import java.util.UUID;

public class Contacto {
    String nombre;
    String telefono;
    int edad;
    String id;

    public Contacto(String nombre, String telefono, int edad, String id) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.edad = edad;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return "Contacto{" +
                "nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", edad=" + edad +
                ", id='" + id + '\'' +
                '}';
    }
}
