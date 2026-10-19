/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermWeek;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Enock
 */
@Repository
public interface TermWeekRepository extends JpaRepository<TermWeek, java.util.UUID>{
    List<TermWeek> findByTerm(Term term);
    List<TermWeek> findByTermId(Long termId);
}
