package com.example.lab5_iot;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab5_iot.databinding.FragmentPerfilUserBinding;

public class PerfilUserFragment extends Fragment {

    FragmentPerfilUserBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPerfilUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}