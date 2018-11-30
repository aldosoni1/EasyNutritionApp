package com.example.aldo.easynutritionapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.aldo.easynutritionapp.Models.DietaCompleta;
import com.example.aldo.easynutritionapp.Models.Paciente;
import com.example.aldo.easynutritionapp.Utils.Globales;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


public class EditarInfoFragment extends Fragment {
    String[] generos = {"Masculino","Femenino"};
    EditText txtNombreEditar, txtFechaNacimiento, txtDireccion;
    Spinner spnGenero;
    Button btnEnviarInfos;
    View myFragmentView;
    Gson gson = new Gson();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragmentView = inflater.inflate(R.layout.fragment_editar_info, container, false);
        return inflater.inflate(R.layout.fragment_editar_info, container, false);
    }
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        txtDireccion= getActivity().findViewById(R.id.txtDireccion);
        txtFechaNacimiento=getActivity().findViewById(R.id.txtFechaNacimiento);
        txtNombreEditar=getActivity().findViewById(R.id.txtNombreEditar);
        btnEnviarInfos=getActivity().findViewById(R.id.btnEditar);
        spnGenero=getActivity().findViewById(R.id.spnGenero);
        cargarUsuario();
        iniciarSpiner();
        btnEnviarInfos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar();
            }
        });
    }

    private void cargarUsuario(){
        final ProgressDialog dialogo = new ProgressDialog(myFragmentView.getContext());
        dialogo.setMessage("Descargando Información");
        dialogo.show();
        Ion.with(myFragmentView.getContext())
                .load(Globales.URL+"api/Paciente/GetPacientesByEmail?email="+Globales.nombreUsuario)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        Paciente paciente = gson.fromJson(result, Paciente.class);
                        dialogo.dismiss();
                        txtNombreEditar.setText(paciente.getNombre());
                        txtDireccion.setText(paciente.getDomicilio());
                        txtFechaNacimiento.setText(paciente.getFechaNacimiento());
                        spnGenero.setSelection(getPosicion(paciente.getGenero()));

                    }
                });
    }
    private int getPosicion(String puesto){
        for (int i=0; i<generos.length; i++){
            if (generos[i].equals(puesto)){
                return i;
            }
        }
        return 0;
    }
    private void iniciarSpiner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(myFragmentView.getContext(),android.R.layout.simple_spinner_dropdown_item,generos);
        spnGenero.setAdapter(adapter);
    }
    private void validar(){
        String nombreAux = txtNombreEditar.getText().toString();
        String fechaNacimientoAux= txtFechaNacimiento.getText().toString();
        String direccionAux = txtDireccion.getText().toString();
        String generoAux = (String) spnGenero.getSelectedItem();

        Paciente paciente = new Paciente();
        paciente.setDomicilio(direccionAux);
        paciente.setFechaNacimiento(fechaNacimientoAux);
        paciente.setNombre(nombreAux);
        paciente.setGenero(generoAux);
        paciente.setEmail(Globales.nombreUsuario);
        editarPerfil(paciente);


    }

    private void editarPerfil(Paciente paciente){
        Gson gson = new Gson();
        String jsonUser= gson.toJson(paciente);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(jsonUser).getAsJsonObject();
        Ion.with(myFragmentView.getContext()).load("PUT",Globales.URL+"api/Paciente/EditarPacienteMovil").setJsonObjectBody(jsonObject).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                if (e !=null){
                    Toast.makeText(myFragmentView.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }else {
                    /*
                    Gson gson = new Gson();
                    Usuario ur = gson.fromJson(result,Usuario.class);*/
                    Toast.makeText(myFragmentView.getContext(), result,Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
