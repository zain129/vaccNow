package com.nagarro.vaccnow.api;

import com.nagarro.vaccnow.model.jpa.AppliedVaccination;
import com.nagarro.vaccnow.service.ReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reporting")
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
        List<AppliedVaccination> appliedVaccinationList = null;
        int status = HttpStatus.NOT_FOUND.value();
        Map result = new HashMap();

        try {
            appliedVaccinationList = reportingService.getListOfAppliedVaccinationPerBranch();
            status = HttpStatus.OK.value();
        } catch (Exception ex) {
            System.out.println("Exception in vaccPerBranch: " + ex.getLocalizedMessage());
            ex.printStackTrace();
            result.put("message", ex.getLocalizedMessage());
        }

        result.put("status", status);
        result.put("list", appliedVaccinationList);
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
            @RequestParam(value = "endDate", required = false) String endDate) {
        List<AppliedVaccination> appliedVaccinationList = null;
        int status = HttpStatus.NOT_FOUND.value();
        Map result = new HashMap();

        try {
            if (endDate == null) {
                appliedVaccinationList = reportingService.getListOfAppliedVaccinationPerDay(startDate);
            } else {
                appliedVaccinationList = reportingService.getListOfAppliedVaccinationPerPeriod(startDate, endDate);
            }
            status = HttpStatus.OK.value();
        } catch (Exception ex) {
            System.out.println("Exception in vaccPerDayPeriod: " + ex.getLocalizedMessage());
            ex.printStackTrace();
            result.put("message", ex.getLocalizedMessage());
        }

        result.put("status", status);
        result.put("list", appliedVaccinationList);
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
            @RequestParam(value = "endDate") String endDate) {
        List<AppliedVaccination> appliedVaccinationList = null;
        int status = HttpStatus.NOT_FOUND.value();
        Map result = new HashMap();

        try {
            appliedVaccinationList = reportingService.getListOfAppliedVaccinationPerPeriod(startDate, endDate);
            status = HttpStatus.OK.value();
        } catch (Exception ex) {
            System.out.println("Exception in confirmVaccOverPeriod: " + ex.getLocalizedMessage());
            ex.printStackTrace();
            result.put("message", ex.getLocalizedMessage());
        }

        result.put("status", status);
        result.put("list", appliedVaccinationList);
        return ResponseEntity.ok(result);
    }
}
