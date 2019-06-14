package com.example.apperasmus;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ActivityChatAlumno extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    public FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    UsuarioAlumno uA;
    ArrayList<Chat> chats = new ArrayList<>();
    ArrayList<Mensaje> mensajes = new ArrayList<>();
    ArrayList<String> mensajesFinal = new ArrayList<>();
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_alumno);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        listView = (ListView) findViewById(R.id.listaMensajesAlumno);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            uA =  b.getParcelable("USUARIOALUMNO");
            chats = b.getParcelableArrayList("CHATS");
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout_alumno);
        NavigationView navigationView = findViewById(R.id.nav_view_alumno);
        navigationView.setNavigationItemSelectedListener(this);
        updateHeader();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        comprobarMensajes();
        mostrarMensajes();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_chat_alumno:
                Intent i = new Intent(getApplicationContext(), ActivityChatAlumno.class);
                i.putExtra("USUARIOALUMNO", uA);
                i.putExtra("CHATS", chats);
                startActivity(i);
                break;
            case R.id.nav_nuevoMensaje_alumno:
                Intent i2 = new Intent(getApplicationContext(), ActivityNuevoMensajeAlumno.class);
                i2.putExtra("USUARIOALUMNO", uA);
                i2.putExtra("CHATS", chats);
                startActivity(i2);
                break;
            case R.id.nav_salir_alumno:
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
        NavigationView navigationView = findViewById(R.id.nav_view_alumno);
        View headerView = navigationView.getHeaderView(0);

        TextView navEmail = headerView.findViewById(R.id.emailCurrentUser);

        navEmail.setText(currentUser.getEmail());
    }

    private void comprobarMensajes(){
        for(int i = 0; i < chats.size(); i++){
            if ((chats.get(i).getOrigen().equals(uA.getTutorEmpresa()) || chats.get(i).getOrigen().equals(uA.getDni()))
                    && (chats.get(i).getDestino().equals(uA.getTutorEmpresa()) || chats.get(i).getDestino().equals(uA.getDni()))){
                if (chats.get(i).getOrigen().equals(uA.getDni())){
                    Mensaje mensaje= new Mensaje(chats.get(i).getMensaje(),uA.getNombre());
                    mensajes.add(mensaje);
                }else{
                    Mensaje mensaje= new Mensaje(chats.get(i).getMensaje(),chats.get(i).getOrigen());
                    mensajes.add(mensaje);
                }
            }
        }
    }

    private void mostrarMensajes(){
        for (int i = 0; i < mensajes.size(); i++){
            mensajesFinal.add(mensajes.get(i).toString());
        }
        ArrayAdapter adaptador = new ArrayAdapter(this,android.R.layout.simple_list_item_1,mensajesFinal);
        listView.setAdapter(adaptador);
    }
}
