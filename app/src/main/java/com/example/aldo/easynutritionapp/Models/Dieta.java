package com.example.aldo.easynutritionapp.Models;

import java.util.Date;

public class Dieta {
    private String NombreDieta;
    private String FechaAsignacion;
    private String Comentarios;
    private String DietaGuid;

    public String getDietaGuid() {
        return DietaGuid;
    }

    public void setDietaGuid(String dietaGuid) {
        DietaGuid = dietaGuid;
    }

    public String getNombreDieta() {
        return NombreDieta;
    }

    public void setNombreDieta(String nombreDieta) {
        NombreDieta = nombreDieta;
    }

    public String getFechaAsignacion() {
        return FechaAsignacion;
    }

    public void setFechaAsignacion(String fechaAsignacion) {
        FechaAsignacion = fechaAsignacion;
    }

    public String getComentarios() {
        return Comentarios;
    }

    public void setComentarios(String comentarios) {
        Comentarios = comentarios;
    }
}
