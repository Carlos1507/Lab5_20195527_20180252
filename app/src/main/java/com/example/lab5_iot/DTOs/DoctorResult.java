package com.example.lab5_iot.DTOs;

import java.util.List;

public class DoctorResult {
    private List<RandomUser> results;

    public List<RandomUser> getResults() {
        return results;
    }

    public void setResults(List<RandomUser> results) {
        this.results = results;
    }
}
