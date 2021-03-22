package com.nagarro.vaccnow.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BranchVaccinesDto {
    private Integer branchId;
    private String branchName;
    private Integer vaccineId;
    private String vaccineName;
    private Integer vaccineQty;
}
