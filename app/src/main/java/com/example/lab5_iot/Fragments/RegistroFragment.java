package com.example.lab5_iot.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab5_iot.DTOs.User;
import com.example.lab5_iot.R;
import com.example.lab5_iot.databinding.FragmentRegistroBinding;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegistroFragment extends Fragment {
    FragmentRegistroBinding binding;
    private FirebaseAuth auth;
    private User user;
    private DatabaseReference databaseReference;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegistroBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    FirebaseDatabase firebaseDatabase;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");
        navController = NavHostFragment.findNavController(this);

        binding.ingresar.setOnClickListener(view -> {
            String nombre = binding.editTextNombre.getText().toString();
            String contraseniaa = binding.editTextPassword.getText().toString();
            String telefono = binding.editTextTelefono.getText().toString();
            String correo = binding.editTextCorreo.getText().toString();

            user = new User();
            user.setNombre(nombre);
            user.setContraseniaa(contraseniaa);
            user.setTelefono(telefono);
            user.setCorreo(correo);
            saveUserToDatabase(user);
        });

        binding.registrate.setOnClickListener(view -> {
            navController.navigate(R.id.action_registroFragment_to_loginFragment);
        });
    }

    private void saveUserToDatabase(User user) {
        databaseReference.push().setValue(user)
                .addOnSuccessListener(aVoid -> {
                    Log.d("msg", "Data guardada exitosamente");
                })
                .addOnFailureListener(e -> {
                    Log.d("msg", e.getMessage());
                });
    }


}