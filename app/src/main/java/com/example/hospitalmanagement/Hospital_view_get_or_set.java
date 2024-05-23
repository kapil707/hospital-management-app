package com.example.hospitalmanagement;

import java.util.ArrayList;

public class Hospital_view_get_or_set {
    private String id,
            doctor_name,
            doctor_description,
            doctor_photo;

    public Hospital_view_get_or_set() {
    }

    public Hospital_view_get_or_set(
            String id,
            String doctor_name,
            String doctor_description,
            String doctor_photo,
            ArrayList<String> genre) {
        this.id = id;
        this.doctor_name = doctor_name;
        this.doctor_description = doctor_description;
        this.doctor_photo = doctor_photo;
    }

    public String id() {
        return id;
    }

    public void id(String id) {
        this.id = id;
    }

    public String doctor_name() {
        return doctor_name;
    }

    public void doctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String doctor_description() {
        return doctor_description;
    }

    public void doctor_description(String doctor_description) {
        this.doctor_description = doctor_description;
    }

    public String doctor_photo() {
        return doctor_photo;
    }

    public void doctor_photo(String doctor_photo) {
        this.doctor_photo = doctor_photo;
    }
}