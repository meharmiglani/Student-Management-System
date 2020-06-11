package com.flipkart.service;

import com.flipkart.dao.UserDaoImpl;
import com.flipkart.exception.UserNotFoundException;

public class UserOperation {
    private UserDaoImpl userDao = new UserDaoImpl();

    public int checkIdentity(String username, String password) throws UserNotFoundException {
        int userId = userDao.checkIdentity(username, password);

        if(userId != -1){
            System.out.println("USER OP");
            System.out.println(userId);
            return userId;
        }else{
            throw new UserNotFoundException("User does not exist");
        }
    }

    public String getStudentName(int studentId) throws UserNotFoundException{
        String name = userDao.getStudentName(studentId);
        if(!name.equals("")){
            return name;
        }else{
            throw new UserNotFoundException("User does not exist");
        }
    }
}
