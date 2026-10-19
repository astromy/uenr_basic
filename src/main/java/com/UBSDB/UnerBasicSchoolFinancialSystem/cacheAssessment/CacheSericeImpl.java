/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.cacheAssessment;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Teacher.Teacher;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lys
 */
@Service
public class CacheSericeImpl implements CacheAssessmentService{
private @Autowired CacheAssessmentRepository CR;
    
@Override
    public List<CacheAssessment> getCacheAssessment() {
        try{
     return CR.findAll();
          }catch (Exception ex){
       System.out.println(ex);
       return  new ArrayList<>();
       }
       }

    @Override
    public CacheAssessment saveCacheAssessment(CacheAssessment CA) {
    try{
     
      return CR.save(CA);
      
    }catch (Exception ex){
       return new CacheAssessment();
       }
    }

    @Override
    public CacheAssessment editCachAssessment(CacheAssessment CA) {
    try{
     
      return CR.save(CA);
      
    }catch (Exception ex){
       return new CacheAssessment();
       }
    }

    @Override
    public void deleteCachAssessment(CacheAssessment CA) {
    
   try{
     
   CR.delete(CA);
      
    }catch (Exception ex){
       
       }
    
    }

    @Override
    public List<CacheAssessment> saveAllCacheAssessment(List<CacheAssessment> cachedAssessments) {
    try {
    
    return  CR.saveAll(cachedAssessments);
    
    }catch (Exception ex){
    
    return new ArrayList<>();
    }
    
    
    
    }

    @Override
    public void deleteAllCachedAssessment(Teacher t) {
   
    CR.deleteInBatch(t.getCachedAssessments());

    
    }
    
}
