package com.nagarro.vaccnow.controller;

import com.nagarro.vaccnow.service.VaccinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/vaccination")
@Validated
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
    public ResponseEntity scheduleSlot(
            @RequestParam(value = "branchId") @NotNull Integer branchId,
            @RequestParam(value = "patientId") @NotNull Integer patientId,
            @RequestParam(value = "vaccineId") @NotNull Integer vaccineId,
            @RequestParam(value = "date") @NotBlank String date,
            @RequestParam(value = "startTime") @NotBlank String startTime) throws Exception {
        Map result = new HashMap();
        result.put("status", HttpStatus.OK.value());
        result.put("data",
                vaccinationService.scheduleVaccineSlot(branchId, patientId, vaccineId, date, startTime));
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
    public ResponseEntity payment(
            @RequestParam(value = "scheduleId") @NotNull Integer scheduleId,
            @RequestParam(value = "paymentMethod") @NotBlank String paymentMethod,
            @RequestParam(value = "accountNumber", required = false) String accountNumber) throws Exception {
        Map result = new HashMap();
        result.put("status", HttpStatus.OK.value());
        result.put("data",
                vaccinationService.madePayment(scheduleId, paymentMethod, accountNumber));
        return ResponseEntity.ok(result);
    }

}
