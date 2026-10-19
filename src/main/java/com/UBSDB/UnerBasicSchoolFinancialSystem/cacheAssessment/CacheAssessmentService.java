/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.cacheAssessment;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Teacher.Teacher;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author lys
 */
@Service
public interface CacheAssessmentService {
    public List<CacheAssessment> getCacheAssessment();
    public CacheAssessment saveCacheAssessment(CacheAssessment CA);
    public CacheAssessment editCachAssessment(CacheAssessment CA);
    public void deleteCachAssessment (CacheAssessment CA);
    public List<CacheAssessment> saveAllCacheAssessment(List<CacheAssessment>cachedAssessments);
    public void deleteAllCachedAssessment (Teacher t);
}
