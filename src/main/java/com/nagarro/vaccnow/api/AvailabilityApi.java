package com.nagarro.vaccnow.api;

import com.nagarro.vaccnow.model.dto.BranchAvailabiltyDto;
import com.nagarro.vaccnow.model.dto.BranchDto;
import com.nagarro.vaccnow.model.dto.BranchVaccinesDto;
import com.nagarro.vaccnow.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;
import java.text.ParseException;
import java.util.*;

@RestController
@RequestMapping("/api")
public class AvailabilityApi {

    @Autowired
    private BranchService branchService;

    /**
     * Get a list of all branches
     *
     * @return
     */
    @GetMapping("/availability/allBranches")
    public ResponseEntity allBranches() {
        List<BranchDto> allBranches = null;
        int status = HttpStatus.NOT_FOUND.value();

        try {
            allBranches = branchService.getAllBranches();
            status = HttpStatus.OK.value();
        } catch (Exception ex) {
            System.out.println("Exception in allBranches: " + ex.getLocalizedMessage());
            ex.printStackTrace();
        }

        Map result = new HashMap();
        result.put("status", status);
        result.put("branches", allBranches);
        return ResponseEntity.ok(result);
    }

    /**
     * Get a list of vaccines per branch.
     *
     * @return
     */
    @GetMapping("/availability/branchVaccines")
    public ResponseEntity vaccinesPerBranch() {
        List<BranchVaccinesDto> branchVaccinesDtos = null;
        int status = HttpStatus.NOT_FOUND.value();

        try {
            branchVaccinesDtos = branchService.getAvailableVaccincesPerBranch();
            status = HttpStatus.OK.value();
        } catch (Exception ex) {
            System.out.println("Exception in vaccinesPerBranch: " + ex.getLocalizedMessage());
            ex.printStackTrace();
        }

        Map result = new HashMap();
        result.put("status", status);
        result.put("vaccinesPerBranch", branchVaccinesDtos);
        return ResponseEntity.ok(result);
    }

    /**
     * Get a specific availability by branch
     *
     * @param startTime: start Date Time
     * @param endTime:   end Date Time
     * @return
     */
    @GetMapping("/searchBranchAvailability")
    public ResponseEntity searchBranchAvailability(@RequestParam(value = "date") String date,
                                                   @RequestParam(value = "startTime") String startTime,
                                                   @RequestParam(value = "endTime") String endTime) {
        List<BranchAvailabiltyDto> branchVaccinesDtos = null;
        int status = HttpStatus.NOT_FOUND.value();
        Map result = new HashMap();

        try {
            branchVaccinesDtos = branchService.getBranchAvailability(date, startTime, endTime);
            status = HttpStatus.OK.value();
            result.put("status", status);
            result.put("vaccinesPerBranch", branchVaccinesDtos);
        } catch (Exception ex) {
            if (ex.getClass().equals(ParseException.class)) {
                result.put("error",
                        "Please check date and time format. Date format 'ddMMyyyy' (01012020), Time Format 'HHmm' (1300)");
            }
            result.put("status", status);
            result.put("vaccinesPerBranch", null);
            System.out.println("Exception in vaccinesPerBranch: " + ex.getLocalizedMessage());
            ex.printStackTrace();
        }

        return ResponseEntity.ok(result);
    }

    /**
     * Get available time for a branch.
     *
     * @param availabilityDate: Time portion will be discarded and only date will be used to search for records.
     * @return
     */
    @GetMapping("/branchAvailabilityByDate/")
    public ResponseEntity branchAvailabilityByDate(@RequestParam(value = "availabilityDate") Date availabilityDate) {
        List<Time> times = new ArrayList<>();

        Map result = new HashMap();
//        result.put("Date", availabilityDate.getDate());
//        result.put("Time", times);
        return ResponseEntity.ok(result);
    }

}
