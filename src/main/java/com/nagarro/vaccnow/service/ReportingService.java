package com.nagarro.vaccnow.service;

import com.nagarro.vaccnow.model.jpa.AppliedVaccination;

import java.text.ParseException;
import java.util.List;

public interface ReportingService {
    List<AppliedVaccination> getListOfAppliedVaccinationPerBranch();

    List<AppliedVaccination> getListOfAppliedVaccinationPerDay(String date) throws ParseException;

    List<AppliedVaccination> getListOfAppliedVaccinationPerPeriod(String startDate, String endDate) throws ParseException;

    List<AppliedVaccination> getListOfConfirmedVaccinationOverTime(String startDate, String endDate) throws ParseException;
}
