package com.example.apperasmus;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityAlumnos extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    ArrayList<UsuarioAlumno> alumnos = new ArrayList<>();
    ArrayList<Chat> chats = new ArrayList<>();
    private RecyclerView rvAlumnos;
    TextView tv;
    //FIREBASE
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;
    public ValueEventListener valueEventListener;
    public UsuarioAlumno user;
    public UsuarioTutor uT;
    public FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    public int tipoAccion;
    //DRAWER
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumnos);

        rvAlumnos = (RecyclerView) findViewById(R.id.rvAlumnos);
        rvAlumnos.setHasFixedSize(true);
        rvAlumnos.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        tv = (TextView) findViewById(R.id.tvRecycler);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            uT =  b.getParcelable("USUARIOTUTOR");
            tipoAccion = b.getInt("VALIDAR");
        }
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        updateHeader();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        rellenarTv(tipoAccion);
        cargarAlumnosFireBase();
        cargarMensajesFireBase();
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
                for (DataSnapshot dataSnapshotAlumnos : dataSnapshot.getChildren()) {
                    cargarRecyclerViewAlumnos(dataSnapshotAlumnos);
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

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tipoAccion == 2){
                    Intent i = new Intent(getApplicationContext(), ActivityEvaluacion.class);
                    user = obtenerAlumno(rvAlumnos.getChildAdapterPosition(v));
                    i.putExtra("USUARIOALUMNO", user);
                    getApplicationContext().startActivity(i);
                }else if(tipoAccion == 1){
                    Intent i = new Intent(getApplicationContext(), FichaVerAlumno.class);
                    user = obtenerAlumno(rvAlumnos.getChildAdapterPosition(v));
                    i.putExtra("USUARIOALUMNO", user);
                    getApplicationContext().startActivity(i);
                }else if(tipoAccion == 3){
                    Intent i = new Intent(getApplicationContext(), ActivityChat.class);
                    user = obtenerAlumno(rvAlumnos.getChildAdapterPosition(v));
                    i.putExtra("USUARIOALUMNO", user);
                    i.putExtra("USUARIOTUTOR", uT);
                    i.putExtra("CHATS", chats);
                    getApplicationContext().startActivity(i);
                }
            }
        });

        rvAlumnos.setAdapter(adapter);

    }

    private UsuarioAlumno obtenerAlumno(int position){
        return alumnos.get(position);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_chat:
                Intent i4 = new Intent(getApplicationContext(), ActivityAlumnos.class);
                i4.putExtra("VALIDAR", 3); //3 --> Chat
                i4.putExtra("USUARIOTUTOR", uT);
                i4.putExtra("CHATS", chats);
                startActivity(i4);
                break;
            case R.id.nav_alumno:
                Intent i2 = new Intent(getApplicationContext(), ActivityAlumnos.class);
                i2.putExtra("VALIDAR",1); //1 --> Ver y modificar alumno
                startActivity(i2);
                break;
            case R.id.nav_nuevoAlumno:
                Intent i = new Intent(getApplicationContext(), ActivityNuevoAlumno.class);
                startActivity(i);
                break;
            case R.id.nav_evaluacion:
                Intent i3 = new Intent(getApplicationContext(), ActivityAlumnos.class);
                i3.putExtra("VALIDAR",2); //2--> Evaluar alumno
                startActivity(i3);
                break;
            case R.id.nav_salir:
                mAuth.getInstance().signOut();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void updateHeader(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        TextView navEmail = headerView.findViewById(R.id.emailCurrentUser);

        navEmail.setText(currentUser.getEmail());
    }

    private void rellenarTv(int tipoAccion){
        if(tipoAccion == 2){
            tv.setText("Elija el alumno a evaluar" + "\n");
        }else if(tipoAccion == 1){
            tv.setText("Listado de alumnos" + "\n");
        }else if(tipoAccion == 3){
            tv.setText("Elija el alumno con el que contactar" + "\n");
        }
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
}
