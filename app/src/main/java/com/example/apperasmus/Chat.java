package com.example.apperasmus;

import android.os.Parcel;
import android.os.Parcelable;

public class Chat implements Parcelable {
    String id;
    String origen;
    String destino;
    String mensaje;
    String fecha;

    public Chat(){

    }

    public Chat(String id, String origen, String destino, String mensaje, String fecha) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.mensaje = mensaje;
        this.fecha = fecha;
    }

    protected Chat (Parcel in){
        id = in.readString();
        origen = in.readString();
        destino = in.readString();
        mensaje = in.readString();
        fecha = in.readString();
    }

    public static final Creator<Chat> CREATOR = new Creator<Chat>() {
        @Override
        public Chat createFromParcel(Parcel in) {
            return new Chat(in);
        }

        @Override
        public Chat[] newArray(int size) {
            return new Chat[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(origen);
        dest.writeString(destino);
        dest.writeString(mensaje);
        dest.writeString(fecha);
    }
}
