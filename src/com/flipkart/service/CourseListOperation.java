package com.flipkart.service;

import com.flipkart.dao.CourseCatalogDaoImpl;
import com.flipkart.model.Catalog;
import org.apache.log4j.Logger;

import java.util.List;

public class CourseListOperation implements CourseListInterface{
    private static Logger logger = Logger.getLogger(CourseListOperation.class);
    private static CourseCatalogDaoImpl courseCatalogDao = new CourseCatalogDaoImpl();

    public void viewCourseCatalog(){
        List<Catalog> catalogsList = courseCatalogDao.viewCourseCatalog();
        if(catalogsList != null){
            logger.info("********* CATALOG ************");
            for(Catalog catalog: catalogsList){
                logger.info(catalog.getCourseId() + "   " + catalog.getCourseName()+ "   " + catalog.getCredits());
            }
            logger.info("******************************");
        }else{
            logger.error("Course Catalog couldn't be fetched");
        }
    }
}
