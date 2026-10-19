/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.BillEdit;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.Bill;
import org.springframework.stereotype.Service;

/**
 *
 * @author lys
 */
@Service
public interface EditBillService {

    public BillEdit saveBill(BillEdit BE);
    public BillEdit updateStudentBill(BillEdit BE);
    public BillEdit FindBillEdit (int ID);
    public Float TotalEditAmount(Bill sB);
    
}
/*
  FIBONACCI SERIES IN JAVA
  This method implements fibonacci series for the first 10 numbers
Example: f(10)=0,1,1,2,3,5,8,13,21,34
        create an array of elements of 10 
       first , second , next 
        display first and second
         display to the 10th number
        next =first + second
        display next
        first =second
        second =next
        
       
 
*/



