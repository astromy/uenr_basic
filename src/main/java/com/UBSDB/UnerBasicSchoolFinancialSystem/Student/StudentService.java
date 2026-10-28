/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Student;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Academic.Academic;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Academic.AcademicService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.BillService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.Bill;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.StudentBill.StudentBill;
import com.UBSDB.UnerBasicSchoolFinancialSystem.SchoolFees.SchoolFees;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentID_Checker.IDChecker;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentID_Checker.IDCheckerService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klassService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.UserService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.role;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.users;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author enock
 */
@Service
public class StudentService {
   @Autowired
     private StudentRepository studentRepository;
     @Autowired
     private BillService billService; 
     @Autowired
     private klassService KS;
     @Autowired
     private UserService US;
       @Autowired
     private StudentPromotion SP;
       private @Autowired IDCheckerService checkIds;
       private @Autowired AcademicService AYS;
       
    public String promoteStudent(Student student){
    klass klas=null;
  if(student.getStudentStatus().equals("Stopped")){
  
  }else{
        klas=student.getStudentklass();
        
        if(klas.getClassName().equals("LowerCreche")){
          
          student.setStudentklass(KS.FindKlass("UpperCreche"));
          
        }
         if(klas.getClassName().equals("UpperCreche"+klas.getClassClassification())){
      
          student.setStudentklass(KS.FindKlass("Nursery1"+klas.getClassClassification()));
       
         }
         
        if(klas.getClassName().equals("Nursery1"+klas.getClassClassification())){
        
          student.setStudentklass(KS.FindKlass("Nursery2"+klas.getClassClassification()));
         
        }
        
        if(klas.getClassName().equals("Nursery2"+klas.getClassClassification())){
      student.setStudentklass(KS.FindKlass("KG1"+klas.getClassClassification()));
        
         
        }
        
        if(klas.getClassName().equals("KG1"+klas.getClassClassification())){
      student.setStudentklass(KS.FindKlass("KG2"+klas.getClassClassification()));
     
          
        }
         if(klas.getClassName().equals("Basic5"+klas.getClassClassification())){
      student.setStudentklass(KS.FindKlass("Basic6"+klas.getClassClassification()));
        
         
         }
          if(klas.getClassName().equals("KG2"+klas.getClassClassification())){
            student.setStudentklass(KS.FindKlass("Basic1"+klas.getClassClassification()));
     
          
          }
        
       if(klas.getClassName().equals("Basic1"+klas.getClassClassification())){
      student.setStudentklass(KS.FindKlass("Basic2"+klas.getClassClassification()));
     
       }
         if(klas.getClassName().equals("Basic2"+klas.getClassClassification())){
      student.setStudentklass(KS.FindKlass("Basic3"+klas.getClassClassification()));
       
         }
     if(klas.getClassName().equals("Basic3"+klas.getClassClassification())){
    student.setStudentklass(KS.FindKlass("Basic4"+klas.getClassClassification()));
        
     }
     
       if(klas.getClassName().equals("Basic4"+klas.getClassClassification())){
       student.setStudentklass(KS.FindKlass("Basic5"+klas.getClassClassification()));
   
       }
       
       if(klas.getClassName().equals("Basic5"+klas.getClassClassification())){
       student.setStudentklass(KS.FindKlass("Basic6"+klas.getClassClassification()));
     
       }
        if(klas.getClassName().equals("Basic6"+klas.getClassClassification())){
      student.setStudentklass(KS.FindKlass("JHS1"+klas.getClassClassification()));
      
        }
        if(klas.getClassName().equals("JHS1"+klas.getClassClassification())){
       student.setStudentklass(KS.FindKlass("JHS2"+klas.getClassClassification()));
     
           
      }
        if(klas.getClassName().equals("JHS2"+klas.getClassClassification())){
     student.setStudentklass(KS.FindKlass("JHS3"+klas.getClassClassification()));
     
            
      }
         if(klas.getClassName().equals("JHS3"+klas.getClassClassification())){
     student.setStudentklass(KS.FindKlass("Alumi"));
     }
              try{
      return UpdateStudentRecords(student);
          }catch(Exception ex){
          return "fatal error;"+ex.toString();
          }
      }
        return "null";
    
  }
    
    
    //Student who have stooped school
    public String FlagStudent(String studId){
    
        Student stud=findStudent(studId);
   
         stud.setStudentStatus("Stopped");
         studentRepository.save(stud);
    return "Flagged Successfully";
       
        }
    public Optional<Student>findOptionalStudent(String id){
    return studentRepository.findById(id);
    
    }
    public Optional<Student>findOptionalStudentByReference(String reference){
    return studentRepository.getStudentByStudentReference(reference);
    
    }
    public List<Student>AALLStudents(){
    return studentRepository.findAll();
    }

