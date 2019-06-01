package com.example.apperasmus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActivityEvaluacion extends AppCompatActivity {

    List<Pregunta> preguntas = new ArrayList<Pregunta>();
    TextView pregunta1, pregunta2, pregunta3, pregunta4, pregunta5, pregunta6, enunciado, etContador;
    TextView[] array_tv;
    Button botonAtras, botonSiguiente;
    int contador = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluacion);

        enunciado = (TextView) findViewById(R.id.enunciado);
        pregunta1 = (TextView) findViewById(R.id.pregunta1);
        pregunta2 = (TextView) findViewById(R.id.pregunta2);
        pregunta3 = (TextView) findViewById(R.id.pregunta3);
        pregunta4 = (TextView) findViewById(R.id.pregunta4);
        pregunta5 = (TextView) findViewById(R.id.pregunta5);
        pregunta6 = (TextView) findViewById(R.id.pregunta6);
        array_tv = new TextView[]{enunciado, pregunta1, pregunta2, pregunta3, pregunta4, pregunta5, pregunta6};


        etContador = (TextView) findViewById(R.id.contador);

        botonAtras = (Button) findViewById(R.id.boton1);
        botonSiguiente = (Button) findViewById(R.id.boton2);
        cargarPreguntas();
        botonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (botonSiguiente.getText().equals("Siguiente")) {

                    if (contador < 5) {
                        contador++;
                        cargarPreguntas();
                    } else {
                        botonSiguiente.setText(R.string.botonFinalizar);
                    }
                } else {

                }


            }
        });

        botonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (contador > 1) {
                    contador--;
                    cargarPreguntas();
                } else {
                    Toast.makeText(ActivityEvaluacion.this, "Esta en la primera pagina", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private void cargarPreguntas() {
        etContador.setText("" + contador);


        if (contador == 1) {
            String[] misPreguntas = getResources().getStringArray(R.array.preguntas1);
            cargarDatos(misPreguntas);
            recorrerArray();
        } else if (contador == 2) {
            String[] misPreguntas = getResources().getStringArray(R.array.preguntas2);
            cargarDatos(misPreguntas);
            recorrerArray();
        } else if (contador == 3) {
            String[] misPreguntas = getResources().getStringArray(R.array.preguntas3);
            cargarDatos(misPreguntas);
            recorrerArray();
        } else if (contador == 4) {
            String[] misPreguntas = getResources().getStringArray(R.array.preguntas4);
            cargarDatos(misPreguntas);
            recorrerArray();
        } else if (contador == 5) {
            String[] misPreguntas = getResources().getStringArray(R.array.preguntas5);
            cargarDatos(misPreguntas);
            recorrerArray();
        }
    }

    private void cargarDatos(String[] array) {
        for (int i = 0; i < array.length; i++) {
            Pregunta p = new Pregunta(array[i]);
            preguntas.add(p);
        }
    }

    private void recorrerArray() {
        enunciado.setText(this.preguntas.get(0).getTexto());


        for (int i = 0; i < preguntas.size(); i++) {

            array_tv[i].setText(preguntas.get(i).getTexto());


        }

        preguntas.clear();


    }
}
