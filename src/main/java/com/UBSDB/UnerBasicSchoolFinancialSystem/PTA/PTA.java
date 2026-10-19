/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.PTA;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;


/**
 *
 * @author enock
 */
@Entity
public class PTA {

    public Long getId() {
        return id;
    }
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private double Amount;
  @OneToOne
    private Term term;

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public PTA(Long id, double Amount, Term term) {
        this.id = id;
        this.Amount = Amount;
        this.term = term;
    }

    public PTA() {
    }
    
}
