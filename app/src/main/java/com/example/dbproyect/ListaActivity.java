package com.example.dbproyect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListaActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lista;
    ArrayList<String> listausuarios;
    ArrayList<Usuarios> datosusuario; //ArrayList de la clase Usuarios.java
    Conexion conexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        setTitle("Lista de usuarios");

        lista = findViewById(R.id.ltvlista);
        mostrar();
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listausuarios);
        lista.setAdapter(aa);
        lista.setOnItemClickListener(this);

    }

    private void mostrar() {
        conexion = new Conexion(this, Variables.NOMBRE_DB, null, 1);
        SQLiteDatabase db = conexion.getReadableDatabase();
        Usuarios usuario = null;
        datosusuario = new ArrayList<Usuarios>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+Variables.NOMBRE_TABLA, null);
        while(cursor.moveToNext()){
            usuario = new Usuarios();
            usuario.setId(cursor.getInt(0));
            usuario.setNombre(cursor.getString(1));
            usuario.setTelefono(cursor.getString(2));

            datosusuario.add(usuario); //Array de objetos tipo Usuario
        }
        agregaralista();
        db.close();
    }

    private void agregaralista() {
        listausuarios = new ArrayList<String>();
        for (int i = 0; i < datosusuario.size(); i++) {
            listausuarios.add(datosusuario.get(i).getId()+" - "+datosusuario.get(i).getNombre());
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Usuarios usuario = datosusuario.get(i);
        Intent ii = new Intent(this, DetalleActivity.class);
        Bundle b =  new Bundle();
        b.putSerializable("usuario", usuario); //Empaquetar el objeto con la etiqueta usuario
        ii.putExtras(b);
        startActivity(ii);
    }
}