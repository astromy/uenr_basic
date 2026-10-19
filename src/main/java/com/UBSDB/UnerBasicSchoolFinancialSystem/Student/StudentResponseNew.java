package com.UBSDB.UnerBasicSchoolFinancialSystem.Student;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Attendance.Attendance;
import lombok.Builder;
import lombok.Data;

import jakarta.persistence.OneToMany;
import java.util.List;
@Builder
@Data
public class StudentResponseNew {
    private String StudentId;
    private Long idSpecia;
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
    private String studentklass;
//    @OneToMany(mappedBy = "student")
//    private List<Attendance> attendances;
}
