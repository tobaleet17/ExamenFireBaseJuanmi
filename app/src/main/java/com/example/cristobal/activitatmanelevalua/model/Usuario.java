package com.example.cristobal.activitatmanelevalua.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Usuario implements Parcelable {
    private int telefono;
    private String email;
    private String pass;
    private String nombre;
    private String apellidos;
    private int userID;

    public Usuario(int telefono, String email, String pass, String nombre, String apellidos, int userID) {
        this.telefono = telefono;
        this.email = email;
        this.pass = pass;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.userID = userID;
    }
    public Usuario(){

    }

    public int getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getUserID() {

        return userID;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }



    protected Usuario(Parcel in) {
        telefono = in.readInt();
        email = in.readString();
        pass = in.readString();
        nombre = in.readString();
        apellidos = in.readString();
        userID = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(telefono);
        dest.writeString(email);
        dest.writeString(pass);
        dest.writeString(nombre);
        dest.writeString(apellidos);
        dest.writeInt(userID);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Usuario> CREATOR = new Parcelable.Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };
}