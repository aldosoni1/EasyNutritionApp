package com.example.aldo.easynutritionapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aldo.easynutritionapp.Models.ErrorToken;
import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class LoginActivity extends AppCompatActivity {

    EditText txtEmail, txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Easy Nutrition");
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        Button btnIngresar = findViewById(R.id.btnIngresar);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validaCampos();
            }
        });
    }
    private void validaCampos(){
        String valEmail = txtEmail.getText().toString();
        String valPassword = txtPassword.getText().toString();
        boolean isError=false;

        if (valEmail.isEmpty()){
            isError=true;
            txtEmail.setError("Email Requerido");
        }
        if (valPassword.isEmpty()){
            isError=true;
            txtPassword.setError("Password Requerido");
        }
        if (!isError){
            enviarLogin(txtEmail.getText().toString(), txtPassword.getText().toString());
        }

    }
    private void enviarLogin(final String email, String password){
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Enviando Información....");
        dialog.show();
        Ion.with(this).load("POST","http://192.168.92.201/api/token")
                .setBodyParameter("userName",email)
                .setBodyParameter("password",password)
                .setBodyParameter("grant_type","password")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        dialog.dismiss();
                        Gson gson = new Gson();
                        ErrorToken et = gson.fromJson(result,ErrorToken.class);
                        if (e!=null){
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }else {
                            if (et.getAccess_token()!=null)
                                Toast.makeText(LoginActivity.this,et.getAccess_token(), Toast.LENGTH_LONG).show();
                            else{
                                Toast.makeText(LoginActivity.this,et.getError_description(), Toast.LENGTH_LONG).show();
                                finish();
                                //Impelementar Vista
                            }
                        }
                    }
                });
    }
}
