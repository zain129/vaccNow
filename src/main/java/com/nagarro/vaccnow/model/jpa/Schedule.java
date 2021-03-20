package com.nagarro.vaccnow.model.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @Column(name = "schedule_id")
    private Integer scheduleId;
    @Basic
    @Column(name = "status", nullable = false)
    private Boolean status;
    @Basic
    @Column(name = "start_time", nullable = false)
    private Time startTime;
    @Basic
    @Column(name = "end_time", nullable = false)
    private Time endTime;
    @Basic
    @Column(name = "date", nullable = false)
    private Date date;
    @ManyToOne
    @JoinColumn(name = "branch_id", referencedColumnName = "branch_id", nullable = false)
    private Branch branchByBranchId;
    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "patient_id", nullable = false)
    private Patient patientByPatientId;
    @OneToMany(mappedBy = "scheduleByScheduleId")
    private List<Payment> paymentByScheduleId;
    @Basic
    @Column(name = "created", nullable = false, updatable = false)
    private Timestamp created;
    @Basic
    @Column(name = "updated")
    private Timestamp updated;
}
