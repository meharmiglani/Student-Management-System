package com.flipkart.dao;

import com.flipkart.model.Payment;

import java.util.List;

public interface PayFeeDao {
    List<Payment> fetchCourseFee(int studentId);
    double getScholarshipAmount(int studentId);
    //boolean makePayment(int studentId, String mode, double amount);
    //int getPaymentId(int studentId);
    boolean insertIntoRegistration(int studentId, int mode, double amount);
}
