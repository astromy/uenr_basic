/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.SMS;

import java.io.IOException;
import java.net.MalformedURLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SmsController {
    @Autowired
    private SmsService sS;

    public SmsController() {
    }

    @GetMapping({"/ShowSendSmsForm/"})
    public String ShowSendSmsForm(Model model) throws Exception {
        try {
            model.addAttribute("sms", new Sms());
            return "index";
        } catch (Exception var3) {
            Exception ex = var3;
            model.addAttribute("msg", "Error contact admin");
            ex.printStackTrace();
            return "index";
        }
    }

    @GetMapping({"/sendSms"})
    public String SendSms(Model model, Sms sms) throws MalformedURLException, IOException {
        try {
            return "index";
        } catch (Exception var4) {
            Exception ex = var4;
            ex.printStackTrace();
            model.addAttribute("msg", "Error" + ex.getLocalizedMessage());
            return "index";
        }
    }

    @GetMapping({"/sendMessageToPeggy"})
    public String sendMessageToPeggy(Model m) throws Exception {
        try {
            m.addAttribute("msg", this.sS.sendSMS_To_Enock());
        } catch (Exception var3) {
            Exception ex = var3;
            m.addAttribute("err", ex.getMessage());
        }

        return "index";
    }
}
