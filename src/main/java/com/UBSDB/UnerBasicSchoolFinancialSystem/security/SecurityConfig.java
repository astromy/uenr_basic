 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.security;

/**
 *
 * @author enock
 */
import com.UBSDB.UnerBasicSchoolFinancialSystem.ActivityLog.CustomAuthenticationSuccessHandler;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
 
@Configuration
@EnableWebSecurity
public class SecurityConfig {
//security settings
    private final UserDetailsServiceImpl userDetailsService;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        ExceptionHandlingConfigurer<HttpSecurity> accessDeniedPage = http
                .authorizeRequests()
                
                
                .requestMatchers("/","/SelectExcelFileToUpload","/ShowSendSmsForm" )
                .hasRole("Admin")
                .requestMatchers("/TeacherPortal/**").hasAnyAuthority("Teacher")
                .requestMatchers("/edit/**").hasAnyAuthority("Admin", "Admin").
                requestMatchers("/FinancePortal/**").hasAnyAuthority("Finance","Admin")
                .requestMatchers("/reset","/reset/**","/api/allStudents/","/GetStudentDetails/**",
                        "/StudentPayment","/AddUserRoles","/AddUserAdmin","/bankPortal/home","/BankPortal/CheckStudent").permitAll().requestMatchers
                ("/css/**","/script/**","/css/pro-pics/**","/css/stud-pics/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").successHandler(customAuthenticationSuccessHandler).permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/login");
http.authorizeRequests().and().csrf().disable();

           

        return http.build();
    }
/*
    .antMatchers("/","/SelectExcelFileToUpload","/ShowSendSmsForm" ).hasAnyAuthority("Admin")
            .antMatchers("/TeacherPortal/**").hasAnyAuthority("Teacher","Admin") .
                antMatchers("/StudentPortal/**").hasAnyAuthority("Student")
            .antMatchers("/edit/**").hasAnyAuthority("Admin", "Admin").
                antMatchers("/FinancePortal/**").hasAnyAuthority("Finance","Admin")
            .antMatchers("/AddUserAdmin","/AddUserRoles","/reset","/reset/**","/api/allStudents/","/GetStudentDetails/**","/StudentPayment").permitAll().antMatchers("/css/**","/script/**","/css/pro-pics/**","/css/stud-pics/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin().loginPage("/login").defaultSuccessUrl("/RouteUser",true).permitAll()
            .and()
            .logout().permitAll()
            .and()
            .exceptionHandling().accessDeniedPage("/login")
            ; http.authorizeRequests().and().csrf().disable();

    */


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
