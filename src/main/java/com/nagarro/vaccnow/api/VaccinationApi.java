package com.nagarro.vaccnow.api;

import com.nagarro.vaccnow.model.PaymentStatus;
import com.nagarro.vaccnow.model.dto.BranchScheduleDto;
import com.nagarro.vaccnow.service.VaccinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/vaccination")
public class VaccinationApi {

    @Autowired
    private VaccinationService vaccinationService;

    /**
     * Schedule a slot (15 minutes)
     *
     * @param branchId
     * @param patientId
     * @param vaccineId
     * @param date
     * @param startTime
     * @return
     */
    @GetMapping("/scheduleSlot")
    public ResponseEntity scheduleSlot(@RequestParam(value = "branchId") Integer branchId,
                                       @RequestParam(value = "patientId") Integer patientId,
                                       @RequestParam(value = "vaccineId") Integer vaccineId,
                                       @RequestParam(value = "date") String date,
                                       @RequestParam(value = "startTime") String startTime) {
        BranchScheduleDto branchScheduleDto = null;
        int status = HttpStatus.NOT_FOUND.value();
        Map result = new HashMap();

        try {
            branchScheduleDto =
                    vaccinationService.scheduleVaccineSlot(branchId, patientId, vaccineId, date, startTime);
            status = HttpStatus.OK.value();
        } catch (Exception ex) {
            System.out.println("Exception in scheduleSlot: " + ex.getLocalizedMessage());
            ex.printStackTrace();
            result.put("message", ex.getLocalizedMessage());
        }

        result.put("status", status);
        result.put("schedule", branchScheduleDto);
        return ResponseEntity.ok(result);
    }

    /**
     * Choose Payment Method Cash, Credit, or Fawry
     *
     * @param scheduleId
     * @param paymentMethod: Cash, Credit, Fawry
     * @param accountNumber
     * @return
     */
    @GetMapping("/payment")
    public ResponseEntity payment(@RequestParam(value = "scheduleId") Integer scheduleId,
                                  @RequestParam(value = "paymentMethod") String paymentMethod,
                                  @RequestParam(value = "accountNumber", required = false) String accountNumber) {
        PaymentStatus paymentStatus = null;
        int status = HttpStatus.NOT_FOUND.value();
        Map result = new HashMap();

        try {
            paymentStatus = vaccinationService.madePayment(scheduleId, paymentMethod, accountNumber);
            status = HttpStatus.OK.value();
        } catch (Exception ex) {
            System.out.println("Exception in scheduleSlot: " + ex.getLocalizedMessage());
            ex.printStackTrace();
            result.put("message", ex.getLocalizedMessage());
        }

        result.put("status", status);
        result.put("paymentStatus", paymentStatus);
        return ResponseEntity.ok(result);
    }

    /**
     * Confirm scheduled vaccination by email
     *
     * @return
     */
    @GetMapping("/sendEmail")
    public ResponseEntity sendEmail() {
        int status = HttpStatus.NOT_FOUND.value();
        String message = "Emails sent successfully";
        Map result = new HashMap();

        try {
            vaccinationService.sendEmail();
            status = HttpStatus.OK.value();
        } catch (Exception ex) {
            System.out.println("Exception in scheduleSlot: " + ex.getLocalizedMessage());
            ex.printStackTrace();
            result.put("message", ex.getLocalizedMessage());
        }

        result.put("status", status);
        result.put("message", message);
        return ResponseEntity.ok(result);
    }
}
