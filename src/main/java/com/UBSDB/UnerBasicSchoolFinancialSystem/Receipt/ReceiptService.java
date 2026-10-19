/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Receipt;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 *
 * @author HP
 */
@Service
public interface ReceiptService {
   public Receipt saveReceipt(Receipt receipt);
   
   public List<Receipt>receipts(Receipt receipt);
   
   public Receipt updateReceipt(Receipt receipt);
   public Optional<Receipt>findReceiptByPayment(Receipt receipt);
   public Optional<Receipt>findReceipt(Receipt receipt);
}
