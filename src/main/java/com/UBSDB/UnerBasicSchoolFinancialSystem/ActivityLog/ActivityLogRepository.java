package com.UBSDB.UnerBasicSchoolFinancialSystem.ActivityLog;

import com.UBSDB.UnerBasicSchoolFinancialSystem.security.users;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
