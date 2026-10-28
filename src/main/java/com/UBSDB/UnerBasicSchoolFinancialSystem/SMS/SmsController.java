/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.SMS;

import java.io.IOException;
import java.net.MalformedURLException;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class SmsController {
    @Autowired
    private SmsService sS;
private  @Autowired StudentService studentService;
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

    @PostMapping({"/sendSms"})
    public String SendSms (Model model, Sms sms) throws MalformedURLException, IOException {
        try {

            sS.sendMessage(sms.getMessage());
            model.addAttribute("msg", "SMS sent successfully" );

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
            var students = studentService.students();

                this.sS.sendAdmissionMessages();

            m.addAttribute("msg","Done");
        } catch (Exception var3) {
            Exception ex = var3;
            log.error(ex.getMessage());
            m.addAttribute("err", ex.getMessage());
        }

        return "index";
    }
}
