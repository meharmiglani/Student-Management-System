package com.flipkart.service;

import com.flipkart.exception.UserNotFoundException;
import com.flipkart.exception.UsernameAlreadyExistsException;
import com.flipkart.model.Student;
import com.flipkart.model.User;

import java.util.ArrayList;
import java.util.List;

public class StudentCRUD implements UserInterface{
    List<Student> listOfStudents = new ArrayList<>();

    @Override
    public User addUser(User user) throws UsernameAlreadyExistsException{
        Student student = (Student)user;
        for(Student student1: listOfStudents){
            if(student1.getUsername().equals(student.getUsername())){
                throw new UsernameAlreadyExistsException("Username " + student.getUsername() + " not available");
            }
        }

        listOfStudents.add(student);
        return student;
    }

    @Override
    public void deleteUser(int studentId) throws UserNotFoundException {
        boolean flag = false;
        for(Student student: listOfStudents){
            if(student.getStudentId() == studentId){
                flag = true;
                listOfStudents.remove(student);
                break;
            }
        }

        if(!flag){
            throw new UserNotFoundException("Student with ID " + studentId + " does not exist.");
        }
    }

    @Override
    public void editUser(int studentId, User newUser) throws UserNotFoundException {
        boolean flag = false;
        int i = 0;
        Student student = (Student)newUser;

        for(Student student1: listOfStudents){
            if(student1.getStudentId() == studentId){
                flag = true;
                listOfStudents.set(i, student);
                break;
            }
            i++;
        }

        if(!flag){
            throw new UserNotFoundException("Enter a valid Student ID to update.");
        }
    }

    @Override
    public void listUsers() {
        System.out.println("Total number of enrolled students: " + listOfStudents.size());
        for(Student student: listOfStudents){
            System.out.println(student.getName() + " " + student.getUsername() + " " + student.getEmail());
        }
    }
}
