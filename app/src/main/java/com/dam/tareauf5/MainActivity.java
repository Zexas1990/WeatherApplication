package com.dam.tareauf5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static  final String CLAVE_LATITUD = "LAT";
    public static  final String CLAVE_LONGITUD = "LONG";

    EditText etLatitud;
    EditText etLongitud;
    Button btnConsultar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etLatitud = findViewById(R.id.etLatitud);
        etLongitud = findViewById(R.id.etLongitud);
        btnConsultar = findViewById(R.id.btnConsultar);

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String latitud = etLatitud.getText().toString().trim();
                String longitud = etLongitud.getText().toString().trim();

                if(latitud.isEmpty() || longitud.isEmpty()){
                    Toast.makeText(MainActivity.this, "No puede haber campos vacios", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(MainActivity.this, WetherActivity.class);
                    intent.putExtra(CLAVE_LATITUD, latitud);
                    intent.putExtra(CLAVE_LONGITUD, longitud);
                    startActivity(intent);
                }


            }
        });

    }
}