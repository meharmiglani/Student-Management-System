package com.flipkart.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

//Default interface for closing the connection with the db
public interface CloseConnectionInterface {
    Logger logger = Logger.getLogger(CloseConnectionInterface.class);

    //Closes the statement
    default void closeStatement(PreparedStatement statement){
        try {
            if(statement != null) {
                statement.close();
            }
        }catch(SQLException e) {
            logger.error(e.getMessage());
        }
    }

    //Closes a statement and a connection
    default void closeConnection(PreparedStatement statement, Connection conn){

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
