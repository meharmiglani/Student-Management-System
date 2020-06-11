package com.flipkart.service;

import com.flipkart.dao.ViewStudentListDaoImpl;
import com.flipkart.model.StudentList;
import org.apache.log4j.Logger;

import java.util.List;

public class ViewStudentListOperation implements ViewStudentListInterface{
    private static Logger logger = Logger.getLogger(ViewStudentListOperation.class);
    private ViewStudentListDaoImpl viewStudentListDao = new ViewStudentListDaoImpl();

    @Override
    public void viewStudentList(int professorId) {
        List<StudentList> list = viewStudentListDao.studentList(professorId);
        if(list != null){
            for(StudentList student: list){
                logger.info(student.getName() + "   " + student.getStudentId());
            }
        }else{
            logger.error("No enrolled students");
        }
    }
}
