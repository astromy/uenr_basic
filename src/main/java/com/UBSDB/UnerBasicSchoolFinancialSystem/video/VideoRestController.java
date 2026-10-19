//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.UBSDB.UnerBasicSchoolFinancialSystem.video;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.SubjectResponse;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.SubjectService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermResponse;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klassService;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VideoRestController {
    @Autowired
    private VideoService vs;
    @Autowired
    private TermService TS;
    @Autowired
    private StudentService sS;
    @Autowired
    private SubjectService SubS;
    @Autowired
    private klassService ks;

    public VideoRestController() {
    }

    @GetMapping({"/VideoTerms"})
    public ResponseEntity<List<TermResponse>> getVideoTerms() throws Exception {
        try {
            return ResponseEntity.ok((List)this.TS.terms().stream().map(Term::getTermResponse).collect(Collectors.toList()));
        } catch (Exception var2) {
            return null;
        }
    }

    @GetMapping({"/singleVideo"})
    public ResponseEntity<Video> getSIngleVideo() {
        return ResponseEntity.ok(new Video());
    }

    @GetMapping({"/GetSubjectStudent"})
    public ResponseEntity<Set<SubjectResponse>> subjects(Principal p) throws Exception {
        try {
            Student student = this.sS.findStudent(p.getName());
            Set<SubjectResponse> subjectResponses = new HashSet();
            this.SubS.Allsubjects().forEach((c) -> {
                c.getKlaases().stream().forEach((k) -> {
                    if (k.getClassName().equals(student.getStudentklass().getClassName())) {
                        subjectResponses.add(c.SubjectResponse());
                    }

                });
            });
            return ResponseEntity.ok(subjectResponses);
        } catch (Exception var4) {
            return null;
        }
    }

    @PostMapping({"/saveVideo"})
    public ResponseEntity<Boolean> saveVideo(VideoRequest videoRequest) throws Exception {
        System.out.println(videoRequest.toString());

        try {
            videoRequest.getKlasses().forEach((c) -> {
                Term t = this.TS.getCurrentTerm();
                Video vid = new Video();
                vid.setDescription(videoRequest.getDescription());
                vid.setIndicator(videoRequest.getIndicator());
                vid.setUrl(videoRequest.getUrl());

                try {
                    klass k = this.ks.FindKlass(c);
                    vid.setKlass(k);
                    vid.setSubject(this.SubS.findSubjectByVideo(videoRequest.getSubject(), k.getClassName()));
                    vid.setTerm(t);
                    vid.setSearchString(vid.getDescription() + vid.getKlass().getClassName() + vid.getIndicator() + t.getTermName() + t.getAcademicyear().getAcademicName() + vid.getIndicator() + vid.getSubject().getSubjectName());
                    vid.setVideoId((long)vid.hashCode() + t.getId() + (long)(k.getVideo().size() + 1) + "");
                    this.vs.saveVideo(vid);
                } catch (Exception var6) {
                    Exception ex = var6;
                    Logger.getLogger(VideoRestController.class.getName()).log(Level.SEVERE, (String)null, ex);
                }

            });
            return ResponseEntity.ok(true);
        } catch (Exception var3) {
            Exception ex = var3;
            ex.printStackTrace();
            return ResponseEntity.ok(false);
        }
    }

    @PostMapping({"/EditVideo"})
    public ResponseEntity<Boolean> EditVideo(VideoRequest videoRequest) throws Exception {
        System.out.println(videoRequest.toString());

        try {
            videoRequest.getKlasses().forEach((c) -> {
                Term t = this.TS.getCurrentTerm();
                Video vid = new Video();
                vid.setDescription(videoRequest.getDescription());
                vid.setIndicator(videoRequest.getIndicator());
                vid.setUrl(videoRequest.getUrl());

                try {
                    klass k = this.ks.FindKlass(c);
                    vid.setKlass(k);
                    vid.setSubject(this.SubS.findSubjectByVideo(videoRequest.getSubject(), k.getClassName()));
                    vid.setTerm(t);
                    vid.setSearchString(vid.getDescription() + vid.getKlass().getClassName() + vid.getIndicator() + t.getTermName() + vid.getIndicator() + vid.getSubject().getSubjectName());
                    this.vs.saveVideo(vid);
                } catch (Exception var6) {
                    Exception ex = var6;
                    Logger.getLogger(VideoRestController.class.getName()).log(Level.SEVERE, (String)null, ex);
                }

            });
            return ResponseEntity.ok(true);
        } catch (Exception var3) {
            Exception ex = var3;
            ex.printStackTrace();
            return ResponseEntity.ok(false);
        }
    }

    @GetMapping({"/getAllVideos"})
    public ResponseEntity<List<VideoResponse>> getAllVideos() throws Exception {
        try {
            List<VideoResponse> responses = (List)this.vs.getVideos().stream().map(Video::getresponse).limit(5L).collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        } catch (Exception var2) {
            Exception ex = var2;
            ex.printStackTrace();
            return null;
        }
    }

    @GetMapping({"/getAllVideosStudent"})
    public ResponseEntity<List<VideoResponse>> getAllVideosStudent(Principal p) throws Exception {
        try {
            Student stud = this.sS.findStudent(p.getName());
            List<VideoResponse> responses = (List)stud.getStudentklass().getVideo().stream().filter((c) -> {
                return c.getTerm().getId() == this.TS.getCurrentTerm().getId();
            }).map(Video::getresponse).limit(5L).collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        } catch (Exception var4) {
            Exception ex = var4;
            ex.printStackTrace();
            return null;
        }
    }

    @GetMapping({"/getByVidoesBySearch/{search}"})
    public ResponseEntity<List<VideoResponse>> getsearchVideos(@PathVariable("search") String search) throws Exception {
        try {
            List<VideoResponse> responses = (List)this.vs.SearchVideo(search).stream().map(Video::getresponse).collect(Collectors.toList());
            System.out.println(" Search criteria " + responses.size() + " " + search);
            return ResponseEntity.ok(responses);
        } catch (Exception var3) {
            Exception ex = var3;
            ex.printStackTrace();
            return null;
        }
    }

    @GetMapping({"/getByVidoesByStudentSearch"})
    public ResponseEntity<List<VideoResponse>> getsearchVideosStudent(Principal p, String search) throws Exception {
        try {
            Student stud = this.sS.findStudent(p.getName());
            List<VideoResponse> responses = (List)stud.getStudentklass().getVideo().stream().filter((c) -> {
                return c.getSearchString().toUpperCase().contains(search.toUpperCase());
            }).map(Video::getresponse).limit(5L).collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        } catch (Exception var5) {
            Exception ex = var5;
            ex.printStackTrace();
            return null;
        }
    }
}
