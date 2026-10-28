/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.SMS;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.Bill;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.StudentBill.StudentBill;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Receipt.Receipt;
import com.UBSDB.UnerBasicSchoolFinancialSystem.SchoolFees.SchoolFees;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Teacher.Teacher;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author enock
 */
@Slf4j
@Service
public class SmsService {
    @Autowired
    private StudentService SS;

    public SmsService() {
    }

    public List<String> getParentPhonenumbers() {
        List<String> TemPhonenumbers = new ArrayList();
        Iterator var2 = this.SS.students().iterator();

        while(var2.hasNext()) {
            Student stud = (Student)var2.next();
            TemPhonenumbers.add("+233" + stud.getParentNumber());
        }

        return TemPhonenumbers;
    }

    public String sendSMS_To_Enock() {
        OkHttpClient client = (new OkHttpClient()).newBuilder().build();
        String outCOME = "";
        Request request = (new Request.Builder()).get().url("https://sms.arkesel.com/sms/api?action=send-sms&api_key=Oll3MmpoZVp5RkZyWTc3eDQ=&to=0556655441,0246577024&from=joyheart&sms=Hello world. Spreading peace and joy only. Remeber to put on your face mask. Stay safe!&schedule=13-01-2021 05:30 PM").build();

        try {
            Response response = client.newCall(request).execute();

            String var5;
            try {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                System.out.println(response.body().string() + " ................sms ");
                outCOME = response.body().string();
                var5 = outCOME;
            } catch (Throwable var8) {
                if (response != null) {
                    try {
                        response.close();
                    } catch (Throwable var7) {
                        var8.addSuppressed(var7);
                    }
                }

                throw var8;
            }

            if (response != null) {
                response.close();
            }

            return var5;
        } catch (IOException var9) {
            IOException ex = var9;
            Logger.getLogger(SmsService.class.getName()).log(Level.SEVERE, (String)null, ex);
            return "error";
        }
    }

    public String sendAdmissionMessage(Student stud) {
        OkHttpClient client = (new OkHttpClient()).newBuilder().build();
       // stud = this.SS.findStudent(stud.getStudentId());
        String outCOME = "";
        String AdmissionMessage = "Dear Parent, Your ward " + stud.getFirstName() + " " + stud.getLastName() + " " + stud.getSurname() + " with admission number " + stud.getStudentId() + " has been offered admission into Joyheart. " + stud.getStudentId()
                + " password: " + stud.getStudentId() + "1234. Admission tuition fee is "
                + stud.getStudentBills().stream().map(StudentBill::getFees)
                       .map(SchoolFees::getAmount).reduce(0.0, (a, b) -> {
            return a + b;
        });

        Request request = (new Request.Builder()).get().url("https://sms.arkesel.com/sms/api?action=send-sms&api_key=Oll3MmpoZVp5RkZyWTc3eDQ=&to=" + stud.getParentNumber() + "&from=joyheart&sms=" + AdmissionMessage).build();

        try {
            Response response = client.newCall(request).execute();
            log.info(response.message());
                if (!response.isSuccessful()) {log.info(response.toString());

                    throw new IOException("Unexpected code " + response);
                }

                System.out.println(response.body().string() + " ................sms ");
                outCOME = response.body().string();

        } catch (IOException var11) {
            IOException ex = var11;
            Logger.getLogger(SmsService.class.getName()).log(Level.SEVERE, (String)null, ex);
            return "error";
        }
        return "";
    }

