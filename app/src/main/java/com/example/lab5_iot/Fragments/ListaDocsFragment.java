package com.example.lab5_iot.Fragments;

import android.os.Bundle;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab5_iot.DTOs.DoctorDtoBD;
import com.example.lab5_iot.DTOs.DoctorResult;
import com.example.lab5_iot.DTOs.RandomUser;
import com.example.lab5_iot.DoctoresAdapter;
import com.example.lab5_iot.Services.DoctorService;
import com.example.lab5_iot.databinding.FragmentListaDocsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaDocsFragment extends Fragment {
    FragmentListaDocsBinding binding;
    FirebaseDatabase firebaseDatabase;
    DoctorService doctorService = new Retrofit.Builder()
            .baseUrl("https://randomuser.me")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DoctorService.class);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        binding = FragmentListaDocsBinding.inflate(inflater, container, false);
        List<DoctorDtoBD> listaDoctores = new ArrayList<>();
        databaseReference.child("doctors").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaDoctores.clear();
                for (DataSnapshot children : snapshot.getChildren()){
                    DoctorDtoBD doctorDtoBD = children.getValue(DoctorDtoBD.class);
                    Log.d("msg", doctorDtoBD.getApellido());
                    listaDoctores.add(doctorDtoBD);
                }
                DoctoresAdapter doctoresAdapter = new DoctoresAdapter();
                doctoresAdapter.setContext(getContext());
                doctoresAdapter.setListaDoctores(listaDoctores);
                binding.recyclerDocs.setAdapter(doctoresAdapter);
                binding.recyclerDocs.setLayoutManager(new LinearLayoutManager(getContext()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
      //  Log.d("msg",listaDoctores.get(0).getApellido());


        binding.newDoctor.setOnClickListener(view ->{
            doctorService.random().enqueue(new Callback<DoctorResult>() {
                @Override
                public void onResponse(Call<DoctorResult> call, Response<DoctorResult> response) {
                    if (response.isSuccessful()){
                        RandomUser randomUser = response.body().getResults().get(0);
                        DoctorDtoBD doctorDtoBD = new DoctorDtoBD();
                        doctorDtoBD.setNombre(randomUser.getName().getFirst());
                        doctorDtoBD.setApellido(randomUser.getName().getLast());
                        doctorDtoBD.setNacionalidad(randomUser.getNat());
                        doctorDtoBD.setCorreo(randomUser.getEmail());
                        doctorDtoBD.setEdad(randomUser.getDob().getAge());
                        doctorDtoBD.setFotoDoctor(randomUser.getPicture().getLarge());
                        doctorDtoBD.setGenero(randomUser.getGender());
                        doctorDtoBD.setCosto(randomUser.getDob().getAge()*3);
                        doctorDtoBD.setPais(randomUser.getLocation().getCountry());
                        doctorDtoBD.setEstado(randomUser.getLocation().getState());
                        doctorDtoBD.setCiudad(randomUser.getLocation().getCity());
                        doctorDtoBD.setTelefono(randomUser.getPhone());
                        doctorDtoBD.setUsername(randomUser.getLogin().getUsername());
                        databaseReference.child("doctors").child(doctorDtoBD.getUsername()).setValue(doctorDtoBD)
                                .addOnSuccessListener(aVoid ->{
                                    Log.d("msg", "doctor registrado exitosamente");
                                })
                                .addOnFailureListener(e->{
                                    Log.d("msg",e.getMessage());
                                });
                    }
                }
                @Override
                public void onFailure(Call<DoctorResult> call, Throwable t) {

                }
            });
        });
        return binding.getRoot();
    }
}