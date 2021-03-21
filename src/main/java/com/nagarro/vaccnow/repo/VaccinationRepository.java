package com.nagarro.vaccnow.repo;

import com.nagarro.vaccnow.model.jpa.Vaccination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccinationRepository extends JpaRepository<Vaccination, Integer> {
    @Query("SELECT CASE WHEN (MAX(v.vaccinationId) is null) then 0 else MAX(v.vaccinationId) end FROM Vaccination v")
    Integer getMaxId();
}
