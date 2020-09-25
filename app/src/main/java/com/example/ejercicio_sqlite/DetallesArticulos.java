package com.example.ejercicio_sqlite;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import org.w3c.dom.Text;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetallesArticulos extends AppCompatActivity {
    private TextView tv_codigo, tv_descripcion, tv_precio;
    private TextView tv_codigo1 , tv_descripcion1, tv_precio1, tv_fecha;

    @Override
    protected void onCreate(Bundle savedlnstancestate) {
        super.onCreate(savedlnstancestate);
        setContentView(R.layout.activity_detalles_articulos);
        tv_codigo = (TextView) findViewById(R.id.tv_codigo);
        tv_descripcion = (TextView) findViewById(R.id.tv_descripcion);
        tv_precio = (TextView) findViewById(R.id.tv_precio);

        tv_codigo1 = (TextView) findViewById(R.id.tv_codigo1);
        tv_descripcion1 = (TextView) findViewById(R.id.tv_descripcion1);
        tv_precio1 = (TextView) findViewById(R.id.tv_precio1);
        tv_fecha = (TextView) findViewById(R.id.tv_fecha);

        Bundle objeto = getIntent().getExtras();
        Dto dto = null;
        if (objeto != null) {
            dto = (Dto) objeto.getSerializable("articulo");
            tv_codigo.setText("" + dto.getCodigo());
            tv_descripcion.setText(dto.getDescripcion());
            tv_precio.setText(String.valueOf(dto.getPrecio()));
            tv_codigo1.setText("" + dto.getCodigo());
            tv_descripcion1.setText(dto.getDescripcion());
            tv_precio1.setText(String.valueOf(dto.getPrecio()));
            tv_fecha.setText("Fecha de creacién: " + getDateTime());
        }
    }

    private String getDateTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss a", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}