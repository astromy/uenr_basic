//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.UBSDB.UnerBasicSchoolFinancialSystem.video;

import org.springframework.stereotype.Component;

@Component
public class VideoResponse {
    private String videoId;
    private String subject;
    private String subjectName;
    private String url;
    private String Indicator;
    private String description;
    private String searchString;
    private String klass;

    public String getSubjectName() {
        return this.subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getVideoId() {
        return this.videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
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

    public String getKlass() {
        return this.klass;
    }

    public void setKlass(String klass) {
        this.klass = klass;
    }

    public VideoResponse() {
    }

    public VideoResponse(String videoId, String subject, String url, String Indicator, String description, String searchString, String klassName) {
        this.videoId = videoId;
        this.subject = subject;
        this.url = url;
        this.Indicator = Indicator;
        this.description = description;
        this.searchString = searchString;
        this.klass = klassName;
    }
}
