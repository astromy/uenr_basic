/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.PROMOTION;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 *
 * @author lys
 */
@Service
public interface promoteService {
    
    public promote savePromotion(promote promotion);
    public List <promote>Studentpromotions(Student student);
    public String savePromtion_for_A_Class(klass klass);
    public Optional<promote> findPromotion(promote promotion);
    
}
