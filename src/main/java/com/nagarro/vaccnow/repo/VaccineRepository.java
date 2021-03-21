package com.nagarro.vaccnow.repo;

import com.nagarro.vaccnow.model.jpa.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, Integer> {
}
