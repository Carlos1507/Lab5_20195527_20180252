package com.example.lab5_iot;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lab5_iot.DTOs.User;

public class UserViewModel extends ViewModel {
    private MutableLiveData<User> usuarioLogueado = new MutableLiveData<>();

    public MutableLiveData<User> getUsuarioLogueado() {
        return usuarioLogueado;
    }
}
