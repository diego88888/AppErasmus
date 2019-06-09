package com.example.apperasmus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityChat extends AppCompatActivity {
    UsuarioTutor uT;
    UsuarioAlumno uA;
    TextView tv;
    Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        tv = (TextView) findViewById(R.id.tvMensaje);
        boton = (Button) findViewById(R.id.btnNuevoMensaje);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            uT =  b.getParcelable("USUARIOTUTOR");
            uA =  b.getParcelable("USUARIOALUMNO");
        }
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