    public String sendAdmissionMessages() {

            OkHttpClient client = (new OkHttpClient()).newBuilder().build();

        try {
            SS.students().forEach(stud -> {
                stud = this.SS.findStudent(stud.getStudentId());
                log.info("Sending sms to .... "+ stud.getParentNumber()+stud.getFirstName());

                String outCOME = "";
                String AdmissionMessage = "Dear Parent, Your ward " + stud.getFirstName() + " " + stud.getLastName() + " " + stud.getSurname() + " with admission number " + stud.getStudentId() +
                        " has been offered admission into Joyheart. " + stud.getStudentId()
                        + " password: " + stud.getStudentId() + "1234.Login at https://joyheart.onrender.com";
                Request request = (new Request.Builder()).get().url("https://sms.arkesel.com/sms/api?action=send-sms&api_key=Oll3MmpoZVp5RkZyWTc3eDQ=&to=" + stud.getParentNumber() + "&from=joyheart&sms=" + AdmissionMessage).build();

                Response response = null;
                try {
                    response = client.newCall(request).execute();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                String var7;

                if (!response.isSuccessful()) {
                    log.info("Unexpected code " + response);
                }

                var7 = outCOME;
            });

                    } catch (Throwable var9) {

                    }






        return "Done";
    }


    public String sendTeacher(Teacher teacher) {
        OkHttpClient client = (new OkHttpClient()).newBuilder().build();
        String outCOME = "";
        String AdmissionMessage = "Welcome to Joy heart Mr/Mrs"
                + teacher.getLastName() + " " +
               teacher.getFirstName()
                + " your staffId is " + teacher.getStaffId() +
                " password: " + teacher.getStaffId()+"ubs https://www.joyheart.onrender.com";
        Request request = (new Request.Builder()).get()
                .url("https://sms.arkesel.com/sms/api?action=send-sms&api_key=Oll3MmpoZVp5RkZyWTc3eDQ=&to=" + teacher.getPhoneNumber() + "&from=JoyHeart&sms=" + AdmissionMessage).build();

        try {
            Response response = client.newCall(request).execute();

            String var7;
            try {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                System.out.println(response.body().string() + " ................sms ");
                outCOME = response.body().string();
                var7 = outCOME;
            } catch (Throwable var10) {
                if (response != null) {
                    try {
                        response.close();
                    } catch (Throwable var9) {
                        var10.addSuppressed(var9);
                    }
                }

                throw var10;
            }

            if (response != null) {
                response.close();
            }

            return var7;
        } catch (IOException var11) {
            IOException ex = var11;
            Logger.getLogger(SmsService.class.getName()).log(Level.SEVERE, (String)null, ex);
            return "error";
        }
    }
    public String sendPaymentMessage(Receipt r) throws Exception {
        OkHttpClient client = (new OkHttpClient()).newBuilder().build();
        String outCOME = "";
        String AdmissionMessage = "Dear Parent, An Amount of " + r.getPayment().getAmount() + " has been successfully received for " + r.getPayment().getTerm().getTermName() + " with ID " + r.getStud().getStudentId() + " " + r.getStud().getFullName() + ". Bal. " + r.getPayment().getRemaining() + " Thank you ,powered by quantum publications.";
        Request request = (new Request.Builder()).get().url("https://sms.arkesel.com/sms/api?action=send-sms&api_key=Oll3MmpoZVp5RkZyWTc3eDQ=&to=" + r.getStud().getParentNumber() + "&from=joyheart&sms=" + AdmissionMessage).build();

        try {
            Response response = client.newCall(request).execute();

            String var7;
            try {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                System.out.println(response.body().string() + " ................sms ");
                outCOME = response.body().string();
                var7 = outCOME;
            } catch (Throwable var10) {
                if (response != null) {
                    try {
                        response.close();
                    } catch (Throwable var9) {
                        var10.addSuppressed(var9);
                    }
                }

                throw var10;
            }

            if (response != null) {
                response.close();
            }

            return var7;
        } catch (IOException var11) {
            IOException ex = var11;
            Logger.getLogger(SmsService.class.getName()).log(Level.SEVERE, (String)null, ex);
            ex.printStackTrace();
            return "error";
        }
    }
    public void sendMessage(String msg) throws Exception {
        OkHttpClient client = (new OkHttpClient()).newBuilder().build();
      //  String AdmissionMessage = "Dear Parent, An Amount of " + r.getPayment().getAmount() + " has been successfully received for " + r.getPayment().getTerm().getTermName() + " with ID " + r.getStud().getStudentId() + " " + r.getStud().getFullName() + ". Bal. " + r.getPayment().getRemaining() + " Thank you ,powered by quantum publications.";

        getParentPhonenumbers().forEach(c->{
           String outCOME = "";

           final  Request request = (new Request.Builder())
                   .get().url("https://sms.arkesel.com/sms/api?action=send-sms&api_key=Oll3MmpoZVp5RkZyWTc3eDQ=&to="
                + c + "&from=joyheart&sms=" + msg).build();

        try {
            Response response = client.newCall(request).execute();

            String var7;
            try {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                System.out.println(response.body().string() + " ................sms ");
              outCOME = response.body().string();
                var7 = outCOME;
            } catch (Throwable var10) {
                if (response != null) {
                    try {
                        response.close();
                    } catch (Throwable var9) {
                        var10.addSuppressed(var9);
                    }
                }


                throw var10;
            }

            if (response != null) {
                response.close();
            }


        } catch (IOException var11) {
            IOException ex = var11;
            Logger.getLogger(SmsService.class.getName()).log(Level.SEVERE, (String)null, ex);
            ex.printStackTrace();

        }
    });
    }

}
