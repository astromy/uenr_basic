//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.UBSDB.UnerBasicSchoolFinancialSystem.Student;

import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klassReponse;
import jakarta.persistence.Column;
import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@Builder
@AllArgsConstructor
public class StudentResponse {
    private String StudentId;
    private String idSpecia;
    private String FirstName;
    private String LastName;
    private String surname;
    private String dateOfBirth;
    private String physicalDisability;
    private String NHISCardumber;
    private String GuadiaName;
    private String staffId;
    private String ParentCategory;
    private String parentNumber;
    private String residentialAddress;
    private String Occupation;
    private String Sex;
    private String fullName;
    private String GhanaCardNumber;
    private String email;
    private String StudentStatus;
    private boolean newComer;
    private klassReponse studentklass;
    private String className;

    private String StudentReference;
    private String studentPicture;
    private String productPicturePath;
    private String ProductPicturePathPDF;

    @Override
    public String toString() {
        return "StudentResponse{" +
                "StudentId='" + StudentId + '\'' +
                ", idSpecia='" + idSpecia + '\'' +
                ", FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", surname='" + surname + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", physicalDisability='" + physicalDisability + '\'' +
                ", NHISCardumber='" + NHISCardumber + '\'' +
                ", GuadiaName='" + GuadiaName + '\'' +
                ", staffId='" + staffId + '\'' +
                ", ParentCategory='" + ParentCategory + '\'' +
                ", parentNumber='" + parentNumber + '\'' +
                ", residentialAddress='" + residentialAddress + '\'' +
                ", Occupation='" + Occupation + '\'' +
                ", Sex='" + Sex + '\'' +
                ", fullName='" + fullName + '\'' +
                ", GhanaCardNumber='" + GhanaCardNumber + '\'' +
                ", email='" + email + '\'' +
                ", StudentStatus='" + StudentStatus + '\'' +
                ", newComer=" + newComer +
                ", studentklass=" + studentklass +
                ", className='" + className + '\'' +
                ", StudentReference='" + StudentReference + '\'' +
                ", studentPicture='" + studentPicture + '\'' +
                ", productPicturePath='" + productPicturePath + '\'' +
                ", ProductPicturePathPDF='" + ProductPicturePathPDF + '\'' +
                '}';
    }

    public void setIdSpecia(String idSpecia) {
       if (idSpecia==null){
      this.idSpecia="0L";
      }
      
        this.idSpecia = idSpecia;
    }

    public void setSex(String Sex) {
        this.Sex = Sex;
    }

    public StudentResponse(Student stud) {
        this.StudentId = stud.getStudentId();
        this.idSpecia = stud.getIdSpecia().toString();
        
        this.FirstName = stud.getFirstName();
        this.LastName = stud.getLastName();
        this.surname = stud.getSurname();
        this.dateOfBirth = stud.getDateOfBirth();
        this.physicalDisability = stud.getPhysicalDisability();
        this.NHISCardumber = stud.getNHISCardumber();
        this.GuadiaName = stud.getGuadiaName();
        this.staffId = stud.getStaffId();
        this.ParentCategory = stud.getParentCategory();
        this.parentNumber = stud.getParentNumber();
        this.residentialAddress = stud.getResidentialAddress();
        this.Occupation = stud.getOccupation();
        this.Sex = stud.getSex();
        this.fullName = stud.getFullName();
        this.GhanaCardNumber = stud.getGhanaCardNumber();
        this.email = stud.getEmail();
        this.StudentStatus = stud.getStudentStatus();
        this.newComer = stud.isNewComer();
        this.studentklass = stud.getStudentklass().getKlassResponse();
        this.StudentReference = stud.getStudentReference();
        this.studentPicture = stud.getStudentPicture();
        this.productPicturePath = stud.getProductPicturePath();
        this.ProductPicturePathPDF = stud.getProductPicturePathPDF();
    }

    public StudentResponse() {
    }
    public String setStudentName(){
    return    this.className=this.getStudentklass().getClassName();
    }
   public String getClassName(){
       this.className=this.getStudentklass().getClassName();
        return this.className;
   }
    public StudentResponse(String StudentId, Long idSpecia, String FirstName, String LastName, String surname, String dateOfBirth, String physicalDisability, String NHISCardumber, String GuadiaName, String staffId, String ParentCategory, String parentNumber, String residentialAddress, String Occupation, String Sex, String fullName, String GhanaCardNumber, String email, String StudentStatus, boolean newComer, klassReponse studentklass, String StudentReference, String studentPicture, String productPicturePath, String ProductPicturePathPDF) {
        this.StudentId = StudentId;
        this.idSpecia = idSpecia.toString();
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.physicalDisability = physicalDisability;
        this.NHISCardumber = NHISCardumber;
        this.GuadiaName = GuadiaName;
        this.staffId = staffId;
        this.ParentCategory = ParentCategory;
        this.parentNumber = parentNumber;
        this.residentialAddress = residentialAddress;
        this.Occupation = Occupation;
        this.Sex = Sex;
        this.fullName = fullName;
        this.GhanaCardNumber = GhanaCardNumber;
        this.email = email;
        this.StudentStatus = StudentStatus;
        this.newComer = newComer;
        this.studentklass = studentklass;
        this.StudentReference = StudentReference;
        this.studentPicture = studentPicture;
        this.productPicturePath = productPicturePath;
        this.ProductPicturePathPDF = ProductPicturePathPDF;
    }


    public String getProductPicturePath() {
        return this.studentPicture != null && this.StudentId != null ? this.productPicturePath : null;
    }

    public String getProductPicturePathPDF() {
        return this.studentPicture != null && this.StudentId != null ? this.ProductPicturePathPDF : null;
    }
}
