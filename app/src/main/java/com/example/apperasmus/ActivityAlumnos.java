package com.example.apperasmus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityAlumnos extends AppCompatActivity {
    ArrayList<UsuarioAlumno> alumnos = new ArrayList<>();
    private RecyclerView rvAlumnos;
    //FIREBASE
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;
    public ValueEventListener valueEventListener;
    public UsuarioAlumno user;
    public FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumnos);

        rvAlumnos = (RecyclerView) findViewById(R.id.rvAlumnos);
        rvAlumnos.setHasFixedSize(true);
        rvAlumnos.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Bundle b = getIntent().getExtras();
        if(b!=null){
            user =  b.getParcelable("USUARIOALUMNO");
        }
        cargarAlumnosFireBase();
    }

    private void cargarAlumnosFireBase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("usuarioAlumno");

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshotAlumnos : dataSnapshot.getChildren()) {

                    UsuarioAlumno uA = dataSnapshotAlumnos.getValue(UsuarioAlumno.class);

                    alumnos.add(uA);


                }

                alumnos.clear();
                for (DataSnapshot dataSnapshotVideojuegos : dataSnapshot.getChildren()) {
                    cargarRecyclerViewAlumnos(dataSnapshotVideojuegos);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ActivityParte2", "DATABASE ERROR");
            }
        };
        databaseReference.addValueEventListener(valueEventListener);
    }

    private void cargarRecyclerViewAlumnos(DataSnapshot dataSnapshot) {


        alumnos.add(dataSnapshot.getValue(UsuarioAlumno.class));


        AdaptadorAlumno adapter = new AdaptadorAlumno(alumnos, getApplicationContext());

        rvAlumnos.setAdapter(adapter);

    }
}
