package com.UBSDB.UnerBasicSchoolFinancialSystem.Paystack;

import com.UBSDB.UnerBasicSchoolFinancialSystem.FeePayment.PaymentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Receipt.Receipt;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentService;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Public-facing Paystack payment flow:
 *
 *  1. GET  /Paystack/Pay/{studentId}      -> shows an amount-entry page (no login required,
 *                                             so parents can pay without a staff account).
 *  2. POST /Paystack/Initiate             -> calls Paystack, redirects the browser to
 *                                             the hosted checkout page.
 *  3. GET  /Paystack/callback             -> Paystack sends the browser back here after
 *                                             payment; verifies server-side and shows a
 *                                             result page. The webhook (see
 *                                             PaystackWebhookController) is the real
 *                                             source of truth and will also credit the
 *                                             payment even if the user closes the tab
 *                                             before landing here.
 *
 * @author UBS
 */
@Controller
@RequestMapping("/Paystack")
public class PaystackController {

    private static final Logger LOG = Logger.getLogger(PaystackController.class.getName());

    @Autowired private PaystackService paystackService;
    @Autowired private PaystackConfig paystackConfig;
    @Autowired private StudentService SS;
    @Autowired private PaymentService PS;

    @GetMapping("/Pay/{studentId}")
    public String showPayForm(Model model, @PathVariable("studentId") String studentId) {
        Student stud = SS.findStudent(studentId);
        if (stud == null) {
            model.addAttribute("msg", "Student not found. Please check the ID and try again.");
            return "Paystack/PaystackPay";
        }
        model.addAttribute("student", stud);
        model.addAttribute("studentId", studentId);
        model.addAttribute("outstandingBalance", PS.OutStandingbalance(studentId));
        model.addAttribute("paystackConfigured", paystackConfig.isConfigured());
        model.addAttribute("passFees", paystackConfig.isPassFeesToCustomer());
        model.addAttribute("feePercent", paystackConfig.getFeePercent());
        return "Paystack/PaystackPay";
    }

    @PostMapping("/Initiate")
    public String initiate(Model model,
                           @RequestParam("studentId") String studentId,
                           @RequestParam("amount") double amount,
                           @RequestParam(value = "email", required = false) String email,
                           @RequestParam(value = "paymentType", required = false, defaultValue = "SchoolFees") String paymentType) {
        Student stud = SS.findStudent(studentId);
        if (stud == null) {
            model.addAttribute("msg", "Student not found.");
            return "Paystack/PaystackPay";
        }
        if (amount <= 0) {
            model.addAttribute("student", stud);
            model.addAttribute("studentId", studentId);
            model.addAttribute("outstandingBalance", PS.OutStandingbalance(studentId));
            model.addAttribute("msg", "Please enter an amount greater than zero.");
            return "Paystack/PaystackPay";
        }

        String payerEmail = (email != null && !email.isBlank()) ? email
                : (stud.getEmail() != null && !stud.getEmail().isBlank()) ? stud.getEmail()
                  : studentId + "@ubs-noemail.local"; // Paystack requires an email even when the payer has none on file

        String reference = paystackService.generateReference(studentId);

        Map<String, Object> metadata = new LinkedHashMap<>();
        metadata.put("studentId", studentId);
        metadata.put("paymentType", paymentType);
        metadata.put("initiatedBy", "UBS-web");

        // ---- Paystack fee borne by the payer ----
        // netPesewas    = what the parent typed, and what the student is credited with
        // chargePesewas = what Paystack actually charges the parent (net + Paystack fee)
        long netPesewas = Math.round(amount * 100);
        long chargePesewas = paystackService.calculateChargePesewas(netPesewas);
        metadata.put("netAmountPesewas", String.valueOf(netPesewas));
        metadata.put("feePesewas", String.valueOf(chargePesewas - netPesewas));

        try {
            PaystackInitResponse response = paystackService.initializeTransaction(payerEmail, chargePesewas, reference, metadata);
            if (response != null && response.isStatus() && response.getData() != null
                    && response.getData().getAuthorization_url() != null) {
                // Don't use "redirect:" (an HTTP 302 Location header) here. The reverse proxy in
                // front of the live server rewrites the host of Location headers, turning
                // https://checkout.paystack.com/<code> into https://ubs.uenr.edu.gh/<code> (404).
                // Handing off from an HTML page avoids that, because proxies don't rewrite bodies.
                model.addAttribute("authorizationUrl", response.getData().getAuthorization_url());
                return "Paystack/PaystackRedirect";
            }
            LOG.severe("Paystack initialize returned an unsuccessful response for student " + studentId);
            model.addAttribute("student", stud);
            model.addAttribute("studentId", studentId);
            model.addAttribute("outstandingBalance", PS.OutStandingbalance(studentId));
            model.addAttribute("msg", "Could not start the payment. Please try again shortly.");
            return "Paystack/PaystackPay";
        } catch (IllegalStateException notConfigured) {
            model.addAttribute("student", stud);
            model.addAttribute("studentId", studentId);
            model.addAttribute("msg", "Online payment is not yet configured. Please contact the school office.");
            return "Paystack/PaystackPay";
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Paystack initialize failed for student " + studentId, ex);
            model.addAttribute("student", stud);
            model.addAttribute("studentId", studentId);
            model.addAttribute("outstandingBalance", PS.OutStandingbalance(studentId));
            model.addAttribute("msg", "Could not reach the payment provider. Please try again.");
            return "Paystack/PaystackPay";
        }
    }

