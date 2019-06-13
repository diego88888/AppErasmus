package com.example.apperasmus;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    EditText etEmail, etContraseña;
    Button btnLogin;
    //FIREBASE
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;
    public ValueEventListener valueEventListener;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    ArrayList<UsuarioTutor> tutores = new ArrayList<>();
    ArrayList<UsuarioAlumno> alumnos = new ArrayList<>();
    ArrayList<Chat> chats = new ArrayList<>();
    UsuarioTutor tutorLogin;
    UsuarioAlumno alumnoLogin;
    String email;
    String contraseña;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Firebase
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        etEmail = (EditText) findViewById(R.id.etEmail);
        etContraseña = (EditText) findViewById(R.id.etContraseña);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString();
                contraseña = etContraseña.getText().toString();

                if(TextUtils.isEmpty(email) | TextUtils.isEmpty(contraseña)){
                    Toast.makeText(MainActivity.this, R.string.validacionMain, Toast.LENGTH_SHORT).show();
                }else{
                    cargarAlumnoFireBase();
                    cargarTutorFireBase();
                    cargarMensajesFireBase();
                    loginFirebase(email, contraseña);
                }
            }
        });
    }

    //Firebase
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        if (currentUser != null) {

            /*Intent i=new Intent(getApplicationContext(), Inicio.class);
            startActivity(i);*/
            Intent i=new Intent(getApplicationContext(), ActivityAlumnos.class);
            startActivity(i);
        }
    }

    private void loginFirebase(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in uvser can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("tag2", "signInWithEmail:failed", task.getException());
                            Toast.makeText(getApplicationContext(), R.string.toastMain,
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            cargarUsuarioFirebase(mAuth.getCurrentUser().getUid());

                        }


                    }
                });
    }

    private void cargarUsuarioFirebase(String id) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("usuarioTutor/"+id);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*comprobarTutorLogin();
                Intent i=new Intent(getApplicationContext(), Inicio.class);
                i.putExtra("USUARIOTUTOR", tutorLogin);
                getApplicationContext().startActivity(i);*/
                comprobarTutorLogin();
                comprobarAlumnoLogin();
                if (comprobarUsuarioLogin()) {
                    Intent i = new Intent(getApplicationContext(), ActivityAlumnos.class);
                    i.putExtra("USUARIOTUTOR", tutorLogin);
                    i.putExtra("VALIDAR", 1);
                    i.putExtra("CHATS", chats);
                    getApplicationContext().startActivity(i);
                }else{
                    Intent i = new Intent(getApplicationContext(), ActivityChatAlumno.class);
                    i.putExtra("USUARIOALUMNO", alumnoLogin);
                    i.putExtra("CHATS", chats);
                    getApplicationContext().startActivity(i);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ActivityParte2", "DATABASE ERROR");
            }
        };
        databaseReference.addValueEventListener(valueEventListener);
    }

    private void cargarTutorFireBase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("usuarioTutor");

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshotTutor : dataSnapshot.getChildren()) {

                    UsuarioTutor uT = dataSnapshotTutor.getValue(UsuarioTutor.class);

                    tutores.add(uT);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ActivityParte2", "DATABASE ERROR");
            }
        };
        databaseReference.addValueEventListener(valueEventListener);
    }

    private void cargarAlumnoFireBase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("usuarioAlumno");

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshotTutor : dataSnapshot.getChildren()) {

                    UsuarioAlumno uA = dataSnapshotTutor.getValue(UsuarioAlumno.class);

                    alumnos.add(uA);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ActivityParte2", "DATABASE ERROR");
            }
        };
        databaseReference.addValueEventListener(valueEventListener);
    }

    private void cargarMensajesFireBase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("chat");

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshotChat : dataSnapshot.getChildren()) {

                    Chat c = dataSnapshotChat.getValue(Chat.class);

                    chats.add(c);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ActivityParte2", "DATABASE ERROR");
            }
        };
        databaseReference.addValueEventListener(valueEventListener);
    }

    private void comprobarTutorLogin(){
        for (int i = 0; i < tutores.size(); i++){
            if(email.equals(tutores.get(i).getEmail())){
                tutorLogin = tutores.get(i);
            }
        }
    }

    private void comprobarAlumnoLogin(){
        for (int i = 0; i < alumnos.size(); i++){
            if(email.equals(alumnos.get(i).getEmail())){
                alumnoLogin = alumnos.get(i);
            }
        }
    }

    private boolean comprobarUsuarioLogin(){
        boolean esTutor = false;
        for (int i = 0; (i < tutores.size()) && !esTutor; i++){
            if(email.equals(tutores.get(i).getEmail())){
                esTutor = true;
            }
        }
        return esTutor;
    }
}
