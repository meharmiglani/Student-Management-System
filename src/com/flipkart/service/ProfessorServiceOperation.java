package com.flipkart.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.flipkart.dao.CourseToTeachDaoImpl;
import com.flipkart.dao.RegisterStudentDaoImpl;
import com.flipkart.dao.UpdateMarksDaoImpl;
import com.flipkart.dao.ViewStudentListDaoImpl;
import com.flipkart.exception.NoCourseFoundException;
import com.flipkart.model.Course;
import com.flipkart.model.StudentList;

public class ProfessorServiceOperation implements ProfessorServiceInterface{
    private final static Logger logger = Logger.getLogger(ProfessorServiceOperation.class);
    private final ViewStudentListDaoImpl viewStudentListDao = new ViewStudentListDaoImpl();
    private final CourseToTeachDaoImpl courseToTeachDao = new CourseToTeachDaoImpl();
    private final UpdateMarksDaoImpl updateMarksDao = new UpdateMarksDaoImpl();
    private final RegisterStudentDaoImpl registerStudentDao = new RegisterStudentDaoImpl();

    // View student list
    @Override
    public void viewStudentList(int professorId){
        List<StudentList> list = viewStudentListDao.studentList(professorId);
        displayStudentList(list);
    }

    // View student list by courseId
    @Override
    public void viewStudentListByCourseId(int professorId, int courseId){
        List<StudentList> list = viewStudentListDao.studentListByCourseId(professorId, courseId);
        displayStudentList(list);
    }
    
    @Override
    public void displayStudentList(List<StudentList> list){
        logger.info(String.format("%20s %20s %20s %20s %20s", "STUDENT ID", "NAME", "COURSE ID", "COURSE NAME", "MARKS"));
        if(list != null){
            list.forEach(student -> {
                String marks = Integer.toString(student.getMarks());
                if(marks.equals("-1")){
                    marks = "Not uploaded";
                }
                logger.info(String.format("%20s %20s %20s %20s %20s", student.getStudentId(), student.getName(), student.getCourseId(), student.getCourseName(), marks));
            });
        }else{
            logger.error("No enrolled students");
        }
    }

    // Select a course to teach
    @Override
    public void selectCourseToTeach(int professorId, int courseId) throws NoCourseFoundException{
        if(courseToTeachDao.selectCourse(professorId, courseId)){
            logger.info("Course has been selected");
        }else{
            logger.error("Could not select course");
        }
    }

    // View available courses to teach
    @Override
    public void viewCoursesToTeach(){
        List<Course> list = courseToTeachDao.viewCoursesAvailableToTeach();
        if(list != null){
            logger.info("************ AVAILABLE COURSES TO TEACH ****************");
            logger.info(String.format("%15s %20s", "COURSE ID", "COURSE NAME"));
            list.forEach(course -> logger.info(String.format("%15s %20s", course.getCourseId(), course.getCourseName())));
            logger.info("********************************************************");
        }else{
            logger.error("List not available");
        }
    }

    // View Courses Already Teaching
    @Override
    public void viewCoursesTaught(int professorId){
        List<Course> list = courseToTeachDao.coursesTeaching(professorId);
        if(list != null){
            logger.info("************ COURSES TAUGHT BY Professor ID: " + professorId + " ****************");
            logger.info(String.format("%20s %20s %20s", "Course ID", "Course Name", "Count of Students"));
            list.forEach(course -> logger.info(String.format("%20s %20s %20s", course.getCourseId(), course.getCourseName(), course.getCountOfStudents())));
            logger.info("*********************************************************************************");
        }else{
            logger.error("List not available");
        }
    }

    // Update marks for a student after final registration
    @Override
    public void updateStudentMarks(int studentId, int courseId, int marks){
        if(updateMarksDao.updateStudentMarks(studentId, courseId, marks)){
            logger.info("Marks updated for student " + studentId + ", for course " + courseId + " to " + marks);
        }else {
            logger.error("An error occurred, could not update marks");
        }
    }
    
    //Can't update marks if a student isn't registered
    @Override
    public boolean checkForRegistration(int studentId){
        return registerStudentDao.checkRegistration(studentId);
    }
}
