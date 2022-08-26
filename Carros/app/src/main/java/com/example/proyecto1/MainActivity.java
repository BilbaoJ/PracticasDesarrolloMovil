package com.example.proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RadioGroup rgOpciones;
    CheckBox chkLuces, chkSonido, chkSuspension, chkVentanaP;
    Button btnFactura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        conectar();
        btnFactura.setOnClickListener(this);
        if (rgOpciones.getCheckedRadioButtonId()==R.id.rbSedan){

        }
        if (chkLuces.isChecked()){

        }
    }

    private void conectar() {
        chkLuces = findViewById(R.id.chkLuces);
        chkSonido = findViewById(R.id.chkSonido);
        chkSuspension = findViewById(R.id.chkSuspension);
        chkVentanaP = findViewById(R.id.chkVentanaP);
        rgOpciones = findViewById(R.id.rgOpciones);
        btnFactura = findViewById(R.id.btnFactura);


    }

    @Override
    public void onClick(View view) {

        Toast.makeText(getApplicationContext(), "Hola!",Toast.LENGTH_LONG).show();


    }
}