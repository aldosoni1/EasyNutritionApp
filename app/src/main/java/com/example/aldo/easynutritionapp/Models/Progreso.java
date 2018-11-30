package com.example.aldo.easynutritionapp.Models;

public class Progreso {
    private double Peso;
    private double Talla;
    private double Imc;
    private String Fecha;
    private String ProgresoGuid;

    public String getProgresoGuid() {
        return ProgresoGuid;
    }

    public void setProgresoGuid(String progresoGuid) {
        ProgresoGuid = progresoGuid;
    }

    public double getPeso() {
        return Peso;
    }

    public void setPeso(double peso) {
        Peso = peso;
    }

    public double getTalla() {
        return Talla;
    }

    public void setTalla(double talla) {
        Talla = talla;
    }

    public double getImc() {
        return Imc;
    }

    public void setImc(double imc) {
        Imc = imc;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }
}
