package com.example.cristobal.activitatmanelevalua;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class RegisterActivity extends Activity {

    private EditText inptNombre;
    private EditText inptApellidos;
    private EditText inptEmail;
    private EditText inptTelefono;
    private EditText inptPassword;
    private EditText inptUser;
    private Button inptEnviar;
    private Button inptCancelar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        inptNombre = (EditText) findViewById(R.id.txtNombre);
        inptApellidos = (EditText) findViewById(R.id.txtApellidos);
        inptEmail = (EditText) findViewById(R.id.txtEmail);
        inptPassword = (EditText) findViewById(R.id.txtPass);
        inptTelefono = (EditText) findViewById(R.id.txtTelefon);
        inptUser = (EditText) findViewById(R.id.txtUserName);
        inptEnviar = (Button) findViewById(R.id.btnRegister);
        inptCancelar = (Button) findViewById(R.id.btnCancelar);


            inptEnviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (inptPassword.getText().toString().trim().isEmpty()
                            || inptEmail.getText().toString().isEmpty()
                            || inptApellidos.getText().toString().isEmpty()
                            || inptNombre.getText().toString().isEmpty()
                            || inptTelefono.getText().toString().isEmpty()
                            || inptUser.getText().toString().isEmpty()) {



                    } else {

                        Usuario u1=new Usuario(Integer.parseInt(inptTelefono.getText().toString()),inptEmail.getText().toString(),inptPassword.getText().toString(),inptNombre.getText().toString(),inptApellidos.getText().toString(),Integer.parseInt(inptUser.getText().toString()));

                        Intent cambioOK = new Intent();
                        cambioOK.putExtra("user",u1);
                        setResult(RESULT_OK,cambioOK);
                        finish();

                    }
                }
            });
            inptCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent cambioOK = new Intent();
                    setResult(RESULT_CANCELED,cambioOK);
                    finish();

                }
            });
        }
    }