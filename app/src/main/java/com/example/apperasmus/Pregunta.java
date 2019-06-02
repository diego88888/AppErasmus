package com.example.apperasmus;

public class Pregunta {
    String texto;
    boolean respuesta;

    public Pregunta(String texto) {
        this.texto = texto;
        this.respuesta = false;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public boolean isRespuesta() {
        return respuesta;

    }

    public void setRespuesta(boolean respuesta) {
        this.respuesta = respuesta;
    }
}
