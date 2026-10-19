
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.PDF;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment.Assessment;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment.AssessmentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Attendance.Attendance;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Attendance.AttendanceService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.BillService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.Bill;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Excel.ExcelServices;
import com.UBSDB.UnerBasicSchoolFinancialSystem.FeePayment.Payment;
import com.UBSDB.UnerBasicSchoolFinancialSystem.FeePayment.PaymentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.HeadTeacher.headTeacherService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Position.AssessmentPosition;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Position.AssessmentPositionService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.SchoolFees.SchoolFees;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.SubjectService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.TKS.TeachrKlassSubjectService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.UserService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.users;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author enock
 */
@Service
public class MultipleAssessmentPDFService {
    DecimalFormat format = new DecimalFormat("##.#");
    @Autowired
            private AssessmentService AS;
    
    @Autowired
    private AttendanceService AtS;
     @Autowired
    private ExcelServices ES;
    private @Autowired BillService BS;
    private @Autowired PaymentService PS;
    private Student student ;
    private @Autowired TermService TS;
    private @Autowired StudentService SS;
  private  List<Student>studs;
private @Autowired TeachrKlassSubjectService TKS;
    @Autowired
    private AssessmentPositionService APS;
   
    @Autowired private headTeacherService Hts;
    @Autowired private UserService US;
@Autowired private SubjectService subS;
    

    public List<Student> getStuds() {
        return studs;
    }

    public void setStuds(List<Student> studs) {
        this.studs = studs;
    }
    
   public Term term;

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

 
  