   public String deleteStudent(String studId){
   
   Student stud=findStudent(studId);
    studentRepository.delete(stud);
  return " Removed";
   }
    public String UpdateStudentRecords(Student stud){
    stud=ChangeToUpper(stud);
       
        try{
         Student student=findStudent(stud.getStudentId());
         student.setStudentStatus("in School");     
        student.setFirstName(stud.getFirstName());
        student.setLastName(stud.getLastName());
        student.setSurname(stud.getSurname());
        student.setDateOfBirth(stud.getDateOfBirth());
        student.setParentCategory(stud.getParentCategory());
        student.setStudentklass(stud.getStudentklass());
        student.setParentNumber(stud.getParentNumber());
         student.setStudentPicture(student.getStudentPicture());
        student.setProductPicturePath(student.getProductPicturePath());
        student.setOccupation(stud.getOccupation());
         student.setProductPicturePathPDF(student.getProductPicturePathPDF());
    student.setNHISCardumber(stud.getNHISCardumber());
    student.setGuadiaName(stud.getGuadiaName());
    student.setSex(stud.getSex());
    student.setStaffId(stud.getStaffId());
     return "updated Successfully";
       }catch(Exception ez){
                 System.out.println(ez);
     
       return "error"+ez.toString();
       }
    
    }
    public String UpdateStudentBill(Student stud, Bill Bill){
   // stud=ChangeToUpper(stud);
       
        try{
            
            
            
        Student student=findStudent(stud.getStudentId());
    //  stud.setStudentPicture(student.getStudentPicture());
      //  stud.setProductPicturePath(student.getProductPicturePath());
        //stud.setStudentStatus(student.getStudentStatus());
         //stud.setProductPicturePathPDF(student.getProductPicturePathPDF());

   studentRepository.save(stud);
    return "updated Successfully";
       }catch(Exception ez){
       return "error"+ez.toString();
       }
    
    }
     public String UpdateStudentRecords(Student stud,MultipartFile file){
   // stud=ChangeToUpper(stud);
       
        try{
            
            
          
            
        Student student=findStudent(stud.getStudentId());
        student.setFirstName(stud.getFirstName());
        student.setLastName(stud.getLastName());
        student.setSurname(stud.getSurname());
        student.setDateOfBirth(stud.getDateOfBirth());
        student.setStudentklass(stud.getStudentklass());
        student.setNHISCardumber(stud.getNHISCardumber());
       // student.setStudentklass(studentklass);
        //student.getNHISCardumber(stud.);
        student.setGhanaCardNumber(stud.getGhanaCardNumber());
        student.setParentNumber(stud.getParentNumber());
        student.setParentCategory(student.getParentCategory());

        users u =US.FindUserById(stud.getStudentId());
        if (u==null){

        }else{
            u.setPhonnumber(stud.getParentNumber());
            US.updateUser(u);
        }
        student.setStudentStatus("in School");
        String fileName=org.springframework.util.StringUtils.cleanPath(file.getOriginalFilename());
       
            System.out.println(fileName+" ........update side");
        
        student.setStudentPicture(fileName);
      //   stud.setStudentStatus("");
            stud.setProductPicturePath("/var/data/stud-pics/"+LocalDate.now().getYear()+"/"+stud.getStudentId()+"/"+stud.getStudentPicture());
            student.setProductPicturePathPDF(LocalDate.now().getYear()+"/"+stud.getStudentId()+"/"+student.getStudentPicture());
             try {
                uploadPicture(file,ChangeToUpper(stud),fileName);
            } catch (IOException ex) {
                Logger.getLogger(StudentService.class.getName()).log(Level.SEVERE, null, ex);
            }

    //student.setStudentStatus(stud.getStudentStatus());
          System.out.println(studentRepository.save(student).getFirstName()+" update");
   // SR.save(student);
   

    return "updated Successfully";
       }catch(Exception ez){
       return "error"+ez.toString();
       }
    
    }
    public List<Student> SearchStudent (String search ) throws Exception{
     try {
     Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
     Page<Student> page = studentRepository.findByStudentIdOrFirstNameOrLastNameOrsurname(search, pageable);
     return page.getContent();
     }catch (Exception ex){
     return new ArrayList<>();
     }
}
    public Page<Student> getStudentsByClassPaged(
            klass k,
            String search,
            Pageable pageable) {

        return studentRepository
                .findStudentsByClassAndSearch(
                        k,
                        search,
                        pageable
                );
    }
    public long countAllStudents(){
     return studentRepository.count();
    }

