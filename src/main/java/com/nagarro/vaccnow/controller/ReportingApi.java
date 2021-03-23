package com.nagarro.vaccnow.controller;

import com.nagarro.vaccnow.model.jpa.AppliedVaccination;
import com.nagarro.vaccnow.service.ReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reporting")
@Validated
public class ReportingApi {

    @Autowired
    private ReportingService reportingService;

    /**
     * Get a list of all applied vaccination per branch
     *
     * @return
     */
    @GetMapping("/vaccPerBranch")
    public ResponseEntity vaccPerBranch() {
        Map result = new HashMap();
        result.put("status", HttpStatus.OK.value());
        result.put("data", reportingService.getListOfAppliedVaccinationPerBranch());
        return ResponseEntity.ok(result);
    }

    /**
     * Get a list of all applied vaccination per day/period
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("/vaccPerDayPeriod")
    public ResponseEntity vaccPerDayPeriod(
            @RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) throws ParseException {
        List<AppliedVaccination> appliedVaccinationList = null;
        Map result = new HashMap();
        if (endDate == null) {
            appliedVaccinationList = reportingService.getListOfAppliedVaccinationPerDay(startDate);
        } else {
            appliedVaccinationList = reportingService.getListOfAppliedVaccinationPerPeriod(startDate, endDate);
        }
        result.put("status", HttpStatus.NOT_FOUND.value());
        result.put("data", appliedVaccinationList);
        return ResponseEntity.ok(result);
    }

    /**
     * Show all confirmed vaccinations over a time period
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("/confirmVaccOverPeriod")
    public ResponseEntity confirmVaccOverPeriod(
            @RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "endDate") String endDate) throws ParseException {
        Map result = new HashMap();
        result.put("status", HttpStatus.NOT_FOUND.value());
        result.put("data", reportingService.getListOfAppliedVaccinationPerPeriod(startDate, endDate));
        return ResponseEntity.ok(result);
    }
}
