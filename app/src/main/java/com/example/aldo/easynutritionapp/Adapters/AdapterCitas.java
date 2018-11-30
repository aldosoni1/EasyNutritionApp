package com.example.aldo.easynutritionapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import com.example.aldo.easynutritionapp.Models.CitaPojo;
import com.example.aldo.easynutritionapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AdapterCitas extends RecyclerView.Adapter<AdapterCitas.ViewHolderDatos> {
    ArrayList<CitaPojo> listDatos;

    public AdapterCitas(ArrayList<CitaPojo> listDatos) {
        this.listDatos = listDatos;
    }

    public ArrayList<CitaPojo> getListDatos() {
        return listDatos;
    }

    public void setListDatos(ArrayList<CitaPojo> listDatos) {
        this.listDatos = listDatos;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_cita,null,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos viewHolderDatos, int i) {
        try {
            viewHolderDatos.asignarDatos(listDatos.get(i));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView txtObservaciones;
        TextView txtFecha;
        //CalendarView calendarFecha;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            txtObservaciones = itemView.findViewById(R.id.txtObservaciones);
            txtFecha = itemView.findViewById(R.id.txtFecha);
            //calendarFecha = itemView.findViewById(R.id.calendarFecha);
        }

        public void asignarDatos(CitaPojo citaPojo) throws ParseException {
            txtObservaciones.setText("Observaciones realizadas por su medico: "+citaPojo.getObservaciones());
            txtFecha.setText("Fecha de Cita: "+citaPojo.getFechaCita());
            /*Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(citaPojo.getFechaCita());

            Calendar cal = Calendar.getInstance();
            cal.setTime(date1);
            long milliTime = cal.getTimeInMillis();
            calendarFecha.setDate(milliTime,true,true);
            */



        }
    }

}
