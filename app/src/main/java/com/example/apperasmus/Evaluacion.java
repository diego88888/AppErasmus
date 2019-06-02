package com.example.apperasmus;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Evaluacion implements Parcelable {
    String id;
    List<String> preguntas;
    List<String> resultados;

    public Evaluacion(){

    }

    public Evaluacion(String id, List<String> pregunta, List<String> resultado){
        this.id = id;
        this.preguntas = pregunta;
        this.resultados = resultado;
    }

    protected Evaluacion (Parcel in){
        id = in.readString();
        preguntas = in.createStringArrayList();
        resultados = in.createStringArrayList();;
    }

    public static final Creator<Evaluacion> CREATOR = new Creator<Evaluacion>() {
        @Override
        public Evaluacion createFromParcel(Parcel in) {
            return new Evaluacion(in);
        }

        @Override
        public Evaluacion[] newArray(int size) {
            return new Evaluacion[size];
        }
    };

    public String getId() {
        return id;
    }

    public List<String> getPreguntas() {
        return preguntas;
    }

    public List<String> getResultados() {
        return resultados;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPreguntas(List<String> pregunta) {
        this.preguntas = pregunta;
    }

    public void setResultados(List<String> resultado) {
        this.resultados = resultado;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeStringList(preguntas);
        dest.writeStringList(resultados);
    }

}
