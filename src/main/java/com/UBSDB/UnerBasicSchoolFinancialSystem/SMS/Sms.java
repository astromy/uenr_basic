/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.SMS;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class Sms {
    public String message;
    public List<String> Phonenumber;

    public Sms() {
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getPhonenumber() {
        return this.Phonenumber;
    }

    public void setPhonenumber(List<String> Phonenumber) {
        this.Phonenumber = Phonenumber;
    }
}
