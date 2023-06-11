package com.example.lab5_iot.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab5_iot.databinding.FragmentPerfilDoctorBinding;

public class PerfilDoctorFragment extends Fragment {

    FragmentPerfilDoctorBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPerfilDoctorBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}