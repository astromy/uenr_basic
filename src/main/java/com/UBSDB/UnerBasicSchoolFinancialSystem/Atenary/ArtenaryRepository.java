package com.UBSDB.UnerBasicSchoolFinancialSystem.Atenary;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ArtenaryRepository extends JpaRepository<Artenary, UUID> {

    Page<Artenary> findAll(Pageable pageable);
}
