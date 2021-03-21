package com.nagarro.vaccnow.service.impl;

import com.nagarro.vaccnow.model.jpa.AppliedVaccination;
import com.nagarro.vaccnow.repo.BranchRepository;
import com.nagarro.vaccnow.repo.DoseRepository;
import com.nagarro.vaccnow.repo.ScheduleRepository;
import com.nagarro.vaccnow.repo.VaccinationRepository;
import com.nagarro.vaccnow.service.ReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ReportingServiceImpl implements ReportingService {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private VaccinationRepository vaccinationRepository;
    @Autowired
    private DoseRepository doseRepository;

    @Override
    public List<AppliedVaccination> getListOfAppliedVaccinationPerBranch() {
        List<AppliedVaccination> appVaccinationPerBranch = branchRepository.getAppVaccinationPerBranch();
        return appVaccinationPerBranch;
    }

    @Override
    public List<AppliedVaccination> getListOfAppliedVaccinationPerDay(String date) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        Date parsedDate = dateFormat.parse(date);

        List<AppliedVaccination> appVaccinationPerBranch = branchRepository.getAppVaccinationPerDay(parsedDate);
        return appVaccinationPerBranch;
    }

    @Override
    public List<AppliedVaccination> getListOfAppliedVaccinationPerPeriod(String startDate, String endDate) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        Date parsedStartDate = dateFormat.parse(startDate);
        Date parsedEndDate = dateFormat.parse(endDate);

        List<AppliedVaccination> appVaccinationPerBranch = branchRepository.getAppVaccinationPerPeriod(parsedStartDate, parsedEndDate);
        return appVaccinationPerBranch;
    }

    @Override
    public List<AppliedVaccination> getListOfConfirmedVaccinationOverTime(String startDate, String endDate) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        Date parsedStartDate = dateFormat.parse(startDate);
        Date parsedEndDate = dateFormat.parse(endDate);

        List<AppliedVaccination> appVaccinationPerBranch = branchRepository.getAppVaccinationPerPeriod(parsedStartDate, parsedEndDate);
        return appVaccinationPerBranch;
    }
}
