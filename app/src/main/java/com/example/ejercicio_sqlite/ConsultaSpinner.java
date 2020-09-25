package com.example.ejercicio_sqlite;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import  android.view.View;
import android.widget.AdapterView; 
import android.widget.ArrayAdapter; 
import android.widget.Spinner; 
import android.widget.TextView;
import java.util.ArrayList;

public class ConsultaSpinner extends AppCompatActivity { private Spinner sp_options;
    private TextView tv_cod, tv_descripcion, tv_precio;

    Base conexion = new Base(this);
    Dto datos = new Dto();

    @Override
    protected void onCreate(Bundle savedlnstancestate) {
        super.onCreate(savedlnstancestate);
        setContentView(R.layout.activity_consulta_spinner);

        sp_options = (Spinner)findViewById(R.id.sp_options);
        tv_cod = (TextView)findViewById(R.id.tv_cod);
        tv_descripcion = (TextView)findViewById(R.id.tv_descripcion);
        tv_precio = (TextView)findViewById(R.id.tv_precio);

        conexion.consultaListaArticulos();

        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this, android.R.layout.simple_spinner_item, conexion.obtenerListaArticulos());
        sp_options.setAdapter(adaptador);
        sp_options.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    tv_cod.setText("Codigo: " + conexion.consultaListaArticulos().get(i - 1).getCodigo());
                    tv_descripcion.setText("Descripcion: " + conexion.consultaListaArticulos().get(i - 1).getDescripcion());
                    tv_precio.setText("Precio: " + String.valueOf(conexion.consultaListaArticulos().get(i - 1).getPrecio()));
                } else {
                    tv_cod.setText("Codigo: ");
                    tv_descripcion.setText("Descripcion: ");
                    tv_precio.setText("Precio: ");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
}