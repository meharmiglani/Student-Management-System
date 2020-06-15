package com.flipkart.model;

public class Professor extends User{
    private String department;

    public Professor(){

    }

    public Professor(int id, String username, String password, String name, String email) {
        super(id, username, password, name, email, 2);
    }

    public Professor(String username, int id, String name, String email) {
        super(username, id, name, email);
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
