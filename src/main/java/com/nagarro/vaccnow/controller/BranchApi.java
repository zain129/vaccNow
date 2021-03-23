package com.nagarro.vaccnow.controller;

import com.nagarro.vaccnow.service.BranchService;
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
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/branch")
@Validated
public class BranchApi {

    @Autowired
    private BranchService branchService;

    /**
     * Get a list of all branches
     *
     * @return
     */
    @GetMapping("/allBranches")
    public ResponseEntity allBranches() {
        Map result = new HashMap();
        result.put("status", HttpStatus.OK.value());
        result.put("data", branchService.getAllBranches());
        return ResponseEntity.ok(result);
    }

    /**
     * Get a list of vaccines per branch.
     *
     * @return
     */
    @GetMapping("/branchVaccines")
    public ResponseEntity vaccinesPerBranch() {
        Map result = new HashMap();
        result.put("status", HttpStatus.OK.value());
        result.put("data", branchService.getAvailableVaccincesPerBranch());
        return ResponseEntity.ok(result);
    }

    /**
     * Get a specific availability by branch
     *
     * @param date:      Format 'ddMMyyyy'
     * @param startTime: Format 'HHmm'
     * @param endTime:   Format 'HHmm'
     * @return
     */
    @GetMapping("/searchBranchAvailability")
    public ResponseEntity searchBranchAvailability(
            @RequestParam(value = "date") @NotBlank String date,
            @RequestParam(value = "startTime") @NotBlank String startTime,
            @RequestParam(value = "endTime") @NotBlank String endTime) throws ParseException {
        Map result = new HashMap();
        result.put("status", HttpStatus.OK.value());
        result.put("data", branchService.getBranchAvailability(date, startTime, endTime));
        return ResponseEntity.ok(result);
    }

    /**
     * Get available time for a branch.
     *
     * @param branchId: Integer @NotNull
     * @param date:     Format 'ddMMyyyy'
     * @return
     */
    @GetMapping("/branchAvailabilityByDate")
    public ResponseEntity branchAvailabilityByDate(
            @RequestParam(value = "branchId") @NotNull Integer branchId,
            @RequestParam(value = "date") @NotBlank String date) throws ParseException {
        Map result = new HashMap();
        result.put("status", HttpStatus.OK.value());
        result.put("data", branchService.getBranchAvailabilityByDate(branchId, date));
        return ResponseEntity.ok(result);
    }

}
