//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.UBSDB.UnerBasicSchoolFinancialSystem.klass;

import org.springframework.stereotype.Component;

@Component
public class klassReponse {
    private String className;
    private String classLevel;
    private String ClassName_Level;
   private String floralName;

    public String getFloralName() {
        return floralName;
    }

    public void setFloralName(String floralName) {
        this.floralName = floralName;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassLevel() {
        return this.classLevel;
    }

    public void setClassLevel(String classLevel) {
        this.classLevel = classLevel;
    }

    public String getClassName_Level() {
        return this.ClassName_Level;
    }

    public void setClassName_Level(String ClassName_Level) {
        this.ClassName_Level = ClassName_Level;
    }

    public klassReponse(String className, String classLevel, String floralName,String ClassName_Level) {
        this.className = className;
        this.classLevel = classLevel;
        this.ClassName_Level = ClassName_Level;
        this.floralName=floralName;
    }

    public klassReponse() {

    }

    @Override
    public String toString() {
        return "klassReponse{" +
                "className='" + className + '\'' +
                ", classLevel='" + classLevel + '\'' +
                ", ClassName_Level='" + ClassName_Level + '\'' +
                ", floralName='" + floralName + '\'' +
                '}';
    }
}
