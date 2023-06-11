package com.example.lab5_iot.DTOs;

public class User {
    private String nombre;
    private String contraseniaa;
    private String correo;
    private String telefono;
    private String doctorConsulta;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseniaa() {
        return contraseniaa;
    }

    public void setContraseniaa(String contraseniaa) {
        this.contraseniaa = contraseniaa;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDoctorConsulta() {
        return doctorConsulta;
    }

    public void setDoctorConsulta(String doctorConsulta) {
        this.doctorConsulta = doctorConsulta;
    }
}
