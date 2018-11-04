package com.android.evgeniy.firebaseblog.models;



public class User {
    private String email;
    private String name;

    public User() {
    }

    private String surname;

    public User(String email, String name, String surname, Gender gender, Short age) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.age = age;
    }

    private Gender gender;
    private Short age;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Short getAge() {
        return age;
    }

    public void setAge(Short age) {
        this.age = age;
    }

}
