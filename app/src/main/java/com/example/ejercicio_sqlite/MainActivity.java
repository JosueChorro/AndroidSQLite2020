package com.example.ejercicio_sqlite;

import android.os.Bundle;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;

public class MainActivity extends AppCompatActivity {

    private EditText et_codigo, et_descripcion, et_precio;
    private Button btn_guardar, btn_consultar1, btn_consultar2, btn_eliminar, btn_actualizar;
    private TextView tv_resultado;

    boolean inputEt = false;
    boolean inputEd = false;
    boolean input1 = false;
    int resultadolnsert = 0;

    Modal ventanas = new Modal();
    Base conexion = new Base(this);
    Dto datos = new Dto();
    AlertDialog.Builder dialogo;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new android.app.AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_salir)
                    .setTitle("Warning")
                    .setMessage("¿Realmente desea salir?")
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishAffinity();
                        }
                    }).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedlnstancestate) {
        super.onCreate(savedlnstancestate);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_atras));
        toolbar.setTitleTextColor(getResources().getColor(R.color.letra));
        toolbar.setTitleMargin(0, 0, 0, 0);
        toolbar.setSubtitle("CRUD SQLite-2020");
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.letra));
        toolbar.setTitle("Jc_Aries");
        setSupportActionBar(toolbar);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmacion();
            }
        });

        final FloatingActionsMenu fab = findViewById(R.id.fab);
        final FloatingActionButton fab1 = findViewById(R.id.item1);
        final FloatingActionButton fab2 = findViewById(R.id.item2);
        final FloatingActionButton fab3 = findViewById(R.id.item3);
        final FloatingActionButton fab4 = findViewById(R.id.item4);
        final FloatingActionButton fab5 = findViewById(R.id.item5);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmacion();
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ventanas.Search(MainActivity.this);
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consultapordescripcion();
            }
        });

        fab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modificacion();
            }
        });

        fab5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bajaporcodigo();
            }
        });

        et_codigo = (EditText) findViewById(R.id.content_codigo);
        et_descripcion = (EditText) findViewById(R.id.content_descripcion);
        et_precio = (EditText) findViewById(R.id.content_precio);
        btn_guardar = (Button) findViewById(R.id.btn_guardar);
        //tv resultado —— (TextView) findViewByld(R.id.tv resultado),’
        String senal = "";
        String codigo = "";
        String descripcion = "";
        String precio = "";

        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                codigo = bundle.getString("codigo");
                senal = bundle.getString("senal");
                descripcion = bundle.getString("descripcion");
                precio = bundle.getString("precio");
                if (senal.equals("1")) {
                    et_codigo.setText(codigo);
                    et_descripcion.setText(descripcion);
                    et_precio.setText(precio);
                }
            }
        } catch (Exception e) {

        }
    }

    private void confirmacion() {
        String mensaje = "¿Realmente desea salir?";
        dialogo = new AlertDialog.Builder(MainActivity.this);
        dialogo.setIcon(R.drawable.ic_salir);
        dialogo.setTitle("Warning");
        dialogo.setMessage(mensaje);
        dialogo.setCancelable(false);
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo, int id) {
                MainActivity.this.finish();
            }
        });

        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo, int id) {
                //Toast. makeText(getApplicationContext(), "Operacion Cancelada.", Toast. LENGTH LONG). show(),’
            }
        });
        dialogo.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu,’ this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId(); 

        if (id == R.id.action_limpiar){
            et_codigo.setText(null);
            et_descripcion.setText(null);
            et_precio.setText(null);
            return true;
        }else if(id == R.id.action_listarArticulos){
            Intent spinnerActivity = new Intent(MainActivity.this, ConsultaSpinner.class);
            startActivity(spinnerActivity);
            return true;
        }else if(id == R.id.action_listarArticulos1) {
            Intent listViewActivity = new Intent(MainActivity.this, ListViewArticulos.class);
            startActivity(listViewActivity);
            return true;
        }else if(id == R.id.recycler) {
            Intent re = new Intent(MainActivity.this, ConsultaRecyclerView.class);
            startActivity(re);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void alta(View v) {
        if (et_codigo.getText().toString().length() == 0) {
            et_codigo.setError("Campo obligatorio");
            inputEt = false;
        } else {
            inputEt = true;
        }
        if (et_descripcion.getText().toString().length() == 0) {
            et_descripcion.setError("Campo obligatorio");
            inputEd = false;
        } else {
            inputEd = true;
        }
        if (et_precio.getText().toString().length() == 0) {
            et_precio.setError("Campo obligatorio");
            input1 = false;
        } else {
            input1 = true;
        }
        if (inputEt == true && inputEd== true && input1== true) {
            try {
                datos.setCodigo(Integer.parseInt(et_codigo.getText().toString()));
                datos.setDescripcion(et_descripcion.getText().toString());
                datos.setPrecio(Double.parseDouble(et_precio.getText().toString()));
                if (conexion.insertardatos(datos) == true) {
                    Toast.makeText(this, "Registro agregado satisfactoriamente!", Toast.LENGTH_SHORT).show();
                    limpiarDatos();
                } else {
                    Toast.makeText(getApplicationContext(), "Error. Ya existe un registro\n" +
                            " Codigo: " + et_codigo.getText().toString(), Toast.LENGTH_LONG).show();
                    limpiarDatos();
                }
            } catch (Exception ex) {
                Toast.makeText(this, "ERROR. Ya existe.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void mensaje(String mensaje) {
        Toast.makeText(this, "" + mensaje, Toast.LENGTH_SHORT).show();
    }

    public void limpiarDatos() {
        et_codigo.setText(null);
        et_descripcion.setText(null);
        et_precio.setText(null);
        et_codigo.requestFocus();
    }

    public void consultaporcodigo() {
        if (et_codigo.getText().toString().length() == 0) {
            et_codigo.setError("Campo obligatorio");
            inputEt = false;
        } else {
            inputEt = true;
        }
        if (inputEt == true) {
            String codigo = et_codigo.getText().toString();
            datos.setCodigo(Integer.parseInt(codigo));

            if (conexion.consultaArticulos(datos) == true) {
                et_descripcion.setText(datos.getDescripcion());
                et_precio.setText("" + datos.getPrecio());
            } else {
                Toast.makeText(this, "No existe un articulo con dicho codigo", Toast.LENGTH_SHORT).show();
                limpiarDatos();
            }
        } else {
            Toast.makeText(this, "Ingrese el codigo del articulo a buscar.", Toast.LENGTH_SHORT).show();
        }
    }

    public void consultapordescripcion() {
        if (et_descripcion.getText().toString().length() == 0) {
            et_descripcion.setError("Campo obligatorio");
            inputEd = false;
        } else {
            inputEd = true;
        }
        if (inputEd== true) {
            String descripcion = et_descripcion.getText().toString();
            datos.setDescripcion(descripcion);
            if (conexion.consultarDescripcion(datos) == true) {
                et_codigo.setText("" + datos.getCodigo());
                et_descripcion.setText(datos.getDescripcion());
                et_precio.setText("" + datos.getPrecio());
            } else {
                Toast.makeText(this, "No existe un articulo con dicha descripcion", Toast.LENGTH_SHORT).show();
                limpiarDatos();
            }
        } else {
            Toast.makeText(this, "Ingrese la descripcion del articulo a buscar.", Toast.LENGTH_SHORT).show();
        }
    }

    public void bajaporcodigo() {
        if (et_codigo.getText().toString().length() == 0) {
            et_codigo.setError("campo obligatorio");
            inputEt = false;
        } else {
            inputEt = true;
        }
        if (inputEt == true) {
            String cod = et_codigo.getText().toString();
            datos.setCodigo(Integer.parseInt(cod));
            if (conexion.bajaCodigo(MainActivity.this, datos) == true) {
                limpiarDatos();
            } else {
                Toast.makeText(this, "No existe un articulo con dicho codigo.", Toast.LENGTH_SHORT).show();
                limpiarDatos();
            }
        }
    }

    public void modificacion() {
        if (et_codigo.getText().toString().length() == 0) {
            et_codigo.setError("campo obligatorio");
            inputEt = false;
        } else {
            inputEt = true;
        }
        if (inputEt == true) {

            String cod = et_codigo.getText().toString();
            String descripcion = et_descripcion.getText().toString();
            double precio = Double.parseDouble(et_precio.getText().toString());
            datos.setCodigo(Integer.parseInt(cod));
            datos.setDescripcion(descripcion);
            datos.setPrecio(precio);
            if (conexion.modificar(datos) == true) {
                Toast.makeText(this, "Registro Modificado Correctamente.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No se han encontrado resultados para la busqueda especificada.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}