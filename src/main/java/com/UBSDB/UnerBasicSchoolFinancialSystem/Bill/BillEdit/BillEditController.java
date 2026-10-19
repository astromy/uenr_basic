/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.BillEdit;

import com.UBSDB.UnerBasicSchoolFinancialSystem.security.UserService;
import java.security.Principal;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BillEditController {
    @Autowired
    private EditBillService EBS;
    @Autowired
    private UserService us;

    public BillEditController() {
    }

    @PostMapping({"/saveEditBill"})
    @ResponseBody
    public BillEdit saveBill(Principal p, EditBillRequest bill) {
        try {
            BillEdit ed = bill.getBillEdit();
            ed.setUser(this.us.FindUserByUserName(p));
            ed.setDate(LocalDate.now());
            return this.EBS.saveBill(ed);
        } catch (Exception var4) {
            Exception ex = var4;
            System.out.println(ex.toString() + "-------------->SaveEditBill");
            return new BillEdit();
        }
    }

    @GetMapping({"/FindEditBill/{id}"})
    @ResponseBody
    public BillEdit FindEditBill(Principal p, @PathVariable("id") int id) {
        try {
            return this.EBS.FindBillEdit(id);
        } catch (Exception var4) {
            return new BillEdit();
        }
    }
}
