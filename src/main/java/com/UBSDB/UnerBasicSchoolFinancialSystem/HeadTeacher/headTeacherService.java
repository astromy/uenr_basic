/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.HeadTeacher;

import com.UBSDB.UnerBasicSchoolFinancialSystem.security.role;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.users;
import java.util.Iterator;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author HP
 */
@Service
public class headTeacherService {
    public users getHeadTecherFromList(List<users>all_users){
        for (users u : all_users) {
            for(role r :u.getRoles()){
                if(r.getName().equals("HeadTeacher")){
                    
                    return u;
                }
                
            }
        }
    return null; 
    }
}

