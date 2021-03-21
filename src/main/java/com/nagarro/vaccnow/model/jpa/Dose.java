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
@Table(name = "dose")
public class Dose {
    @Id
    @Column(name = "dose_id")
    private Integer doseId;
    @Basic
    @Column(name = "dosage_quantity", nullable = true)
    private Integer dosageQuantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", referencedColumnName = "branch_id", nullable = false)
    private Branch branchByBranchId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaccine_id", referencedColumnName = "vaccine_id", nullable = false)
    private Vaccine vaccineByVaccineId;
//    @ManyToOne
//    @JoinColumn(name = "patient_id", referencedColumnName = "patient_id", nullable = false)
//    private Patient patientByPatientId;
    @OneToMany(mappedBy = "doseByDoseId")
    private List<Vaccination> vaccinationByDoseId;
    @Basic
    @Column(name = "created", nullable = false, updatable = false)
    private Timestamp created;
    @Basic
    @Column(name = "updated", nullable = true)
    private Timestamp updated;
}
