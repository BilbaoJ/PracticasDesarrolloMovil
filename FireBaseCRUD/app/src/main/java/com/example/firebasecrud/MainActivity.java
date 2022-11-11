package com.example.firebasecrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    EditText etNombre, etTelefono, etEdad, etID;
    Button btnInsertar, btnListar, btnActualizar, btnEliminar;
    DatabaseReference db;
    ArrayList<String> listaContactos = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ListView lvContactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        conectar();
        db = FirebaseDatabase.getInstance().getReference();

        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertar();

            }
        });

        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listar();
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizar();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminar();
            }
        });

        lvContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String idActual = lvContactos.getItemAtPosition(i).toString();
                String[] datos = idActual.split(" ");
                String id = datos[0];
                String nombre = datos[1];
                String telefono = datos[2];
                String edad = datos[3];
                etID.setText(id);
                etNombre.setText(nombre);
                etTelefono.setText(telefono);
                etEdad.setText(edad);
            }
        });
    }

    private void insertar(){

        String id = UUID.randomUUID().toString();
        String nombre = etNombre.getText().toString();
        String telefono = etTelefono.getText().toString();
        int edad = Integer.parseInt(etEdad.getText().toString());
        Contacto nuevoContacto = new Contacto(nombre, telefono, edad, id);
        db.child("Contacto").child(nuevoContacto.getId()).setValue(nuevoContacto);
        Toast.makeText(this, "El contacto se guardó con exito", Toast.LENGTH_LONG).show();

    }

    private void listar(){
        db.child("Contacto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaContactos.clear();
                for (DataSnapshot objsnapshot: snapshot.getChildren()){
                    String contacto = objsnapshot.child("id").getValue().toString()
                            + " " + objsnapshot.child("nombre").getValue().toString()
                            + " " + objsnapshot.child("telefono").getValue().toString()
                            + " " + objsnapshot.child("edad").getValue().toString();
                    listaContactos.add(contacto);
                }
                adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,
                        listaContactos);
                lvContactos.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void actualizar(){
        String id = etID.getText().toString();
        String nombre = etNombre.getText().toString();
        String telefono = etTelefono.getText().toString();
        int edad = Integer.parseInt(etEdad.getText().toString());
        Contacto contactoActualizado = new Contacto(nombre, telefono, edad, id);
        db.child("Contacto").child(id).setValue(contactoActualizado);
        Toast.makeText(this, "El contacto se actualizó con exito", Toast.LENGTH_LONG).show();
    }

    private void eliminar(){
        String id = etID.getText().toString();
        String nombre = etNombre.getText().toString();
        String telefono = etTelefono.getText().toString();
        int edad = Integer.parseInt(etEdad.getText().toString());
        Contacto contactoEliminar = new Contacto(nombre, telefono, edad, id);
        db.child("Contacto").child(id).removeValue();
        Toast.makeText(this, "El contacto se eliminó con exito", Toast.LENGTH_LONG).show();
    }

    private void conectar() {
        etNombre = findViewById(R.id.etNombre);
        etTelefono = findViewById(R.id.etTelefono);
        etEdad = findViewById(R.id.etEdad);
        etID = findViewById(R.id.etID);
        btnInsertar = findViewById(R.id.btnInsertar);
        btnListar = findViewById(R.id.btnListar);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);
        lvContactos = findViewById(R.id.lvContactos);
    }
}