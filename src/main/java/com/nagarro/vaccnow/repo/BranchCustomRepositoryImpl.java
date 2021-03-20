package com.nagarro.vaccnow.repo;

import com.nagarro.vaccnow.model.dto.BranchVaccinesDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class BranchCustomRepositoryImpl implements BranchCustomRepository {

//    @PersistenceContext
//    EntityManager entityManager;
//
//    @Override
//    public List getVaccinesPerBranch() {
//        String sql = "SELECT v.vaccine_id, v.name as vaccine_name, b.branch_id, b.name as branch_name, d.dosage_quantity " +
//                " FROM branch as b, dose as d, vaccine as v" +
//                " WHERE b.branch_id = d.branch_id AND d.vaccine_id = v.vaccine_id";
//        Query query = entityManager.createNamedQuery(sql);
//        List result = query.getResultList();
//        return result;
//    }
}
