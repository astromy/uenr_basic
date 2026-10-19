/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.klass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author enock
 */
@Repository
public interface klassRepository extends JpaRepository<klass, String> {

    /**
     * Find all classes whose className starts with the given prefix.
     * Useful for finding all Basic1 classes (Basic1A, Basic1B, etc.)
     */
    List<klass> findByClassNameStartingWith(String prefix);

    /**
     * Find all classes whose className contains the given infix.
     */
    @Query("SELECT k FROM klass k WHERE k.className LIKE CONCAT('%', :infix, '%')")
    List<klass> findByClassNameContaining(@Param("infix") String infix);
}
