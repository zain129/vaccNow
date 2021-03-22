package com.nagarro.vaccnow.model.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BranchVaccinesJoin implements java.io.Serializable {
    private Integer branchId;
    private String branchName;
    private Integer vaccineId;
    private String vaccineName;
    private Integer vaccineQty;

    public BranchVaccinesJoin(Integer vaccineId, String vaccineName, Integer branchId, String branchName, Integer vaccineQty) {
        this.vaccineId = vaccineId;
        this.vaccineName = vaccineName;
        this.branchId = branchId;
        this.branchName = branchName;
        this.vaccineQty = vaccineQty;
    }

}
