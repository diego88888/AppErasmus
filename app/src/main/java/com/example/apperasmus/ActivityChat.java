package com.example.apperasmus;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivityChat extends AppCompatActivity {
    UsuarioTutor uT;
    UsuarioAlumno uA;
    TextView tvMensaje;
    Button botonNuevoMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        tvMensaje = (TextView) findViewById(R.id.tvMensaje);
        botonNuevoMensaje = (Button) findViewById(R.id.btnNuevoMensaje);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            uT =  b.getParcelable("USUARIOTUTOR");
            uA =  b.getParcelable("USUARIOALUMNO");
        }
        botonNuevoMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ActivityNuevoMensaje.class);
                i.putExtra("USUARIOALUMNO", uA);
                i.putExtra("USUARIOTUTOR", uT);
                getApplicationContext().startActivity(i);
                finish();
            }
        });
    }
}
