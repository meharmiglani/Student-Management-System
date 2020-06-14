package com.flipkart.dao;

import com.flipkart.constant.SQLConstantQueries;
import com.flipkart.model.Professor;
import com.flipkart.utils.CloseConnectionInterface;
import com.flipkart.utils.DBUtil;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDaoImpl implements ProfessorDao, CloseConnectionInterface{
    private final static Logger logger = Logger.getLogger(ProfessorDaoImpl.class);

    @Override
    public boolean insertProfessor(Professor professor){
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;

        try{
            statement = conn.prepareStatement(SQLConstantQueries.CREATE_PROFESSOR);
            statement.setInt(1,professor.getId());
            statement.setString(2, professor.getName());
            statement.setString(3, professor.getEmail());
            int row = statement.executeUpdate();
            return row == 1;

        }catch (SQLException e){
            logger.error(e.getMessage());
            return false;
        }finally {
            closeConnection(statement, conn);
        }
    }

    @Override
    public boolean deleteProfessor(int professorId) {
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;

        try{
            statement = conn.prepareStatement(SQLConstantQueries.DELETE_PROFESSOR_PROFESSOR_TABLE);
            statement.setInt(1, professorId);
            int row2 = statement.executeUpdate();

            statement1 = conn.prepareStatement(SQLConstantQueries.DELETE_PROFESSOR_COURSE_TABLE);
            statement1.setInt(1, professorId);
            int row3 = statement1.executeUpdate();

            statement2 = conn.prepareStatement(SQLConstantQueries.UPDATE_COURSE_TABLE);
            statement2.setInt(1, 0);
            statement2.setInt(2, professorId);
            int row4 = statement2.executeUpdate();

            return row2 == 1 && row3 == 1 && row4 == 1;

        }catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }finally {
            closeStatement(statement);
            closeStatement(statement1);
            closeConnection(statement2, conn);
        }
    }

    @Override
    public boolean updateProfessor(int professorId, Professor newProfessor) {
        return false;
    }

    @Override
    public List<Professor> viewAllProfessors() {
        Connection conn = DBUtil.getConnection();
        PreparedStatement statement = null;
        List<Professor> professorList = new ArrayList<>();
        try{
            statement = conn.prepareStatement(SQLConstantQueries.GET_ALL_PROFESSORS);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                Professor professor = new Professor();
                professor.setId(resultSet.getInt(1));
                professor.setUsername(resultSet.getString(2));
                professor.setName(resultSet.getString(3));
                professor.setEmail(resultSet.getString(4));
                professorList.add(professor);
            }
            return professorList;

        }catch (SQLException e){
            logger.error(e.getMessage());
            return null;
        }finally {
            closeConnection(statement, conn);
        }
    }
}