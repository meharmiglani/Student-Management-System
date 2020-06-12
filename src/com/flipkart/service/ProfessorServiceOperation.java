package com.flipkart.service;

import com.flipkart.dao.CourseToTeachDaoImpl;
import com.flipkart.dao.UpdateMarksDaoImpl;
import com.flipkart.dao.ViewStudentListDaoImpl;
import com.flipkart.model.Course;
import com.flipkart.model.StudentList;
import org.apache.log4j.Logger;
import java.util.List;

public class ProfessorServiceOperation implements ProfessorServiceInterface{
    private final static Logger logger = Logger.getLogger(ProfessorServiceOperation.class);
    private final ViewStudentListDaoImpl viewStudentListDao = new ViewStudentListDaoImpl();
    private final CourseToTeachDaoImpl courseToTeachDao = new CourseToTeachDaoImpl();
    private final UpdateMarksDaoImpl updateMarksDao = new UpdateMarksDaoImpl();

    // View student list
    public void viewStudentList(int professorId){
        List<StudentList> list = viewStudentListDao.studentList(professorId);
        if(list != null){
            list.forEach(student -> logger.info(student.getName() + "   " + student.getStudentId() + "   " + student.getCourseName()));
        }else{
            logger.error("No enrolled students");
        }
    }

    // View student list by courseId
    public void viewStudentListByCourseId(int professorId, int courseId){
        List<StudentList> list = viewStudentListDao.studentListByCourseId(professorId, courseId);
        if(list != null){
            list.forEach(student -> logger.info(student.getName() + "   " + student.getStudentId() + "   " + student.getCourseName()));
        }else{
            logger.error("No enrolled students");
        }
    }

    // Select a course to teach
    public void selectCourseToTeach(int professorId, int courseId){
        if(courseToTeachDao.selectCourse(professorId, courseId)){
            logger.info("Course has been selected");
        }else{
            logger.error("Could not select course");
        }
    }

    // View available courses to teach
    public void viewCoursesToTeach(){
        List<Course> list = courseToTeachDao.viewCoursesAvailableToTeach();
        if(list != null){
            logger.info("************ AVAILABLE COURSES TO TEACH ****************");
            list.forEach(course -> logger.info(course.getCourseId() + "    " + course.getCourseName()));
            logger.info("********************************************************");
        }else{
            logger.error("List not available");
        }
    }

    // View Courses Already Teaching
    public void viewCoursesTaught(int professorId){
        List<Course> list = courseToTeachDao.coursesTeaching(professorId);
        if(list != null){
            logger.info("************ COURSES TAUGHT BY Professor ID: " + professorId + " ****************");
            list.forEach(course -> logger.info(course.getCourseId() + "    " + course.getCourseName() + "   " + course.getCountOfStudents()));
            logger.info("*********************************************************************************");
        }else{
            logger.error("List not available");
        }
    }

    // Update marks for a student
    public void updateStudentMarks(int studentId, int courseId, int marks){
        if(updateMarksDao.updateStudentMarks(studentId, courseId, marks)){
            logger.info("Marks updated for student " + studentId + ", for course " + courseId + " to " + marks);
        }else {
            logger.error("An error occurred, could not update marks");
        }
    }
}
