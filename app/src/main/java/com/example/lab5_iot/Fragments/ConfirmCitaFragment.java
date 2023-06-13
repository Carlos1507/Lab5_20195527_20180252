package com.example.lab5_iot.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab5_iot.DTOs.User;
import com.example.lab5_iot.R;
import com.example.lab5_iot.databinding.FragmentConfirmCitaBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class ConfirmCitaFragment extends Fragment {

    FragmentConfirmCitaBinding binding;

    String doctorUsername1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentConfirmCitaBinding.inflate(inflater, container, false);;
        NavController navController = NavHostFragment.findNavController(this);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        User user = gson.fromJson(sharedPreferences.getString("user",""), User.class);
        Bundle arguments = getArguments();
        if (arguments != null) {
            doctorUsername1 = arguments.getString("usernameDoctor");
        }

        if (user.getDoctorConsulta() != null) {
            binding.textView2.setText("Felicidades se agendó su cita con el Dr. " + user.getDoctorConsulta());
        } else if (doctorUsername1 != null) {
            binding.textView2.setText("Felicidades se agendó su cita con el Dr. " + doctorUsername1);
        }


        binding.buttonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("user-test",user.getNombre());
                navController.navigate(R.id.action_confirmCitaFragment_to_listaDocsFragment);
            }
        });


        return binding.getRoot();
    }
}