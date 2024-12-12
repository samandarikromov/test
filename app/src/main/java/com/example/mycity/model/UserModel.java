package com.example.mycity.model;

public class UserModel {
    private String id;
    private String firstnameLastname;
    private int age;
    private String job;
    private String address;
    private String transportType;
    private String phone;
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstnameLastname() {
        return firstnameLastname;
    }

    public void setFirstnameLastname(String firstnameLastname) {
        this.firstnameLastname = firstnameLastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserModel(String id, String firstnameLastname, int age, String job, String address, String transportType, String phone, String password) {
        this.id = id;
        this.firstnameLastname = firstnameLastname;
        this.age = age;
        this.job = job;
        this.address = address;
        this.transportType = transportType;
        this.phone = phone;
        this.password = password;
    }
}