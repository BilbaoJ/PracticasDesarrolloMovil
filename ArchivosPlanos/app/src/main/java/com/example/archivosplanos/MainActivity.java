package com.example.archivosplanos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText etPregunta, etRes1, etRes2, etRes3, etResCorrecta;
    Button btnGuardar;
    TextView tvInfo;
    String pregunta, res1, res2, res3, resCorrecta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        conectar();

        ArchivosPlanos objAP = new ArchivosPlanos(this);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pregunta = etPregunta.getText().toString();
                res1 = etRes1.getText().toString();
                res2 = etRes2.getText().toString();
                res3 = etRes3.getText().toString();
                resCorrecta = etResCorrecta.getText().toString();

                try {
                    etPregunta.setText("");
                    etRes1.setText("");
                    etRes2.setText("");
                    etRes3.setText("");
                    etResCorrecta.setText("");

                    objAP.Escribir(pregunta);
                    objAP.Escribir(res1);
                    objAP.Escribir(res2);
                    objAP.Escribir(res3);
                    objAP.Escribir(resCorrecta);
                    tvInfo.setText(objAP.Leer());

                }catch (IOException e){

                    e.getMessage();
                }
            }
        });
    }

    private void conectar() {
        etPregunta = findViewById(R.id.etPregunta);
        btnGuardar = findViewById(R.id.btnGuardar);
        tvInfo = findViewById(R.id.tvInfo);
        etRes1 = findViewById(R.id.etRes1);
        etRes2 = findViewById(R.id.etRes2);
        etRes3 = findViewById(R.id.etRes3);
        etResCorrecta = findViewById(R.id.etResCorrecta);
    }
}