/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.SMS;

import org.springframework.stereotype.Component;

@Component
public class SMSConfiguration {
    private String accountSid;
    private String authToken;
    private String TrialNumber;

    public SMSConfiguration() {
    }

    public String getAccountSid() {
        return this.accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    public String getAuthToken() {
        return this.authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getTrialNumber() {
        return this.TrialNumber;
    }

    public void setTrialNumber(String TrialNumber) {
        this.TrialNumber = TrialNumber;
    }
}
