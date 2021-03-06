package com.example.uas;

public class User {

    private String id, nis, name, gender, email, asal;

    public User(){
    }

    public User(String nis, String name, String gender, String email, String asal) {
        this.nis = nis;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.asal = asal;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getNis() {
        return nis;
    }
    public void setNis(String nis) {
        this.nis = nis;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getAsal() {
        return asal;
    }

    public void setAsal(String asal) {
        this.asal = asal;
    }
}