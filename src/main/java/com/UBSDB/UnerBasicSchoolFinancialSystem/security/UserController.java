  /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.security;

import static java.lang.System.out;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author enock
 */
@Controller
@Slf4j
public class UserController {
    @Autowired
    private UserService UDSI;
    
    @Autowired 
    private UserRepository repo;
    @Autowired
    private UserService US ;
      @GetMapping("usergeneral")
    public String generalInfo( Model m){
        try{
        m.addAttribute("generalUser", true);
            
        }catch (Exception ex){
     m.addAttribute("err", "PLEASE CONTACT ADMIN");
      System.out.println(ex.toString());
        }
    return "index";
    }
    @GetMapping("/RouteUser")
    public String showAddUserPage(Model model,Principal principal,Authentication authentication)throws Exception{
      try{
        Optional <? extends GrantedAuthority> role=authentication.getAuthorities().stream().findFirst();
   
          switch (role.get().getAuthority()) {
              case "Admin":
                  System.out.println(" admin logging in");
                  return "redirect:/Admin/home";
              case "Teacher":
                  return "redirect:/TeacherPortal/";
              case "Student":
                  out.println ("Student log in......... ");
                  return "redirect:/StudentPortal/";
              case "Finance":
                  return "redirect:/FinancePortal/";
              case "HeadTeacher":
                  return "redirect:/HeadTeacher/";
              default:
                  break;
          }
      }catch(Exception ex){
          
          ex.printStackTrace();
      return "/login";
      }
   return "";
     }
    @PostMapping("/addUser")
    
    public String addUser(Model model,users user ){
    model.addAttribute("msg" , UDSI.addUser(user));
    return "index";    
    }
    @GetMapping("/AllUsers")
    public String AllUsers( Model model){
        List<users>Allusers=UDSI.FindAllUser();
         // DCrypt encode=new BCryptPasswordEncoder();
        
    List<users>TempList=new ArrayList<>();
    Runnable r1 =()->{
    Allusers.stream().forEach(c->{
     c.getRoles().stream().forEach(r->{
    
     c.setPassword(encode.encode(c.getPassword()));
     c.setPassword("");
     TempList.add(c);
   
     });
    });  };r1.run();
        model.addAttribute("users" ,TempList);
   // System.out.println(Allusers.toString());
    return "index";    
    }
     
     String routeuser(users u){
     u =UDSI.FindUserById(u.getId());
     if(u.getRoles().stream().findFirst().get().getName().equals("Admin")){
    return "index";
    }
 if(u.getRoles().stream().findFirst().get().getName().equals("Teacher")){
       
   return "TeacherPortal"; 
  }
 if(u.getRoles().stream().findFirst().get().getName().equals("Student")){
   
   return "StudentPortal";
   
      }
 if(u.getRoles().stream().findFirst().get().getName().equals("Finance")){
   
   return "FinancePortal";
   
      }
  if(u.getRoles().stream().findFirst().get().getName().equals("HeadTeacher")){
   
  return "HeadTeacherPortal";
   
      }
   else{
   return "";
   }
    
     
     }
     
     @GetMapping("/updateUser")
   public String addUserAdminFinance(Model model,Principal p ){
           users u   =UDSI.FindUserByUserName(p);
   
       try {
             
        
     model.addAttribute("updateUser" , UDSI.FindUserById(u.getUsername()));
    model.addAttribute("Roles" , 
            UDSI.FindUserById(u.getUsername()).getRoles());
   
      return routeuser(u); 
         } catch (Exception e) {
       model.addAttribute("msg" , e);
       return routeuser(u); 
     }
      
    }
    @PostMapping("/addUserAdminFinance")
 public String addUserAdminFinance(Model model, users user ){
   
     model.addAttribute("msg" , UDSI.addUserAdminFinance(user));
    return "index";    
    }
  @PostMapping("/updateUserAdminFinance")
 public String UpdateUserAdminFinance(Model model,users user, Principal p ){
     try{
         user.setId(UDSI.FindUserByUserName(p).getId());
         model.addAttribute("msg" , 
                 UDSI.updateUserAdminFinance(user));
     
     }catch(Exception e){
       model.addAttribute("msg" , e);
     
     }
     users u   =UDSI.FindUserByUserName(p);
System.out.println(".........><....Secutiry page "+user.getId());
//
   return routeuser(u);    
    }
    
     BCryptPasswordEncoder encode=new BCryptPasswordEncoder();
        
    @GetMapping("/ShowAddUserForm")
    public String ShowAddUserForm(Model model){
    model.addAttribute("NewUser",new users());
        List<role>roles=US.UserRoles();
        roles.removeIf(c->c.getName().equals("Student"));
        roles.removeIf(c->c.getName().equals("Teacher"));
    model.addAttribute("Roles",roles);
    return "index";
    }
    @GetMapping("/AddUserAdmin")
    public @ResponseBody users AddUser() throws Exception{
    users user=new users();
             BCryptPasswordEncoder encode=new BCryptPasswordEncoder();
            
            user.setEnabled(true);
            user.setUsername("enock");
      
           user.setPassword(encode.encode("Enock1234"));
           
          System.out.println("visited this place");
            
            Set<role>roles=new HashSet<>();
            roles.add(US.findparticularRole(US.FindUserRoles().get("Admin")));
           user.setRoles(roles);
       System.out.println( US.addUser(user).toString()+" User has been added successfully "+user.getId());
        return user;
    }
    @GetMapping("/AddUserRoles")
    public @ResponseBody String AddUserRoles(){
    Set<role>Roles=new HashSet<>();
    Roles.add(new role("Admin"));
    Roles.add(new role("Finance"));
    Roles.add(new role("Student"));
    Roles.add(new role("Teacher"));
    Roles.add(new role("HeadTeacher"));
    for(role r:Roles){
     US.addRole(r);
     
    }
    
    return "index";
    }
    @GetMapping("/login")
    public String ViewLogInPage(){
    System.out.println("login page initialized");
    return "Login";
    }
    @GetMapping("/error")
    public String ViewErrorInPage(){
    
    return "Login";
    } 
    @GetMapping("/reset")
     public String ViewReset(Model m){
    m.addAttribute("reset", true);
    return "Reset";
    }
     @GetMapping("/reset/checkUser")
     public String checkUser(Model model,@RequestParam("ph")String ph
             ,@RequestParam("user_name")String user_name){
        
     if(!US.FindUserByUserPho(ph).isEmpty()){
     Optional <users> u = US.FindUserByUserPho(ph).stream().filter(c->c.username.equals(user_name)).findFirst();
      if(u.isPresent()){
         BCryptPasswordEncoder encode=new BCryptPasswordEncoder();
     users uT =u.get();
         uT.setPassword(encode.encode(ph));
          uT.setEnabled(true);
         model.addAttribute("msg", US.updateUser(uT) +" Your new password is "+ ph);
      }else{
        model.addAttribute("msg", "please check details ");
        model.addAttribute("reset", true);
   
        return "Reset";
      
      }
     
     }else{
     model.addAttribute("msg", "please check details ");
     model.addAttribute("reset", true);
   
     }
        
     return "Reset";
     }
     @GetMapping ("/allUsers")
     public @ResponseBody List<users> getAllUser(){
     
     return US.FindAllUser();
     }
}
