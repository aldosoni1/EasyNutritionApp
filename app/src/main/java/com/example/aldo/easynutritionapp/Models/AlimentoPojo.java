package com.example.aldo.easynutritionapp.Models;

import java.util.ArrayList;

public class AlimentoPojo {
    private String Nombre;
    private String Porcion;
    private double Calorias;


    public String getPorcion() {
        return Porcion;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public void setPorcion(String porcion) {
        Porcion = porcion;
    }

    public double getCalorias() {
        return Calorias;
    }

    public void setCalorias(double calorias) {
        Calorias = calorias;
    }
}
