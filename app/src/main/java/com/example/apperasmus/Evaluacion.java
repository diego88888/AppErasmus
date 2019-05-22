package com.example.apperasmus;

import android.os.Parcel;
import android.os.Parcelable;

public class Evaluacion implements Parcelable {
    String id;
    String pregunta;
    boolean resultado;

    public Evaluacion(){

    }

    public Evaluacion(String id, String pregunta, boolean resultado){
        this.id = id;
        this.pregunta = pregunta;
        this.resultado = resultado;
    }

    protected Evaluacion (Parcel in){
        id = in.readString();
        pregunta = in.readString();
        resultado = in.readByte() != 0;
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

    public String getPregunta() {
        return pregunta;
    }

    public boolean isResultado() {
        return resultado;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public void setResultado(boolean resultado) {
        this.resultado = resultado;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(pregunta);
        dest.writeByte((byte) (resultado ? 1 : 0));
    }

}
