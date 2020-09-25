package com.example.ejercicio_sqlite;

import android.content.Context;
import java.io.Serializable;

public class Dto implements Serializable {
    int codigo;
    String descripcion;
    double precio;

    public Dto() {

    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
