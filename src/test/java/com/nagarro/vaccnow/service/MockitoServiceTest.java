package com.nagarro.vaccnow.service;

import com.nagarro.vaccnow.model.PaymentStatus;
import com.nagarro.vaccnow.model.dto.BranchAvailabiltyDto;
import com.nagarro.vaccnow.model.dto.BranchVaccinesDto;
import com.nagarro.vaccnow.repo.BranchRepository;
import com.nagarro.vaccnow.repo.VaccinationRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MockitoServiceTest {

    @InjectMocks
    private BranchService branchService;
    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private VaccinationService vaccinationService;
    @Mock
    private VaccinationRepository vaccinationRepository;


    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAvailableVaccinesPerBranch() {
        List<BranchVaccinesDto> branchVaccinesJoin = branchService.getAvailableVaccincesPerBranch();
        assertTrue(branchVaccinesJoin.get(0).getBranchName().contains("COVID-19"));
    }

    @Test
    public void testGetBranchAvailabilityByDate() throws ParseException {
        List<BranchAvailabiltyDto> availabilityByDate = branchService.getBranchAvailabilityByDate(1, "22032021");
        assertEquals(availabilityByDate.get(0).getBranchId(), 1);
    }

    @Test
    public void testMadePayment() throws Exception {
        PaymentStatus payment = vaccinationService.madePayment(1, "CASH", null);
        assertEquals(payment.getScheduleId(), 100);
    }
}
