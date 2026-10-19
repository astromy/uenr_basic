//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.UBSDB.UnerBasicSchoolFinancialSystem.video;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class VideoRequest {
    private String subject;
    private String url;
    private String Indicator;
    private String description;
    private String searchString;
    private List<String> klasses;

    public VideoRequest() {
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIndicator() {
        return this.Indicator;
    }

    public void setIndicator(String Indicator) {
        this.Indicator = Indicator;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSearchString() {
        return this.searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public List<String> getKlasses() {
        return this.klasses;
    }

    public void setKlasses(List<String> klasses) {
        this.klasses = klasses;
    }

    public String toString() {
        return "VideoRequest{subject=" + this.subject + ", url=" + this.url + ", Indicator=" + this.Indicator + ", description=" + this.description + ", searchString=" + this.searchString + ", klasses=" + this.klasses + '}';
    }
}
