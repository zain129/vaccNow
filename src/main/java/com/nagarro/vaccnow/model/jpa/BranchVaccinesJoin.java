package com.nagarro.vaccnow.model.jpa;

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

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Integer getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(Integer vaccineId) {
        this.vaccineId = vaccineId;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public Integer getVaccineQty() {
        return vaccineQty;
    }

    public void setVaccineQty(Integer vaccineQty) {
        this.vaccineQty = vaccineQty;
    }
}
