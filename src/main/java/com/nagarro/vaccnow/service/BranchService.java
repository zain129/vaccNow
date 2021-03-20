package com.nagarro.vaccnow.service;

import com.nagarro.vaccnow.model.dto.BranchAvailabiltyDto;
import com.nagarro.vaccnow.model.dto.BranchDto;
import com.nagarro.vaccnow.model.dto.BranchVaccinesDto;

import java.text.ParseException;
import java.util.List;

public interface BranchService {
    List<BranchDto> getAllBranches();
    List<BranchVaccinesDto> getAvailableVaccincesPerBranch();
    List<BranchAvailabiltyDto> getBranchAvailability(String date, String startTime, String endTime) throws ParseException;
}
