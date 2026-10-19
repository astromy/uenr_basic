/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermWeek;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermService;
import java.security.Principal;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Enock
 */
@Controller
@RequestMapping("/TermWeek")
public class TermWeekController {
    @Autowired
    private TermWeekService termWeekService;
    @Autowired
    private TermService termService;
    
    @GetMapping("/add/{termId}")
    public String addTermWeek(Model model, @PathVariable("termId") Long termId) {
        Term term = termService.findTermById(termId).orElse(null);
        if (term == null) {
            return "redirect:/getAllTerms";
        }
        
        TermWeek termWeek = new TermWeek();
        termWeek.setTerm(term);
        model.addAttribute("termWeek", termWeek);
        model.addAttribute("term", term);
        model.addAttribute("weeks", termWeekService.getWeeksByTermId(termId));
        model.addAttribute("addTermWeek", true);
        return "index";
    }
    
    @PostMapping("/save")
    public String saveTermWeek(Model model, TermWeek termWeek) {
        String result = termWeekService.saveTermWeek(termWeek);
        model.addAttribute("msg", result);
        model.addAttribute("addTermWeek", true);
        model.addAttribute("termWeek", new TermWeek());
        model.addAttribute("term", termService.findTermById(termWeek.getTerm().getId()).orElse(null));
        model.addAttribute("weeks", termWeekService.getWeeksByTermId(termWeek.getTerm().getId()));
        return "index";
    }
    
    @GetMapping("/view/{termId}")
    public String viewTermWeeks(Model model, @PathVariable("termId") Long termId) {
        Term term = termService.findTermById(termId).orElse(null);
        if (term == null) {
            return "redirect:/getAllTerms";
        }
        
        List<TermWeek> weeks = termWeekService.getWeeksByTermId(termId);
        model.addAttribute("term", term);
        model.addAttribute("weeks", weeks);
        model.addAttribute("viewTermWeeks", true);
        return "index";
    }
    
    @GetMapping("/edit/{weekId}")
    public String editTermWeek(Model model, @PathVariable("weekId") UUID weekId) {
        TermWeek termWeek = termWeekService.getWeekById(weekId);
        if (termWeek == null) {
            return "redirect:/getAllTerms";
        }
        
        model.addAttribute("termWeek", termWeek);
        model.addAttribute("term", termWeek.getTerm());
        model.addAttribute("weeks", termWeekService.getWeeksByTermId(termWeek.getTerm().getId()));
        model.addAttribute("editTermWeek", true);
        return "index";
    }
    
    @PostMapping("/update")
    public String updateTermWeek(Model model, TermWeek termWeek) {
        String result = termWeekService.updateTermWeek(termWeek);
        model.addAttribute("msg", result);
        model.addAttribute("editTermWeek", true);
        model.addAttribute("termWeek", termWeek);
        model.addAttribute("term", termWeek.getTerm());
        model.addAttribute("weeks", termWeekService.getWeeksByTermId(termWeek.getTerm().getId()));
        return "index";
    }
    
    @GetMapping("/delete/{weekId}")
    public String deleteTermWeek(Model model, @PathVariable("weekId") UUID weekId) {
        TermWeek termWeek = termWeekService.getWeekById(weekId);
        Long termId = termWeek != null ? termWeek.getTerm().getId() : null;
        
        String result = termWeekService.deleteWeek(weekId);
        model.addAttribute("msg", result);
        
        if (termId != null) {
            return "redirect:/TermWeek/view/" + termId;
        }
        return "redirect:/getAllTerms";
    }
}
