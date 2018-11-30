package com.example.aldo.easynutritionapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.aldo.easynutritionapp.Models.Dieta;
import com.example.aldo.easynutritionapp.Models.Progreso;
import com.example.aldo.easynutritionapp.Utils.Globales;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;


public class ProgresoFragment extends Fragment {
    ListView listaProgresos;
    ArrayList<Progreso> progresos;
    Gson gson = new Gson();
    String[] elementos;
    View myFragmentView;
    android.support.v7.widget.SearchView searchView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_progreso, container, false);
        getActivity().setTitle("Progresos");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_progreso, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        listaProgresos = getView().findViewById(R.id.listProgresos);
        searchView = getView().findViewById(R.id.searchProgresos);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (s.equals("")){
                    cargarProgresos();
                }else {
                    cargarProgresosBusqueda(s);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });
        cargarProgresos();
    }

    public void cargarProgresos(){
        final ProgressDialog dialogo = new ProgressDialog(myFragmentView.getContext());
        dialogo.setMessage("Descargando Información");
        dialogo.show();
        Ion.with(myFragmentView.getContext())
                .load(Globales.URL+"api/Progreso/GetProgresosByPersona?email="+Globales.nombreUsuario)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        TypeToken<List<Progreso>> listaProgresosJson= new TypeToken<List<Progreso>>(){};
                        progresos = gson.fromJson(result, listaProgresosJson.getType());
                        elementos = new String[progresos.size()];
                        for(int i=0; i<progresos.size(); i++){
                            elementos[i] = "El Imc es: "+progresos.get(i).getImc()+"\n El peso es: "+progresos.get(i).getPeso()+"\n La talla es: "+progresos.get(i).getTalla()+"\n"+" Se asigno el dia: "+progresos.get(i).getFecha()+"\n";
                        }
                        dialogo.dismiss();
                        inicializarLista();
                    }
                });
    }
    public void cargarProgresosBusqueda(String busqueda){
        final ProgressDialog dialogo = new ProgressDialog(myFragmentView.getContext());
        dialogo.setMessage("Descargando Información");
        dialogo.show();
        Ion.with(myFragmentView.getContext())
                .load(Globales.URL+"api/Progreso/GetProgresosByPersonaYFecha?email="+Globales.nombreUsuario+"&fecha="+busqueda)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        TypeToken<List<Progreso>> listaProgresosJson= new TypeToken<List<Progreso>>(){};
                        progresos = gson.fromJson(result, listaProgresosJson.getType());
                        elementos = new String[progresos.size()];
                        for(int i=0; i<progresos.size(); i++){
                            elementos[i] = "El Imc es: "+progresos.get(i).getImc()+"\n El peso es: "+progresos.get(i).getPeso()+"\n La talla es: "+progresos.get(i).getTalla()+"\n"+" Se asigno el dia: "+progresos.get(i).getFecha()+"\n";
                        }
                        dialogo.dismiss();
                        inicializarLista();
                    }
                });
    }
    private void inicializarLista(){
        ArrayAdapter<String> miAdaptador = new ArrayAdapter<String>(myFragmentView.getContext(), android.R.layout.simple_list_item_1, elementos);
        listaProgresos.setAdapter(miAdaptador);
    }
}
