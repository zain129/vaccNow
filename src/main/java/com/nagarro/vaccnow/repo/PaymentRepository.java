package com.nagarro.vaccnow.repo;

import com.nagarro.vaccnow.model.jpa.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    @Query("SELECT case when (MAX(p.paymentId) is null) then 0 else MAX(p.paymentId) end FROM Payment p" )
    Integer getMaxId();
}
