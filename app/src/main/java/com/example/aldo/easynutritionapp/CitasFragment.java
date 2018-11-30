package com.example.aldo.easynutritionapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.aldo.easynutritionapp.Adapters.AdapterCitas;
import com.example.aldo.easynutritionapp.Models.CitaPojo;
import com.example.aldo.easynutritionapp.Utils.Globales;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class CitasFragment extends Fragment {
    ListView listaCitas;
    ArrayList<CitaPojo> citas;
    Gson gson = new Gson();
    String[] elementos;
    View myFragmentView;
    RecyclerView recyclerView;
    android.support.v7.widget.SearchView searchView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        myFragmentView = inflater.inflate(R.layout.fragment_citas, container, false);
        getActivity().setTitle("Historial de Citas");
        //listaCitas=myFragmentView.findViewById(R.id.listCitas);
        //cargarCitas();
        return inflater.inflate(R.layout.fragment_citas, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        //listaCitas = getView().findViewById(R.id.listCitas);
        recyclerView = getView().findViewById(R.id.recyclerCitas);
        searchView = getView().findViewById(R.id.searchCitas);
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                cargarCitasBusqueda(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getView().getContext(),LinearLayout.VERTICAL,false));
        cargarCitas();
        FloatingActionButton fab = getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarCitasProximas();
            }
        });
    }

    public void cargarCitas(){
        final ProgressDialog dialogo = new ProgressDialog(myFragmentView.getContext());
        dialogo.setMessage("Descargando Información");
        dialogo.show();
        Ion.with(myFragmentView.getContext())
                .load(Globales.URL+"api/Cita/GetCitasToAndroid?nombre="+Globales.nombreUsuario)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        TypeToken<List<CitaPojo>> listaCitasJson= new TypeToken<List<CitaPojo>>(){};
                        citas = gson.fromJson(result, listaCitasJson.getType());
                        elementos = new String[citas.size()];
                        /*for(int i=0; i<citas.size(); i++){
                            //Log.d("String",lista.get(i).getEmail());
                            elementos[i] = citas.get(i).getFechaCita()+"\n"+citas.get(i).getObservaciones();
                        }*/
                        dialogo.dismiss();
                        inicializarLista();
                    }
                });
    }
    private void inicializarLista(){
        AdapterCitas adapterCitas = new AdapterCitas(citas);
        recyclerView.setAdapter(adapterCitas);
        /*ArrayAdapter<String> miAdaptador = new ArrayAdapter<String>(myFragmentView.getContext(), android.R.layout.simple_list_item_1, elementos);
        listaCitas.setAdapter(miAdaptador);*/
    }
    public void cargarCitasProximas(){
        getActivity().setTitle("Citas Proximas");
        final ProgressDialog dialogo = new ProgressDialog(myFragmentView.getContext());
        dialogo.setMessage("Descargando Información");
        dialogo.show();
        Ion.with(myFragmentView.getContext())
                .load(Globales.URL+"api/Cita/GetCitasToAndroidProximas?nombre="+Globales.nombreUsuario)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        TypeToken<List<CitaPojo>> listaCitasJson= new TypeToken<List<CitaPojo>>(){};
                        citas = gson.fromJson(result, listaCitasJson.getType());
                        elementos = new String[citas.size()];
                        /*for(int i=0; i<citas.size(); i++){
                            //Log.d("String",lista.get(i).getEmail());
                            elementos[i] = citas.get(i).getFechaCita()+"\n"+citas.get(i).getObservaciones();
                        }*/
                        dialogo.dismiss();
                        inicializarLista();
                    }
                });
    }
    public void cargarCitasBusqueda(String busqueda){
        getActivity().setTitle("Citas Proximas");
        final ProgressDialog dialogo = new ProgressDialog(myFragmentView.getContext());
        dialogo.setMessage("Descargando Información");
        dialogo.show();
        Ion.with(myFragmentView.getContext())
                .load(Globales.URL+"api/Cita/GetCitasToAndroidBusqueda?nombre="+Globales.nombreUsuario+"&fecha="+ busqueda)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        TypeToken<List<CitaPojo>> listaCitasJson= new TypeToken<List<CitaPojo>>(){};
                        citas = gson.fromJson(result, listaCitasJson.getType());
                        elementos = new String[citas.size()];
                        /*for(int i=0; i<citas.size(); i++){
                            //Log.d("String",lista.get(i).getEmail());
                            elementos[i] = citas.get(i).getFechaCita()+"\n"+citas.get(i).getObservaciones();
                        }*/
                        dialogo.dismiss();
                        inicializarLista();
                    }
                });
    }

}
