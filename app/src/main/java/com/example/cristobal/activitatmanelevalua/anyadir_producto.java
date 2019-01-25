package com.example.cristobal.activitatmanelevalua;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cristobal.activitatmanelevalua.model.Producto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class anyadir_producto extends AppCompatActivity {
    Spinner spCategorias;
    DatabaseReference bbdd2;
    EditText Nombre,Descripcion,Precio;
    Button anyadir,listar,salir;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anyadir_producto);

        Nombre = (EditText) findViewById(R.id.edit_Nombre);
        Descripcion = (EditText) findViewById(R.id.edit_Descripcion);
        Precio = (EditText) findViewById(R.id.edit_Precio);
        anyadir = (Button) findViewById(R.id.btnAñadirProducto);
        listar = (Button) findViewById(R.id.btnListarProducto);
        salir = (Button) findViewById(R.id.btnSalirAnyadir);

        spCategorias = (Spinner) findViewById(R.id.spCategorias);
        cargarSpinner();

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        anyadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                String nombre = Nombre.getText().toString();
                String descripcion = Descripcion.getText().toString();
                String categoria = spCategorias.getSelectedItem().toString();
                String precio = Precio.getText().toString();

                String id = user.getUid();
                Toast.makeText(anyadir_producto.this, "El usuario "+user.getUid(),
                        Toast.LENGTH_LONG).show();




                if (!TextUtils.isEmpty(nombre) || !TextUtils.isEmpty(descripcion) || !TextUtils.isEmpty(categoria)|| !TextUtils.isEmpty(precio)){


                    bbdd2 = FirebaseDatabase.getInstance().getReference("Productos");

                    Producto p1 = new Producto(precio,nombre,descripcion,id,categoria);
                    String clave = bbdd2.push().getKey();
                    bbdd2.child(clave).setValue(p1);



                }

            }
        });

        listar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentListar = new Intent(getApplicationContext(),ModificarProducto.class);

                startActivity(intentListar);



            }
        });

        }





    public void cargarSpinner() {
        ArrayAdapter<String> adaptador;
        ArrayList<String> listadoCategorias = new ArrayList<String>();

        listadoCategorias.add("Coches");
        listadoCategorias.add("Tecnología");
        listadoCategorias.add("Hogar");

        adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listadoCategorias);
        spCategorias.setAdapter(adaptador);

    }

}

