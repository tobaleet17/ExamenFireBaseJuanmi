package com.example.cristobal.activitatmanelevalua;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityLogin extends Activity {

    private EditText inputLogin;
    private EditText inputPassword;
    private Button inputEnviar;
    private Button inputRegistrar;

    ArrayList<String> Usuarios = new ArrayList<String>();
    ArrayList<String> Password = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //ArrayList<String> lista = (ArrayList<String>) getIntent().getSerializableExtra("pepe");



        Usuarios.add("Manel");
        Password.add("prueba");
        inputLogin = (EditText) findViewById(R.id.input_Login);
        inputPassword = (EditText) findViewById(R.id.input_Password);
        inputEnviar = (Button) findViewById(R.id.boton_enviar);
        inputRegistrar = (Button) findViewById(R.id.boton_registrar);

        inputEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = inputLogin.getText().toString();
                String pass = inputPassword.getText().toString();
                if (Usuarios.contains(login)== true && Password.contains(pass)==true){
                    Intent itemintent = new Intent(getApplicationContext(), ActivityMessage.class);
                    ActivityLogin.this.startActivity(itemintent);
                    Usuario u1=new Usuario();
                }

            }
        });

        inputRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itemintent = new Intent(getApplicationContext(), RegisterActivity.class);
                ActivityLogin.this.startActivity(itemintent);
            }
        });
    }


}
