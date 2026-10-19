package com.UBSDB.UnerBasicSchoolFinancialSystem.MountedCourse;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Course.CourseResponse;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.SubjectResponse;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermResponse;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klassReponse;
import lombok.Data;

@Data
public class MountedCourseResponse {
    private Long id;
    private CourseResponse course;
    private SubjectResponse subject;
    private klassReponse klass;
    private TermResponse term;
    private String teacherName;
    private String status;

    public MountedCourseResponse() {
    }

    public MountedCourseResponse(MountedCourse mountedCourse) {
        this.id = mountedCourse.getId();
        this.status = mountedCourse.getStatus();
        if (mountedCourse.getCourse() != null) {
            this.course = new CourseResponse(mountedCourse.getCourse());
        }
        if (mountedCourse.getSubject() != null) {
            this.subject = new SubjectResponse(mountedCourse.getSubject().getId(), mountedCourse.getSubject().getSubjectName());
        }
        if (mountedCourse.getKlass() != null) {
            this.klass = mountedCourse.getKlass().getKlassResponse();
        }
        if (mountedCourse.getTerm() != null) {
            this.term = mountedCourse.getTerm().getTermResponse();
        }
        if (mountedCourse.getTeacher() != null) {
            this.teacherName = mountedCourse.getTeacher().getFirstName() + " " + mountedCourse.getTeacher().getLastName();
        }
    }
}
