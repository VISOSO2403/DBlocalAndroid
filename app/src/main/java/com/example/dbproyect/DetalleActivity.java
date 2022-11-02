package com.example.dbproyect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetalleActivity extends AppCompatActivity {

    TextView id, nombre, telefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        setTitle("Detalles de usuario");

        id = findViewById(R.id.txtid);
        nombre = findViewById(R.id.txtnombre);
        telefono = findViewById(R.id.txttelefono);

        Bundle objeto = getIntent().getExtras(); //Recibimos objetos
        Usuarios usu = null;

        if(objeto != null) {
            usu = (Usuarios) objeto.getSerializable("usuario");
            id.setText("id: "+ usu.getId().toString());
            nombre.setText("Nombre: "+ usu.getNombre());
            telefono.setText("Telefono: "+ usu.getTelefono());
        }
    }
}