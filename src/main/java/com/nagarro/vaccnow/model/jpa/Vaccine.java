package com.nagarro.vaccnow.model.jpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vaccine")
public class Vaccine {
    @Id
    @Column(name = "vaccine_id")
    private Integer vaccineId;
    @Basic
    @Column(name = "name", nullable = false)
    private String name;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "manufacturer")
    private String manufacturer;
    @OneToMany(mappedBy = "vaccineByVaccineId")
    private List<Dose> doseByVaccineId;
    @Basic
    @Column(name = "created", nullable = false, updatable = false)
    private Timestamp created;
    @Basic
    @Column(name = "updated", nullable = true)
    private Timestamp updated;
}
