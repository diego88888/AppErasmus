package com.example.apperasmus;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executor;

public class ActivityNuevoAlumno extends AppCompatActivity {
    Button botonCancelar, botonCrear;
    EditText etDniAlumno, etNombreAlumno, etEstudiosAlumno, etPeriPracticas, etNumHoras, etNombreInsti,
            etEmailInsti, etTutorInsti, etNombreEpresa, etTutorEmpresa, etEmailAlumno, etPasswAlumno1,
            etPasswAlumno2;
    private FirebaseAuth mAuth;
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;
    UsuarioAlumno uA;
    UsuarioAlumno uA_modificar;
    boolean esModificar = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_alumno);

        botonCancelar = (Button) findViewById(R.id.btnCancelar);
        botonCrear = (Button) findViewById(R.id.btnCrear);
        etDniAlumno = (EditText) findViewById(R.id.etDniAlumno);
        etNombreAlumno = (EditText) findViewById(R.id.etNombreAlumno);
        etEstudiosAlumno = (EditText) findViewById(R.id.etEstudiosAlumno);
        etPeriPracticas = (EditText) findViewById(R.id.etPeriPracticas);
        etNombreInsti = (EditText) findViewById(R.id.etNombreInsti);
        etEmailInsti = (EditText) findViewById(R.id.etEmailInsti);
        etTutorInsti = (EditText) findViewById(R.id.etTutorInsti);
        etNombreEpresa = (EditText)findViewById(R.id.etNombreEpresa);
        etTutorEmpresa = (EditText)findViewById(R.id.etTutorEmpresa);
        etEmailAlumno = (EditText) findViewById(R.id.etEmailAlumno);
        etPasswAlumno1 = (EditText) findViewById(R.id.etPasswAlumno1);
        etPasswAlumno2 = (EditText) findViewById(R.id.etPasswAlumno2);
        etNumHoras = (EditText) findViewById(R.id.etNumHoras);
        mAuth = FirebaseAuth.getInstance();
        Bundle b = getIntent().getExtras();

        if (b != null){
            esModificar = true;
            botonCrear.setText(R.string.botonModificar);
            uA = b.getParcelable("USUARIOALUMNO");
            etDniAlumno.setText(uA.getDni());
            etNombreAlumno.setText(uA.getNombre());
            etEstudiosAlumno.setText(uA.getEstudios());
            etPeriPracticas.setText(uA.getPeriodoPracticas());
            etNumHoras.setText("".concat(Integer.toString(uA.getTotalHoras())));
            etNombreInsti.setText(uA.getNombreInsti());
            etEmailInsti.setText(uA.getEmailInsti());
            etTutorInsti.setText(uA.getTutorInsti());
            etNombreEpresa.setText(uA.getEmpresa());
            etTutorEmpresa.setText(uA.getTutorEmpresa());
            etEmailAlumno.setText(uA.getEmail());
            etPasswAlumno1.setText(uA.getPassword());
            etPasswAlumno2.setText(uA.getPassword());

        }else{
            botonCrear.setText(R.string.botonCrear);
            esModificar = false;
        }

        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ponerVacio();
            }
        });

        botonCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dni = etDniAlumno.getText().toString();
                String emailInsti = etEmailInsti.getText().toString();
                String email = etEmailAlumno.getText().toString();
                String empresa = etNombreEpresa.getText().toString();
                String estudios = etEstudiosAlumno.getText().toString();
                String nombre = etNombreAlumno.getText().toString();
                String nombreInsti = etNombreInsti.getText().toString();
                String password = etPasswAlumno1.getText().toString();
                String periodoPracticas = etPeriPracticas.getText().toString();
                int totalHoras = Integer.parseInt(etNumHoras.getText().toString());
                String tutorEmpresa = etTutorEmpresa.getText().toString();
                String tutorInsti = etTutorInsti.getText().toString();
                String password2 = etPasswAlumno2.getText().toString();

                //VALIDACIONES
                if (!password.equals(password2)){
                    Toast.makeText(getApplicationContext(), R.string.toastNuevoAlumno, Toast.LENGTH_SHORT).show();
                }else{
                    if(esModificar){
                        uA_modificar = new UsuarioAlumno(dni, emailInsti, email, empresa, estudios, uA.getId(), nombre,
                                nombreInsti, password, periodoPracticas, totalHoras, tutorEmpresa, tutorInsti);
                        ModificarAlumnoDatabase();
                        finish();
                    }else{
                        uA = new UsuarioAlumno(dni, emailInsti, email, empresa, estudios, "", nombre,
                                nombreInsti, password, periodoPracticas, totalHoras, tutorEmpresa, tutorInsti);
                        CrearUsuarioAuth(email, password);
                        finish();
                    }
                }

            }
        });
    }

    private void ponerVacio(){
        etDniAlumno.setText("");
        etNombreAlumno.setText("");
        etEstudiosAlumno.setText("");
        etPeriPracticas.setText("");
        etNombreInsti.setText("");
        etEmailInsti.setText("");
        etTutorInsti.setText("");
        etNombreEpresa.setText("");
        etTutorEmpresa.setText("");
        etEmailAlumno.setText("");
        etPasswAlumno1.setText("");
        etPasswAlumno2.setText("");
    }

    private void CrearUsuarioAuth(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            String id = user.getUid();
                            uA.setId(id);
                            CrearUsuarioDatabase();
                        }else {
                            Log.w("TAG2", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "fallo",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void CrearUsuarioDatabase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("usuarioAlumno");
        databaseReference.child(uA.getId()).setValue(uA);

        Toast.makeText(getApplicationContext(), R.string.toastNuevoAlumno2,
                Toast.LENGTH_SHORT).show();

    }

    private void ModificarAlumnoDatabase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("usuarioAlumno");
        databaseReference.child(uA.getId()).setValue(uA_modificar);
        String newPassword = etPasswAlumno1.getText().toString();
        if(!uA.getPassword().equals(uA_modificar.getPassword())){
            ModificarAlumnoAuth();
        }
        Toast.makeText(getApplicationContext(), R.string.toastNuevoAlumno3,
                Toast.LENGTH_SHORT).show();

    }

    private void ModificarAlumnoAuth(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String newPassword = uA_modificar.getPassword();

        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(getApplicationContext(), "Cambiado",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {

        }
    }
}
