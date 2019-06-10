package com.example.apperasmus;

public class Mensaje {
    String textoMensaje;
    String escritor;

    public Mensaje(String textoMensaje, String escritor) {
        this.textoMensaje = textoMensaje;
        this.escritor = escritor;
    }

    public String getTextoMensaje() {
        return textoMensaje;
    }

    public void setTextoMensaje(String textoMensaje) {
        this.textoMensaje = textoMensaje;
    }

    public String getEscritor() {
        return escritor;
    }

    public void setEscritor(String escritor) {
        this.escritor = escritor;
    }

    @Override
    public String toString() {
        return "Enviado por: "+ escritor+"\n"+
                textoMensaje;
    }
}
