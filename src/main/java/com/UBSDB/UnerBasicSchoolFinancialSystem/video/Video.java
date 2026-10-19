//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.UBSDB.UnerBasicSchoolFinancialSystem.video;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.Subject;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(
    name = "video"
)
public class Video {
    @Id
    private String videoId;
    @ManyToOne
    private Subject subject;
    private String url;
    private String Indicator;
    private String description;
    private String searchString;
    @ManyToOne
    private klass klass;
    @ManyToOne
    private Term term;

    public Term getTerm() {
        return this.term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public Video() {
    }

    public klass getKlass() {
        return this.klass;
    }

    public Video(String videoId, Subject subject, String url, String Indicator, String description, String searchString, klass klass) {
        this.videoId = videoId;
        this.subject = subject;
        this.url = url;
        this.Indicator = Indicator;
        this.description = description;
        this.searchString = searchString;
        this.klass = klass;
    }

    public void setKlass(klass klass) {
        this.klass = klass;
    }

    public String getSearchString() {
        return this.searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getVideoId() {
        return this.videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public Subject getSubject() {
        return this.subject;
    }

    public void setSubject(Subject subject) {
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

    public VideoResponse getresponse() {
        return new VideoResponse(this.videoId, this.subject.getSubjectName(), this.url, this.Indicator, this.description, this.searchString, "");
    }
}
