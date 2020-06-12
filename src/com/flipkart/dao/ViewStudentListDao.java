package com.flipkart.dao;

import com.flipkart.model.StudentList;
import java.util.List;

public interface ViewStudentListDao {
    List<StudentList> studentList(int professorId);
    List<StudentList> studentListByCourseId(int professorId, int courseId);
}
