

package com.example.cristobal.activitatmanelevalua.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Producto implements Parcelable {


    private String precio;
    private String nombre;
    private String descripcion;
    private String userID;
    private String categoria;

    public Producto(){

    }

    public Producto(String precio, String nombre, String descripcion, String userID, String categoria) {
        this.precio = precio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.userID = userID;
        this.categoria = categoria;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }


    protected Producto(Parcel in) {
        precio = in.readString();
        nombre = in.readString();
        descripcion = in.readString();
        userID = in.readString();
        categoria = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(precio);
        dest.writeString(nombre);
        dest.writeString(descripcion);
        dest.writeString(userID);
        dest.writeString(categoria);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Producto> CREATOR = new Parcelable.Creator<Producto>() {
        @Override
        public Producto createFromParcel(Parcel in) {
            return new Producto(in);
        }

        @Override
        public Producto[] newArray(int size) {
            return new Producto[size];
        }
    };
}