package com.flipkart.service;

import com.flipkart.model.Course;
import com.flipkart.model.Professor;
import com.flipkart.model.Student;
import com.flipkart.model.User;

public interface AdminServiceInterface {
	
	boolean createUser(User user);
	void createStudent(Student student);
	void createProfessor(Professor professor);
	void createCourse(Course course);
	void viewAllStudents();
	void viewAllProfessors();
	void viewPaymentDetails();
	void deleteCourse(int courseId);
	void deleteUser(int userId);
	void deleteStudent(int userId);
	void deleteProfessor(int userId);
	void deleteAdmin(int userId);
	void editUser(int userId, User user);
	void editStudent(int userId, Student student);
	void editProfessor(int userId, Professor professor);
	int getRole(int userId);
	void viewAllCourses();
}
