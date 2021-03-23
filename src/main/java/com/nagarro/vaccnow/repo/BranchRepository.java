package com.nagarro.vaccnow.repo;

import com.nagarro.vaccnow.model.jpa.AppliedVaccination;
import com.nagarro.vaccnow.model.jpa.Branch;
import com.nagarro.vaccnow.model.jpa.BranchVaccinesJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Integer> {

    @Query(" SELECT new com.nagarro.vaccnow.model.jpa.BranchVaccinesJoin(v.vaccineId, v.name as vaccineName, " +
            " b.branchId, b.name as branchName, d.dosageQuantity) " +
            " FROM Branch b, Dose d, Vaccine v " +
            " WHERE b.branchId = d.branchByBranchId.branchId AND d.vaccineByVaccineId.vaccineId = v.vaccineId ")
    List<BranchVaccinesJoin> getBranchVaccinesJoin();

    @Query(" SELECT new com.nagarro.vaccnow.model.jpa.AppliedVaccination(b.branchId, b.name as branchName, " +
            " v.vaccineId, v.name as vaccineName, vn.date, s.startTime, s.endTime) " +
            " FROM Branch b, Schedule s, Vaccination vn, Dose d, Vaccine v " +
            " WHERE b.branchId = s.branchByBranchId.branchId AND s.scheduleId = vn.scheduleByScheduleId.scheduleId " +
            " AND vn.doseByDoseId.doseId = d.doseId AND d.vaccineByVaccineId.vaccineId = v.vaccineId " +
            " ORDER BY b.branchId ")
    List<AppliedVaccination> getAppVaccinationPerBranch();

    @Query(" SELECT new com.nagarro.vaccnow.model.jpa.AppliedVaccination(b.branchId, b.name as branchName, " +
            " v.vaccineId, v.name as vaccineName, vn.date, s.startTime, s.endTime) " +
            " FROM Branch b, Schedule s, Vaccination vn, Dose d, Vaccine v " +
            " WHERE b.branchId = s.branchByBranchId.branchId AND s.scheduleId = vn.scheduleByScheduleId.scheduleId " +
            " AND vn.doseByDoseId.doseId = d.doseId AND d.vaccineByVaccineId.vaccineId = v.vaccineId " +
            " AND vn.date = :date " +
            " ORDER BY b.branchId ")
    List<AppliedVaccination> getAppVaccinationPerDay(@Param("date") Date date);

    @Query(" SELECT new com.nagarro.vaccnow.model.jpa.AppliedVaccination(b.branchId, b.name as branchName, " +
            " v.vaccineId, v.name as vaccineName, vn.date, s.startTime, s.endTime) " +
            " FROM Branch b, Schedule s, Vaccination vn, Dose d, Vaccine v " +
            " WHERE b.branchId = s.branchByBranchId.branchId AND s.scheduleId = vn.scheduleByScheduleId.scheduleId " +
            " AND vn.doseByDoseId.doseId = d.doseId AND d.vaccineByVaccineId.vaccineId = v.vaccineId " +
            " AND vn.date BETWEEN :startDate AND :endDate " +
            " ORDER BY b.branchId ")
    List<AppliedVaccination> getAppVaccinationPerPeriod(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
