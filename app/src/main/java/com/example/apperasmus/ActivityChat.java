package com.example.apperasmus;

import android.app.ActionBar;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityChat extends AppCompatActivity {
    UsuarioTutor uT;
    UsuarioAlumno uA;
    Button botonNuevoMensaje;
    ListView listView;
    ArrayList<Chat> chats = new ArrayList<>();
    ArrayList<Mensaje> mensajes = new ArrayList<>();
    ArrayList<String> mensajesFinal = new ArrayList<>();
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;
    public ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        botonNuevoMensaje = (Button) findViewById(R.id.btnNuevoMensaje);
        listView = (ListView) findViewById(R.id.listaMensajes);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            uT =  b.getParcelable("USUARIOTUTOR");
            uA =  b.getParcelable("USUARIOALUMNO");
        }
        cargarMensajesFireBase();
        comprobarMensajes();
        mostrarMensajes();
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

    private void comprobarMensajes(){
        for(int i = 0; i < chats.size(); i++){
            if ((chats.get(i).getOrigen().equals(uT.getNombre())&&(chats.get(i).getDestino().equals(uA.getDni())))){
                Mensaje mensaje = new Mensaje(chats.get(i).mensaje,chats.get(i).origen);
                mensajes.add(mensaje);
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
