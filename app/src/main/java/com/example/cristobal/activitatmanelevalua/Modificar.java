package com.example.cristobal.activitatmanelevalua;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cristobal.activitatmanelevalua.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Modificar extends AppCompatActivity {

    EditText EditUser, NombreNuevo;
    Button btnElim, btnSeguir, btnCanc;
    CheckBox actEditUser;
    ListView lista;
    DatabaseReference bbdd;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);

        bbdd = FirebaseDatabase.getInstance().getReference("Usuarios");
        btnCanc = (Button) findViewById(R.id.btn_Canc);
        btnSeguir = (Button) findViewById(R.id.btn_Seguir);
        btnElim = (Button) findViewById(R.id.btn_Elim);
        EditUser = (EditText) findViewById(R.id.EditUserNam);
        NombreNuevo = (EditText) findViewById(R.id.ModNom);
        actEditUser = (CheckBox) findViewById(R.id.checkBox);
        lista = (ListView) findViewById(R.id.listViewRegistrados);

        //Ponemos un check para cuando vayamos a eliminar que no nos de problemas y así quitar el campo que tenemos para poner el nuevo nombre.
        actEditUser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!actEditUser.isChecked()) {

                    NombreNuevo.setEnabled(true);

                } else {

                    NombreNuevo.setEnabled(false);
                }


            }
        });
        btnSeguir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();


                String cadenaCogemos = EditUser.getText().toString();

                if (!TextUtils.isEmpty(cadenaCogemos)) {

                    Query q = bbdd.orderByChild("nombre").equalTo(cadenaCogemos);

                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            //EL dataSnap contiene los UUID de los hijos, de cada usuario su identificador.
                            for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                String key = dataSnap.getKey();
                               // bbdd.child(key).child("nombre").setValue(NombreNuevo.getText().toString());
                                if (NombreNuevo.getText().toString().equals("Si"))
                                {
                                    bbdd.child(key).child("seguir").setValue(NombreNuevo.getText().toString() + " lo sigue " +user.getUid() );
                                }else{
                                    bbdd.child(key).child("seguir").setValue(NombreNuevo.getText().toString() + " lo sigue " );
                                }

                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
                if (NombreNuevo.getText().toString().equals("Si"))
                {
                    Toast.makeText(getApplicationContext(), "Se ha COMENZADO a seguir a " + cadenaCogemos , Toast.LENGTH_LONG).show();
                }else
                {
                    Toast.makeText(getApplicationContext(), "Se ha DEJADO DE seguir a " + cadenaCogemos , Toast.LENGTH_LONG).show();
                }



            }
        });

        btnElim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cadenaCogemos = EditUser.getText().toString();

                if (!TextUtils.isEmpty(cadenaCogemos)) {

                    //"nombre" es el campo que queremos identificar en firebase

                    Query q = bbdd.orderByChild("nombre").equalTo(cadenaCogemos);

                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            //EL dataSnap contiene los UUID de los hijos, de cada usuario su identificador.
                            for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                                String key = dataSnap.getKey();
                                DatabaseReference ref = bbdd.child(key); //aquí nos quedamos con el nodo que queremos eliminar.

                                ref.removeValue();


                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
                Toast.makeText(getApplicationContext(), "Se ha eliminado el user " + cadenaCogemos, Toast.LENGTH_LONG).show();


            }
        });

            //CARGAMOS EL LIST VIEW PARA SABER EN TODO MOMENTO EL LISTADO DE USUARIOS....-->
        bbdd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mAuth = FirebaseAuth.getInstance();

            //NOS CREAMOS UN ARRAY ADAPTER PARA MOSTRARLO SOBRE EL LIST VIEW
                ArrayAdapter<String> adapter;
                ArrayList<String> listado = new ArrayList<>();

                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {

                    FirebaseUser user = mAuth.getCurrentUser();
                    Usuario usuario = datasnapshot.getValue(Usuario.class);


                    String nombreUsuario = Integer.toString(usuario.getUserID());

                    String correo = usuario.getEmail();
                    String nombre = usuario.getNombre();
                    String apellidos = usuario.getApellidos();
                    String seguir = usuario.getSeguir();

                    String sCadena2 = usuario.getSeguir().toString();
                    String subCadena2 = sCadena2.substring(0,12);

                    if (!usuario.getSeguir().isEmpty() ){

                        if (subCadena2.compareTo("No lo sigue ")==0)
                        {
                            listado.add("no sigue a nadie");
                        }else {
//subida juanmi
                            String sCadena = usuario.getSeguir().toString();
                            String subCadena = sCadena.substring(12,40);
                            Toast.makeText(getApplicationContext(), "la sub " + subCadena, Toast.LENGTH_LONG).show();
                            if (subCadena.compareTo(user.getUid())==0){
                                listado.add("NOMBRE USUARIO:" + nombreUsuario);
                                listado.add("NOMBRE: " + nombre);
                                listado.add("LO SIGUES: " + seguir);
                                listado.add("APELLIDOS: " + apellidos);
                                listado.add("DIRECCION: " + correo);
                                listado.add("<------------------------------------->");

                            }else{
                                listado.add("sin nada");
                            }
                        }

                    }







                }
                adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listado);
                lista.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    }

