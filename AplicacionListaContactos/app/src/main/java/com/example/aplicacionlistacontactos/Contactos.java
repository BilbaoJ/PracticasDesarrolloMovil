package com.example.aplicacionlistacontactos;

public class Contactos {

    private String strNombre;
    private String strApellido;
    private String strCorreo;
    private int intEstatura;

    public Contactos(String strNombre, String strApellido, String strCorreo, int intEstatura) {
        this.strNombre = strNombre;
        this.strApellido = strApellido;
        this.strCorreo = strCorreo;
        this.intEstatura = intEstatura;
    }

    public String getStrNombre() {
        return strNombre;
    }

    public void setStrNombre(String strNombre) {
        this.strNombre = strNombre;
    }

    public String getStrApellido() {
        return strApellido;
    }

    public void setStrApellido(String strApellido) {
        this.strApellido = strApellido;
    }

    public String getStrCorreo() {
        return strCorreo;
    }

    public void setStrCorreo(String strCorreo) {
        this.strCorreo = strCorreo;
    }

    public int getIntEstatura() {
        return intEstatura;
    }

    public void setIntEstatura(int intEstatura) {
        this.intEstatura = intEstatura;
    }

    @Override
    public String toString() {
        return "Nombre: " + strNombre + " " + strApellido + '\n' +
                "Correo: " + strCorreo + '\n' +
                "Estatura:" + intEstatura;
    }
}
