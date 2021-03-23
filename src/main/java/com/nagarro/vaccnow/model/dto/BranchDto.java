package com.nagarro.vaccnow.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BranchDto {
    private Integer branchId;
    private String name;
    private Time openAt;
    private Time closeAt;
    private Timestamp created;
    private Timestamp updated;
}