    public long countJuniorStaffStudents(){
     return studentRepository.countJuniorStaffStudents();
    }

    public long countNonStaffStudents(){
     return studentRepository.countNonStaffStudents();
    }

    public long countSeniorMemberStudents(){
     return studentRepository.countSeniorMemberStudents();
    }

    public long countStudentsByClassLevel(String level){
     return studentRepository.countStudentsByClassLevel(level);
    }
    public String UpdateStudent(Student stud, MultipartFile file) {

        stud = ChangeToUpper(stud);

        try {
            Student student = findStudent(stud.getStudentId());

            student.setFirstName(stud.getFirstName());
            student.setLastName(stud.getLastName());
            student.setSurname(stud.getSurname());
            student.setDateOfBirth(stud.getDateOfBirth());
            student.setStudentklass(stud.getStudentklass());
            student.setNHISCardumber(stud.getNHISCardumber());
            student.setGhanaCardNumber(stud.getGhanaCardNumber());

            if (file != null && !file.isEmpty()) {

                String fileName = "profile.jpg";

                String result = uploadPicture(file, student, fileName);

                // If upload failed, don't update the student
                if (!result.startsWith("successfully")) {
                    return result;
                }

                // If your Student entity stores the image path,
                // update it here.
                String imagePath =
                        "/css/page/stud-pics/"
                                + LocalDate.now().getYear()
                                + "/"
                                + student.getStudentId()
                                + "/"
                                + fileName;

                student.setStudentPicture(imagePath);
            }

            studentRepository.save(student);

            return "updated Successfully";

        } catch (Exception ez) {
            return "error: " + ez.getMessage();
        }
    }


    public Student ChangeToUpper(Student stud){
    stud.setFirstName(stud.getFirstName().toUpperCase());
        stud.setLastName(stud.getLastName().toUpperCase());
        stud.setGuadiaName(stud.getGuadiaName().toUpperCase());
        stud.setOccupation(stud.getOccupation().toUpperCase());
        stud.setSurname(stud.getSurname().toUpperCase());
        
        return stud;
    
    }
    
     public String saveStudentWithPicture(Student stud,MultipartFile file) throws Exception{
     Optional <Academic>academicYear=AYS.getAcademicyear_Date(LocalDate.now());
     if (academicYear.isPresent()){
         String size = academicYear.get().getIDCheckers().size()+"";
         if (size.length()==1){
         size = "00"+size;
         }else if (size.length()==2){
         size="0"+size;
         }
         stud.setStudentId("UBS"+LocalDate.now().getYear()+size);
         stud= ChangeToUpper(stud);
         
         InputStream input;
       stud.setStudentStatus("in School");
       stud.setNewComer(true);
       try{
              if(studentRepository.findById(stud.getStudentId()).isPresent()){
              return "please User Id alreasy assigned";
              }
              if(file.isEmpty()){
             
              }
     String fileName=org.springframework.util.StringUtils.cleanPath(file.getOriginalFilename());
     stud.setStudentPicture(fileName);
   stud.setProductPicturePath("/var/data/stud-pics/"+LocalDate.now().getYear()+"/"+stud.getStudentId()+"/"+stud.getStudentPicture());
   stud.setProductPicturePathPDF(LocalDate.now().getYear()+"/"+stud.getStudentId()+"/"+stud.getStudentPicture());
    
        
   stud  = studentRepository.save(stud);
  
   CreateStudentAccount(stud);
   uploadPicture(file,stud,fileName);


    checkIds.Savechecker(new IDChecker(Integer.parseInt(size), academicYear.get()));

    return "Student "+stud.getFirstName()+" "+stud.getSurname()
            +" have been added successfully with user name "+ stud.getStudentId() + "and password "+stud.getStudentId()+"1234";
    }catch (Exception ex){
    
    return " fatal error please contact admin "+ex.toString();
    }
     }else{
     return "Please set current academic year ";
     }
    }
   
    
//    public String uploadPicture(MultipartFile multiPartFile,Student stud,String fileName) throws IOException{
//        BufferedImage im=ImageIO.read(multiPartFile.getInputStream());
//       ByteArrayOutputStream OS=new ByteArrayOutputStream();
//    ImageIO.write(resizeImage(im,300,300),StringUtils.getFilenameExtension(multiPartFile.getOriginalFilename()) ,OS);
//    byte[] b=OS.toByteArray();
//
//        try{
//     String uploadDir="stud-pics/"+LocalDate.now().getYear()+"/"+stud.getStudentId();
//        Path
//                uploadPath=Paths.get(uploadDir);
//        if(!Files.exists(uploadPath)){
//        Files.createDirectories(uploadPath);
//        }
//        InputStream inputStream=new ByteArrayInputStream(b);
//
//        Path filePath=uploadPath.resolve(fileName);
//        Files.copy( inputStream, filePath,StandardCopyOption.REPLACE_EXISTING);
//        return "successfully uploaded";
//    }catch(IOException ex){
//    return "error"+ex.toString();
//    }
//
//    }
//
//

