package com.example.aldo.easynutritionapp.Models;

public class CitaPojo {
    private String CitaGuid;
    private String FechaCita;
    private String Observaciones;

    public String getCitaGuid() {
        return CitaGuid;
    }

    public void setCitaGuid(String citaGuid) {
        CitaGuid = citaGuid;
    }

    public String getFechaCita() {
        return FechaCita;
    }

    public void setFechaCita(String fechaCita) {
        FechaCita = fechaCita;
    }

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String observaciones) {
        Observaciones = observaciones;
    }
}
