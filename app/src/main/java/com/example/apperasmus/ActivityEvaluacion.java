package com.example.apperasmus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ActivityEvaluacion extends AppCompatActivity {

    List<Pregunta> preguntas = new ArrayList<>();
    List<Pregunta> respuestas = new ArrayList<>();
    TextView pregunta1, pregunta2, pregunta3, pregunta4, pregunta5, pregunta6, enunciado, etContador;
    TextView[] array_tv;
    Button botonAtras, botonSiguiente;
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6;
    CheckBox[] array_cb;
    TableRow row1, row2, row3, row4, row5, row6;
    TableRow[] array_tr;
    ScrollView scroll;
    int contador = 1;
    int contadorRespuestas;
    Evaluacion eval = new Evaluacion();
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;
    UsuarioAlumno uA;
    //PDF
    TemplatePDF templatePDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluacion);
        Bundle b = getIntent().getExtras();
        if(b!=null) {
            uA = b.getParcelable("USUARIOALUMNO");

            enunciado = (TextView) findViewById(R.id.enunciado);
            pregunta1 = (TextView) findViewById(R.id.pregunta1);
            pregunta2 = (TextView) findViewById(R.id.pregunta2);
            pregunta3 = (TextView) findViewById(R.id.pregunta3);
            pregunta4 = (TextView) findViewById(R.id.pregunta4);
            pregunta5 = (TextView) findViewById(R.id.pregunta5);
            pregunta6 = (TextView) findViewById(R.id.pregunta6);
            array_tv = new TextView[]{enunciado, pregunta1, pregunta2, pregunta3, pregunta4, pregunta5, pregunta6};

            checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
            checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
            checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
            checkBox4 = (CheckBox) findViewById(R.id.checkBox4);
            checkBox5 = (CheckBox) findViewById(R.id.checkBox5);
            checkBox6 = (CheckBox) findViewById(R.id.checkBox6);
            array_cb = new CheckBox[]{checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6};

            row1 = (TableRow) findViewById(R.id.row1);
            row2 = (TableRow) findViewById(R.id.row2);
            row3 = (TableRow) findViewById(R.id.row3);
            row4 = (TableRow) findViewById(R.id.row4);
            row5 = (TableRow) findViewById(R.id.row5);
            row6 = (TableRow) findViewById(R.id.row6);
            array_tr = new TableRow[]{row1, row2, row3, row4, row5, row6};

            scroll = (ScrollView) findViewById(R.id.scroll);

            etContador = (TextView) findViewById(R.id.contador);

            botonAtras = (Button) findViewById(R.id.boton1);
            botonSiguiente = (Button) findViewById(R.id.boton2);
            cargarPreguntas();
            botonSiguiente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    scroll.fullScroll(ScrollView.FOCUS_UP);
                    comprobarChecksBoxs(contadorRespuestas);
                    guardarEvaluacion();
                    preguntas.clear();
                    if (botonSiguiente.getText().equals("Siguiente")) {
                        desmarcarCheckBox();
                        rowsInvisible();
                        contador++;
                        cargarPreguntas();
                        if (contador == 5) {
                            botonSiguiente.setText(R.string.botonFinalizar);
                        }

                    } else {
                        eval.setDniAlumno(uA.getDni());
                        //crearPDF();
                        generarEmail();
                        CrearEvaluacionDatabase();
                        finish();
                    }
                }
            });

            botonAtras.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    botonSiguiente.setText(R.string.botonSiguiente);
                    comprobarChecksBoxs(contadorRespuestas);
                    preguntas.clear();
                    if (contador > 1) {
                        desmarcarCheckBox();
                        rowsInvisible();
                        contador--;
                        borrarRespuesta();
                        cargarPreguntas();
                    } else {
                        Toast.makeText(ActivityEvaluacion.this, R.string.toastEvaluacion, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
    private void borrarRespuesta(){
        int cont_aux = contador;
        if (cont_aux == 1) {
            for(int i = 4; i >= 0; i--){
                eval.resultados.remove(i);
                eval.preguntas.remove(i);
            }
        } else if (cont_aux == 2) {
             for(int i = 10; i >= 5;i--){
                 eval.resultados.remove(i);
                 eval.preguntas.remove(i);
             }
        } else if (cont_aux == 3) {

            eval.resultados.remove(11);
            eval.preguntas.remove(11);

        }else if (cont_aux == 4) {
            for(int i = 17; i >= 12;i--) {
                eval.resultados.remove(i);
                eval.preguntas.remove(i);
            }
        }else if (cont_aux == 5) {
            for (int i = 21; i >= 18; i--) {
                eval.resultados.remove(i);
                eval.preguntas.remove(i);
            }
        }

    }

    private void cargarPreguntas() {
        etContador.setText("" + contador);

        if (contador == 1) {
            String[] misPreguntas = getResources().getStringArray(R.array.preguntas1);
            contadorRespuestas = (misPreguntas.length - 1);
            cargarRows(misPreguntas.length);
            cargarDatos(misPreguntas);
            imprimirPreguntas();
        } else if (contador == 2) {
            String[] misPreguntas = getResources().getStringArray(R.array.preguntas2);
            contadorRespuestas = (misPreguntas.length - 1);
            cargarRows(misPreguntas.length);
            cargarDatos(misPreguntas);
            imprimirPreguntas();
        } else if (contador == 3) {
            String[] misPreguntas = getResources().getStringArray(R.array.preguntas3);
            contadorRespuestas = (misPreguntas.length - 1);
            cargarRows(misPreguntas.length);
            cargarDatos(misPreguntas);
            imprimirPreguntas();
        } else if (contador == 4) {
            String[] misPreguntas = getResources().getStringArray(R.array.preguntas4);
            contadorRespuestas = (misPreguntas.length - 1);
            cargarRows(misPreguntas.length);
            cargarDatos(misPreguntas);
            imprimirPreguntas();
        } else if (contador == 5) {
            String[] misPreguntas = getResources().getStringArray(R.array.preguntas5);
            contadorRespuestas = (misPreguntas.length - 1);
            cargarRows(misPreguntas.length);
            cargarDatos(misPreguntas);
            imprimirPreguntas();
        }
    }

    private void cargarDatos(String[] array) {

        for (int i = 0; i < array.length; i++) {
            Pregunta p = new Pregunta(array[i]);
            preguntas.add(p);
        }
    }

    private void cargarRows(int enunciados){
        for (int i = 0; i < (enunciados - 1); i++){
            array_tr[i].setVisibility(View.VISIBLE);
        }
    }

    private void rowsInvisible(){
        for (int i = 0; i < array_tr.length; i++){
            array_tr[i].setVisibility(View.INVISIBLE);
        }
    }

    private void imprimirPreguntas() {
        enunciado.setText(this.preguntas.get(0).getTexto());

        for (int i = 0; i < preguntas.size(); i++) {

            array_tv[i].setText(preguntas.get(i).getTexto());

        }
    }
    private void comprobarChecksBoxs(int contadorRespuestas){
        for (int i = 0; i < contadorRespuestas; i++){
            if (array_cb[i].isChecked()){
                preguntas.get(i).setRespuesta(true);
            }else{
                preguntas.get(i).setRespuesta(false);
            }
        }
    }
    private void desmarcarCheckBox(){
        for (int i = 0; i < array_cb.length; i++){
            array_cb[i].setChecked(false);

        }
    }

    private void guardarEvaluacion(){
        for(int i = 0; i < preguntas.size(); i++){
            if (i != 0) {
                eval.rellenarPreguntas(preguntas.get(i).getTexto());
            }
            if(!(i == (preguntas.size()-1))) {
                eval.rellenarRespuestas(preguntas.get(i).isRespuesta());
            }
        }
    }

    private void CrearEvaluacionDatabase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("evaluacion");
        String id_eval =  RandomString
                .getAlphaNumericString(20);
        eval.setId(id_eval);
        databaseReference.child(id_eval).setValue(eval);

        /*Toast.makeText(getApplicationContext(), "evaluacion guardada",
                Toast.LENGTH_SHORT).show();*/

    }

    /*public void crearPDF(){
        templatePDF = new TemplatePDF(getApplicationContext());
        templatePDF.openDocument();
        templatePDF.addMetaData("FCT","Evaluaciones", "Tutor");
        templatePDF.addTitle("Evaluacion FCT", "A continuacion se muestran los" +
                " resultados de la FCT del alumno " + uA.getNombre());
        for (int i = 0; i < eval.preguntas.size(); i++){
            templatePDF.addParagraph(eval.getPreguntas().get(i) + " " + eval.getResultados().get(i));
        }
        templatePDF.closeDocument();
    }*/

    private void generarEmail(){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"diegofirebase2@gmail.com",uA.getEmailInsti()});
        i.putExtra(Intent.EXTRA_SUBJECT, "Resultados evaluacion "+uA.getNombre());

        i.putExtra(Intent.EXTRA_TEXT   , "Resultados obtenidos de la evaluacion\n"+
                "Alumno: " + uA.getNombre() + " DNI: " + uA.getDni() + "\n" + "IES: " + uA.getNombreInsti()+" EMPRESA: "
                + uA.getEmpresa() +"\n"+"\n"+
                imprimirPreguntasResultados());

        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private String imprimirPreguntasResultados(){
        String resultadosRespuestas = "";
        int numPregunta = 1;
        for (int i = 0; i < eval.resultados.size(); i++){
            resultadosRespuestas = resultadosRespuestas + numPregunta + "." +
                    eval.getPreguntas().get(i) + " --> " + eval.getResultados().get(i)+"\n";
            numPregunta++;
        }

        return resultadosRespuestas;
    }


}