    public String uploadPicture(
            MultipartFile multiPartFile,
            Student stud,
            String fileName) throws IOException {

        BufferedImage im =
                ImageIO.read(multiPartFile.getInputStream());

        if (im == null) {
            return "error: uploaded file is not a valid image";
        }

        String extension =
                StringUtils.getFilenameExtension(
                        multiPartFile.getOriginalFilename()
                );

        if (extension == null || extension.isBlank()) {
            extension = "jpg";
        }

        ByteArrayOutputStream outputStream =
                new ByteArrayOutputStream();

        ImageIO.write(
                resizeImage(im, 300, 300),
                extension,
                outputStream
        );

        byte[] bytes = outputStream.toByteArray();

        try {

            String uploadDir =
                    "/var/data/stud-pics/"
                            + LocalDate.now().getYear()
                            + "/"
                            + stud.getStudentId();

            Path uploadPath =
                    Paths.get(uploadDir);

            Files.createDirectories(uploadPath);

            Path filePath =
                    uploadPath.resolve(fileName);

            Files.write(
                    filePath,
                    bytes,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );

            return "successfully uploaded";

        } catch (IOException ex) {

            return "error: " + ex.getMessage();
        }
    }


    public users CreateStudentAccount(Student student){
        users user=new users();
             BCryptPasswordEncoder encode=new BCryptPasswordEncoder();
        
        try{
            
            user.setEnabled(true);
            user.setUsername(student.getStudentId());
      
           user.setPassword(encode.encode(student.getStudentId()+"1234"));
            
            Set<role>roles=new HashSet<>();
            roles.add(getStudentRoleId());
           user.setRoles(roles);
           if(US.checkIfUserNameIsAssigned(user).equals("false")){
            US.addUser(user);}else{
           return null;
           }
        }catch(Exception ex){ex.printStackTrace();
        }
    return user;
    }
    public String ChangeDefaulPassword(String studentId,String Newpassword){
       BCryptPasswordEncoder encode=new BCryptPasswordEncoder();
        
        try{
    users user=US.FindUserById(studentId);
    user.setPassword(encode.encode(Newpassword));
    US.addUser(user);
    return "successfully changed password";
    
    }catch(Exception ex){return " error... "+ex.toString();}
    }
   
