package com.example.mycrud;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActualizarActivity extends AppCompatActivity {

    EditText etId, etTelefonoNuevo;
    Button btnActualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar);
        conectar();

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String telefonoNuevo = etTelefonoNuevo.getText().toString();
                int id = Integer.parseInt(etId.getText().toString());
                actualizarContacto(telefonoNuevo, id);
            }
        });
    }

    private void actualizarContacto(String telefonoNuevo, int id){
        DBHelper helper = new DBHelper(this, "Mis_Contactos", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        String SQL = "update Contactos set telefono = '"+ telefonoNuevo + "' where id = '"+ id + "'";
        db.execSQL(SQL);
        Toast.makeText(getApplicationContext(), "Contacto actualizado", Toast.LENGTH_LONG).show();
        db.close();
    }

    private void conectar() {
        etId = findViewById(R.id.etId);
        etTelefonoNuevo = findViewById(R.id.etTelefonoNuevo);
        btnActualizar = findViewById(R.id.btnActualizar);
    }
}