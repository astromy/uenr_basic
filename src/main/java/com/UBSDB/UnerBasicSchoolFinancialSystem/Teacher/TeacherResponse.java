package com.UBSDB.UnerBasicSchoolFinancialSystem.Teacher;

import lombok.*;
import org.springframework.stereotype.Component;


@Builder
@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherResponse {

    private String FirstName;
    private String ClassTeacher;


    private String StaffId;
  private String LastName;
    private String sex;

    private long  TeacherId;

    private String PhoneNumber ;

    private String TeacherStatus;


    @Override
    public String toString() {
        return "TeacherResponse{" +
                "FirstName='" + FirstName + '\'' +
                ", ClassTeacher='" + ClassTeacher + '\'' +
                ", StaffId='" + StaffId + '\'' +
                ", LastName='" + LastName + '\'' +
                ", sex='" + sex + '\'' +
                ", TeacherId=" + TeacherId +
                ", PhoneNumber='" + PhoneNumber + '\'' +
                ", TeacherStatus='" + TeacherStatus + '\'' +
                '}';
    }


}
