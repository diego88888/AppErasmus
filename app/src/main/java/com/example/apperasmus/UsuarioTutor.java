package com.example.apperasmus;

import android.os.Parcel;
import android.os.Parcelable;

public class UsuarioTutor implements Parcelable {
    String id;
    String email;
    String password;
    String nombre;

    public UsuarioTutor(){

    }

    public UsuarioTutor(String id, String email, String password, String nombre) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nombre = nombre;
    }

    protected UsuarioTutor(Parcel in){
        id = in.readString();
        email = in.readString();
        password = in.readString();
        nombre = in.readString();
    }

    public static final Creator<UsuarioTutor> CREATOR = new Creator<UsuarioTutor>() {
        @Override
        public UsuarioTutor createFromParcel(Parcel in) {
            return new UsuarioTutor(in);
        }

        @Override
        public UsuarioTutor[] newArray(int size) {
            return new UsuarioTutor[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(nombre);
    }
}
