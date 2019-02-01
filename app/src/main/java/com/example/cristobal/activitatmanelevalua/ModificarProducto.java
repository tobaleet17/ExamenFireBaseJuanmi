package com.example.cristobal.activitatmanelevalua;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cristobal.activitatmanelevalua.model.Producto;
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

public class ModificarProducto extends AppCompatActivity {
    DatabaseReference bbdd;
    ListView lista;
    Button btnEliminar,btnSalir;
    EditText editEliminar;
    Producto prod;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_producto);
        lista = (ListView) findViewById(R.id.listViewProductos);
        btnEliminar = (Button) findViewById(R.id.btn_ElimProd);
        btnSalir = (Button) findViewById(R.id.btn_SalirListar);
        editEliminar = (EditText) findViewById(R.id.editEliminar);


        bbdd = FirebaseDatabase.getInstance().getReference("Productos");


        bbdd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();


                //NOS CREAMOS UN ARRAY ADAPTER PARA MOSTRARLO SOBRE EL LIST VIEW
                ArrayAdapter<String> adapter;
                ArrayList<String> listado = new ArrayList<>();


                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {


                    Producto product = datasnapshot.getValue(Producto.class);

                    String precio = product.getPrecio();
                    String nombreProducto = product.getNombre();
                    String categoria = product.getCategoria();
                    //CON ESTE IF LO QUE HACEMOS ES QUE COMPARAMOS EL CAMPO USERID DE PRODUCTO CON EL GETUID DEL USUARIO LOGEADO
                    if (product.getUserID().compareTo(user.getUid())==0)
                    {
                        listado.add("NOMBRE PRODUCTO: " + nombreProducto);
                        listado.add("PRECIO: " + precio);
                        listado.add("CATEGORÍA: "+ categoria);
                        listado.add("<------------------------------------->");

                    }else{

                        listado.add("PRODUCTO DEL USUARIO: "+product.getUserID());
                        listado.add("<------------------------------------->");

                    }

                }
                adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listado);
                lista.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });



        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cadenacogemos es una variable donde almacenamos el valor que hay en el EditText
                String cadenaCogemos = editEliminar.getText().toString();

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
                            Toast.makeText(ModificarProducto.this, "Eliminado el producto ", Toast.LENGTH_LONG).show();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }


            }
        });

    }
}
