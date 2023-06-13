package com.example.lab5_iot.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab5_iot.MainActivity;
import com.example.lab5_iot.R;
import com.example.lab5_iot.UserViewModel;
import com.example.lab5_iot.databinding.FragmentPerfilUserBinding;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class PerfilUserFragment extends Fragment {

    FragmentPerfilUserBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPerfilUserBinding.inflate(inflater, container, false);
        NavController navController = NavHostFragment.findNavController(this);
        binding.regresar.setOnClickListener(view -> {
            navController.navigate(R.id.action_perfilUserFragment_to_listaDocsFragment);
        });
        binding.cerrarSesion.setOnClickListener(view -> {
            AuthUI.getInstance()
                    .signOut(getContext())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d("infoApp", "logout exitoso");
                        }
                    });
            UserViewModel viewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
            viewModel.getUsuarioLogueado().postValue(null);
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        });
        return binding.getRoot();
    }
}