    private void writeTableDataAssessment(PdfPTable pdfp,Student student){
        
       Runnable r=()->{ 
        AS.getAssessmentBasedOnTerm(term, student).get(term).stream().map((Assessment as) -> {
            pdfp.addCell(as.getSubject().getSubjectName());
            return as;
        }).map(as -> {
             pdfp.addCell(format.format(as.getTotalTestScoreAverage()));
         return as;
        }).map(as -> {
            pdfp.addCell(format.format((50*as.getExamScores()/100)));
            return as;
        }).map(as -> {
           
            pdfp.addCell(format.format((as.getTotalScore())));
            return as;
        }).map(as -> {
             if(as.getStudent().getStudentklass().getClassLevel().equals("JuniorHigh")){
            pdfp.addCell(ES.addRemarksJHS(as.getTotalScore()));
            }else{
           pdfp.addCell(ES.setGrade(as.getTotalScore()));
             }
            
            return as;
        }).map(as -> {
             
           pdfp.addCell(as.getPosition());
             
            
            return as;
        }).map(as -> {
            pdfp.addCell(ES.addRemarks(as.getTotalScore()));
            return as;
        }).forEachOrdered(as -> {
            
            pdfp.addCell(as.getHighestScore()+"");
            
            
        });
        
}; r.run();
    }
    private void writeTableStudentInfo(PdfPTable Table,Student student,Term t) throws BadElementException, IOException{
        if(student==null){}else{
        Runnable r=()->{ 
          Student  stud=SS.FindstudentById(student).get();
        PdfPCell cell=new PdfPCell(); PdfPCell cell2=new PdfPCell();
         Font font=FontFactory.getFont("Gil Sans MT", 11, Color.BLACK);
         cell.setPadding(-1);cell.setBorderWidthTop(10);
         cell.setBorderColor(Color.WHITE);Path imagepath=Paths.get("stud-pics");
           
         AssessmentPosition AP=new AssessmentPosition();
           DecimalFormat decimalF= new DecimalFormat("##.#");
           if(student.getStudentklass().getClassLevel().equalsIgnoreCase("Kindagarten")){
           AP.setPosition("");
           }else{
          AP=  APS.getStudentPostitionInClass(student,getStuds(),t);  
           }
          int TotalSubject = AS.getAssessmentBasedOnTerm(t, student).get(t).size();
          
         Image img = null;
             try {
                 img = Image.getInstance(imagepath+"/"+student.getProductPicturePathPDF());
             } catch (BadElementException ex) {
                 Logger.getLogger(MultipleAssessmentPDFService.class.getName()).log(Level.SEVERE, null, ex);
             } catch (IOException ex) {
                 Logger.getLogger(MultipleAssessmentPDFService.class.getName()).log(Level.SEVERE, null, ex);
             }
         int StudentNumber=getStuds().size();
        String promotionMessage="";String gradeMessage="";
        if( stud.getStudentklass().getClassLevel().equals("JuniorHigh")){
        gradeMessage="GRADE:"+ES.aggregate(
                 stud.getAssessment()
                        .stream().filter(C->
                                C.getTerm().getId()==
                                        t.getId()).collect(Collectors.toList()))+"\n\n";
        
        }        
         
        if(t.getTermName().equals("ThirdTerm")){
            Student stud1=student;
            stud1.setStudentklass
        (AS.getStudentClassForATermAssessment(t, student).getStudentClass());
             float text =(TotalSubject*100)/2;
             System.out.println(text+" / ");
           //  TeachrKlassSubject promotedClassTeacher =TKS.getClassMaster(nextClass);
         if(AP.getTotalScore()>=text){
             String nextClass=SS.promoteStudent_withoutsaving(stud1);
         promotionMessage="PROMOTED TO: "+
                nextClass//+" \n(CLASS TEACHER: "
                //+promotedClassTeacher.getTeacher().getFirstName()+" "+promotedClassTeacher.getTeacher().getLastName()+")"
                ;
         }else{
          promotionMessage="REPEATED AT: "+
                stud1.getStudentklass().getClassName()//+" \n(CLASS TEACHER: "
                //+promotedClassTeacher.getTeacher().getFirstName()+" "+promotedClassTeacher.getTeacher().getLastName()+")"
                ;
         }
             
        }
    cell .setPhrase(new Phrase("NAME: "+student.getSurname()+" "+student.getFirstName()+" "+student.getLastName()
   +"\n \n CLASS: "+AS.getStudentClassForATermAssessment(t, student).getStudentClass().getClassName()
                   +"\n\n" +"\t\tNUMBER ON ROLL:  "+StudentNumber+
            "\n\nPOSITION: "+AP.getPosition()
            +"\n\nSCHOOL REOPENS: "+TS.findNextTermBasedOnTerm(t).get().getStartDate() ,font));Table.addCell(cell);
         cell .setPhrase(new Phrase("\t \tTERM:  "+t.getTermName()+" \n \n ACADEMIC YEAR: "
                 + ""+String.valueOf(t.getAcademicyear().getAcademicName()+"\n \nTOTAL SCORE: "+decimalF.format(AP.getTotalScore())+"\n\n "+gradeMessage+"\n\n"+promotionMessage )
       
                          
                ,font));Table.addCell(cell); 
                img.scalePercent(45,45);
                cell.addElement(img);
        
                Table.addCell(cell);
                
     };      r.run();
    }
    }
     private void writeTableHeadRemarks(PdfPTable Table) throws BadElementException, IOException{
         PdfPCell cell=new PdfPCell(); PdfPCell cell2=new PdfPCell();
         Font font=FontFactory.getFont("Candara", 11, Color.BLACK);
         cell.setPadding(-1);cell.setBorderWidthTop(10);
        
        
        
    cell .setPhrase(new Phrase("HeadTeacher's Signature: "+"-----------------------",font));
                    Table.addCell(cell);  
    
    }
    private void writeTableTopicAssessment(PdfPTable Table){
    PdfPCell cell=new PdfPCell(); 
        Font font=FontFactory.getFont("Gil Sans MT", 13, Color.BLACK);
        cell.setPadding(5); cell.setBorderColor(Color.WHITE);
        
        Table.addCell(cell);
    
    }
    private void writeTableTopicBill(PdfPTable Table){
   PdfPCell cell=new PdfPCell(); 
        Font font=FontFactory.getFont("Gil Sans MT", 12, Color.WHITE);
        cell.setPadding(5); cell.setBorderColor(Color.WHITE);
        cell.setPhrase(new Phrase("Student Bill",font));
        Table.addCell(cell);
    }
     private void writeHeadMasterTeacherSignature(PdfPTable Table)
             throws BadElementException, IOException{
   PdfPCell cell=new PdfPCell(); 
        Font font=FontFactory.getFont("Gil Sans MT", 11, Color.BLACK);
        Font font1=FontFactory.getFont("Gil Sans MT", 12, Color.BLACK);
        cell.setPadding(5); cell.setBorderColor(Color.WHITE);
         cell.setPadding(-1);cell.setBorderWidthTop(10);
       //  Path imagepath=Paths.get("stud-pics");
          Path imagepath=Paths.get("./pro-pics");
    
         Image img=Image.getInstance(imagepath+"/headteacherSign.png");
        
        img.scalePercent(50, 40);
        cell .setPhrase(new Phrase("CLASS TEACHER'S SIGNATURE: ________________________________________\n\n",font));
                  Table.addCell(cell);   
   // cell .setPhrase(new Phrase("\n\nHeadTeacher's Signature: \n",font));Table.addCell(cell); 
          cell.addElement(img);         Table.addCell(cell); 
          if(Hts.getHeadTecherFromList(US.FindAllUser())!=null){
              users u=Hts.getHeadTecherFromList(US.FindAllUser());
              String ht=u.getLastname()+" " +u.getFirstname()+" (Headteacher)";
              cell .setPhrase(new Phrase(ht,font1));
          
          }else{
             cell .setPhrase(new Phrase("MRS. IRENE ASARE YEBOAH (HEADMISTRESS)",font));
          
          }
    
    
    Table.addCell(cell);
  //  cell .setPhrase(new Phrase("\n\n\n\npowered by: enocklyn computers contact:0241232915/ENOCKLYN@YAHOO.COM",font1));
  
    //Table.addCell(cell); 
     }
     
    
        private void writeStudentFinanceBill(PdfPTable Table,Student stud,Term t){
       // double    amount=0.0;
      long id=0;
       try{
    //    stud=SS.findStudent(stud.getStudentId());
      //     Optional<StudentBill>Current_stud_bill=
        //      stud .getBill()
          //              .stream().filter(c->c.getTerm().getId()
            //                    ==TS.findNextTermBasedOnTerm(t).get().getId()).findFirst();
        
           double areas =(stud.getStudentBills().stream().filter(c-> LocalDate.parse(c.getTerm().getEndDate())
                   .isBefore(LocalDate.parse(t.getEndDate())))
                   .flatMap(bill -> bill.getBill().getFee().stream()).map(SchoolFees::getAmount)
                   .reduce(0.0, (b,f)-> b+f)-stud.getPayments().stream().map(Payment::getDiscount).reduce(0.f, (f,s)->f+s))-(stud.getPayments().stream()
                           .filter(c-> c.getDate()
                   .isBefore(LocalDate.parse(t
                           .getEndDate()))).map(Payment::getAmount).reduce(0.0, (b,f)-> b+f));
    //    Table.addCell(areas+"",cell);
     //-(BS.BillsBasedOnTerm(TS.getCurrentTerm(), student).getFee().getAmount())+"");
   //  if(Current_stud_bill.isPresent()){ 
    // Table.addCell(Current_stud_bill.get().getFee().getAmount()+"");
    // Table.addCell( (areas+Current_stud_bill.get().getFee().getAmount())+"");
    // }else{
     // Table.addCell(0.0+""); 
          //   Table.addCell(areas+"");
     //}
       }catch(Exception ex){
       
       
       }
     
    }
 
