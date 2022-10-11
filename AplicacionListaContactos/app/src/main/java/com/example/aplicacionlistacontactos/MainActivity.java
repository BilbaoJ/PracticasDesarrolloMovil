package com.example.aplicacionlistacontactos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    EditText etNombre, etApellido, etCorreo, etEstatura;
    Button btnAgregar, btnEliminar, btnMasinfo;
    ListView lvContactos;
    ArrayList<String> misContactos = new ArrayList<>();
    ArrayList<Contactos> misContactosC = new ArrayList<>();
    ArrayAdapter<String> adapter;
    String strNombre, strApellido, strCorreo;
    int intEstatura;
    int elementoE;
    int promedio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        conectar();
        calcularPromedio();

        lvContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                elementoE = i;
                btnEliminar.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "Contacto seleccionado", Toast.LENGTH_LONG).show();
            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strNombre = etNombre.getText().toString();
                strApellido = etApellido.getText().toString();
                strCorreo = etCorreo.getText().toString();
                intEstatura = Integer.valueOf(etEstatura.getText().toString());
                Contactos c = new Contactos(strNombre,strApellido,strCorreo,intEstatura);
                misContactosC.add(c);

                Collections.sort(misContactosC, new Comparator<Contactos>() {
                    @Override
                    public int compare(Contactos contacto1, Contactos contacto2) {
                        return new Integer(contacto1.getIntEstatura()).compareTo(new Integer(contacto2.getIntEstatura()));
                    }
                });

                misContactos.clear();

                for(Contactos contacto: misContactosC){
                    misContactos.add(contacto.toString());
                };

                adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, misContactos);
                lvContactos.setAdapter(adapter);
                limpiarCampos();
                Toast.makeText(getApplicationContext(), "Contacto Agregado", Toast.LENGTH_LONG).show();
            }

        });


        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                misContactos.remove(elementoE);
                misContactosC.remove(elementoE);
                calcularPromedio();
                adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, misContactos);
                lvContactos.setAdapter(adapter);
                btnEliminar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Contacto Eliminado", Toast.LENGTH_LONG).show();
            }
        });

        btnMasinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(getApplicationContext(), Estadisticas.class);
                calcularPromedio();
                I.putExtra("promedio", promedio);

                ArrayList<String> altos = encontrarContactosAltos();
                I.putExtra("contactosAltos", altos);

                startActivity(I);
            }
        });

    }

    private ArrayList<String> encontrarContactosAltos(){

        ArrayList<String> contactosAltos = new ArrayList<>();
        for(Contactos contacto: misContactosC){
            if (contacto.getIntEstatura() >= promedio){
                contactosAltos.add(contacto.toString());
            }
        };
        return  contactosAltos;
    }

    private void limpiarCampos(){
        etNombre.setText("");
        etApellido.setText("");
        etCorreo.setText("");
        etEstatura.setText("");
    }


    private void calcularPromedio() {
        int suma = 0;
        if (misContactosC.size() > 0){
            for(Contactos contacto: misContactosC){
                suma += contacto.getIntEstatura();
            };
            promedio = suma / misContactosC.size();
        }else {
            promedio = 0;
        }

    }

    private void conectar() {

        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etCorreo = findViewById(R.id.etCorreo);
        etEstatura = findViewById(R.id.etEstatura);
        btnAgregar = findViewById(R.id.btnAgregar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnMasinfo = findViewById(R.id.btnMasinfo);
        lvContactos = findViewById(R.id.lvContactos);

    }
}