package com.example.aldo.easynutritionapp.Models;

public class AlimentoDietaPojo {
    private AlimentoPojo Alimento;
    private String HoraDia;
    private double Cantidad;

    public double getCantidad() {
        return Cantidad;
    }

    public void setCantidad(double cantidad) {
        Cantidad = cantidad;
    }

    public String getHoraDia() {
        return HoraDia;
    }

    public void setHoraDia(String horaDia) {
        HoraDia = horaDia;
    }

    public AlimentoPojo getAlimento() {
        return Alimento;
    }

    public void setAlimento(AlimentoPojo alimento) {
        Alimento = alimento;
    }
}
