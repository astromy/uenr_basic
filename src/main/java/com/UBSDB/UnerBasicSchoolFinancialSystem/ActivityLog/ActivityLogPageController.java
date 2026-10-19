package com.UBSDB.UnerBasicSchoolFinancialSystem.ActivityLog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Admin/activity-logs")
public class ActivityLogPageController {

    @GetMapping
    public String showActivityLogs(Model model) {
        model.addAttribute("activityLogs", true);
        return "index";
    }
}
