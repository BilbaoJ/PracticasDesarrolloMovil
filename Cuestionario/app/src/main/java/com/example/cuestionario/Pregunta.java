package com.example.cuestionario;

public class Pregunta {
    String pregunta;
    String opcion1;
    String opcion2;
    String opcionCorrecta;
    Integer puntaje;

    public Pregunta(String pregunta, String opcion1, String opcion2, String opcionCorrecta, Integer puntaje) {
        this.pregunta = pregunta;
        this.opcion1 = opcion1;
        this.opcion2 = opcion2;
        this.opcionCorrecta = opcionCorrecta;
        this.puntaje = puntaje;
    }

    public Pregunta(String ooo, String opcion1, String opcion2, int i) {
    }

    public String getPregunta() {
        return pregunta;
    }


    public String getOpcion1() {
        return opcion1;
    }


    public String getOpcion2() {
        return opcion2;
    }


    public String getOpcionCorrecta() {
        return opcionCorrecta;
    }


    public Integer getPuntaje() {
        return puntaje;
    }

}
