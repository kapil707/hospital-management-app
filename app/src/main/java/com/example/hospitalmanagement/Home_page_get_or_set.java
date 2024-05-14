package com.example.hospitalmanagement;

import java.util.ArrayList;

public class Home_page_get_or_set {
    private String id,
     hospital_name,
     hospital_description,
     hospital_photo,
     doctor_name,
     doctor_description,
     doctor_photo;
    public Home_page_get_or_set() {
    }

    public Home_page_get_or_set(
            String id,
            String hospital_name,
            String hospital_description,
            String hospital_photo,
            String doctor_name,
            String doctor_description,
            String doctor_photo,
                                ArrayList<String> genre) {
        this.id = id;
        this.hospital_name = hospital_name;
        this.hospital_description = hospital_description;
        this.hospital_photo = hospital_photo;
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

    public String hospital_name() {
        return hospital_name;
    }
    public void hospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public String hospital_description() {
        return hospital_description;
    }
    public void hospital_description(String hospital_description) {
        this.hospital_description = hospital_description;
    }

    public String hospital_photo() {
        return hospital_photo;
    }
    public void hospital_photo(String hospital_photo) {
        this.hospital_photo = hospital_photo;
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