/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Admission;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermService;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author lys
 */
@Controller
public class AdmissionController {
    @Autowired
    private AdmissionService AS;
    @Autowired
    private TermService TS;

    public AdmissionController() {
    }

    @GetMapping({"downloadApplicationForm/{term}"})
    public String downloadAppllicationForm(Model model, @PathVariable("term") Long termId) {
        try {
            ApplicationForm app = new ApplicationForm("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
            if (this.AS.GetApplicationForm().isEmpty()) {
                app.setID("UBS" + LocalDate.now().getYear() + 0 + this.AS.GetApplicationForm().size() + 1);
            } else {
                app.setID("UBS" + LocalDate.now().getYear() + 0 + this.AS.GetApplicationForm().size());
            }

            app = this.AS.saveApplicationForm(app);
            model.addAttribute("AppId", app.getID());
            model.addAttribute("TermAppForm", this.TS.findTermById(termId).get());
            model.addAttribute("appForm", true);
            return "Receipt";
        } catch (Exception var4) {
            model.addAttribute("msg", "Sorry things are not right");
            return "index";
        }
    }

    @GetMapping({"/DeleteForms"})
    public void DeleteForms() {
        this.AS.deleteAll();
    }
}