    private role getStudentRoleId(){
        if(US.FindUserRoles().isEmpty()){
        role rol=new role();
        rol.setName("Student");
     return rol;
        }else{
       
       if(US.FindUserRoles().get("Student")==null){
        role rol=new role();
        rol.setName("Student");
     return rol;
       }else{
           Integer get = US.FindUserRoles().get("Student");
    return US.findparticularRole(get);
     
       }
    }
    }
    public String saveStudent(Student stud){
       List<Student>studs= studentRepository.findAll();
        studs.removeIf(c->c.getIdSpecia()==null);
         if(studs.isEmpty()){
          stud.setIdSpecia(1L);
         }else{
       stud.setIdSpecia(studs.stream().map(Student::getIdSpecia)
               .sorted().collect
        (Collectors.toList()).get(studs.size()-1)+1);  
         }
           stud.setStudentId("UBS"+LocalDate.now().getYear()+(stud.getIdSpecia()+1));
            stud.setStudentReference(stud.getStudentReference());
        stud= ChangeToUpper(stud);
       stud.setStudentStatus("in School");
    try{
        
    studentRepository.save(stud);
   // stud.setBill(BS.getBillBasedOn_Fee(fee));
    CreateStudentAccount(stud);
    
    return "Student "+stud.getFirstName()+" "+stud.getSurname()+" have been added successfully";
    }catch (Exception ex){
    
    return " fatal error please contact admin "+ex.toString();
    }
    
    }
   public Student saveStudentCategory(Student stud){
      try{
   stud=  studentRepository.save(stud);
   // stud.setBill(BS.getBillBasedOn_Fee(fee));
     
    return stud;
    }catch (Exception ex){
    
    return null;
    }
    
    }
    public Student findStudent(String id){
    return studentRepository.findById(id).get();
    }
    @Autowired TermService ts;
    public double GetStudentTotalFees(Student student){
    double getTotalStudentBills=0;

        for(SchoolFees fee: student.getStudentBills().stream().map(StudentBill::getFees).toList()){
    if(Objects.equals(fee.getTerm().getId(), ts.getCurrentTerm().getId())){
                getTotalStudentBills += fee.getAmount();
            }
        }
    
        return  getTotalStudentBills;
    }
    
    
    public Bill GetStudentBillForATerm(Student stud, Term term){
    
        for (Bill studbill:billService.bills()){
        if(studbill.getTerm().getId().equals(term)){
          if (stud.getParentCategory().equals(studbill.getFee()))
              
        return studbill;
        } }
    return null;
    }
    
    public List<Student>students(){
    
    return studentRepository.findAll().stream().filter(c->c.getStudentStatus().equals("in School")).collect(Collectors.toList());
    }
    
