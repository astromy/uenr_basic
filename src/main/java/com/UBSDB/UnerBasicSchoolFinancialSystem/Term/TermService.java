/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Term;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Academic.Academic;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Academic.AcademicService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermWeek.TermWeek;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermWeek.TermWeekService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author enock
 */
@Service
@Slf4j
public class TermService {

    @Autowired
    private TermRepository TS;
    @Autowired
    private AcademicService AS;
    @Autowired
    private TermWeekService termWeekService;

    public String SaveTerm(Term term) {
        try{
        //if(LocalDate.parse(term.getStartDate()).getYear()!=LocalDate.now().getYear()||
        //  LocalDate.parse(term.getEndDate()).getYear()!=LocalDate.now().getYear()

        // ){ return "please the year input is incorrect please correct it to "+LocalDate.now().getYear();
//      } 
        for (Term t : terms()) {
            if ((t.getAcademicyear().equals(term.getAcademicyear())) && (t.getTermName().contains(term.getTermName()))) {
                return "Term has been added already";
            }
        }

        term = TS.save(term);

        var termWeeks = termWeekService.calculateTermWeeks_(LocalDate.parse(term.getStartDate()),
                LocalDate.parse(term.getEndDate()));
        for (var tempTermWeek : termWeeks) {
            tempTermWeek.setTerm(term);
            termWeekService.saveTermWeek(tempTermWeek);

        }

        Academic a = term.getAcademicyear();
        if (term.getTermName().equals("FirstTerm")) {
            a.setStartDate(LocalDate.parse(term.getStartDate()));
            a.setAcademicName(a.getStartDate().getYear() + "-");
            AS.EditAcademic(a);

        }
        if (term.equals("ThirdTerm")) {
            a.setEndDate(LocalDate.parse(term.getEndDate()));
            a.setAcademicName(a.getStartDate().getYear() + "-" + a.getEndDate().getYear());
            a.setSuccessorAcademicYear(a.getEndDate().getYear() + "-");
            AS.EditAcademic(a);
        }
        }catch (Exception ex){
        ex.printStackTrace();
        
        }
        return "saved Term successfully";
    }

    public List<Term> terms() {
        return TS.findAll();

    }

    public Term getCurrentTerm() {
        try {
            for (Term t : terms()) {
                if (LocalDate.now().isBefore(LocalDate.parse(t.getStartDate())) || LocalDate.now().
                        isAfter(LocalDate.parse(t.getEndDate())) == true) {
                } else {
                    return t;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Term updateTerm(Term term) {
        try{
        term = TS.save(term);
        var termWeeks = termWeekService.calculateTermWeeks_(LocalDate.parse(term.getStartDate()),
                LocalDate.parse(term.getEndDate()));
        var tempTermWeeks = term.getWeeks();
        if (tempTermWeeks == null) {
            term = TS.save(term);

            var output = termWeekService.calculateTermWeeks_(LocalDate.parse(term.getStartDate()),
                    LocalDate.parse(term.getEndDate()));

            for (var tempTermWeek : output) {
                tempTermWeek.setTerm(term);
                termWeekService.saveTermWeek(tempTermWeek);

            }
        }else{

        for (int a=0; a<=termWeeks.size()-1; a++) {

            for (int t=0; t<=tempTermWeeks.size()-1; t++) {
                if (termWeeks.get(a).getWeekName().equals(tempTermWeeks.get(t).getWeekName())) {
                    var newTermWeek=tempTermWeeks.get(t);
                    var updatedTermWeek = termWeeks.get(a);
                    newTermWeek.setStartDate(updatedTermWeek.getStartDate());
                    newTermWeek.setEndDate(updatedTermWeek.getEndDate());
                    newTermWeek.setNumberOfDays(updatedTermWeek.getNumberOfDays());
                    termWeekService.updateTermWeek( newTermWeek);
                }
            }

        }
        
        }
        Academic a = term.getAcademicyear();
        if (term.getTermName().equals("FirstTerm")) {
            a.setStartDate(LocalDate.parse(term.getStartDate()));
            a.setAcademicName(a.getStartDate().getYear() + "-");
            AS.EditAcademic(a);

        }
        if (term.equals("ThirdTerm")) {
            a.setEndDate(LocalDate.parse(term.getEndDate()));
            a.setAcademicName(a.getStartDate().getYear() + "-" + a.getEndDate().getYear());
            a.setSuccessorAcademicYear(a.getEndDate().getYear() + "-");
            AS.EditAcademic(a);
        }
        }catch (Exception ex){
               ex.printStackTrace();
                }
        return term;

    }

    public List<Term> BasedOnAcademicYear(Academic academic) {
        List<Term> t = new ArrayList<>();
        for (Term term : TS.findAll()) {
            if (term.getAcademicyear().getAcademicYear().equals(academic.getAcademicYear())) {

                t.add(term);

            }

        }
        return t;
    }

    public Optional<Term> findTermById(long termId) {

        return TS.findById(termId);
    }

    public List<Term> DeleteById(long termId) {
        TS.deleteById(termId);
        return TS.findAll();
    }

    public Optional<Term> findNextTermBasedOnTerm(Term term) {

        Set<Term> terms = TS.findById(term.getId()).get().getAcademicyear().getTerm();

        System.out.print("term_successor term___[[[]]]----->"
                + term.getAcademicyear().getSuccessorAcademicYear());
        if (term.getTermName().equals("FirstTerm")) {
            return terms.stream().filter(c -> c.getTermName().equals("SecondTerm")).findFirst();

        } else if (term.getTermName().equals("SecondTerm")) {

            return terms.stream().filter(c -> c.getTermName().equals("ThirdTerm")).findFirst();
        } else {
            Optional<Term> t = TS.findAll().stream().filter(c -> c.getAcademicyear().getAcademicName()
                    .equals((Integer.parseInt(term.getAcademicyear()
                            .getSuccessorAcademicYear()) - 1) + " - " + term.getAcademicyear()
                                    .getSuccessorAcademicYear()))
                    .filter(c -> c.getTermName().equals("FirstTerm")).findFirst();

            if (t.isPresent()) {

                System.out.println(t.get().getAcademicyear());
            } else {

                System.out.println("Not present");
            }
            return t;
        }
    }
}
