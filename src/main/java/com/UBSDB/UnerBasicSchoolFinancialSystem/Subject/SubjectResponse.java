//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.UBSDB.UnerBasicSchoolFinancialSystem.Subject;

import org.springframework.stereotype.Component;

@Component
public class SubjectResponse {
    private long id;
    private String SubjectName;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubjectName() {
        return this.SubjectName;
    }

    public void setSubjectName(String SubjectName) {
        this.SubjectName = SubjectName;
    }

    public SubjectResponse(long id, String SubjectName) {
        this.id = id;
        this.SubjectName = SubjectName;
    }

    public SubjectResponse() {
    }
}
