package com.example.apperasmus;

import android.content.Intent;
import android.support.annotation.NonNull;
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

public class MainActivity extends AppCompatActivity{
    EditText etEmail, etContraseña;
    Button btnLogin;
    //FIREBASE
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;
    public ValueEventListener valueEventListener;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Firebase
        mAuth = FirebaseAuth.getInstance();

        etEmail = (EditText) findViewById(R.id.etEmail);
        etContraseña = (EditText) findViewById(R.id.etContraseña);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        //Firebase
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String contraseña = etContraseña.getText().toString();

                if(TextUtils.isEmpty(email) | TextUtils.isEmpty(contraseña)){
                    Toast.makeText(MainActivity.this, R.string.validacionMain, Toast.LENGTH_SHORT).show();
                }else{
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
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {

            Intent i=new Intent(getApplicationContext(), Inicio.class);
            startActivity(i);
        }
    }

    private void loginFirebase(String email,String password){
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
                UsuarioTutor u = dataSnapshot.getValue(UsuarioTutor.class);
                Intent i=new Intent(getApplicationContext(), Inicio.class);
                i.putExtra("USUARIOTUTOR",u);
                startActivity(i);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ActivityParte2", "DATABASE ERROR");
            }
        };
        databaseReference.addValueEventListener(valueEventListener);
    }

}
