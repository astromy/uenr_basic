/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.FeePayment;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import java.time.LocalDate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 *
 * @author lys
 */
@Component
public class PaymentResponse {
    private String studentId;
    private String amount;
    private String transactionId;
    private String PaymentDateTime;
    private String telephoneNumber;
    private String paymentDescription;
    private String bankName;
    
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPaymentDateTime() {
        return PaymentDateTime;
    }

    public void setPaymentDateTime(String PaymentDateTime) {
        this.PaymentDateTime = PaymentDateTime;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getPaymentDescription() {
        return paymentDescription;
    }

    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription;
    }

    //public Payment getConvertPayment(){
   // Payment p = new Payment(bankName, paymentDescription, PaymentDateTime, PaymentDateTime, null, Double.parseDouble(amount), LocalDate.parse(PaymentDateTime), 0, new Student(studentId));
  //  return p;
    //}

    @Override
    public String toString() {
        return "PaymentResponse{" + "studentId=" + studentId + ", amount=" + amount + ", transactionId=" + transactionId + ", PaymentDateTime=" + PaymentDateTime + ", telephoneNumber=" + telephoneNumber + ", paymentDescription=" + paymentDescription + ", bankName=" + bankName + '}';
    }
}
