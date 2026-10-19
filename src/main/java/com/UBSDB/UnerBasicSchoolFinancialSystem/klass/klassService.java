
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.klass;

import com.UBSDB.UnerBasicSchoolFinancialSystem.TKS.TeachrKlassSubjectService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Teacher.Teacher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class klassService {

   @Autowired
   private klassRepository ks;
   @Autowired
   private TeachrKlassSubjectService TKS;
   public klassReponse saveKlass(klass klss){
   try{
   klassReponse response= ks.save(klss).getKlassResponse();
   return response;
   }catch (Exception ex){return null; }
   }
   
   public Set<klass>AddClasses(){
       List <String> ClassClassification=new ArrayList<>(); 
   int as=0;
     String  ClassificationLetters []= {"A","B","C","D","E","F"};
     ClassClassification.addAll(Arrays.asList(ClassificationLetters));
   Set<klass>klasses=new HashSet<>();
   for(int a=1;a<=2;a++){
    for(String cl:ClassClassification){
     
  
  klass klas=new klass();
    klas.setClassClassification(cl);
    klas.setClassLevel("Nursery");
     klas.setClassName("Nursery"+a+cl);
    klas.setClassNumber(a);
     klasses.add(klas);
  }
    
   }
       for(int a=1;a<=2;a++){
           for(String cl:ClassClassification){


               klass klas=new klass();
               klas.setClassClassification(cl);
               klas.setClassLevel("Claases");
               klas.setClassName("Classes"+a+cl);
               klas.setClassNumber(a);
               klasses.add(klas);
           }

       }
    for(int a=1;a<=2;a++){
    for(String cl:ClassClassification){
    
    klass klas=new klass();
    klas.setClassClassification(cl);
    klas.setClassLevel("Kindergaten");
    klas.setClassNumber(a);
    klas.setClassName("KG"+a+cl);
     klasses.add(klas);
    }
   }
    
   
  
  for(int a=1;a<=6;a++){
      for(String cl:ClassClassification){
    
  klass klassr=new klass();
  klassr.setClassName("Basic"+a+cl);
  klassr.setClassNumber(a);
  if(a<=3){
  klassr.setClassLevel("LowerPrimary");
  }else{
  klassr.setClassLevel("UpperPrimary");
  }
  klassr.setClassClassification(cl);
  klasses.add(klassr);
  }
  }
  for(int a=1;a<=4;a++){
      for(String cl:ClassClassification){
    
   klass klassr=new klass();
   klassr.setClassName("JHS"+a+cl);
    klassr.setClassLevel("JuniorHigh");
    
  klassr.setClassNumber(a);
 klassr.setClassClassification(cl);
  
 klasses.add(klassr);
  }
  }
  
  klass klassr=new klass();
   klassr.setClassName("Alumi");
   klassr.setClassLevel("Alumi");



  klassr.setClassNumber(0);
 klassr.setClassClassification("Alumi");
  klasses.add(klassr);
  klassr=new klass();
  klassr.setClassName("Creche");
    klassr.setClassLevel("Creche");
   klasses.add(klassr);
  return klasses;
   }
   
   public klass FindKlass(String id){
   return ks.getOne(id);
   }
   
  public Set<String >GetAllSpecifications(){
  return ks.findAll().stream().map(klass::getClassLevel).collect(Collectors.toSet());
  
  }
   
   public List<klass>AllKlass(){
       List<klass>kl=ks.findAll();
       kl.removeIf(c->c.getClassName().equals("Alumi"));
   
   return kl;
   }
  public Map<Teacher,klass>GetClassTeachersInTheWholeSchool(){
  Map<Teacher,klass>allClassTeachers=new HashMap<>();
  ks.findAll().forEach((klass c) -> {
      if(TKS.getClassMaster(c.getClassName())==null){}else{
      allClassTeachers.put(TKS.getClassMaster(c.getClassName()).getTeacher(),
              TKS.getClassMaster(c.getClassName()).getKlas());}
  });
  return allClassTeachers;
}
public List<klass>getClassesFromStrings(List <String>klasses){

List<klass>allKllases=new ArrayList<>();
klasses.forEach(c->{
allKllases.add(ks.findById(c).get());

});
return allKllases;
}
}
