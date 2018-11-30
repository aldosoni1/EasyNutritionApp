package com.example.aldo.easynutritionapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aldo.easynutritionapp.Models.CitaPojo;
import com.example.aldo.easynutritionapp.Models.Dieta;
import com.example.aldo.easynutritionapp.Utils.Globales;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;


public class DietasFragment extends Fragment {
    ListView listDietas;
    ArrayList<Dieta> dietas;
    Gson gson = new Gson();
    String[] elementos;
    View myFragmentView;
    android.support.v7.widget.SearchView searchView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_citas, container, false);
        getActivity().setTitle("Dietas");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dietas, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        listDietas = getView().findViewById(R.id.listDietas);
        listDietas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lanzaDialogoOpciones(position);
            }
        });
        FloatingActionButton fab = getView().findViewById(R.id.dietaActual);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarDietaReciente();
            }
        });
        searchView = getView().findViewById(R.id.searchDieta);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                cargarDietasBusqueda(s);
                return false;
            }
        });
        cargarDietas();
    }
    public void cargarDietas(){
        final ProgressDialog dialogo = new ProgressDialog(myFragmentView.getContext());
        dialogo.setMessage("Descargando Información");
        dialogo.show();
        Ion.with(myFragmentView.getContext())
                .load(Globales.URL+"api/Dieta/GetDietasHistorial?email="+Globales.nombreUsuario)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        TypeToken<List<Dieta>> listaDietasJson= new TypeToken<List<Dieta>>(){};
                        dietas = gson.fromJson(result, listaDietasJson.getType());
                        elementos = new String[dietas.size()];
                        for(int i=0; i<dietas.size(); i++){
                            //Log.d("String",lista.get(i).getEmail());
                            elementos[i] = "Nombre de la Dieta: "+dietas.get(i).getNombreDieta()+"\n"+"Comentarios mencionados por el medico: "+dietas.get(i).getComentarios()+"\n"+"Fue asignada en la consulta del dia: "+dietas.get(i).getFechaAsignacion()+"\n";
                        }
                        dialogo.dismiss();
                        inicializarLista();
                    }
                });
    }
    public void cargarDietaReciente(){
        getActivity().setTitle("Ultima dieta asignada");
        final ProgressDialog dialogo = new ProgressDialog(myFragmentView.getContext());
        dialogo.setMessage("Descargando Información");
        dialogo.show();
        Ion.with(myFragmentView.getContext())
                .load(Globales.URL+"api/Dieta/GetDietasHistorialReciente?email="+Globales.nombreUsuario)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        TypeToken<List<Dieta>> listaDietasJson= new TypeToken<List<Dieta>>(){};
                        dietas = gson.fromJson(result, listaDietasJson.getType());
                        elementos = new String[dietas.size()];
                        for(int i=0; i<dietas.size(); i++){
                            //Log.d("String",lista.get(i).getEmail());
                            elementos[i] = "Nombre de la Dieta: "+dietas.get(i).getNombreDieta()+"\n"+"Comentarios mencionados por el medico: "+dietas.get(i).getComentarios()+"\n"+"Fue asignada en la consulta del dia: "+dietas.get(i).getFechaAsignacion()+"\n";
                        }
                        dialogo.dismiss();
                        inicializarLista();
                    }
                });
    }
    public void cargarDietasBusqueda(String busqueda){
        if (busqueda.equals("")){
            cargarDietas();
        }else{


        getActivity().setTitle("Resultado de busqueda");
        final ProgressDialog dialogo = new ProgressDialog(myFragmentView.getContext());
        dialogo.setMessage("Descargando Información");
        dialogo.show();
        Ion.with(myFragmentView.getContext())
                .load(Globales.URL+"api/Dieta/GetDietasHistorialBusqueda?email="+Globales.nombreUsuario+"&busqueda="+busqueda)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        TypeToken<List<Dieta>> listaDietasJson= new TypeToken<List<Dieta>>(){};
                        dietas = gson.fromJson(result, listaDietasJson.getType());
                        elementos = new String[dietas.size()];
                        for(int i=0; i<dietas.size(); i++){
                            //Log.d("String",lista.get(i).getEmail());
                            elementos[i] = "Nombre de la Dieta: "+dietas.get(i).getNombreDieta()+"\n"+"Comentarios mencionados por el medico: "+dietas.get(i).getComentarios()+"\n"+"Fue asignada en la consulta del dia: "+dietas.get(i).getFechaAsignacion()+"\n";
                        }
                        dialogo.dismiss();
                        inicializarLista();
                    }
                });
        }
    }
    private void inicializarLista(){
        ArrayAdapter<String> miAdaptador = new ArrayAdapter<String>(myFragmentView.getContext(), android.R.layout.simple_list_item_1, elementos);
        listDietas.setAdapter(miAdaptador);
    }

    private void lanzaDialogoOpciones(final int posicion){
        String[] opciones = {"Ver Dieta"};
        AlertDialog.Builder alert = new AlertDialog.Builder(myFragmentView.getContext());
        alert.setTitle("Selecciona Operación");
        alert.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        VerDieta(posicion);
                    break;
                }
            }
        });
        alert.create().show();
    }

    private void VerDieta(int index){
        Dieta dieta = dietas.get(index);
        Intent intent = new Intent(myFragmentView.getContext(), DietaDetalladaActivity.class);
        intent.putExtra("dietaGuid",dieta.getDietaGuid());
        startActivity(intent);
    }
}
