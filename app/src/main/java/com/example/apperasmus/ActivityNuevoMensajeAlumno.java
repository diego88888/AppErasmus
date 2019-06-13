package com.example.apperasmus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

public class ActivityNuevoMensajeAlumno extends AppCompatActivity {
    UsuarioAlumno uA;
    EditText etFecha, etHora1, etHora2, etReporte;
    Button btnEnviar;
    Chat chat;
    Date date = new Date();
    String reporte, mensaje1, mensaje2, mensaje3, mensaje4;
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;
    ArrayList<Chat> chats = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_mensaje_alumno);

        etFecha = (EditText) findViewById(R.id.etFecha);
        etHora1 = (EditText) findViewById(R.id.etHora1);
        etHora2 = (EditText) findViewById(R.id.etHora2);
        etReporte = (EditText) findViewById(R.id.etReporte);
        btnEnviar = (Button) findViewById(R.id.btnEnviarMensajeAlumno);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            uA =  b.getParcelable("USUARIOALUMNO");
            chats = b.getParcelableArrayList("CHATS");
        }

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etFecha.getText().toString().equals("")||etHora1.getText().toString().equals("")||
                        etHora2.getText().toString().equals("")||etReporte.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.toastMensajeVacio,
                            Toast.LENGTH_SHORT).show();
                }else{
                    mensaje1 = "Fecha de envio de mensaje: " + etFecha.getText().toString();
                    mensaje2 = "Hora de inicio de jornada: " + etHora1.getText().toString();
                    mensaje3 = "Hora de fin de jornada: " + etHora2.getText().toString();
                    mensaje4 = etReporte.getText().toString();
                    reporte = mensaje1 + "\n" + mensaje2 + "\n" + mensaje3 + "\n" + mensaje4;
                    chat = new Chat("", uA.getDni(), uA.getTutorEmpresa(), reporte, date.toString());
                    String id_chat = RandomString.getAlphaNumericString(20);
                    chat.setId(id_chat);
                    guardarMensajeAlumnoFirebase();
                    Intent i = new Intent(getApplicationContext(), ActivityChatAlumno.class);
                    getApplicationContext().startActivity(i);
                    finish();
                }
            }
        });
    }

    private void guardarMensajeAlumnoFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("chat");
        databaseReference.child(chat.getId()).setValue(chat);

        Toast.makeText(getApplicationContext(), R.string.toastEnviarMensaje,
                Toast.LENGTH_SHORT).show();

    }
}
