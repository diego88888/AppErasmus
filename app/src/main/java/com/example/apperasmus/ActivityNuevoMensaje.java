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

import java.util.Date;

public class ActivityNuevoMensaje extends AppCompatActivity{
    Button btnEnviarMensaje;
    EditText etMensaje;
    UsuarioTutor uT;
    UsuarioAlumno uA;
    Date fecha = new Date();
    Chat chat;
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_mensaje);

        btnEnviarMensaje = (Button) findViewById(R.id.btnEnviarMensaje);
        etMensaje = (EditText) findViewById(R.id.etMensaje);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            uT =  b.getParcelable("USUARIOTUTOR");
            uA =  b.getParcelable("USUARIOALUMNO");
        }

        btnEnviarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chat = new Chat("", uT.getNombre(), uA.getDni(), etMensaje.getText().toString(), fecha.toString());
                String id_chat =  RandomString.getAlphaNumericString(20);
                chat.setId(id_chat);
                guardarMensajeFirebase();
                Intent i = new Intent(getApplicationContext(), ActivityChat.class);
                i.putExtra("USUARIOALUMNO", uA);
                i.putExtra("USUARIOTUTOR", uT);
                getApplicationContext().startActivity(i);
                finish();
            }
        });
    }
    private void guardarMensajeFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("chat");
        databaseReference.child(chat.getId()).setValue(chat);

        Toast.makeText(getApplicationContext(), R.string.toastEnviarMensaje,
                Toast.LENGTH_SHORT).show();

    }
}
