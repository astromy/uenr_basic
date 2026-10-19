/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.PTA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author enock
 */
@org.springframework.stereotype.Controller
@   RequestMapping("/FinancePortal/")
public class PTAController {
    @Autowired
    private PTAService PTAS;
    
    @GetMapping("/ShowPTAForm")
    public String ShowPtaForm(Model model){
        
      model.addAttribute("pta",new PTA());
      model.addAttribute("pTaTerm",PTAS.PTATerm());
      
        return "FinancePortal";
}
    @PostMapping("/SavePTA")
    public String SavePTA(Model model,PTA pta){
    model.addAttribute("msg",PTAS.SavePTA(pta));
    return "FinancePortal";
    }
    
}