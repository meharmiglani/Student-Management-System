package com.flipkart.service;

public interface ProfessorServiceInterface {
    void viewStudentList(int professorId);
    void viewStudentListByCourseId(int professorId, int courseId);
    void selectCourseToTeach(int professorId, int courseId);
    void viewCoursesToTeach();
    void viewCoursesTaught(int professorId);
    void updateStudentMarks(int studentId, int courseId, int marks);
}
