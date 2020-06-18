package com.flipkart.dao;

import java.util.List;

import com.flipkart.model.Grade;

public interface FetchReportCardDao {
    List<Grade> getReportCard(int studentId);
    boolean checkForReportCard(int studentId);
    List<Grade> getMarksByCourse(int studentId);
    double getFinalPercentage(int studentId);
}
