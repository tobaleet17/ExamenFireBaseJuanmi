package com.example.cristobal.activitatmanelevalua;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

public class ActivityLogin extends Activity {

    private EditText inputLogin;
    private EditText inputPassword;
    private Button inputEnviar;
    private Button inputRegistrar;
    private ArrayList<Usuario> Usuarios = new ArrayList<Usuario>();
    String mensaje="ERROR";
    String mensaje2="ACCESO PERMITIDO";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Usuario u2= new Usuario(654321098,"mviel@florida-uni.es","1234,","Manel","Viel",1);
        Usuarios.add(u2);


        inputLogin = (EditText) findViewById(R.id.input_Login);
        inputPassword = (EditText) findViewById(R.id.input_Password);
        inputEnviar = (Button) findViewById(R.id.boton_enviar);
        inputRegistrar = (Button) findViewById(R.id.boton_registrar);

        inputEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = inputLogin.getText().toString();
                String pass = inputPassword.getText().toString();

                Iterator<Usuario> iter1= Usuarios.iterator();

                while (iter1.hasNext())
                {
                    Usuario u1 = (Usuario) iter1.next();

                    if ((login.compareTo(u1.getEmail())==0) && (pass.compareTo(u1.getPass())==0)){


                        Intent itemintent = new Intent(getApplicationContext(), ActivityMessage.class);
                        ActivityLogin.this.startActivity(itemintent);
                        Toast.makeText(getApplicationContext(), ""+mensaje2, Toast.LENGTH_SHORT).show();
                    }else{

                        Toast.makeText(getApplicationContext(), ""+mensaje, Toast.LENGTH_SHORT).show();

                    }

                }


            }
        });

        inputRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itemintent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivityForResult(itemintent,1); //Aqúi en el RequestCode le indico el número de mi subactivity

            }
        });
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RegisterActivity.RESULT_OK){

             Usuario u2 = data.getExtras().getParcelable("user"); //Aquí con el data del intent del Register cogemos los datos

            Usuarios.add(u2);

        }




    }


}
