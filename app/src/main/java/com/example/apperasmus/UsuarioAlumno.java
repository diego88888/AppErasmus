package com.example.apperasmus;

import android.os.Parcel;
import android.os.Parcelable;

public class UsuarioAlumno implements Parcelable {
    String dni;
    String emailInsti;
    String email;
    String empresa;
    String estudios;
    String id;
    String nombre;
    String nombreInsti;
    String password;
    String periodoPracticas;
    int totalHoras;
    String tutorEmpresa;
    String tutorInsti;

    public UsuarioAlumno(String dni, String emailInsti, String email, String empresa, String estudios, String id, String nombre, String nombreInsti, String password, String periodoPracticas, int totalHoras, String tutorEmpresa, String tutorInsti) {
        this.dni = dni;
        this.emailInsti = emailInsti;
        this.email = email;
        this.empresa = empresa;
        this.estudios = estudios;
        this.id = id;
        this.nombre = nombre;
        this.nombreInsti = nombreInsti;
        this.password = password;
        this.periodoPracticas = periodoPracticas;
        this.totalHoras = totalHoras;
        this.tutorEmpresa = tutorEmpresa;
        this.tutorInsti = tutorInsti;
    }

    protected UsuarioAlumno (Parcel in){
        dni = in.readString();
        emailInsti = in.readString();
        email = in.readString();
        empresa = in.readString();
        estudios = in.readString();
        id = in.readString();
        nombre = in.readString();
        nombreInsti = in.readString();
        password = in.readString();
        periodoPracticas = in.readString();
        totalHoras = in.readInt();
        tutorEmpresa = in.readString();
        tutorInsti = in.readString();
    }

    public static final Creator<UsuarioAlumno> CREATOR = new Creator<UsuarioAlumno>() {
        @Override
        public UsuarioAlumno createFromParcel(Parcel in) {
            return new UsuarioAlumno(in);
        }

        @Override
        public UsuarioAlumno[] newArray(int size) {
            return new UsuarioAlumno[size];
        }
    };

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmailInsti() {
        return emailInsti;
    }

    public void setEmailInsti(String emailInsti) {
        this.emailInsti = emailInsti;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getEstudios() {
        return estudios;
    }

    public void setEstudios(String estudios) {
        this.estudios = estudios;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreInsti() {
        return nombreInsti;
    }

    public void setNombreInsti(String nombreInsti) {
        this.nombreInsti = nombreInsti;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPeriodoPracticas() {
        return periodoPracticas;
    }

    public void setPeriodoPracticas(String periodoPracticas) {
        this.periodoPracticas = periodoPracticas;
    }

    public int getTotalHoras() {
        return totalHoras;
    }

    public void setTotalHoras(int totalHoras) {
        this.totalHoras = totalHoras;
    }

    public String getTutorEmpresa() {
        return tutorEmpresa;
    }

    public void setTutorEmpresa(String tutorEmpresa) {
        this.tutorEmpresa = tutorEmpresa;
    }

    public String getTutorInsti() {
        return tutorInsti;
    }

    public void setTutorInsti(String tutorInsti) {
        this.tutorInsti = tutorInsti;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dni);
        dest.writeString(emailInsti);
        dest.writeString(email);
        dest.writeString(empresa);
        dest.writeString(estudios);
        dest.writeString(id);
        dest.writeString(nombre);
        dest.writeString(nombreInsti);
        dest.writeString(password);
        dest.writeString(periodoPracticas);
        dest.writeString(tutorEmpresa);
        dest.writeString(tutorInsti);
        dest.writeInt(totalHoras);
    }
}
