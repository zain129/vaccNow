package com.nagarro.vaccnow.model.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "patient")
public class Patient {
    @Id
    @Column(name = "patient_id")
    private Integer patientId;
    @Basic
    @Column(name = "name", nullable = false)
    private String name;
    @Basic
    @Column(name = "national_id", nullable = false)
    private String nationalId;
    @Basic
    @Column(name = "country")
    private String country;
    @OneToMany(mappedBy = "patientByPatientId")
    private List<Vaccination> vaccinationByPatientId;
    @OneToMany(mappedBy = "patientByPatientId")
    private List<Schedule> scheduleByPatientId;
//    @OneToMany(mappedBy = "patientByPatientId")
//    private List<Dose> doseByPatientId;
    @Basic
    @Column(name = "created", nullable = false, updatable = false)
    private Timestamp created;
    @Basic
    @Column(name = "updated")
    private Timestamp updated;
}
