/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.security;

/**
 *
 * @author enock
 *
    // remaining getters and setters   
}
 */
import jakarta.persistence.*;

/**
 *
 * @author enock
 */

@Entity

@Table(name = "roles")
public class role {

    public role() {
    }

    public role(String name) {
        this.name = name;
    }
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
     
    public String name;
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
 