package com.nagarro.vaccnow.repo;

import com.nagarro.vaccnow.model.jpa.Dose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DoseRepository extends JpaRepository<Dose, Integer> {

    @Query("Select d From Dose d WHERE d.vaccineByVaccineId.vaccineId = :vaccineId AND d.branchByBranchId.branchId = :branchId")
    Dose getDoseByBranchIdAndVaccineId(@Param("branchId") Integer branchId, @Param("vaccineId") Integer vaccineId);

}
