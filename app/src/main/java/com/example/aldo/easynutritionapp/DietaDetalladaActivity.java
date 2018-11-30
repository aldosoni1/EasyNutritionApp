package com.example.aldo.easynutritionapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.aldo.easynutritionapp.Models.DietaCompleta;
import com.example.aldo.easynutritionapp.Utils.Globales;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class DietaDetalladaActivity extends AppCompatActivity {
    public String dietaGuid ="";
    TextView txtNombre;
    ListView listaAlimentos;
    String[] elementos;
    Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dieta_detallada);
        txtNombre=findViewById(R.id.txtNombreDieta);
        dietaGuid=getIntent().getStringExtra("dietaGuid");
        listaAlimentos = findViewById(R.id.listaAlimentos);
        //txtNombre.setText(dietaGuid);
        CargarDieta();
    }

    private void CargarDieta(){
        final ProgressDialog dialogo = new ProgressDialog(this);
        dialogo.setMessage("Descargando Información");
        dialogo.show();
        Ion.with(DietaDetalladaActivity.this)
                .load(Globales.URL+"api/Dieta/GetDieta?dietaGuid="+dietaGuid)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        DietaCompleta dietaCompleta = gson.fromJson(result, DietaCompleta.class);
                        elementos = new String[dietaCompleta.getDietaAlimentos().size()];
                        for(int i=0; i<dietaCompleta.getDietaAlimentos().size(); i++){
                            //Log.d("String",lista.get(i).getEmail());
                            elementos[i] = "Alimento: "+dietaCompleta.getDietaAlimentos().get(i).getHoraDia()+"\n"+"Hora del día: "+dietaCompleta.getDietaAlimentos().get(i).getAlimento().getNombre()+"\n Cantidad: "+dietaCompleta.getDietaAlimentos().get(i).getCantidad()+"\n";
                        }
                        dialogo.dismiss();
                        txtNombre.setText(dietaCompleta.getNombre());
                        inicializarLista();
                    }
                });
    }
    private void inicializarLista(){
        ArrayAdapter<String> miAdaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, elementos);
        listaAlimentos.setAdapter(miAdaptador);
    }
}
