package com.example.apperasmus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FichaVerAlumno extends AppCompatActivity {
    TextView tvDni, tvNombreAlumno, tvEstudios, tvPracticas, tvNumHoras, tvNombreInsti, tvEmailInsti,
    tvTutorInsti, tvNombreEmpresa, tvTutorEmpresa, tvEmailLogin, tvPasswAlumno;
    UsuarioAlumno uA;
    Button btnModificar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_ver_alumno);
        Bundle b = getIntent().getExtras();
        if(b!=null){
            uA = b.getParcelable("USUARIOALUMNO");

            tvDni = (TextView) findViewById(R.id.tvDniAlumno2);
            tvNombreAlumno = (TextView) findViewById(R.id.tvNombreAlumno2);
            tvEstudios = (TextView) findViewById(R.id.tvEstudiosAlumno2);
            tvPracticas = (TextView) findViewById(R.id.tvPeriPracticas2);
            tvNumHoras = (TextView) findViewById(R.id.tvNumHoras2);
            tvNombreInsti = (TextView) findViewById(R.id.tvNombreInsti2);
            tvEmailInsti = (TextView) findViewById(R.id.tvEmailInsti2);
            tvTutorInsti = (TextView) findViewById(R.id.tvTutorInsti2);
            tvNombreEmpresa = (TextView) findViewById(R.id.tvNombreEpresa2);
            tvTutorEmpresa = (TextView) findViewById(R.id.tvTutorEmpresa2);
            tvEmailLogin = (TextView) findViewById(R.id.tvEmailAlumno2);
            tvPasswAlumno = (TextView) findViewById(R.id.tvPasswAlumno1_2);
            btnModificar = (Button) findViewById(R.id.btnModificar);

            tvDni.setText(uA.getDni());
            tvNombreAlumno.setText(uA.getNombre());
            tvEstudios.setText(uA.getEstudios());
            tvPracticas.setText(uA.getPeriodoPracticas());
            tvNumHoras.setText("".concat(Integer.toString(uA.getTotalHoras())));
            tvNombreInsti.setText(uA.getNombreInsti());
            tvEmailInsti.setText(uA.getEmailInsti());
            tvTutorInsti.setText(uA.getTutorInsti());
            tvNombreEmpresa.setText(uA.getTutorEmpresa());
            tvTutorEmpresa.setText(uA.getTutorEmpresa());
            tvEmailLogin.setText(uA.getEmail());
            tvPasswAlumno.setText(uA.getPassword());

            btnModificar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ActivityNuevoAlumno.class);
                    intent.putExtra("USUARIOALUMNO",uA);
                    startActivity(intent);
                }
            });

        }
    }
}
