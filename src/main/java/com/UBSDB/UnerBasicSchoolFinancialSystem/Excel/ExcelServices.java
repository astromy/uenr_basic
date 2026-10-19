package com.UBSDB.UnerBasicSchoolFinancialSystem.Excel;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment.Assessment;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment.AssessmentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Attendance.Attendance;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Course.Course;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Course.CourseService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.FeePayment.Payment;
import com.UBSDB.UnerBasicSchoolFinancialSystem.FeePayment.PaymentResponse;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.Subject;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.SubjectService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.TKS.TeachrKlassSubjectService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Teacher.Teacher;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Teacher.TeacherService1;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermWeek.TermWeek;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermWeek.TermWeekService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.cacheAssessment.CacheAssessment;
import com.UBSDB.UnerBasicSchoolFinancialSystem.cacheAssessment.CacheAssessmentService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.KlassRequest;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klassService;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.CellStyle;
import static org.apache.poi.ss.usermodel.CellType.BLANK;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import org.apache.poi.xssf.usermodel.XSSFFont;

/**
 *
 * @author enock
 */
@Service
public class ExcelServices {
    //public   

    @Autowired
    private klassService kS;
    @Autowired
    private StudentService SS;
    @Autowired
    private SubjectService Ss;
    @Autowired
    private AssessmentService AS;
    @Autowired
    private CacheAssessmentService CAS;
    private @Autowired
    TeachrKlassSubjectService TKSs;
    private @Autowired
    TeacherService1 TS;
    @Autowired
    private CourseService courseService;
    @Autowired
    private TermWeekService termWeekService;

