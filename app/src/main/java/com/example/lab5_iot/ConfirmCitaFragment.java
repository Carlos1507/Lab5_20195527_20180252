package com.example.lab5_iot;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab5_iot.databinding.FragmentConfirmCitaBinding;

public class ConfirmCitaFragment extends Fragment {

    FragmentConfirmCitaBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentConfirmCitaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}