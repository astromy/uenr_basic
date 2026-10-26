package com.UBSDB.UnerBasicSchoolFinancialSystem.ActivityLog;

import com.UBSDB.UnerBasicSchoolFinancialSystem.security.UserService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.users;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    private ActivityLogRepository activityLogRepository;

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        String role = "Unknown";

        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            role = authentication.getAuthorities().iterator().next().getAuthority();
        }

        ActivityLog activityLog = activityLogService.recordLogin(username, role, request);
        if (activityLog != null) {
            activityLog.setActivityType("LOGIN");

            activityLogRepository.save(activityLog);
        }

        // Continue with the default behavior (redirect to /RouteUser)
        response.sendRedirect(request.getContextPath() + "/RouteUser");
    }
}
