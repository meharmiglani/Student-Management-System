package com.flipkart.model;

public class Professor extends User{
    private int professorId;
    public Professor(String username, String password, String name, String email) {
        super(username, password, name, email);
    }

    public int getProfessorId(){
        return professorId;
    }

    public void setProfessorId(int professorId) {
        this.professorId = professorId;
    }
}
