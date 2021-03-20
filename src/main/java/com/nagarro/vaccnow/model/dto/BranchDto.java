package com.nagarro.vaccnow.model.dto;

import java.sql.Time;
import java.sql.Timestamp;

public class BranchDto {
    private Integer branchId;
    private String name;
    private Time openAt;
    private Time closeAt;
    //  private List<DoseDto> doses;
//  private List<ScheduleDto> schedules;
    private Timestamp created;
    private Timestamp updated;

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Time getOpenAt() {
        return openAt;
    }

    public void setOpenAt(Time openAt) {
        this.openAt = openAt;
    }

    public Time getCloseAt() {
        return closeAt;
    }

    public void setCloseAt(Time closeAt) {
        this.closeAt = closeAt;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }
}
