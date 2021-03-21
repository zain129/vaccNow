package com.nagarro.vaccnow.model.jpa;

import java.sql.Time;
import java.util.Date;

public class AppliedVaccination implements java.io.Serializable {
    private Integer branchId;
    private String branchName;
    private Integer vaccineId;
    private String vaccineName;
    private Date vaccinationDate;
    private Time startTime;
    private Time endTime;

    public AppliedVaccination(Integer branchId, String branchName, Integer vaccineId, String vaccineName,
                              Date vaccinationDate, Date startTime, Date endTime) {
        this.branchId = branchId;
        this.branchName = branchName;
        this.vaccineId = vaccineId;
        this.vaccineName = vaccineName;
        this.vaccinationDate = vaccinationDate;
        this.startTime = new Time(startTime.getTime());
        this.endTime = new Time(endTime.getTime());
    }

    public AppliedVaccination() {
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Integer getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(Integer vaccineId) {
        this.vaccineId = vaccineId;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public Date getVaccinationDate() {
        return vaccinationDate;
    }

    public void setVaccinationDate(Date vaccinationDate) {
        this.vaccinationDate = vaccinationDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
}