     private void writeTableHeader(PdfPTable Table){
         PdfPCell cell=new PdfPCell();
         Font font=FontFactory.getFont("Gil Sans MT", 10, Color.white);
         cell.setBackgroundColor(Color.blue);
         cell.setPadding(4);
         cell.setPhrase(new Phrase("SUBJECT",font));Table.addCell(cell);
          cell.setPhrase(new Phrase("CLASS SCORE",font));Table.addCell(cell);
           cell.setPhrase(new Phrase("EXAM SCORE",font));Table.addCell(cell);
     
           cell.setPhrase(new Phrase("TOTAL%",font));Table.addCell(cell);
           cell.setPhrase(new Phrase("GRADE",font));Table.addCell(cell);
         
          cell.setPhrase(new Phrase("POSITION IN SUBJECT",font));Table.addCell(cell);
        
          cell.setPhrase(new Phrase("REMARKS",font));Table.addCell(cell);
          cell.setPhrase(new Phrase("HIGHEST SCORE IN SUBJECT ",font));Table.addCell(cell);
            
//Attendance	37	Out Of	67	Conduct	Perfect	Attitude	Normal	Interest	Reading    
         
    }
     
     
     
     private void writeTableHeaderStudentBill(PdfPTable Table){
           PdfPCell cell=new PdfPCell();
         Font font=FontFactory.getFont("Gil Sans MT", 12, Color.white);
         cell.setBorderColor(Color.WHITE);
         cell.setBackgroundColor(Color.white);
         cell.setPadding(4);
         cell.setPhrase(new Phrase("Arreas",font));Table.addCell(cell);
          //  cell.setPhrase(new Phrase("Current Term Bill (GHC)",font));Table.addCell(cell);
         
      
          // cell.setPhrase(new Phrase("Payment (GHC)",font));Table.addCell(cell);
                cell.setPhrase(new Phrase("Total",font));Table.addCell(cell);
     //    cell.setPhrase(new Phrase("Term Payment",font));Table.addCell(cell);
     //Attendance	37	Out Of	67	Conduct	Perfect	Attitude	Normal	Interest	Reading    
         
    }
    private void writeStudentAttendance(PdfPTable Table,Student stud){
        Attendance Att=AtS.getStudentAttendaceTermly( stud, getTerm());
      //System.out.println(Att.getStudentAttendedDays()+"///---Conduct"+Att.getInterest());
        PdfPCell cell=new PdfPCell(); 
        Font font=FontFactory.getFont("Gil Sans MT", 12, Color.BLACK);
        cell.setPadding(5); cell.setBorderColor(Color.WHITE);
        cell.setPhrase(new Phrase("ATTENDANCE: "+Att.getStudentAttendedDays()
                +"     OUT OF: "+Att.getTotalNumberOfDays() +   "\n\nCONDUCT: "+Att.getConduct()+"\n\nINTEREST: "+Att.getInterest(),font));
        Table.addCell(cell);
    }
    public Document GetPDFDocument(Student stud, Term t) throws BadElementException, IOException{
     Path imagepath=Paths.get("./pro-pics");
     
        Document document=new Document(PageSize.A4);
       document.open();
         Image img=Image.getInstance(imagepath+"/Assessment.jpg");
      img.scalePercent(100,70);
         PdfPTable StudentInfoTable=new PdfPTable(3);
          PdfPTable AssessmentTopic=new PdfPTable(1);
          PdfPTable BillTopic=new PdfPTable(1);
         writeTableStudentInfo(StudentInfoTable,stud,t);
         PdfPTable AssessmentTable=new PdfPTable(8);
         PdfPTable BillTable=new PdfPTable(4);
         BillTable.setWidthPercentage(100);
          AssessmentTopic.setWidthPercentage(100);
           BillTopic.setWidthPercentage(100);
        PdfPTable Attendance =new PdfPTable(1);
         Attendance.setWidthPercentage(100);
       Attendance.setSpacingBefore(15);
         AssessmentTable.setWidthPercentage(100);
         AssessmentTable.setSpacingBefore(15);
         writeTableHeader(AssessmentTable);
        writeTableDataAssessment(AssessmentTable,stud);
        writeStudentAttendance(Attendance,stud);
      writeTableTopicAssessment(AssessmentTopic);
     // writeTableTopicBill(BillTopic);
       // writeTableHeaderStudentBill(BillTable);
       //writeStudentFinanceBill(BillTable,stud,t);
        document.add(img);
        document.add(StudentInfoTable);
        document.add(AssessmentTopic);
         document.add(AssessmentTable);
         // document.add(BillTopic);
          //document.add(BillTable);
         document.add(Attendance);
         
     return document;    
    
    }
    
