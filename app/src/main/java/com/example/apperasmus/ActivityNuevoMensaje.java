package com.example.apperasmus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ActivityNuevoMensaje extends AppCompatActivity{
    Button btnEnviarMensaje;
    EditText etMensaje;
    UsuarioTutor uT;
    UsuarioAlumno uA;
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
                Intent i = new Intent(getApplicationContext(), ActivityChat.class);
                i.putExtra("USUARIOALUMNO", uA);
                i.putExtra("USUARIOTUTOR", uT);
                getApplicationContext().startActivity(i);
                finish();
            }
        });
    }
}
