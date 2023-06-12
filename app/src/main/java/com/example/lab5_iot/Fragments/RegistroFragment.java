package com.example.lab5_iot.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lab5_iot.DTOs.User;
import com.example.lab5_iot.databinding.FragmentRegistroBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroFragment extends Fragment {
    FragmentRegistroBinding binding;
    private FirebaseAuth auth;
    private User user, usuario1;
    private String userID;
    private FirebaseDatabase firebaseDatabase;
    private GoogleSignInClient googleSignInClient;
    private int RC_SIGN_IN = 40;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegistroBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //google

        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        binding.ingresar.setOnClickListener(v -> registro());

        binding.loginGoogle.setOnClickListener(v -> registro_google());

        binding.loginFacebook.setOnClickListener(v -> registro_facebook());
    }

    private void registro_google() {

    }

    private void registro_facebook(){

    }


    private void registro() {
        String nombre = binding.editTextNombre.getText().toString();
        String password = binding.editTextPassword.getText().toString();
        String correo = binding.editTextCorreo.getText().toString();
        String telefono = binding.editTextTelefono.getText().toString();

        if (nombre.isEmpty()) {
            error(binding.editTextNombre,"Complete su nombre");
        } else if (password.isEmpty()) {
            error(binding.editTextPassword,"Complete su contraseña");
        } else if (telefono.isEmpty()) {
            error(binding.editTextTelefono,"Complete su telefono");

        } else if(correo.isEmpty()){
            error(binding.editTextCorreo,"Complete su correo");
        } else {
            user = new User();
            user.setNombre(nombre);
            user.setCorreo(correo);
            user.setContraseniaa(password);
            user.setTelefono(telefono);
            auth.createUserWithEmailAndPassword(correo, password)
                    .addOnCompleteListener(requireActivity(), task -> {
                        if (task.isSuccessful()) {
                            userID = auth.getCurrentUser().getUid();
                            firebaseDatabase.collection("usuarios")
                                    .document(userID)
                                    .set(user)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(requireContext(), "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                                        auth.signOut();
                                        startActivity(new Intent(requireContext(), Login.class));
                                    })
                                    .addOnFailureListener(e -> e.printStackTrace());
                        } else {
                            Toast.makeText(requireContext(), "Error en el registro" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void error(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

}