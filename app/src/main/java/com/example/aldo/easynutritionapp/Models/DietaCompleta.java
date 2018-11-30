package com.example.aldo.easynutritionapp.Models;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DietaCompleta {
    private String Nombre;
    private double TotalCalorias;
    private ArrayList<AlimentoDietaPojo> DietaAlimentos;

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public double getTotalCalorias() {
        return TotalCalorias;
    }

    public void setTotalCalorias(double totalCalorias) {
        TotalCalorias = totalCalorias;
    }

    public ArrayList<AlimentoDietaPojo> getDietaAlimentos() {
        return DietaAlimentos;
    }

    public void setDietaAlimentos(ArrayList<AlimentoDietaPojo> dietaAlimentos) {
        DietaAlimentos = dietaAlimentos;
    }
}
