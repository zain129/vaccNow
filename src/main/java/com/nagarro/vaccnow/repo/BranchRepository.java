package com.nagarro.vaccnow.repo;

import com.nagarro.vaccnow.model.jpa.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Integer>, BranchCustomRepository {

//    @Query("SELECT new com.nagarro.vaccnow.model.jpa.BranchVaccinesJoin(v.vaccine_id, v.name as vaccine_name, b.branch_id, b.name as branch_name, d.dosage_quantity) FROM branch b, dose d, vaccine v WHERE b.branch_id = d.branch_id AND d.vaccine_id = v.vaccine_id")
//    public List<BranchVaccinesJoin> getBranchVaccinesJoin();
}