    public XSSFWorkbook getCellData(MultipartFile file) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook((file.getInputStream()));
        return workbook;
    }

    public List<String> ClassNames(MultipartFile getPath) throws IOException {
        List<String> names = new ArrayList<>();
        for (int a = 0; a <= getCellData(getPath).getNumberOfSheets() - 1; a++) {

            names.add(getCellData(getPath).getSheetName(a));
        }
        return names;
    }

    public String ClassName(MultipartFile getPath) throws IOException {
        System.out.println(getCellData(getPath).getSheetName(0));
        return getCellData(getPath).getSheetName(0);

    }

    public List<ExceModell> getStudetBills(MultipartFile path, String ClassName) throws IOException {
        XSSFSheet sheet = getCellData(path).getSheet(ClassName);
        List<ExceModell> studentBils = new ArrayList<>();
        DataFormatter df = new DataFormatter();

        for (Row row : sheet) {
            ExceModell SBE = new ExceModell();
            SBE.setNames(df.formatCellValue(row.getCell(1)));
            SBE.setOutstandingBalace(ReturnCellValueFromFomula(row.getCell(3)));
            SBE.setStudentId((df.formatCellValue(row.getCell(0))));
            studentBils.add(SBE);
        }
        return studentBils;
    }

    public List<ExceModell> getStudetBills(MultipartFile path) throws IOException {
        // List<XSSFSheet> sheet =getCellData(path).get;
        int TotalNumberOfSheets = getCellData(path).getNumberOfSheets() - 1;
        List<ExceModell> studentBils = new ArrayList<>();
        DataFormatter df = new DataFormatter();
        while (TotalNumberOfSheets >= 0) {
            for (Row row : getCellData(path).getSheetAt(TotalNumberOfSheets)) {
                ExceModell SBE = new ExceModell();
                //SBE.setNames(df.formatCellValue(row.getCell()));
                SBE.setOutstandingBalace(ReturnCellValueFromFomula(row.getCell(2)));
                SBE.setStudentId((df.formatCellValue(row.getCell(0))));
                SBE.setParentCategory(df.formatCellValue(row.getCell(1)));
                studentBils.add(SBE);
            }TotalNumberOfSheets--;
        }
        return studentBils;
    }

    public double ReturnCellValueFromFomula(Cell cell) {
        double value = 0.0;

        System.out.println(cell.getCellType() + " cellType");
        switch (cell.getCellType()) {
            case FORMULA:
                value = cell.getNumericCellValue();
                return value;
            case STRING:
                value = Double.parseDouble(cell.getStringCellValue());
                System.out.println(value + " cellType is STRING ");
                return value;
            case ERROR:
                value = 0.0;
           
                System.out.println(value + " cellType is error ");
                return value;
            case BLANK:
                value = 0.0;
                return value;
                 case _NONE:
                value = 0.0;
                return value;
            case NUMERIC:
                System.out.println(value + " cellType is numeric ");
                value = cell.getNumericCellValue();
                return value;
            
            default:
                value = 0.0;
                System.out.println(value + " DEFAULT ");
                return value;
        }

    }
 public String ReturnStringCellValueFromFomula(Cell cell) {
     String output="";
      switch (cell.getCellType()) {
               case STRING:
                output = cell.getStringCellValue();
                 System.out.println(output+" converting To sTRING");
              //  System.out.println(value + " cellType is STRING ");
                return output;
               case _NONE:
                   return output;
               case ERROR:
                   return output;
               
              case BLANK:
                  return output;
              case NUMERIC:
                 output =cell.getNumericCellValue()+"";
                 
                  System.out.println(output);
            
              default:
                  return "";
      } 
 }
 
    public List<Student> GetStudentsFromExcelToDB(MultipartFile path) throws IOException {
        XSSFSheet sheet = getCellData(path).getSheetAt(0);
        List<Student> students = new ArrayList<>();
        DataFormatter df = new DataFormatter();

        for (Row row : sheet) {
            Student stud = new Student();
            stud.setFirstName(df.formatCellValue(row.getCell(0)));
            stud.setLastName("");
            stud.setParentNumber(df.formatCellValue(row.getCell(2)));
            // stud.setStudentId(df.formatCellValue(row.getCell(0)));
            stud.setStudentklass(kS.FindKlass(sheet.getSheetName()));
            stud.setGuadiaName("");
            stud.setNHISCardumber("");
            stud.setStaffId("");
            stud.setSex(df.formatCellValue(row.getCell(3)));
            stud.setPhysicalDisability("");
            stud.setOccupation("");
            stud.setDateOfBirth(df.formatCellValue(row.getCell(1)));
            stud.setParentCategory("");
            stud.setResidentialAddress("");
            stud.setSurname("");
            stud.setProductPicturePath("/stud-pics/2022/205/default.jpg");
            stud.setProductPicturePathPDF("/stud-pics/2022/205/default.jpg");
            stud.setStudentPicture("default.jpg");
            //    System.out.println(kS.FindKlass(sheet.getSheetName()).getClassName());
            students.add(stud);
        }
        return students;
    }
      public List<Payment> 
                GetStudentsPaymentsFromExcelToDB
        (MultipartFile path) throws IOException {
        XSSFSheet sheet = getCellData(path).getSheetAt(0);
        List<Payment> payments = new ArrayList<>();
        DataFormatter df = new DataFormatter();

        for (Row row : sheet) {
            if (!ReturnStringCellValueFromFomula(row.getCell(0)).equals("")){
                Payment pay= new Payment();
            PaymentResponse payment = new PaymentResponse();
              payment.setStudentId(ReturnStringCellValueFromFomula(row.getCell(0)));   //    System.out.println(kS.FindKlass(sheet.getSheetName()).getClassName());
            pay.setDiscount(0.f);
            //    System.out.println(kS.FindKlass(sheet.getSheetName()).getClassName());
            payment.setAmount(ReturnCellValueFromFomula(row.getCell(2))+"");   //    System.out.println(kS.FindKlass(sheet.getSheetName()).getClassName());
            payment.setTransactionId( ReturnStringCellValueFromFomula(row.getCell(1))); 
            //    System.out.println(kS.FindKlass(sheet.getSheetName()).getClassName());
         payment.setPaymentDateTime( row.getCell(3).getDateCellValue().toString());
         System.out.print("Checking the real DATE: "+ payment.getPaymentDateTime());
            payment.setBankName("Prudential Bank");
            System.out.println("\n\n<-amount->"+payment.getAmount()+" /Date->"+ payment.getPaymentDateTime()+payment.getTransactionId());
             pay.setStudent(new Student (payment.getStudentId()));
            pay.setAmount(Double.parseDouble(payment.getAmount()));
      pay.setBank(payment.getBankName());
  pay.setTransactionSpecialId(payment.getTransactionId());
     pay.setPaymentTime(payment.getPaymentDateTime());
            
            payments.add(pay);
        }
        }
        return payments;
    }

    //Saving assessment into db using excel file
        //Date	Transaction Detail	Student Ref	Reference No	Value Date	Debit	Credit

    public List<CacheAssessment> GetStudentAssessmentFromExcelSheet(Teacher t, MultipartFile path, Long SubjectId, Term term) throws IOException {

        XSSFSheet sheet = getCellData(path).getSheetAt(0);
        List<CacheAssessment> Assesment = new ArrayList<>();
        DataFormatter df = new DataFormatter();
        int a = 0;

        for (int rowCount = sheet.getFirstRowNum() + 1; rowCount <= sheet.getLastRowNum(); rowCount++) {
            if (sheet.getRow(rowCount).getCell(2).getRawValue() != null || sheet.getRow(rowCount).getCell(3).getRawValue() != null
                    || sheet.getRow(rowCount).getCell(4).getRawValue() != null || sheet.getRow(rowCount).getCell(5).getRawValue() != null
                    || sheet.getRow(rowCount).getCell(2).getRawValue() != null) {
                Student student = SS.findStudent(sheet.getRow(rowCount).getCell(0).getStringCellValue());
                CacheAssessment as = new CacheAssessment(0, 0, Ss.findAParticularSubject(SubjectId), 0, 0, 0);
                as.setStudent(student);
                as.setSubject(Ss.findAParticularSubject(SubjectId));
                Assesment.add(as);
                a++;

            } else {
                Student student = SS.findStudent(sheet.getRow(rowCount).getCell(0).getStringCellValue());
                CacheAssessment assess = new CacheAssessment();
                assess.setStudent(student);
                assess.setTerm(term);
                assess.setSubject(Ss.findAParticularSubject(SubjectId));
                assess.setTotalTestScore(ReturnCellValueFromFomula(sheet.getRow(rowCount).getCell(2))
                        + ReturnCellValueFromFomula(sheet.getRow(rowCount).getCell(3))
                        + ReturnCellValueFromFomula(sheet.getRow(rowCount).getCell(4))
                        + ReturnCellValueFromFomula(sheet.getRow(rowCount).getCell(5)) / 60 * 30);

                // assess.setTestScoreAverage((assess.getTotalTestScore()/60)*50);
                assess.setExamScores(ReturnCellValueFromFomula(sheet.getRow(rowCount).getCell(6)));
                assess.setExamsAverage((50 * assess.getExamScores()) / 100);
                assess.setTotalTestScoreAverage(assess.getTotalTestScore());
                assess.setTotalScore(assess.getExamsAverage() + assess.getTotalTestScoreAverage());
                assess.setTeacher(t);
                Assesment.add(assess);
                a++;
            }
        }
        return SortedAssessment(t, Assesment);
    }

    public List<CacheAssessment> GetStudentMockFromExcelSheet(Teacher t, MultipartFile path, Long SubjectId,
             Term term) throws IOException {

        XSSFSheet sheet = getCellData(path).getSheetAt(0);
        List<CacheAssessment> Assesment = new ArrayList<>();
        DataFormatter df = new DataFormatter();
        int a = 0;

        for (int rowCount = sheet.getFirstRowNum() + 1; rowCount <= sheet.getLastRowNum(); rowCount++) {
            if (sheet.getRow(rowCount).getCell(2).getRawValue() != null
                    || sheet.getRow(rowCount).getCell(3).getRawValue() != null) {
                Student student = SS.findStudent(sheet.getRow(rowCount).getCell(0).getStringCellValue());
                CacheAssessment as = new CacheAssessment(0, 0, Ss.findAParticularSubject(SubjectId), 0, 0, 0);
                as.setStudent(student);
                as.setSubject(Ss.findAParticularSubject(SubjectId));
                Assesment.add(as);
                a++;

            } else {
                Student student = SS.findStudent((String) ReturnStringCellValueFromFomula(sheet.getRow(rowCount).getCell(0)));
                CacheAssessment assess = new CacheAssessment();
                assess.setStudent(student);
                assess.setTerm(term);
                assess.setSubject(Ss.findAParticularSubject(SubjectId));
                //assess.setTotalTestScore(
                //   ((sheet.getRow(rowCount).getCell(2).getNumericCellValue()+
                //     sheet.getRow(rowCount).getCell(3).getNumericCellValue()+
                //     sheet.getRow(rowCount).getCell(4).getNumericCellValue()+
                //     sheet.getRow(rowCount).getCell(5).getNumericCellValue())/60)*50);
                // assess.setTestScoreAverage((assess.getTotalTestScore()/60)*50);
                assess.setExamScores(ReturnCellValueFromFomula(sheet.getRow(rowCount).getCell(2)));
                // assess.setExamsAverage((50*assess.getExamScores())/100);
                // assess.setTotalTestScoreAverage(assess.getTotalTestScore());
                assess.setTotalScore(assess.getExamScores());
                assess.setTeacher(t);
                Assesment.add(assess);
                a++;
            }
        }
        return SortedAssessment(t, Assesment);
    }

    public List<CacheAssessment> SortedAssessment(Teacher t, List<CacheAssessment> unsortedAssessment) {
        List<CacheAssessment> TempSorted = unsortedAssessment.stream().sorted(Comparator.comparing(CacheAssessment::getTotalScore).reversed()).collect(Collectors.toList());
        List<CacheAssessment> FinalSorted = new ArrayList<>();
        float Highest = 0.f;
        if (TempSorted.isEmpty()) {
        } else {
            Highest = (float) TempSorted.get(0).getTotalScore();
        }

        // TempSorted.forEach(c->{
        // });
       
      int currentRank = 1;
        for (int i = 0; i < TempSorted.size(); i++) {
            TempSorted.get(i).setHighestScore(Highest);
            if (i > 0 && TempSorted.get(i).getTotalScore() ==TempSorted.get(i - 1).getTotalScore()) {
              //   = learners.get(i - 1).rank;
               TempSorted.get(i).setPosition(TempSorted.get(i-1).getPosition());
            } else {
                 TempSorted.get(i).setPosition(AS.AddRankEnding(currentRank ));
            }
            currentRank++;
            FinalSorted.add(TempSorted.get(i));
        }

//        for (CacheAssessment s : TempSorted) {
//            double mark =s.getTotalScore();
//            
//            s.setHighestScore(Highest);
//            //  s.setInitials(t.getFirstName().charAt(0)+""+t.getLastName().charAt(0));
//            Student stud = SS.findStudent(s.getStudent().getStudentId());
//
//            if (stud.getStudentklass().getClassLevel().equals("Kindagarten")) {
//                s.setPosition("N/A");
//
//            } else {
//                
//                s.setPosition(AS.AddRankEnding(a));
//            }
//            s.setRemarks(addRemarks(s.getTotalScore()));
//            s.setGrade(setGrade(s.getTotalScore()));
//            a++;
//            FinalSorted.add(s);
//        }
       return FinalSorted;
   }

    public String setGrade(double marks) {
        if (marks >= 80) {
            return "HP";
        } else if (marks >= 68) {
            return "P";
        } else if (marks >= 54) {
            return "AP";
        } else if (marks >= 40) {
            return "D";

        } else {
            return "B";
        }

    }

    public String aggregate(List<Assessment> ass) {
        int grade = 0;

        if (ass.size() <= 5) {

            return "WAITING";
        }
        //ist<Assessment>TempAss=ass;
        int count = 0;
        while (count <= 1) {
            List<Assessment> CoreSubjects = ass.stream().filter(c -> c.getSubject()
                    .getSubjectName().equalsIgnoreCase("English") || c.getSubject().getSubjectName().equals("Mathematics")
                    || c.getSubject().getSubjectName()
                            .equalsIgnoreCase("Integrated Science)")
                    || c.getSubject().getSubjectName()
                            .equals("Social Studies")).collect(Collectors.toList());

            List<Assessment> OtherSubject = ass;
            OtherSubject.removeIf(c
                    -> c.getSubject()
                            .getSubjectName().equalsIgnoreCase("English") || c.getSubject().getSubjectName().equals("Mathematics")
                    || c.getSubject().getSubjectName()
                            .equalsIgnoreCase("Integrated Science)")
                    || c.getSubject().getSubjectName()
                            .equals("Social Studies")
            );
            for (double gradeValue : CoreSubjects.stream().map(Assessment::getTotalScore)
                    .collect(Collectors.toList())) {
                grade += Integer.parseInt(addRemarksJHS(gradeValue));
            }

            grade += Integer.parseInt(addRemarksJHS(OtherSubject.stream().sorted(
                    Comparator.comparing(Assessment::getTotalScore)
                            .reversed()).collect(Collectors.toList()).get(count).getTotalScore()));
            count++;
        }

        return grade + "";
    }

    public String addRemarksJHS(double marks) {
        if (marks >= 80) {
            return "1";
        } else if (marks >= 75) {
            return "2";
        } else if (marks >= 70) {
            return "3";
        } else if (marks >= 65) {
            return "4";
        } else if (marks >= 60) {
            return "5";
        } else if (marks >= 55) {
            return "6";
        } else if (marks >= 40) {
            return "7";
        } else if (marks >= 35) {
            return "8";
        } else {
            return "9";
        }

    }

    public String addRemarks(double marks) {
        if (marks >= 80) {
            return "Excellent";
        } else if (marks >= 70) {
            return "Very Good";
        } else if (marks >= 60) {
            return "Good";
        } else if (marks >= 45) {
            return "Pass";
        } else if (marks >= 35) {
            return "Weak";
        } else {
            return "Very Weak";
        }

    }

    public List<Attendance> GetStudentAttendanceFromExcel(MultipartFile path, Term term) throws IOException {

        XSSFSheet sheet = getCellData(path).getSheetAt(0);
        List<Attendance> Assesment = new ArrayList<>();
        DataFormatter df = new DataFormatter();
        int a = 0;

        for (int rowCount = sheet.getFirstRowNum() + 1; rowCount <= sheet.getLastRowNum(); rowCount++) {

            Student student = SS.findStudent(sheet.getRow(rowCount).getCell(0).getStringCellValue());
            Attendance assess = new Attendance();
            assess.setAttendancepk(assess.getId()+"");
            assess.setStudent(student);
            assess.setTerm(term);
            assess.setInterest(sheet.getRow(rowCount).getCell(5).getStringCellValue());
            assess.setTotalNumberOfDays(sheet.getRow(rowCount).getCell(2).getStringCellValue());
            assess.setStudentAttendedDays(sheet.getRow(rowCount).getCell(3).getNumericCellValue() + "");
            assess.setConduct(sheet.getRow(rowCount).getCell(4).getStringCellValue());

            // createCell(row, 4, "Total Days", style);
            //  createCell(row, 5, "Days In School", style);
            //  createCell(row, 6, "Conduct", style);
            //createCell(row, 7, "Interest", style);
            // 
            Assesment.add(assess);
            a++;
        }
        return Assesment;
    }

    public String saveStudentsFromExcelFile(MultipartFile path) throws IOException {
        return SS.saveStudentsIntoDb(GetStudentsFromExcelToDB(path));

    }

    private final XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private final List<Student> listStudent;

    public ExcelServices(List<Student> listUsers) {
        this.listStudent = listUsers;

        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine(String ClassName) {
        sheet = workbook.createSheet(ClassName);

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "ID", style);
        createCell(row, 1, "FullName", style);

        createCell(row, 2, "sex", style);
         createCell(row, 3, "Date0fBirth", style); 
          createCell(row, 4, "Number", style); 
           createCell(row, 5, "Parent Category", style); 
        
    }

    private void writeHeaderLineFinance(String ClassName) {
        sheet = workbook.createSheet(ClassName);

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "ID", style);
        createCell(row, 1, "FullName", style);

        createCell(row, 2, "Class", style);
        createCell(row, 3, "", style);
        createCell(row, 4, "", style);

        createCell(row, 5, "", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    public double convertCellValueToDouble(Object value) {
        if (value instanceof Integer) {

            return Double.parseDouble(value + "");
        } else if (value instanceof String) {
            return Double.parseDouble(value + "");
        } else {
            return (double) value;
        }

    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Student user : listStudent.stream().sorted(Comparator.comparing(Student::getFirstName)).collect(Collectors.toList())) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, user.getStudentReference(), style);
            createCell(row, columnCount++, user.getFirstName() + user.getSurname() + user.getLastName(), style);
            createCell(row, columnCount++, user.getSex(), style);
            createCell(row, columnCount++, user.getDateOfBirth(), style);
	     createCell(row, columnCount++, user.getParentNumber(), style);
            createCell(row, columnCount++, user.getParentCategory(), style);
           
        }
    }

    private void writeDataLinesForFinance() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Student user : listStudent.stream().sorted(Comparator.comparing(Student::getFirstName)).collect(Collectors.toList())) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, user.getStudentId(), style);
            createCell(row, columnCount++, user.getFirstName() + user.getSurname() + user.getLastName(), style);
            createCell(row, columnCount++, user.getStudentklass().getClassName(), style);
            createCell(row, columnCount++, "0", style);
            createCell(row, columnCount++, "0", style);
            createCell(row, columnCount++, "0", style);

        }
    }

    public void export(HttpServletResponse response, String ClassName) throws IOException {
        writeHeaderLine(ClassName);
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }

    public void exportFinance(HttpServletResponse response, String ClassName) throws IOException {
        writeHeaderLineFinance(ClassName);
        writeDataLinesForFinance();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }

    public Map<String, List<CacheAssessment>>
            GetStudentAssessmentsFromExcelSheet(Teacher t, MultipartFile path, Term term)
            throws IOException {
        System.out.println("Asssessment checking =============>>>>>>>>>>> " + term.getTermName() + "_" + t.getStaffId() + path.getOriginalFilename());
        int TotalNumberOfSheets = getCellData(path).getNumberOfSheets() - 1;
        Map<String, List<CacheAssessment>> TempMapData = new HashMap<>();
        String special = "";
        if (TS.findTeacher(t.getTeacherId()).getCachedAssessments().isEmpty()) {
            System.out.println(" none no data inside ");
        } else {
            CAS.deleteAllCachedAssessment(t);
        }
        if (CAS.getCacheAssessment().size() == 0) {

            special = "0";
        } else {

            special = "" + CAS.getCacheAssessment().size();
        }

        try {
            while (TotalNumberOfSheets >= 0) {
                List<CacheAssessment> Assesments = new ArrayList<>();

                XSSFSheet sheet = getCellData(path).getSheetAt(TotalNumberOfSheets);
                DecimalFormat decimalF = new DecimalFormat("##");
                int a = 0;

                for (int rowCount = sheet.getFirstRowNum() + 1; rowCount <= sheet.getLastRowNum(); rowCount++) {
                    if (sheet.getRow(rowCount).getCell(0).getRawValue() == null || sheet.getRow(rowCount).getCell(0).getRawValue().isEmpty()) {

                    } else {
                        Student student = SS.findStudent(sheet.getRow(rowCount).getCell(0).getStringCellValue());

                        CacheAssessment assess = new CacheAssessment();
                        assess.setStudent(student);
                        assess.setTerm(term);
                        assess.setSpecialId(special);
                        //  assess.setSubject(Ss.findAParticularSubject(getSubjectName(path,TotalNumberOfSheets)));
                        //  assess.setStudentClass(kS.FindKlass(getClassName(path,TotalNumberOfSheets)));
                        if (student.getStudentklass().getClassLevel().equalsIgnoreCase("Kindergaten")) {
                            assess.setExamScores(Double.parseDouble(sheet.getRow(rowCount).getCell(6).getRawValue()));
                            assess.setExamsAverage((assess.getExamScores()));

                        } else {
                            assess.setTotalTestScore(
                                    50 * ((ReturnCellValueFromFomula(sheet.getRow(rowCount)
                                            .getCell(2))
                                    + ReturnCellValueFromFomula(sheet.getRow(rowCount).getCell(3))
                                    + ReturnCellValueFromFomula(sheet.getRow(rowCount).getCell(4))
                                    + (ReturnCellValueFromFomula(sheet.getRow(rowCount).getCell(5))) )/ 60));
                            //   assess.setTestScoreAverage((assess.gKS.etTotalTestScore()/60)*50);
                            assess.setExamScores(ReturnCellValueFromFomula(sheet.getRow(rowCount).getCell(6)));
                            assess.setExamsAverage((50 * assess.getExamScores()) / 100);
                        }
                        assess.setTotalTestScoreAverage(assess.getTotalTestScore());
                        //   System.out.println(  assess.getExamsAverage());
                        assess.setTotalScore(Double.parseDouble(decimalF.format(assess.getExamsAverage()
                                + assess.getTotalTestScoreAverage())));
                        assess.setTeacher(t);
                        //      System.out.println("---------->"+(getSubjectName(path,TotalNumberOfSheets)));
                        Assesments.add(assess);
                        a++;
                    }
                }

                TempMapData.put(getClassNameAndSubjectNameFromExcel(path, TotalNumberOfSheets),
                         SortedAssessment(t, Assesments));
                TotalNumberOfSheets--;
                System.out.println("Asssessment checking =============>>>>>>>>>>> ");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("error getAssessmentExcelFile side  " + ex.toString() + ex.getLocalizedMessage());

        }

        return AddSubjectNamesKlassToAssessmentFromExcel(TempMapData);
    }

    public Map<String, List<CacheAssessment>>
            GetStudentMockFromExcelSheet(Teacher t, MultipartFile path, Term term)
            throws IOException {
        int TotalNumberOfSheets = getCellData(path).getNumberOfSheets() - 1;
        Map<String, List<CacheAssessment>> TempMapData = new HashMap<>();
        String special = "";
        if (CAS.getCacheAssessment().size() == 0) {
            special = "0";

        } else {
            special = "" + CAS.getCacheAssessment().size();
        }

        try {
            while (TotalNumberOfSheets >= 0) {
                List<CacheAssessment> Assesments = new ArrayList<>();

                XSSFSheet sheet = getCellData(path).getSheetAt(TotalNumberOfSheets);
                DecimalFormat decimalF = new DecimalFormat("##.#");
                int a = 0;

                for (int rowCount = sheet.getFirstRowNum() + 1; rowCount <= sheet.getLastRowNum(); rowCount++) {
                    if (sheet.getRow(rowCount).getCell(0).getRawValue() == null || sheet.getRow(rowCount).getCell(0).getRawValue().isEmpty()) {

                    } else {
                        Student student = SS.findStudent(sheet.getRow(rowCount).getCell(0).getStringCellValue());

                        CacheAssessment assess = new CacheAssessment();
                        assess.setStudent(student);
                        assess.setTerm(term);
                        assess.setSpecialId(special);
                        //  assess.setSubject(Ss.findAParticularSubject(getSubjectName(path,TotalNumberOfSheets)));
                        //  assess.setStudentClass(kS.FindKlass(getClassName(path,TotalNumberOfSheets)));
                        //   assess.setTotalTestScore(Double.parseDouble(decimalF.format
                        // (50*(convertCellValueToDouble(sheet.getRow(rowCount)
                        //     .getCell(2).getRawValue())+
                        //  convertCellValueToDouble(sheet.getRow(rowCount).getCell(3).getRawValue())+
                        //  (Double.parseDouble(sheet.getRow(rowCount).getCell(4).getRawValue()))+
                        //  (Double.parseDouble(sheet.getRow(rowCount).getCell(5).getRawValue())))/60)));
                        //   assess.setTestScoreAverage((assess.gKS.etTotalTestScore()/60)*50);
                        assess.setExamScores(
                                Double.parseDouble(sheet.getRow(rowCount).getCell(2).getRawValue()));
                        // assess.setExamsAverage((50*assess.getExamScores())/100);
                        //  assess.setTotalTestScoreAverage(assess.getTotalTestScore());
                        //   System.out.println(  assess.getExamsAverage());
                        assess.setTotalScore(assess.getExamScores());
                        assess.setTeacher(t);
                        //      System.out.println("---------->"+(getSubjectName(path,TotalNumberOfSheets)));
                        Assesments.add(assess);
                        a++;
                    }
                }

                TempMapData.put(getClassNameAndSubjectNameFromExcel(path, TotalNumberOfSheets),
                         SortedAssessment(t, Assesments));
                TotalNumberOfSheets--;
                System.out.println("Mock checking =============>>>>>>>>>>> ");
            }
        } catch (Exception ex) {
            System.out.println("error  " + ex.toString());

        }

        return AddSubjectNamesKlassToAssessmentFromExcel(TempMapData);
    }

    public String getClassNameAndSubjectNameFromExcel(MultipartFile f, int index) throws IOException {
        String SheetName = getCellData(f).getSheetAt(index).getSheetName();
        return SheetName;

    }

    public String getClassName(String name) {
        String[] klassName = name.split("_");
        return klassName[0];
    }

    public String getSubjectName(String name) {
        String[] klassName = name.split("_");
        return klassName[1];
    }

    public String getTeacherId(String name) {
        String[] klassName = name.split("_");
        return klassName[2];
    }

    public String getTermId(String name) {
        String[] klassName = name.split("_");
        return klassName[3];
    }

    public Map<String, List<CacheAssessment>> AddSubjectNamesKlassToAssessmentFromExcel(Map<String, List<CacheAssessment>> data) throws IOException {
        Map<String, List<CacheAssessment>> TempMapData = new HashMap<>();

        data.keySet().forEach(s -> {
            List<CacheAssessment> assesment = new ArrayList<>();
            assesment = data.get(s);
            assesment.stream().forEach(c -> {
                c.setSubject(TKSs.FindSubjectTaughtByTeacher(getClassName(s), getSubjectName(s)));
                c.setStudentClass(kS.FindKlass(getClassName(s)));
            });

            TempMapData.put(s, assesment);
        });

        return TempMapData;
    }

    // Course Excel methods
    public List<Course> getCoursesFromExcel(MultipartFile path) throws IOException {
        XSSFSheet sheet = getCellData(path).getSheetAt(0);
        List<Course> courses = new ArrayList<>();
        DataFormatter df = new DataFormatter();

        for (int rowCount = sheet.getFirstRowNum() + 1; rowCount <= sheet.getLastRowNum(); rowCount++) {
            Row row = sheet.getRow(rowCount);
            if (row == null || df.formatCellValue(row.getCell(0)).trim().isEmpty()) {
                continue;
            }

            Course course = new Course();
            course.setCourseName(df.formatCellValue(row.getCell(0)).trim());
            course.setDescription(df.formatCellValue(row.getCell(1)).trim());
            course.setNotes(df.formatCellValue(row.getCell(2)).trim());
            klass klass = new klass();
            klass.setClassName(df.formatCellValue(row.getCell(2)).trim());
            course.setKlass(klass);
            String subjectName = df.formatCellValue(row.getCell(3)).trim();
            if (!subjectName.isEmpty()) {
                Subject subject = Ss.Allsubjects().stream()
                        .filter(s -> s.getSubjectName().equalsIgnoreCase(subjectName))
                        .findFirst()
                        .orElse(null);
                if (subject != null) {
                    course.setSubject(subject);
                }
            }

            String termWeekStr = df.formatCellValue(row.getCell(4)).trim();
            if (!termWeekStr.isEmpty()) {
                TermWeek termWeek = findTermWeekByDisplayName(termWeekStr);
                if (termWeek != null) {
                    course.setTermWeek(termWeek);
                    course.setTerm(termWeek.getTerm());
                }
            }

            courses.add(course);
        }
        return courses;
    }

    public String saveCoursesFromExcel(MultipartFile path) throws IOException {
        List<Course> courses = getCoursesFromExcel(path);
        StringBuilder result = new StringBuilder();
        int successCount = 0;
        int failCount = 0;

        for (Course course : courses) {
            try {
                courseService.saveCourse(course);
                successCount++;
            } catch (Exception e) {
                failCount++;
                result.append("Failed to save course: ").append(course.getCourseName())
                      .append(" - ").append(e.getMessage()).append("\n");
            }
        }

        result.insert(0, "Import completed: " + successCount + " courses imported successfully");
        if (failCount > 0) {
            result.append(", ").append(failCount).append(" failed");
        }
        return result.toString();
    }

    private TermWeek findTermWeekByDisplayName(String displayName) {
        List<TermWeek> allTermWeeks = termWeekService.getAllTermWeeks();
        for (TermWeek tw : allTermWeeks) {
            String twDisplay = tw.getTerm().getTermName() + " - " + tw.getWeekName();
            if (twDisplay.equalsIgnoreCase(displayName)) {
                return tw;
            }
        }
        return null;
    }

}
