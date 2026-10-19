
package com.UBSDB.UnerBasicSchoolFinancialSystem.video;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, String> {
    @Query("SELECT u FROM Video u WHERE u.searchString LIKE %:searchString")
    List<Video> getVideoBysearchString(@Param("searchString") String searchString);
}
