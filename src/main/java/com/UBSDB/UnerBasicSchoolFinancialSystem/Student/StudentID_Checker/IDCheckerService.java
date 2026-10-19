//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentID_Checker;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public interface IDCheckerService {
    Optional<IDChecker> findIDChecker(int id) throws Exception;

    List<IDChecker> getIDCheckers() throws Exception;

    IDChecker Savechecker(IDChecker idChecker) throws Exception;
}
