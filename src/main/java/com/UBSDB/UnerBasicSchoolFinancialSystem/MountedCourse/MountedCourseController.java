package com.UBSDB.UnerBasicSchoolFinancialSystem.MountedCourse;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Course.Course;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Course.CourseService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.Subject;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.SubjectService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Teacher.Teacher;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Teacher.TeacherService1;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MountedCourseController {

    @Autowired
    private MountedCourseService mountedCourseService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private TermService termService;

    @Autowired
    private klassService klassService;

    @Autowired
    private TeacherService1 teacherService;

    private static final int PAGE_SIZE = 10;

    @GetMapping({"/MountedCourses"})
    public String listMountedCourses(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Page<MountedCourse> mountedCoursePage = mountedCourseService.getAllMountedCoursesPaged(pageable);

        model.addAttribute("mountedCourses", mountedCoursePage.getContent().stream()
                .map(MountedCourse::getMountedCourseResponse)
                .collect(Collectors.toList()));
        model.addAttribute("mountedCoursePage", mountedCoursePage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", mountedCoursePage.getTotalPages());
        model.addAttribute("totalItems", mountedCoursePage.getTotalElements());

        List<Course> courses = courseService.getAllCourses();
        List<Subject> subjects = subjectService.Allsubjects();
        List<Term> terms = termService.terms();
        List<klass> klasses = klassService.AllKlass();
        List<Teacher> teachers = teacherService.listOfTeachers();

        model.addAttribute("courses", courses);
        model.addAttribute("subjectsCourse", subjects);
        model.addAttribute("terms", terms);
        model.addAttribute("klasses", klasses);
        model.addAttribute("teachers", teachers);

        return "index";
    }

    @GetMapping({"/MountedCourses/class/{className}/term/{termId}"})
    public String listMountedCoursesByClassAndTerm(Model model,
                                                   @PathVariable("className") String className,
                                                   @PathVariable("termId") Long termId,
                                                   @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        klass klass = klassService.FindKlass(className);
        Term term = termService.findTermById(termId).orElse(null);

        if (term == null) {
            model.addAttribute("err", "Term not found");
            return "index";
        }

        List<MountedCourse> mountedCourses = mountedCourseService.getActiveMountedCoursesByClassAndTerm(klass, term);
        model.addAttribute("mountedCourses", mountedCourses.stream()
                .map(MountedCourse::getMountedCourseResponse)
                .collect(Collectors.toList()));
        model.addAttribute("className", className);
        model.addAttribute("termId", termId);
        model.addAttribute("termName", term.getTermName());

        List<Course> courses = courseService.getAllCourses();
        List<Subject> subjects = subjectService.Allsubjects();
        List<Term> terms = termService.terms();
        List<klass> klasses = klassService.AllKlass();
        List<Teacher> teachers = teacherService.listOfTeachers();

        model.addAttribute("courses", courses);
        model.addAttribute("subjects", subjects);
        model.addAttribute("terms", terms);
        model.addAttribute("klasses", klasses);
        model.addAttribute("teachers", teachers);

        return "index";
    }

    @GetMapping({"/AddMountedCourseForm"})
    public String showAddMountedCourseForm(Model model) {
        List<Course> courses = courseService.getAllCourses();
        List<Subject> subjects = subjectService.Allsubjects();
        List<Term> terms = termService.terms();
        List<klass> klasses = klassService.AllKlass();
        List<Teacher> teachers = teacherService.listOfTeachers();

        model.addAttribute("courses", courses);
        model.addAttribute("subjects", subjects);
        model.addAttribute("terms", terms);
        model.addAttribute("klasses", klasses);
        model.addAttribute("teachers", teachers);
        model.addAttribute("newMountedCourse", new MountedCourse());

        return "index";
    }

    @PostMapping({"/SaveMountedCourse"})
    public String saveMountedCourse(Model model,
                                    @RequestParam("courseId") String courseId,
                                    @RequestParam("subjectId") Long subjectId,
                                    @RequestParam("className") String className,
                                    @RequestParam("termId") Long termId,
                                    @RequestParam(value = "teacherId", required = false) Long teacherId,
                                    @RequestParam(value = "status", required = false, defaultValue = "Active") String status) {
        try {
            Course course = courseService.findCourseById(courseId).orElse(null);
            Subject subject = subjectService.findAParticularSubject(subjectId);
            klass klass = klassService.FindKlass(className);
            Term term = termService.findTermById(termId).orElse(null);
            Teacher teacher = teacherId != null ? teacherService.findTeacher(teacherId) : null;

            if (course == null || subject == null || klass == null || term == null) {
                model.addAttribute("msg", "Invalid selection. Please check all fields.");
                return "redirect:/AddMountedCourseForm";
            }

            if (mountedCourseService.existsByCourseAndKlassAndTerm(course, klass, term)) {
                model.addAttribute("msg", "This course is already mounted for the selected class and term!");
                return "redirect:/MountedCourses";
            }

            MountedCourse mountedCourse = new MountedCourse();
            mountedCourse.setCourse(course);
            mountedCourse.setSubject(subject);
            mountedCourse.setKlass(klass);
            mountedCourse.setTerm(term);
            mountedCourse.setTeacher(teacher);
            mountedCourse.setStatus(status);

            mountedCourseService.saveMountedCourse(mountedCourse);
            model.addAttribute("msg", "Mounted course saved successfully!");
        } catch (Exception ex) {
            model.addAttribute("msg", "Error saving mounted course: " + ex.getMessage());
        }
        return "redirect:/MountedCourses";
    }

    @GetMapping({"/EditMountedCourse/{id}"})
    public String showEditMountedCourseForm(Model model, @PathVariable("id") Long id) {
        MountedCourse mountedCourse = mountedCourseService.getMountedCourseById(id)
                .orElseThrow(() -> new RuntimeException("Mounted course not found"));

        List<Course> courses = courseService.getAllCourses();
        List<Subject> subjects = subjectService.Allsubjects();
        List<Term> terms = termService.terms();
        List<klass> klasses = klassService.AllKlass();
        List<Teacher> teachers = teacherService.listOfTeachers();

        model.addAttribute("courses", courses);
        model.addAttribute("subjects", subjects);
        model.addAttribute("terms", terms);
        model.addAttribute("klasses", klasses);
        model.addAttribute("teachers", teachers);
        model.addAttribute("updateMountedCourse", mountedCourse.getMountedCourseResponse());

        return "index";
    }

    @PostMapping({"/UpdateMountedCourse"})
    public String updateMountedCourse(Model model,
                                      @RequestParam("id") Long id,
                                      @RequestParam("courseId") String courseId,
                                      @RequestParam("subjectId") Long subjectId,
                                      @RequestParam("className") String className,
                                      @RequestParam("termId") Long termId,
                                      @RequestParam(value = "teacherId", required = false) Long teacherId,
                                      @RequestParam(value = "status", required = false, defaultValue = "Active") String status) {
        try {
            MountedCourse mountedCourse = mountedCourseService.getMountedCourseById(id)
                    .orElseThrow(() -> new RuntimeException("Mounted course not found"));

            Course course = courseService.findCourseById(courseId).orElse(null);
            Subject subject = subjectService.findAParticularSubject(subjectId);
            klass klass = klassService.FindKlass(className);
            Term term = termService.findTermById(termId).orElse(null);
            Teacher teacher = teacherId != null ? teacherService.findTeacher(teacherId) : null;

            if (course == null || subject == null || klass == null || term == null) {
                model.addAttribute("msg", "Invalid selection. Please check all fields.");
                return "redirect:/EditMountedCourse/" + id;
            }

            mountedCourse.setCourse(course);
            mountedCourse.setSubject(subject);
            mountedCourse.setKlass(klass);
            mountedCourse.setTerm(term);
            mountedCourse.setTeacher(teacher);
            mountedCourse.setStatus(status);

            mountedCourseService.saveMountedCourse(mountedCourse);
            model.addAttribute("msg", "Mounted course updated successfully!");
        } catch (Exception ex) {
            model.addAttribute("msg", "Error updating mounted course: " + ex.getMessage());
        }
        return "redirect:/MountedCourses";
    }

    @GetMapping({"/DeleteMountedCourse/{id}"})
    public String deleteMountedCourse(Model model, @PathVariable("id") Long id) {
        try {
            mountedCourseService.deleteMountedCourse(id);
            model.addAttribute("msg", "Mounted course deleted successfully!");
        } catch (Exception ex) {
            model.addAttribute("msg", "Error deleting mounted course: " + ex.getMessage());
        }
        return "redirect:/MountedCourses";
    }
}
