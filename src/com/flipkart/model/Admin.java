package com.flipkart.model;

public class Admin extends User{

    private int adminId;
    public Admin(String username, String password, String name, String email) {
        super(username, password, name, email);
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId){
        this.adminId = adminId;
    }
}
