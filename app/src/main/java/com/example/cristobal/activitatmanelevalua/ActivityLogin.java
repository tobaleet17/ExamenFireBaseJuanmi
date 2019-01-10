package com.example.cristobal.activitatmanelevalua;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cristobal.activitatmanelevalua.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class ActivityLogin extends Activity {

    private EditText inputLogin;
    private EditText inputPassword;
    private Button inputEnviar;
    private Button inputRegistrar;
    private Button inputModificar;
    private ArrayList<Usuario> Usuarios = new ArrayList<Usuario>();
    private FirebaseAuth mAuth;
    private boolean comprobar=true;
    Usuario u2;
    String mensaje="ERROR";
    String mensaje2="ACCESO PERMITIDO";
    DatabaseReference bbdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);





        inputLogin = (EditText) findViewById(R.id.input_Login);
        inputPassword = (EditText) findViewById(R.id.input_Password);
        inputEnviar = (Button) findViewById(R.id.boton_enviar);
        inputRegistrar = (Button) findViewById(R.id.boton_registrar);
        inputModificar = (Button) findViewById(R.id.boton_modificar);

        inputModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i1 = new Intent(getApplicationContext(),Modificar.class);
                ActivityLogin.this.startActivity(i1);

            }
        });


        inputEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = inputLogin.getText().toString();
                String pass = inputPassword.getText().toString();



                login(login,pass);






                //Iterator<Usuario> iter1= Usuarios.iterator();

                //while (iter1.hasNext())
                //{
                  //  Usuario u1 = (Usuario) iter1.next();

                    //if ((login.compareTo(u1.getEmail())==0) && (pass.compareTo(u1.getPass())==0)){

                      //  Log.d("prueba","entra enviar");


                        //Toast.makeText(getApplicationContext(), ""+mensaje2, Toast.LENGTH_SHORT).show();
                    //}else{

                      //  Toast.makeText(getApplicationContext(), ""+mensaje, Toast.LENGTH_SHORT).show();

                    //}

                //}


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

             u2 = data.getExtras().getParcelable("user"); //Aquí con el data del intent del Register cogemos los datos

            Usuarios.add(u2);

            registrar(u2);

        }

        }

        private void registrar(final Usuario u2){

            mAuth = FirebaseAuth.getInstance();
            bbdd = FirebaseDatabase.getInstance().getReference("Usuarios");

            Query q = bbdd.orderByChild("nombre");

            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot aux: dataSnapshot.getChildren() ){

                        Usuario up = aux.getValue(Usuario.class);
                        String nomRecog = up.getNombre();
                        if (u2.getNombre().compareTo(nomRecog)==0){

                            Toast.makeText(ActivityLogin.this, "El usuario "+u2.getNombre()+" ya existe.",
                                    Toast.LENGTH_LONG).show();
                            comprobar = false;
                            break;

                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            mAuth.createUserWithEmailAndPassword(u2.getEmail(), u2.getPass())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information

                                FirebaseUser user = mAuth.getCurrentUser();


                                Toast.makeText(ActivityLogin.this, "Registro correcto. "+user.getUid(),Toast.LENGTH_LONG).show();
                                if (comprobar){

                                    String clave = user.getUid();

                                    bbdd.child(clave).setValue(u2); //con esto insertamos los datos del usuario.

                                }


                            } else {
                                // If sign in fails, display a message to the user.

                                Toast.makeText(ActivityLogin.this, "Authentication failed. "+task.getException(),
                                        Toast.LENGTH_LONG).show();

                            }


                        }
                    });

        }
        public void login(String email,String password){


            mAuth = FirebaseAuth.getInstance();
            bbdd = FirebaseDatabase.getInstance().getReference("Usuarios");

            Query q = bbdd.orderByChild("nombre");
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("prueba entrada", "signInWithEmail:success");

                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent itemintent = new Intent(getApplicationContext(), anyadir_producto.class);
                                ActivityLogin.this.startActivity(itemintent);
                                Toast.makeText(ActivityLogin.this, "Authentication correcta. "+user.getUid(),
                                        Toast.LENGTH_LONG).show();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("prueba fallida", "signInWithEmail:failure");
                                Toast.makeText(ActivityLogin.this, "Authentication failed. "+task.getException(),
                                        Toast.LENGTH_LONG).show();

                            }


                        }
                    });




        }

}
