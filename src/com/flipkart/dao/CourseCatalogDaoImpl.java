package com.flipkart.dao;

import com.flipkart.constant.SQLConstantQueries;
import com.flipkart.model.Catalog;
import com.flipkart.utils.DBUtil;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseCatalogDaoImpl implements CourseCatalogDao{
    private static Logger logger = Logger.getLogger(CourseCatalogDaoImpl.class);

    @Override
    public List<Catalog> viewCourseCatalog() {
        List<Catalog> catalogs = new ArrayList<>();
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;
        try{
            statement = conn.prepareStatement(SQLConstantQueries.GET_CATALOG);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                int courseId = resultSet.getInt(1);
                String name = resultSet.getString(2);
                int credits = resultSet.getInt(3);
                Catalog catalog = new Catalog(courseId, name, credits);
                catalogs.add(catalog);
            }
            return catalogs;

        }catch (SQLException e){
            logger.error(e.getMessage());
            return null;
        }finally {

            //Close the all connections and statements
            try {
                if(statement != null) {
                    statement.close();
                }
            }catch(SQLException e) {
                logger.error(e.getMessage());
            }

            try {
                if(conn != null) {
                    conn.close();
                }
            }catch(SQLException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
