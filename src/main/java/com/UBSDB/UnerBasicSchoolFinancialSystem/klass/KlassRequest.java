package com.UBSDB.UnerBasicSchoolFinancialSystem.klass;

import org.springframework.stereotype.Component;

@Component

public class KlassRequest {

    private String ClassName;
    private String floralName;
    public String getClassName() {
        return ClassName;
    }
    public void setClassName(String className) {
        ClassName = className;
    }
    public String getFloralName() {
        return floralName;
    }

    @Override
    public String toString() {
        return "KlassRequest{" +
                "ClassName='" + ClassName + '\'' +
                ", floralName='" + floralName + '\'' +
                '}';
    }

    public void setFloralName(String floralName) {
        this.floralName = floralName;
    }
}
