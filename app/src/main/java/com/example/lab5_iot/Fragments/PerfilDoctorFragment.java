package com.example.lab5_iot.Fragments;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.lab5_iot.DTOs.DoctorDtoBD;
import com.example.lab5_iot.DTOs.User;
import com.example.lab5_iot.R;
import com.example.lab5_iot.databinding.FragmentPerfilDoctorBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class PerfilDoctorFragment extends Fragment {

    FragmentPerfilDoctorBinding binding;
    FirebaseDatabase firebaseDatabase;

    private String doctorUsername;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPerfilDoctorBinding.inflate(inflater, container, false);
        String username = getArguments().getString("usernameDoctor");
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        NavController navController = NavHostFragment.findNavController(this);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        User user = gson.fromJson(sharedPreferences.getString("user",""), User.class);


        databaseReference.child("doctors").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DoctorDtoBD doctorDtoBD = snapshot.getValue(DoctorDtoBD.class);
                Glide.with(getContext()).load(doctorDtoBD.getFotoDoctor()).into(binding.fotoDoctor);
                binding.correo.setText(doctorDtoBD.getCorreo());
                binding.nombreDoctor.setText(doctorDtoBD.getNombre());
                binding.genero.setText(doctorDtoBD.getGenero());
                binding.username.setText(doctorDtoBD.getUsername());
                binding.costo.setText("S/ "+String.valueOf(doctorDtoBD.getCosto()));
                binding.nacionalidad.setText(doctorDtoBD.getNacionalidad());
                binding.telefono.setText(doctorDtoBD.getTelefono());
                binding.edad.setText(String.valueOf(doctorDtoBD.getEdad()));
                binding.ubicacion.setText(doctorDtoBD.getPais() + " - "+
                        doctorDtoBD.getEstado() + " - "+ doctorDtoBD.getCiudad());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        doctorUsername = getArguments().getString("usernameDoctor");
        binding.buttonCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Guardar el username del doctor en el objeto User
                user.setDoctorConsulta(doctorUsername);

                Log.d(TAG, "Doctor Consulta: " + user.getDoctorConsulta());

                // Actualizar el objeto User en la base de datos

                // Navegar a la vista de confirmación de cita y pasar los argumentos
                Bundle args = new Bundle();
                args.putString("usernameDoctor", doctorUsername);
                navController.navigate(R.id.action_perfilDoctorFragment_to_confirmCitaFragment, args);
            }
        });


        binding.regresar.setOnClickListener(view -> {
            navController.navigate(R.id.action_perfilDoctor_to_listaDocsFragment);
        });

        return binding.getRoot();
    }
}