    @GetMapping("/callback")
    public String callback(Model model, @RequestParam("reference") String reference) {
        try {
            PaystackVerifyResponse verify = paystackService.verifyTransaction(reference);

            if (verify == null || !verify.isSuccessfulCharge()) {
                model.addAttribute("success", false);
                model.addAttribute("reference", reference);
                model.addAttribute("msg", verify != null && verify.getData() != null
                        ? "Payment was not successful: " + verify.getData().getGateway_response()
                        : "Payment could not be verified.");
                return "Paystack/PaystackResult";
            }

            PaystackVerifyResponse.Data data = verify.getData();
            String studentId = data.getMetadata() != null ? String.valueOf(data.getMetadata().get("studentId")) : null;
            String paymentType = data.getMetadata() != null ? String.valueOf(data.getMetadata().getOrDefault("paymentType", "SchoolFees")) : "SchoolFees";
            String payerEmail = data.getCustomer() != null ? data.getCustomer().getEmail() : null;

            if (studentId == null || studentId.isBlank() || "null".equals(studentId)) {
                LOG.severe("Paystack callback for reference " + reference + " has no studentId in metadata");
                model.addAttribute("success", false);
                model.addAttribute("reference", reference);
                model.addAttribute("msg", "Payment was received but could not be matched to a student. Please contact the school office with this reference.");
                return "Paystack/PaystackResult";
            }

            // Credit the fee amount the parent entered, not the total that included Paystack's charge.
            double creditAmount = paystackService.resolveCreditAmount(
                    data.getMetadata() != null ? data.getMetadata().get("netAmountPesewas") : null,
                    data.getAmount());

            // Idempotent: if the webhook already credited this reference, this just fetches the same receipt.
            Receipt receipt = PS.SavePaystackPayment(studentId, creditAmount, reference, paymentType, payerEmail);

            model.addAttribute("success", true);
            model.addAttribute("reference", reference);
            model.addAttribute("amount", creditAmount);
            model.addAttribute("totalCharged", data.getAmountInBaseCurrency());
            model.addAttribute("studentId", studentId);
            model.addAttribute("receipt", receipt);
            Student stud = SS.findStudent(studentId);
            model.addAttribute("student", stud);
            if (stud != null) {
                model.addAttribute("outstandingBalance", PS.OutStandingbalance(studentId));
            }
            return "Paystack/PaystackResult";
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Paystack verify failed for reference " + reference, ex);
            model.addAttribute("success", false);
            model.addAttribute("reference", reference);
            model.addAttribute("msg", "We could not confirm this payment right now. If money was deducted, it will still be recorded automatically — please check back shortly or contact the school office.");
            return "Paystack/PaystackResult";
        }
    }
}