   public Image GetGradeBoundary() throws BadElementException, IOException{
       Path imagepath=Paths.get("./pro-pics");
       Image img=Image.getInstance(imagepath+"/gradeExplanation.png");
 
   img.scalePercent(50,60);
   return img;
   }
     public void export(HttpServletResponse response,Term t )throws IOException
     {  
          Document document=new Document(PageSize.A4);
          document.setPageCount(1);
          PdfWriter.getInstance(document, response.getOutputStream());
       document.open();
      
       getStuds().forEach(c->{
           Path imagepath=Paths.get("./pro-pics");
     
         Image img = null;
              try {
                  img = Image.getInstance(imagepath+"/Assessment.jpg");
              } catch (BadElementException ex) {
                  Logger.getLogger(MultipleAssessmentPDFService.class.getName()).log(Level.SEVERE, null, ex);
              } catch (IOException ex) {
                  Logger.getLogger(MultipleAssessmentPDFService.class.getName()).log(Level.SEVERE, null, ex);
              }
         // img.scalePercent(13,14);
       img.scalePercent(60,60);
        img.setSpacingAfter(50);
        img.setSpacingBefore(-10);
         PdfPTable StudentInfoTable=new PdfPTable(3);
       
              try {
                  writeTableStudentInfo(StudentInfoTable,c,t);
              } catch (BadElementException ex) {
                  Logger.getLogger(MultipleAssessmentPDFService.class.getName()).log(Level.SEVERE, null, ex);
              } catch (IOException ex) {
                  Logger.getLogger(MultipleAssessmentPDFService.class.getName()).log(Level.SEVERE, null, ex);
              }
         PdfPTable AssessmentTable=new PdfPTable(8);
         PdfPTable BillTable=new PdfPTable(2); 
         PdfPTable BillTableData=new PdfPTable(4); 
         
         PdfPTable AssessmentTopic=new PdfPTable(1);
          PdfPTable BillTopic=new PdfPTable(1);
         
         BillTable.setWidthPercentage(100);
          StudentInfoTable.setWidthPercentage(100);
        PdfPTable Attendance =new PdfPTable(1);
        PdfPTable Head_TeacherSignature =new PdfPTable(1);
        Head_TeacherSignature.setWidthPercentage(100);
        
        Attendance.setWidthPercentage(100);
       Attendance.setSpacingBefore(15);
       Attendance.setSpacingAfter(5);
         AssessmentTable.setWidthPercentage(100);
         AssessmentTable.setSpacingAfter(5);
         //AssessmentTable.setSpacingBefore(15);
         writeTableHeader(AssessmentTable);
          writeTableTopicAssessment(AssessmentTopic);
      writeTableTopicBill(BillTopic);
        writeTableDataAssessment(AssessmentTable,c);
        writeStudentAttendance(Attendance,c);
         writeTableHeaderStudentBill(BillTable);
       writeStudentFinanceBill(BillTable,c,t);
      StudentInfoTable.setSpacingBefore(5);
        document.add(img);
       AssessmentTopic.setSpacingAfter(10);
              try { 
                  writeHeadMasterTeacherSignature(Head_TeacherSignature);
              } catch (BadElementException ex) {
                  Logger.getLogger(MultipleAssessmentPDFService.class.getName()).log(Level.SEVERE, null, ex);
              } catch (IOException ex) {
                  Logger.getLogger(MultipleAssessmentPDFService.class.getName()).log(Level.SEVERE, null, ex);
              }
              
       Head_TeacherSignature.setSpacingAfter(70);
        Head_TeacherSignature.setSpacingBefore(10);
       document.add(StudentInfoTable);
         document.add(AssessmentTopic);
         document.add(AssessmentTable);
              try {
                  document.add(GetGradeBoundary());
              } catch (BadElementException ex) {
                  Logger.getLogger(MultipleAssessmentPDFService.class.getName()).log(Level.SEVERE, null, ex);
              } catch (IOException ex) {
                  Logger.getLogger(MultipleAssessmentPDFService.class.getName()).log(Level.SEVERE, null, ex);
              }
         
         document.top(-5.3f);
         document.add(Attendance);
          document.add(Head_TeacherSignature);
          document.add(BillTopic);
         BillTable.setSpacingAfter(700);
         document.add(BillTable);
         
        //Head_TeacherSignature.setSpacingAfter();
       
        
         
           
        
        
     }); document.close(); }; 
}
