package com.flipkart.model;

public class Admin extends User{

    private int adminId;
    public Admin(int id, String username, String password, String name, String email) {
        super(id, username, password, name, email, "admin");
    }

    public Admin(String username, int id, String name, String email) {
        super(username, id, name, email);
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId){
        this.adminId = adminId;
    }
}
