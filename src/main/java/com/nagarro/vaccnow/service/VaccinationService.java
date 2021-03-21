package com.nagarro.vaccnow.service;

import com.nagarro.vaccnow.model.PaymentStatus;
import com.nagarro.vaccnow.model.dto.BranchScheduleDto;

public interface VaccinationService {
    BranchScheduleDto scheduleVaccineSlot(Integer branchId, Integer patientId, Integer vaccineId,
                                          String date, String startTime) throws Exception;
    PaymentStatus madePayment(Integer scheduleId, String paymentMethod, String accountNumber) throws Exception;

    void sendEmail() throws Exception;
}
