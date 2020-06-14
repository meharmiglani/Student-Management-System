package com.flipkart.dao;

import com.flipkart.model.Professor;

import java.util.List;

public interface ProfessorDao {
    boolean insertProfessor(Professor professor);
    boolean deleteProfessor(int professorId);
    boolean updateProfessor(int professorId, Professor newProfessor);
    List<Professor> viewAllProfessors();
}
