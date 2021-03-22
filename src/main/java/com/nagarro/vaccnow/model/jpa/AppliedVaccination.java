package com.nagarro.vaccnow.model.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
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
}
