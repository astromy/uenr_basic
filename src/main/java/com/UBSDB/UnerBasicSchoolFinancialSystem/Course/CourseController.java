package com.UBSDB.UnerBasicSchoolFinancialSystem.Course;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.Subject;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.SubjectService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Teacher.Teacher;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Teacher.TeacherService1;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermWeek.TermWeek;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermWeek.TermWeekService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private TermService termService;

    @Autowired
    private TermWeekService termWeekService;

    @Autowired
    private klassService klassService;

    @Autowired
    private TeacherService1 teacherService;

    private static final int PAGE_SIZE = 10;

    @GetMapping({"/Courses"})
    public String listCourses(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Page<Course> coursePage = courseService.getAllCoursesPaged(pageable);

        model.addAttribute("courses", coursePage.getContent().stream()
                .map(Course::getCourseResponse)
                .collect(Collectors.toList()));
        model.addAttribute("coursePage", coursePage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", coursePage.getTotalPages());
        model.addAttribute("totalItems", coursePage.getTotalElements());

        List<Subject> subjects = subjectService.Allsubjects();
        model.addAttribute("subjectsCourses", subjects);

        List<Term> terms = termService.terms();
        model.addAttribute("terms", terms);

        List<TermWeek> termWeeks = termWeekService.getAllTermWeeks();
        model.addAttribute("termWeeks", termWeeks);

        List<klass> klasses = klassService.AllKlass();
        model.addAttribute("Courseklasses", klasses);

        List<Teacher> teachers = teacherService.listOfTeachers();
        model.addAttribute("teachers", teachers);

        return "index";
    }

    @GetMapping({"/SearchCourses"})
    public String searchCourses(Model model,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam("search") String search) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Page<Course> coursePage = courseService.searchCourses(search, pageable);

        model.addAttribute("courses", coursePage.getContent().stream()
                .map(Course::getCourseResponse)
                .collect(Collectors.toList()));
        model.addAttribute("coursePage", coursePage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", coursePage.getTotalPages());
        model.addAttribute("totalItems", coursePage.getTotalElements());
        model.addAttribute("searchQuery", search);

        List<Subject> subjects = subjectService.Allsubjects();
        model.addAttribute("subjectsCourses", subjects);

        List<Term> terms = termService.terms();
        model.addAttribute("terms", terms);

        List<TermWeek> termWeeks = termWeekService.getAllTermWeeks();
        model.addAttribute("termWeeks", termWeeks);

        List<klass> klasses = klassService.AllKlass();
        model.addAttribute("klasses", klasses);

        List<Teacher> teachers = teacherService.listOfTeachers();
        model.addAttribute("teachers", teachers);

        return "index";
    }

    @GetMapping({"/CoursesBySubject/{subjectId}"})
    public String listCoursesBySubject(Model model,
                                       @PathVariable("subjectId") Long subjectId,
                                       @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Page<Course> coursePage = courseService.getCoursesBySubject(subjectId, pageable);

        model.addAttribute("courses", coursePage.getContent().stream()
                .map(Course::getCourseResponse)
                .collect(Collectors.toList()));
        model.addAttribute("coursePage", coursePage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", coursePage.getTotalPages());
        model.addAttribute("totalItems", coursePage.getTotalElements());
        model.addAttribute("selectedSubjectId", subjectId);

        List<Subject> subjects = subjectService.Allsubjects();
        model.addAttribute("subjectsCourses", subjects);

        List<Term> terms = termService.terms();
        model.addAttribute("terms", terms);

        List<TermWeek> termWeeks = termWeekService.getAllTermWeeks();
        model.addAttribute("termWeeks", termWeeks);

        List<klass> klasses = klassService.AllKlass();
        model.addAttribute("klasses", klasses);

        List<Teacher> teachers = teacherService.listOfTeachers();
        model.addAttribute("teachers", teachers);

        return "index";
    }

    @GetMapping({"/AddCourseForm"})
    public String showAddCourseForm(Model model) {
        model.addAttribute("newCourse", new Course());
        List<Subject> subjects = subjectService.Allsubjects();
        model.addAttribute("subjectsCourses", subjects);
        List<TermWeek> termWeeks = termWeekService.getAllTermWeeks();

        model.addAttribute("termWeeks", termWeeks);
        return "index";
    }

    @PostMapping({"/SaveCourse"})
    public String saveCourse(Model model, Course course,
                             @RequestParam(value = "subjectId", required = false) Long subjectId,
                             @RequestParam(value = "termWeekId", required = false) String termWeekId,
                             @RequestParam(value = "classId", required = false) String classId) {
        try {
            if (subjectId != null) {
                Subject subject = subjectService.findAParticularSubject(subjectId);
                course.setSubject(subject);
            }

            if (termWeekId != null && !termWeekId.isEmpty()) {
                UUID twId = UUID.fromString(termWeekId);
                TermWeek termWeek = termWeekService.getWeekById(twId);
                course.setTermWeek(termWeek);
                if (termWeek != null && termWeek.getTerm() != null) {
                    course.setTerm(termWeek.getTerm());
                }
            }

            if (courseService.existsByCourseName(course.getCourseName())) {
                model.addAttribute("msg", "Course with this name already exists!");
                return "redirect:/AddCourseForm";
            }

            // Save course and link to class(es) via the junction table
            // If a Basic class is selected (e.g., Basic1A), the course is linked
            // to all classes with the same number (Basic1A through Basic1F)
            courseService.saveCourseWithClass(course, classId);
            model.addAttribute("msg", "Course saved successfully!");
        } catch (Exception ex) {
            model.addAttribute("msg", "Error saving course: " + ex.getMessage());
        }
        return "redirect:/Courses";
    }

    @GetMapping({"/EditCourse/{courseId}"})
    public String showEditCourseForm(Model model, @PathVariable("courseId") String courseId) {
        Course course = courseService.findCourseById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        model.addAttribute("updateCourse", course.getCourseResponse());
        List<Subject> subjects = subjectService.Allsubjects();
        model.addAttribute("subjectsCourses", subjects);
        List<TermWeek> termWeeks = termWeekService.getAllTermWeeks();
        model.addAttribute("termWeeks", termWeeks);
        return "index";
    }

    @PostMapping({"/UpdateCourse"})
    public String updateCourse(Model model, Course course,
                               @RequestParam("subjectId") Long subjectId,
                               @RequestParam(value = "termWeekId", required = false) String termWeekId,
                               @RequestParam(value = "classId", required = false) String classId) {
        try {
            Subject subject = subjectService.findAParticularSubject(subjectId);
            course.setSubject(subject);

            if (termWeekId != null && !termWeekId.isEmpty()) {
                UUID twId = UUID.fromString(termWeekId);
                TermWeek termWeek = termWeekService.getWeekById(twId);
                course.setTermWeek(termWeek);
                if (termWeek != null && termWeek.getTerm() != null) {
                    course.setTerm(termWeek.getTerm());
                }
            } else {
                course.setTermWeek(null);
                course.setTerm(null);
            }

            // Save course and link to class(es) via the junction table
            courseService.saveCourseWithClass(course, classId);
            model.addAttribute("msg", "Course updated successfully!");
        } catch (Exception ex) {
            model.addAttribute("msg", "Error updating course: " + ex.getMessage());
        }
        return "redirect:/Courses";
    }

    @GetMapping({"/DeleteCourse/{courseId}"})
    public String deleteCourse(Model model, @PathVariable("courseId") String courseId) {
        try {
            courseService.deleteCourse(courseId);
            model.addAttribute("msg", "Course deleted successfully!");
        } catch (Exception ex) {
            model.addAttribute("msg", "Error deleting course: " + ex.getMessage());
        }
        return "redirect:/Courses";
    }

    @PostMapping({"/AssignCourseToClasses"})
    public String assignCourseToClasses(Model model,
                                        @RequestParam("courseId") String courseId,
                                        @RequestParam(value = "classIds", required = false) List<String> classIds) {
        try {
            UUID uuid = UUID.fromString(courseId);
            if (classIds != null && !classIds.isEmpty()) {
                courseService.assignCourseToClasses(uuid, classIds);
                model.addAttribute("msg", "Course assigned to selected classes successfully!");
            } else {
                model.addAttribute("err", "Please select at least one class.");
            }
        } catch (Exception ex) {
            model.addAttribute("msg", "Error assigning course to classes: " + ex.getMessage());
        }
        return "redirect:/Courses";
    }
}
