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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Modificar extends AppCompatActivity {

    EditText EditUser, NombreNuevo;
    Button btnElim, btnModifi, btnCanc;
    CheckBox actEditUser;
    ListView lista;
    DatabaseReference bbdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);

        bbdd = FirebaseDatabase.getInstance().getReference("Usuarios");
        btnCanc = (Button) findViewById(R.id.btn_Canc);
        btnModifi = (Button) findViewById(R.id.btn_Editar);
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
        btnElim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cadenaCogemos = EditUser.getText().toString();

                if (!TextUtils.isEmpty(cadenaCogemos)) {

                    Query q = bbdd.orderByChild("nombre").equalTo(cadenaCogemos);

                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            //EL dataSnap contiene los UUID de los hijos, de cada usuario su identificador.
                            for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                                String key = dataSnap.getKey();
                                bbdd.child(key).child("nombre").setValue(NombreNuevo.getText().toString());
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
                Toast.makeText(getApplicationContext(), "El nombre del user " + cadenaCogemos + " se ha modificado con éxito", Toast.LENGTH_LONG).show();


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

//NOS CREAMOS UN ARRAY ADAPTER PARA MOSTRARLO SOBRE EL LIST VIEW
                ArrayAdapter<String> adapter;
                ArrayList<String> listado = new ArrayList<>();

                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {

                    Usuario usuario = datasnapshot.getValue(Usuario.class);

                    String nombreUsuario = Integer.toString(usuario.getUserID());

                    String correo = usuario.getEmail();
                    String nombre = usuario.getNombre();
                    String apellidos = usuario.getApellidos();

                    listado.add("NOMBRE USUARIO:" + nombreUsuario);
                    listado.add("NOMBRE: " + nombre);
                    listado.add("APELLIDOS: " + apellidos);
                    listado.add("DIRECCION: " + correo);
                    listado.add("<------------------------------------->");
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

