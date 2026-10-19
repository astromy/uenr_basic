/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem;

import java.util.ArrayList;
import org.springframework.stereotype.Component;

/**
 *
 * @author lys
 */
@Component
public class ArrayJava {
    public static void main (String []args){
   int [] data={1,2,3,4,5,6,7,8,9};
   //finding the mid point 
   //lets first of all find the size of the whole array
   int totalsize = data.length;
   System.out.println(totalsize);
  // now lets find the middle element in this array
  System.out.println(data[totalsize/2]);
           }
}
