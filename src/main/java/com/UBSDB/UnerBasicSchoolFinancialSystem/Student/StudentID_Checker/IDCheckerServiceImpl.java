//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentID_Checker;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IDCheckerServiceImpl implements IDCheckerService {
    @Autowired
    private IDRepository IDR;

    public IDCheckerServiceImpl() {
    }

    public Optional<IDChecker> findIDChecker(int id) throws Exception {
        try {
            return this.IDR.findById(id);
        } catch (Exception var3) {
            return null;
        }
    }

    public List<IDChecker> getIDCheckers() throws Exception {
        try {
            return this.IDR.findAll();
        } catch (Exception var2) {
            Exception ex = var2;
            ex.printStackTrace();
            return null;
        }
    }

    public IDChecker Savechecker(IDChecker idChecker) throws Exception {
        try {
            return (IDChecker)this.IDR.save(idChecker);
        } catch (Exception var3) {
            Exception ex = var3;
            ex.printStackTrace();
            return null;
        }
    }
}
