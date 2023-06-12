package com.example.lab5_iot.Services;

import com.example.lab5_iot.DTOs.DoctorDtoBD;
import com.example.lab5_iot.DTOs.DoctorResult;
import com.example.lab5_iot.DTOs.RandomUser;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DoctorService {
    @GET("/api/")
    Call<DoctorResult> random();
}
