package com.nagarro.vaccnow.model.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "vaccination")
public class Vaccination {
    @Id
    @Column(name = "vaccination_id")
    private Integer vaccinationId;
    @ManyToOne
    @JoinColumn(name = "dose_id", referencedColumnName = "dose_id", nullable = false)
    private Dose doseByDoseId;
    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "patient_id", nullable = false)
    private Patient patientByPatientId;
    @ManyToOne
    @JoinColumn(name = "schedule_id", referencedColumnName = "schedule_id", nullable = false)
    private Schedule scheduleByScheduleId;
    @Basic
    @Column(name = "date", nullable = false)
    private Date date;
    @Basic
    @Column(name = "created", nullable = false, updatable = false)
    private Timestamp created;
    @Basic
    @Column(name = "updated", nullable = true)
    private Timestamp updated;
}
