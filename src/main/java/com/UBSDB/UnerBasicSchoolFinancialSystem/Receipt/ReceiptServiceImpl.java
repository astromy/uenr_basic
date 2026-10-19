/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Receipt;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author HP
 */
@Service
public class ReceiptServiceImpl implements ReceiptService{
@Autowired private ReceiptRepository RR; 
    @Override
    public Receipt saveReceipt(Receipt receipt) {
     try{
         
     return receipt =RR.save(receipt);
     }catch (Exception ex){
         System.out.println(ex.toString());
     return null;
     }
     
    }

    @Override
    public List<Receipt> receipts(Receipt receipt) {
   
   return RR.findAll();
    }

    @Override
    public Receipt updateReceipt(Receipt receipt) {
if(RR.findById(receipt.getId()).isPresent()){
  
   return RR.save(receipt);
}else{

return null;
}
}
    

    @Override
    public Optional<Receipt> findReceipt(Receipt receipt) {
     return RR.findById(receipt.getId())  ;
    
}

    @Override
    public Optional<Receipt> findReceiptByPayment(Receipt receipt) {
        return RR.getReceiptBypayment(receipt.getPayment().getId()); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    

}