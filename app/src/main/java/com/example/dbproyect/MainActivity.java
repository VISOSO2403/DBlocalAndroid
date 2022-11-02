package com.example.dbproyect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText id, nombre, telefono;
    Button insertar1, insertar2, buscar1, buscar2, editar, eliminar, ver;
    Conexion conexion;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Control de usuarios");

        id = findViewById(R.id.etxtcampoid);
        nombre = findViewById(R.id.etxtnombre);
        telefono = findViewById(R.id.etxttelefono);

        insertar1 = findViewById(R.id.btninsertar1);
        insertar2 = findViewById(R.id.btninsertar2);
        buscar1 = findViewById(R.id.btnbuscar1);
        buscar2 = findViewById(R.id.btnbuscar2);
        editar = findViewById(R.id.btneditar);
        eliminar = findViewById(R.id.btneliminar);
        ver = findViewById(R.id.btnver);

        insertar1.setOnClickListener(this);
        insertar2.setOnClickListener(this);
        buscar1.setOnClickListener(this);
        buscar2.setOnClickListener(this);
        editar.setOnClickListener(this);
        eliminar.setOnClickListener(this);
        ver.setOnClickListener(this);

        //Primero conectamos con la DB
        conexion = new Conexion(this, Variables.NOMBRE_DB, null, 1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btninsertar1:
                insertar1();
                break;
            case R.id.btninsertar2:
                insertar2();
                break;
            case R.id.btnbuscar1:
                buscar1();
                break;
            case R.id.btnbuscar2:
                buscar2();
                break;
            case R.id.btneditar:
                editar();
                break;
            case R.id.btneliminar:
                eliminar();
                break;
            case R.id.btnver:
                    i = new Intent(MainActivity.this, ListaActivity.class);
                    startActivity(i);
                break;
        }
    }

    //METODOS
    private void insertar1() {
        SQLiteDatabase db = conexion.getWritableDatabase();

        //Primera forma de insertar datos a la base de datos
        ContentValues valores = new ContentValues();
        valores.put(Variables.CAMPO_NOMBRE, nombre.getText().toString());
        valores.put(Variables.CAMPO_TELEFONO, telefono.getText().toString());

        long id = db.insert(Variables.NOMBRE_TABLA, Variables.CAMPO_ID, valores);
        //Toast.makeText(this, "id: "+id, Toast.LENGTH_LONG).show();
        nombre.setText("");
        telefono.setText("");
        db.close();
    }

    private void insertar2() {
        SQLiteDatabase db = conexion.getWritableDatabase();

        //Segunda forma de insertar datos a la base de datos
        String insertar =  "INSERT INTO "+Variables.NOMBRE_TABLA+" ("+Variables.CAMPO_NOMBRE+
                ", "+Variables.CAMPO_TELEFONO+") VALUES ('"+nombre.getText().toString()
                +"','"+telefono.getText().toString()+"')";
        db.execSQL(insertar);
        nombre.setText("");
        telefono.setText("");
        db.close();
    }

    private void buscar1() {
        SQLiteDatabase db = conexion.getReadableDatabase();

        String[] pararametro = {id.getText().toString()};
        String[] campos = {Variables.CAMPO_NOMBRE, Variables.CAMPO_TELEFONO};

        try {
            Cursor cursor = db.query(Variables.NOMBRE_TABLA, campos,
                    Variables.CAMPO_ID+"=?", pararametro, null, null, null);
            cursor.moveToFirst();
            nombre.setText(cursor.getString(0));
            telefono.setText(cursor.getString(1));
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(this, "El usuario no existe", Toast.LENGTH_LONG).show();
            nombre.setText("");
            telefono.setText("");
            e.printStackTrace();
        }
        db.close();
    }

    private void buscar2() {
        SQLiteDatabase db = conexion.getReadableDatabase();
        String[] parametro = {id.getText().toString()};

        try {
            Cursor cursor = db.rawQuery("SELECT "+Variables.CAMPO_NOMBRE+", "+Variables.CAMPO_TELEFONO+
                    " FROM "+Variables.NOMBRE_TABLA+
                    " WHERE "+Variables.CAMPO_ID+"=?", parametro);
            cursor.moveToFirst();
            nombre.setText(cursor.getString(0));
            telefono.setText(cursor.getString(1));
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(this, "El usuario no existe", Toast.LENGTH_LONG).show();
            nombre.setText("");
            telefono.setText("");
            e.printStackTrace();
        }
        db.close();
    }

    private void editar() {
        SQLiteDatabase db = conexion.getWritableDatabase();

        String[] parametro = {id.getText().toString()};
        ContentValues valores = new ContentValues();

        valores.put(Variables.CAMPO_NOMBRE, nombre.getText().toString());
        valores.put(Variables.CAMPO_TELEFONO, telefono.getText().toString());
        db.update(Variables.NOMBRE_TABLA, valores, Variables.CAMPO_ID+"=?", parametro);
        Toast.makeText(this, "Datos actualizados", Toast.LENGTH_LONG).show();
        nombre.setText("");
        telefono.setText("");
        db.close();
    }

    private void eliminar() {
        SQLiteDatabase db = conexion.getWritableDatabase();

        String[] parametro = {id.getText().toString()};
        int n = db.delete(Variables.NOMBRE_TABLA,Variables.CAMPO_ID+"=?", parametro);
        //n regresa registros eliminados
        Toast.makeText(this, "Datos eliminados:"+n, Toast.LENGTH_LONG).show();
        nombre.setText("");
        telefono.setText("");
        db.close();
    }

}