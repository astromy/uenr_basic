/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.SchoolFees;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author enock
 */
@Repository
public interface SchoolFeesRepository extends JpaRepository<SchoolFees,Long>{
     @Query("SELECT u FROM SchoolFees u WHERE u.hashCode = :hashCode")
    public List<SchoolFees> getSchoolFeesByhashCode(@Param("hashCode") int hashCode);
    @Query("SELECT u FROM SchoolFees u WHERE u.FeeSpecification = :FeeSpecification And u.term.id= :term")
    public List<SchoolFees> getSchoolFeesByFeeSpecificationAndTerm
            (@Param("FeeSpecification") String FeeSpecification,
             @Param("term")long term
             );

}
