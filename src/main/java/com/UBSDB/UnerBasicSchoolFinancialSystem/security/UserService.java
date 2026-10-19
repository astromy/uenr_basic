/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.security;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author enock
 */
@Service
public class UserService {
    
     @Autowired
    private UserRepository userRepository;
    
      @Autowired
    private RoleRepository roleRepository;
    
  public String encode(String password){
BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
    return encoder.encode(password);
    }
  
    public users FindUserById(String id){
    return userRepository.getUserByUsername(id);
    }
     public users FindUserById(Long id){
    return userRepository.findById(id).get();
    }
    public List<users>FindAllUser(){
    return (List<users>)userRepository.findAll();
    
    }
    
  
    public String addRole(role r){
    try{
    roleRepository.save(r);return "saved";
    }catch (Exception ex){
    return "error"+ex.toString();
    
    }
    
    }
    
    public String addUser(users user){
        if(checkIfUserNameIsAssigned(user).equals("false")){
         
           try{
               user.setEnabled(true);
            user=     userRepository.save(user);
 System.out.println(user.toString());
             
      return"UserName: "+user.getUsername() +" Password: "+user.getUsername()+"ubs";
           }catch (Exception ex){
               ex.printStackTrace();
               return "null";
           }
      }else{
      return "User has been added already";
      }
   }
     public String updateUser(users user){
     //   if(      checkIfUserNameIsAssigned(user).equals("false")){
   
     // return"UserName: "+user.getUsername() +" password: "+user.getUsername()+"ubs";
    
     // }else{
      return  userRepository.save(user).getUsername()+" updated";
      
   }
    public String addUserAdminFinance(users user){
        BCryptPasswordEncoder BPE=new BCryptPasswordEncoder();
        user.setPassword(BPE.encode(user.getUsername()+"ubs"));
        user.setEnabled(true); System.out.println(checkIfUserNameIsAssigned(user));
         
        try{
      if(      checkIfUserNameIsAssigned(user).equals("false")){
            Runnable r3=()->{
            List<users>Tusers=findALLUserWithParticularRole("HeadTeacher");
    if(Tusers.isEmpty()){}else{
    Tusers.stream().forEach(c->{
   System.out.println("found headteacher and will be disabled for now -------------->--------------->");
   c.setEnabled(false);
   userRepository.save(c);
    });}
    };r3.run();
          userRepository.save(user);
          return"UserName is : "+user.getUsername() +"  and  password: "+user.getUsername()+"ubs";
          
      }else{
          return "User has been added already";
          
      }
        }catch(Exception ex){
        return "error contact admin"+ex.toString();
        }
   }
   public String updateUserAdminFinance(users user){
        BCryptPasswordEncoder BPE=new BCryptPasswordEncoder();
       // user.setPassword(BPE.encode(user.getUsername()+"ubs"));
        user.setEnabled(true); 
       //System.out.println(checkIfUserNameIsAssigned(user));
         
        try{
          users tuser=userRepository.findById(user.getId()).get();
          tuser.setEnabled(true);
          tuser.setFirstname(user.getFirstname());
          
          tuser.setPassword(BPE.encode(user.getPassword()));
          tuser.setPhonnumber(user.getPhonnumber());
          userRepository.save(tuser);
          return " UserName: "+tuser.getUsername() +" updated Successfully";
          
      
        }catch(Exception ex){
        return "error contact admin"+ex.toString();
        }
   }
 public Map <String,Integer> FindUserRoles(){
     
     Map <String,Integer>UserRole=new HashMap<>();
      UserRoles().forEach(rol -> {
             UserRole.put(rol.getName(), rol.getId());
         });
     
 return UserRole;
 }
 
 public role findparticularRole(int roleid){
 return roleRepository.findById(roleid).get();
 } 
 public List<role>UserRoles(){
 return (List<role>) roleRepository.findAll();
 }
 
  public String addRoleTOUser(String Userid,role newRole){
      Set<role>userRoles=new HashSet<>();
      userRoles.add(newRole);
  users user=FindUserById( Userid);
  user.setRoles(userRoles);
  try{
      
  user=userRepository.save(user);
  return "user roles"+user.getRoles().toString();
  }catch(Exception ex){
  return"error adding role";
  }
 
  }
  public String checkIfUserNameIsAssigned(users user){
  
      if(userRepository.getUserByUser_name(user.username).isPresent()){return "true";}
     

  return "false";
  }
  public users FindUserByUserName(Principal p){
  return userRepository.getUserByUsername(p.getName());
  }
   public List<users> FindUserByUserPho(String p_){
  return userRepository.getUserByPhonnumber(p_);
  }
   private List<users>findALLUserWithParticularRole(String role){
  List<users>TempUsers=new ArrayList<>();
Runnable r2=()->{     
  userRepository.findAll().forEach(c->{
           if(c.getRoles().stream().filter(r->r.getName().equals(role)).findFirst().isPresent()){
           TempUsers.add(c);
           
           }
   
   });
};r2.run();
return TempUsers;
   }
}
