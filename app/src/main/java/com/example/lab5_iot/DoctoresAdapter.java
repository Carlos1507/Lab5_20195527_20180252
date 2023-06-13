package com.example.lab5_iot;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lab5_iot.DTOs.DoctorDtoBD;

import java.util.List;

public class DoctoresAdapter extends RecyclerView.Adapter<DoctoresAdapter.DoctoresViewHolder>{
    private List<DoctorDtoBD> listaDoctores;
    private Context context;
    private NavController navController;

    @NonNull
    @Override
    public DoctoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rv_doctor, parent, false);
        return new DoctoresViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctoresViewHolder holder, int position) {
        DoctorDtoBD doctor = listaDoctores.get(position);
        holder.doctor = doctor;
        View view = holder.itemView.findViewById(R.id.view);
        view.setBackgroundColor(Color.GRAY);
        TextView nombreDoc = holder.itemView.findViewById(R.id.nombreDoc);
        nombreDoc.setText(doctor.getNombre());
        TextView location = holder.itemView.findViewById(R.id.location);
        location.setText(doctor.getPais()+ " - "+doctor.getEstado()+" - "+doctor.getCiudad());
        ImageView foto = holder.itemView.findViewById(R.id.image);
        Glide.with(context).load(doctor.getFotoDoctor()).into(foto);
        Button perfilDocBtn = holder.itemView.findViewById(R.id.verDoctor);
        perfilDocBtn.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("usernameDoctor", doctor.getUsername());
            navController.navigate(R.id.action_listaDocsFragment_to_perfilDoctorFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return listaDoctores.size();
    }

    public class DoctoresViewHolder extends RecyclerView.ViewHolder{
        DoctorDtoBD doctor;
        public DoctoresViewHolder(@NonNull View itemView){
            super(itemView);
        }
    }

    public List<DoctorDtoBD> getListaDoctores() {
        return listaDoctores;
    }

    public void setListaDoctores(List<DoctorDtoBD> listaDoctores) {
        this.listaDoctores = listaDoctores;
    }

    public Context getContext() {
        return context;
    }

    public NavController getNavController() {
        return navController;
    }

    public void setNavController(NavController navController) {
        this.navController = navController;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
