package com.UBSDB.UnerBasicSchoolFinancialSystem.ActivityLog;

import com.UBSDB.UnerBasicSchoolFinancialSystem.security.users;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
    List<ActivityLog> findByUserOrderByLoginTimeDesc(users user);
    List<ActivityLog> findByUsernameOrderByLoginTimeDesc(String username);
    List<ActivityLog> findByRoleOrderByLoginTimeDesc(String role);
    List<ActivityLog> findByIsActiveOrderByLoginTimeDesc(boolean isActive);
    List<ActivityLog> findByLoginTimeBetweenOrderByLoginTimeDesc(LocalDateTime start, LocalDateTime end);
    List<ActivityLog> findByUserAndIsActiveOrderByLoginTimeDesc(users user, boolean isActive);
    List<ActivityLog> findByUsernameAndIsActiveOrderByLoginTimeDesc(String username, boolean isActive);
    long countByIsActive(boolean isActive);

    Page<ActivityLog> findAllByOrderByLoginTimeDesc(Pageable pageable);
    Page<ActivityLog> findByIsActiveOrderByLoginTimeDesc(boolean isActive, Pageable pageable);
    Page<ActivityLog> findByUsernameAndIsActiveOrderByLoginTimeDesc(String username, boolean isActive, Pageable pageable);
    Page<ActivityLog> findByRoleOrderByLoginTimeDesc(String role, Pageable pageable);
    long deleteByLoginTimeBefore(LocalDateTime cutoffTime);

    List<ActivityLog> findByActivityTypeOrderByLoginTimeDesc(String activityType);

    @Query("SELECT FUNCTION('DATE', a.loginTime) as day, COUNT(a) as count FROM ActivityLog a " +
            "WHERE a.activityType = 'LOGIN' AND YEAR(a.loginTime) = :year AND MONTH(a.loginTime) = :month " +
            "GROUP BY FUNCTION('DATE', a.loginTime) ORDER BY day")
    List<Object[]> countLoginsPerDay(@Param("year") int year, @Param("month") int month);

    @Query("SELECT a.user, COUNT(a) as loginCount FROM ActivityLog a " +
            "WHERE a.activityType = 'LOGIN' AND a.user IS NOT NULL " +
            "GROUP BY a.user ORDER BY loginCount DESC")
    List<Object[]> countLoginsByUser();

    @Query("SELECT a.role, COUNT(a) as loginCount FROM ActivityLog a " +
            "WHERE a.activityType = 'LOGIN' " +
            "GROUP BY a.role ORDER BY loginCount DESC")
    List<Object[]> countLoginsByRole();
}
