package com.nagarro.vaccnow.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BranchScheduleDto {
    private Integer branchId;
    private String branchName;
    private Integer patientId;
    private String patientName;
    private Integer vaccineId;
    private String vaccineName;
    private Integer scheduleId;
    private Date date;
    private Time startTime;
    private Time endTime;
}
