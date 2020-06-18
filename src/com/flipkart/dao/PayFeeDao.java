package com.flipkart.dao;

import java.util.List;

import com.flipkart.model.Payment;

public interface PayFeeDao {
    List<Payment> fetchCourseFee(int studentId);
    double getScholarshipAmount(int studentId);
    boolean insertIntoRegistration(int studentId, int mode, double amount);
}
