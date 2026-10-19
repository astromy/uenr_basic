/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Academic;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermService;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author enock
 */

@Controller
public class AcademicController {
    @Autowired
    private AcademicService AS;
    @Autowired
    TermService Ts;

    public AcademicController() {
    }

    @RequestMapping({"/EditAcademicYear"})
    public String EditAca(Model model, Academic aca_year, @RequestParam("ID") Long Id, @RequestParam("sDate") String startDate, @RequestParam("eDate") String endDate) {
        try {
            aca_year.setEndDate(LocalDate.parse(endDate));
            aca_year.setStartDate(LocalDate.parse(startDate));
            aca_year.setId(Id);
            model.addAttribute("msg", this.AS.SaveAcademicYear(aca_year).getAcademicName() + " has been updated successfully");
            return "index";
        } catch (Exception var7) {
            Exception ex = var7;
            model.addAttribute("msg", "sorry we will get back to you soon");
            System.out.println("" + ex);
            return "index";
        }
    }

    @RequestMapping({"/SaveAca"})
    public String SaveAca(Model model, Academic aca_year, @RequestParam("sDate") String startDate, @RequestParam("eDate") String endDate) {
        try {
            aca_year.setEndDate(LocalDate.parse(endDate));
            aca_year.setStartDate(LocalDate.parse(startDate));
            model.addAttribute("msg", this.AS.SaveAcademicYear(aca_year).getAcademicName() + " has been saved successfully");
            return "index";
        } catch (Exception var6) {
            Exception ex = var6;
            model.addAttribute("msg", "sorry we will get back to you soon");
            System.out.println("" + ex);
            return "index";
        }
    }

    @GetMapping({"/showAcademicForm"})
    public String showFormAcademicForm(Model model) {
        model.addAttribute("AcademicForm", new Academic());
        return "index";
    }

    @GetMapping({"/AllAcademicYears"})
    public String AllAcademicYears(Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Page<Academic> academicPage = this.AS.AllAcademicPaginated(page, size);
        model.addAttribute("allAcademicYears", academicPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", academicPage.getTotalPages());
        model.addAttribute("totalItems", academicPage.getTotalElements());
        model.addAttribute("size", size);
        return "index";
    }

    @GetMapping({"/EditAcademicYear/{id}"})
    public String EditAcademicYearForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("EditAcademicyear", this.
                AS.FindAcademic(new Academic(id)).getAcademicResponse());
        return "index";
    }

    @GetMapping({"/DeleteAcademicYear/{id}"})
    public String DeleteAcademicYear(@PathVariable("id") Long id) {
        try {
            this.AS.DeleteAcademic(id);
        } catch (Exception ex) {
            System.out.println("Error deleting academic year: " + ex);
        }
        return "redirect:/AllAcademicYears";
    }
}
