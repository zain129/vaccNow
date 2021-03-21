package com.nagarro.vaccnow.repo;

import com.nagarro.vaccnow.model.jpa.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
}
