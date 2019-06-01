package com.example.apperasmus;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptadorAlumno extends RecyclerView.Adapter<AdaptadorAlumno.ViewHolderAlumnos>  implements View.OnClickListener {
    private ArrayList<UsuarioAlumno> usuariosAlumno;
    private Context mContext;

    private View.OnClickListener listener;
    public AdaptadorAlumno(ArrayList<UsuarioAlumno> usuariosAlumno, Context mContext) {
        this.usuariosAlumno = usuariosAlumno;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolderAlumnos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_alumno, parent, false);

        itemView.setOnClickListener(this);
        ViewHolderAlumnos tvh = new ViewHolderAlumnos(itemView, parent.getContext());

        return tvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAlumnos holder, int position) {
        UsuarioAlumno usuarioAlumno = usuariosAlumno.get(position);
        holder.bindAlumnos(usuarioAlumno);
    }
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null)
            listener.onClick(view);
    }


    @Override
    public int getItemCount() {
        return usuariosAlumno.size();
    }

    static class ViewHolderAlumnos extends RecyclerView.ViewHolder   {
        private View mView;
        TextView tvNombreAlumno, tvNombreInstiuto;
        UsuarioAlumno usuarioAlumno;
        Context mContext;


        public ViewHolderAlumnos(View itemView, Context c){
            super(itemView);
            this.mView = itemView;
            tvNombreAlumno = (TextView)itemView.findViewById(R.id.tableNombre);
            tvNombreInstiuto = (TextView)itemView.findViewById(R.id.tableInsti);
            this.mContext = c;

        }

        public void bindAlumnos(UsuarioAlumno uA){
            usuarioAlumno = uA;
            tvNombreAlumno.setText(uA.getNombre());
            tvNombreInstiuto.setText(uA.getNombreInsti());
        }
        /*
        @Override
        public void onClick(View v) {
            Intent i = new Intent(mContext, FichaVerAlumno.class);
            i.putExtra("USUARIOALUMNO", usuarioAlumno);
            mContext.startActivity(i);
        }

        */


    }
}
