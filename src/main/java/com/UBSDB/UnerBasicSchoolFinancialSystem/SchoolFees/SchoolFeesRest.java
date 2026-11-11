package com.UBSDB.UnerBasicSchoolFinancialSystem.SchoolFees;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that exposes SchoolFees data as JSON for client-side
 * live filtering in the Finance Portal.
 */
@RestController
@RequestMapping("/api")
public class SchoolFeesRest {

    private @Autowired SchoolFeesService SF;

    /**
     * Returns all school fees as SchoolFeesResponse DTOs.
     * The flattened DTO avoids lazy-loading issues and exposes only
     * the fields needed by the Finance Portal table.
     *
     * @return list of SchoolFeesResponse
     */
    @GetMapping("/school-fees")
    public ResponseEntity<List<SchoolFeesResponse>> getAllSchoolFees() {
        try {
            List<SchoolFeesResponse> fees = SF.getAllSchoolFeesResponse();
            return ResponseEntity.ok(fees);
        } catch (Exception ex) {
            System.out.println("Error fetching school fees: " + ex.toString());
            return ResponseEntity.ok(null);
        }
    }
}