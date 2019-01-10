package com.example.cristobal.activitatmanelevalua.model;

public class Producto {


    private String precio;
    private String nombre;
    private String descripcion;
    private int userID;
    private String categoria;

    public Producto(String precio, String nombre, String descripcion, int userID, String categoria) {
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

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

}
