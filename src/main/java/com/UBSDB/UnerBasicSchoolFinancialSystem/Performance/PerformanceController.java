package com.UBSDB.UnerBasicSchoolFinancialSystem.Performance;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/Performance")
public class PerformanceController {

    @Autowired
    private PerformanceService performanceService;

    @Autowired
    private TermService termService;

    @Autowired
    private klassService klassService;

    @GetMapping({"", "/"})
    public String performanceDashboard(Model model) {
        List<Term> terms = performanceService.getAllTerms();
        List<klass> klasses = performanceService.getAllClasses();

        model.addAttribute("terms", terms);
        model.addAttribute("klasses", klasses);

        // Default to current term and first class
        if (!terms.isEmpty()) {
            Term currentTerm = termService.getCurrentTerm();
            if (currentTerm != null) {
                model.addAttribute("selectedTermId", currentTerm.getId());
            } else {
                model.addAttribute("selectedTermId", terms.get(0).getId());
            }
        }
        if (!klasses.isEmpty()) {
            model.addAttribute("selectedClassName", klasses.get(0).getClassName());
        }

        return "index";
    }

    @GetMapping("/chart-data")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getChartData(
            @RequestParam(defaultValue = "") String className,
            @RequestParam(defaultValue = "0") Long termId) {

        Map<String, Object> response = new HashMap<>();

        if (className.isEmpty() || termId == 0) {
            response.put("error", "Please select both class and term");
            return ResponseEntity.ok(response);
        }

        try {
            PerformanceChartData chartData = performanceService.getPerformanceChartData(className, termId);
            List<SubjectPerformanceData> subjectPerformance = performanceService.getSubjectPerformanceByClassAndTerm(className, termId);
            List<CoursePerformanceData> coursePerformance = performanceService.getCoursePerformanceByClassAndTerm(className, termId);

            response.put("labels", chartData.getLabels());
            response.put("testScores", chartData.getTestScores());
            response.put("examScores", chartData.getExamScores());
            response.put("totalScores", chartData.getTotalScores());
            response.put("subjectPerformance", subjectPerformance);
            response.put("coursePerformance", coursePerformance);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Error loading performance data: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/subject-performance")
    @ResponseBody
    public ResponseEntity<List<SubjectPerformanceData>> getSubjectPerformance(
            @RequestParam(defaultValue = "") String className,
            @RequestParam(defaultValue = "0") Long termId) {

        if (className.isEmpty() || termId == 0) {
            return ResponseEntity.ok(new java.util.ArrayList<>());
        }

        List<SubjectPerformanceData> data = performanceService.getSubjectPerformanceByClassAndTerm(className, termId);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/course-performance")
    @ResponseBody
    public ResponseEntity<List<CoursePerformanceData>> getCoursePerformance(
            @RequestParam(defaultValue = "") String className,
            @RequestParam(defaultValue = "0") Long termId) {

        if (className.isEmpty() || termId == 0) {
            return ResponseEntity.ok(new java.util.ArrayList<>());
        }

        List<CoursePerformanceData> data = performanceService.getCoursePerformanceByClassAndTerm(className, termId);
        return ResponseEntity.ok(data);
    }
}
