package com.example.lab5_iot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.lab5_iot.DTOs.User;
import com.example.lab5_iot.databinding.ActivityRegistroBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroActivity extends AppCompatActivity {
    ActivityRegistroBinding binding;
    private FirebaseAuth auth;
    private User user;
    private DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");

        binding.registrarse.setOnClickListener(view -> {
            String nombre = binding.editTextNombre.getText().toString();
            String contraseniaa = binding.editTextPassword.getText().toString();
            String telefono = binding.editTextTelefono.getText().toString();
            String correo = binding.editTextCorreo.getText().toString();

            user = new User();
            user.setNombre(nombre);
            user.setContrasenia(contraseniaa);
            user.setTelefono(telefono);
            user.setCorreo(correo);
            saveUserToDatabase(user);
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
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