    public List<Student>GetStudents_In_A_Particular_Class(klass klass){
      
        return KS.FindKlass(klass.getClassName()).getStudents().stream()
                .filter(c->!c.getStudentStatus().equals("Stopped")).collect(Collectors.toList());
        
        
      }
    public List<Student>GetStopped_Students_In_A_Particular_Class(klass klass){
      
        return KS.FindKlass(klass.getClassName()).getStudents().stream()
                .filter(c->c.getStudentStatus().equals("Stopped")).collect(Collectors.toList());
        
        
      }
    //saving many students into db. Mostly this deal with data from excel
     public String saveStudentsIntoDb(List<Student>students){
     try{
         students.forEach(stud ->{
             stud.setStudentReference(stud.getStudentId());
         System.out.println(saveStudent(stud));
         
         });
               return "saved successfully";
     
     }catch(Exception ex){
     return "fatal error "+ ex.toString()+students.toString();
     
     }
     }
     public BufferedImage resizeImage(BufferedImage originalFile,int targetWidth,int TartgetHeight){
         Image reultingImage =originalFile.getScaledInstance(targetWidth, TartgetHeight, BufferedImage.SCALE_DEFAULT);
          BufferedImage OutputImage =new BufferedImage(targetWidth, TartgetHeight,BufferedImage.TYPE_INT_RGB);
        OutputImage.getGraphics().drawImage(reultingImage, 0, 0, null);
        return OutputImage;
     }
   public Optional<Student>FindstudentById(Student stud){
   
  return studentRepository.findById(stud.getStudentId());
   }
public String promoteStudent_withoutsaving(Student student){
    klass klas=null;
  if(student.getStudentStatus().equals("Stopped")){
  
  }else{
        klas=student.getStudentklass();
        
        if(klas.getClassName().equals("LowerCreche")){
          
          student.setStudentklass(KS.FindKlass("UpperCreche"));
          
        }
         if(klas.getClassName().equals("UpperCreche"+klas.getClassClassification())){
      
          student.setStudentklass(KS.FindKlass("Nursery1"+klas.getClassClassification()));
       
         }
         
        if(klas.getClassName().equals("Nursery1"+klas.getClassClassification())){
        
          student.setStudentklass(KS.FindKlass("Nursery2"+klas.getClassClassification()));
         
        }
        
        if(klas.getClassName().equals("Nursery2"+klas.getClassClassification())){
      student.setStudentklass(KS.FindKlass("KG1"+klas.getClassClassification()));
        
         
        }
        
        if(klas.getClassName().equals("KG1"+klas.getClassClassification())){
      student.setStudentklass(KS.FindKlass("KG2"+klas.getClassClassification()));
     
          
        }
         if(klas.getClassName().equals("Basic5"+klas.getClassClassification())){
      student.setStudentklass(KS.FindKlass("Basic6"+klas.getClassClassification()));
        
         
         }
          if(klas.getClassName().equals("KG2"+klas.getClassClassification())){
            student.setStudentklass(KS.FindKlass("Basic1"+klas.getClassClassification()));
     
          
          }
        
       if(klas.getClassName().equals("Basic1"+klas.getClassClassification())){
      student.setStudentklass(KS.FindKlass("Basic2"+klas.getClassClassification()));
     
       }
         if(klas.getClassName().equals("Basic2"+klas.getClassClassification())){
      student.setStudentklass(KS.FindKlass("Basic3"+klas.getClassClassification()));
       
         }
     if(klas.getClassName().equals("Basic3"+klas.getClassClassification())){
    student.setStudentklass(KS.FindKlass("Basic4"+klas.getClassClassification()));
        
     }
     
       if(klas.getClassName().equals("Basic4"+klas.getClassClassification())){
       student.setStudentklass(KS.FindKlass("Basic5"+klas.getClassClassification()));
   
       }
       
       if(klas.getClassName().equals("Basic5"+klas.getClassClassification())){
       student.setStudentklass(KS.FindKlass("Basic6"+klas.getClassClassification()));
     
       }
        if(klas.getClassName().equals("Basic6"+klas.getClassClassification())){
      student.setStudentklass(KS.FindKlass("JHS1"+klas.getClassClassification()));
      
        }
        if(klas.getClassName().equals("JHS1"+klas.getClassClassification())){
       student.setStudentklass(KS.FindKlass("JHS2"+klas.getClassClassification()));
     
           
      }
        if(klas.getClassName().equals("JHS2"+klas.getClassClassification())){
     student.setStudentklass(KS.FindKlass("JHS3"+klas.getClassClassification()));
     
            
      }
         if(klas.getClassName().equals("JHS3"+klas.getClassClassification())){
     student.setStudentklass(KS.FindKlass("JHS4"+klas.getClassClassification()));
     }
              try{
      return student.getStudentklass().getClassName();
          }catch(Exception ex){
          return "fatal error;"+ex.toString();
          }
      }
        return "null";
    
  }
  
public void changeStudentId(){
 List <Student>students = new ArrayList<>();
    students().stream().forEach(c->{
   char [] ids = c.getStudentId().toCharArray();
   String id="UBS";
   String check =ids[0]+ids[1]+ids[2]+ids[3]+"";
  System.out.println(check+ " <---ubsddddddd--->");
  
   if(check.equals("UBSR")){
       
      for (int a =4; a<=ids.length-1; a++){
       
     id=id+ids[a];
  
   }
   }else{
   for (int a =3; a<=ids.length-1; a++){
       
   id=id+ids[a];
  
   }
   }
   System.out.println(id);
    c.setStudentReference(id);
   students.add(c);
   });
 System.out.println( studentRepository.saveAll(students));
}
public void changeStudentId_(){
     List <Student>students = new ArrayList<>();
 students().stream().forEach(c->{
  String check = c.getStudentId().substring(0, 3);
  
  // String check =c.getStudentId().charAt(0)+c.getStudentId().charAt(1)+c.getStudentId().charAt(2)+"";
  // System.out.println(check+"______"+c.getStudentId());
  
   if(check.equals("UBS")){
 System.out.println(check+ " list"+c.getStudentId()+"_referece"+c.getStudentReference());       
     
   
    c.setStudentReference(c.getStudentId());
    
   students.add(c);
studentRepository.save(c);


}
 
 });
 //System.out.println( SR.saveAll(students));
}
  public Optional<Student>getStudentByReference(String reference){
  
  return studentRepository.getStudentByStudentReference(reference);
  } 
  
  public String converDetailsToString (Student student){
   return student.getFirstName()+student.getSurname()+student.getLastName()+student.getStudentId();
   }

   public Page<Student> getAllStudentsPaged(Pageable pageable) {
       return studentRepository.findAll(pageable);
   }

   public Page<Student> getStudentsByClassPaged(klass k, Pageable pageable) {
       return studentRepository.findByStudentStatusAndStudentklassClassName("in School",k.getClassName(), pageable);
   }

   public Page<Student> getStoppedStudentsByClassPaged(klass k, Pageable pageable) {
       return studentRepository.findByStudentStatusAndStudentklassClassName("Stopped", k.getClassName(), pageable);
   }

   public Page<Student> searchStudentsPaged(String search, Pageable pageable) {
       return studentRepository.findByStudentIdOrFirstNameOrLastNameOrsurname(search, pageable);
   }
  
}
