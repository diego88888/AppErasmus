package com.example.apperasmus;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Evaluacion implements Parcelable {
    String id;
    List<String> preguntas = new ArrayList<>();
    List<String> resultados = new ArrayList<>();
    String dniAlumno;

    public Evaluacion(){

    }

    public Evaluacion(String id, List<String> resultados, String dniAlumno){
        this.id = id;
        this.resultados = resultados;
        this.dniAlumno = dniAlumno;
    }

    protected Evaluacion (Parcel in){
        id = in.readString();
        resultados = in.createStringArrayList();
        dniAlumno = in.readString();
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
    public String getDniAlumno() {
        return dniAlumno;
    }

    public void setDniAlumno(String dniAlumno) {
        this.dniAlumno = dniAlumno;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeStringList(resultados);
        dest.writeString(dniAlumno);
    }

    public void rellenarPreguntas(String pregunta){
        preguntas.add(pregunta);
    }

    public void rellenarRespuestas(boolean respuesta){
        if (respuesta){
            resultados.add("Conseguido");
        }else{
            resultados.add("No conseguido");
        }
    }

}
