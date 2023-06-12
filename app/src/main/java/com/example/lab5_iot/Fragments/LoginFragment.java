package com.example.lab5_iot.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab5_iot.DTOs.User;
import com.example.lab5_iot.R;
import com.example.lab5_iot.UserViewModel;
import com.example.lab5_iot.databinding.FragmentLoginBinding;
import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.Arrays;

public class LoginFragment extends Fragment {
    FragmentLoginBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    FirebaseDatabase firebaseDatabase;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDatabase = FirebaseDatabase.getInstance();

        NavController navController = NavHostFragment.findNavController(LoginFragment.this);
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        /*
        User user = new User();
        user.setNombre("Carlos");
        user.setContraseniaa("1234");
        user.setTelefono("987654321");
        databaseReference.child("users").push().setValue(user)
                .addOnSuccessListener(aVoid->{
                    Log.d("msg", "Data guardada exitosamente");
                })
                .addOnFailureListener(e -> {
                    Log.d("msg", e.getMessage());
                });
        */
        UserViewModel userViewModel =new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewModel.getUsuarioLogueado().observe(getActivity(), usuario ->{
            navController.navigate(R.id.action_loginFragment_to_listaDocsFragment);
            User user = userViewModel.getUsuarioLogueado().getValue();
            Log.d("msg-tst En Login", "firebase UID: "+user.getCorreo());
        });

    }


}