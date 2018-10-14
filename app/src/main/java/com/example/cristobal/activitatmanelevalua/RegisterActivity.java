package com.example.cristobal.activitatmanelevalua;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    private EditText inptNombre;
    private EditText inptApellidos;
    private EditText inptEmail;
    private EditText inptTelefono;
    private EditText inptPassword;
    private EditText inptUser;
    private Button inptEnviar;
    private Button inptCancelar;

    ArrayList<String> miLista = new ArrayList<String>();


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
        inptEnviar = (Button) findViewById(R.id.boton_enviar);
        inptCancelar = (Button) findViewById(R.id.btnCancelar);

        if (inptPassword.getText().toString().isEmpty()
                || inptEmail.getText().toString().isEmpty()
                || inptApellidos.getText().toString().isEmpty()
                || inptNombre.getText().toString().isEmpty()
                || inptTelefono.getText().toString().isEmpty()
                || inptUser.getText().toString().isEmpty()) {





        }else{
            inptEnviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("llegosssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
                    miLista.add(inptUser.getText().toString());
                    miLista.add(inptNombre.getText().toString());
                    miLista.add(inptApellidos.getText().toString());
                    miLista.add(inptEmail.getText().toString());
                    miLista.add(inptTelefono.getText().toString());
                    miLista.add(inptPassword.getText().toString());

                    Intent cambioOK = new Intent(getApplicationContext(),ActivityLogin.class);
                    //cambioOK.putExtra("pepe",miLista);
                    RegisterActivity.this.startActivity(cambioOK);
                    //Intent itemintent = new Intent(getApplicationContext(), ActivityMessage.class);
                    //RegisterActivity.this.startActivity(itemintent);

                }
            });
        }
    }
}