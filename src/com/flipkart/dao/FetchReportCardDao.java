package com.flipkart.dao;

import com.flipkart.model.Grade;

import java.util.List;

public interface FetchReportCardDao {
    List<Grade> getReportCard(int studentId);
    boolean checkForReportCard(int studentId);
    List<Grade> getMarksByCourse(int studentId);
}
