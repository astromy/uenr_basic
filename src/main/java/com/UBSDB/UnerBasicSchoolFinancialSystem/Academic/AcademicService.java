/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Academic;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

     /**
     *
     * @author enock
     */
      @Service
public class AcademicService {
    @Autowired
    private AcademicRepository academicRepository;
    @Autowired
    private StudentService studentService;

    public AcademicService() {
    }

    public Academic SaveAcademicYear(Academic aca) {
        aca.setAcademicYear(aca.getEndDate());
        aca.setAcademicName(aca.getStartDate().getYear() + " - " + aca.getEndDate().getYear());
        aca.setSuccessorAcademicYear(aca.getEndDate().getYear() + 1 + "");

        try {
            return (Academic)this.academicRepository.save(aca);
        } catch (Exception var3) {
            return aca;
        }
    }

    public boolean CompareDate(LocalDate date) {
        List<LocalDate> ddate = new ArrayList();
        Iterator var3 = this.AllAcademic().iterator();

        while(var3.hasNext()) {
            Academic newAca = (Academic)var3.next();
            ddate.add(newAca.getEndDate());
        }

        if (ddate.isEmpty()) {
            return false;
        } else {
            return ddate.equals(date.toString());
        }
    }

    public Optional<Academic> getAcademicyear_Date(LocalDate d) {
        return this.AllAcademic().stream().filter((c) -> {
            return d.equals(c) || d.isAfter(c.getStartDate()) || d.isBefore(c.getEndDate());
        }).findFirst();
    }

    public List<Academic> AllAcademic() {
        return this.academicRepository.findAll();
    }

    public Page<Academic> AllAcademicPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return this.academicRepository.findAll(pageable);
    }

    public Academic EditAcademic(Academic AcademicYear) {
        return (Academic)this.academicRepository.save(AcademicYear);
    }

    public Academic FindAcademic(Academic AcademicYear) {
        return (Academic)this.academicRepository.findById(AcademicYear.getId()).get();
    }

    public void DeleteAcademic(Long id) {
        this.academicRepository.deleteById(id);
    }
}
