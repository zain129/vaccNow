package com.nagarro.vaccnow.model.jpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "branch")
public class Branch {
    @Id
    @Column(name = "branch_id")
    private Integer branchId;
    @Basic
    @Column(name = "name", nullable = true)
    private String name;
    @Basic
    @Column(name = "open_at", nullable = false)
    private Time openAt;
    @Basic
    @Column(name = "close_at", nullable = false)
    private Time closeAt;
    @OneToMany(mappedBy = "branchByBranchId", fetch = FetchType.LAZY)
    private List<Dose> doseByBranchId;
    @OneToMany(mappedBy = "branchByBranchId", fetch = FetchType.LAZY)
    private List<Schedule> scheduleByBranchId;
    @Basic
    @Column(name = "created", nullable = false, updatable = false)
    private Timestamp created;
    @Basic
    @Column(name = "updated", nullable = true)
    private Timestamp updated;

}
