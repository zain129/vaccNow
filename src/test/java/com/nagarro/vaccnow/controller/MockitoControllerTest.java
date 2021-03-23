package com.nagarro.vaccnow.controller;

import com.nagarro.vaccnow.model.dto.BranchScheduleDto;
import com.nagarro.vaccnow.model.jpa.AppliedVaccination;
import com.nagarro.vaccnow.repo.BranchRepository;
import com.nagarro.vaccnow.repo.VaccinationRepository;
import com.nagarro.vaccnow.service.BranchService;
import com.nagarro.vaccnow.service.ReportingService;
import com.nagarro.vaccnow.service.VaccinationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MockitoControllerTest {

    @InjectMocks
    private BranchApi branchApi;
    @Mock
    private BranchRepository branchRepository;
    @Mock
    private BranchService branchService;

    @InjectMocks
    private VaccinationApi vaccinationApi;
    @Mock
    private VaccinationRepository vaccinationRepository;
    @Mock
    private VaccinationService vaccinationService;

    @InjectMocks
    private ReportingApi reportingApi;
    @Mock
    private ReportingService reportingService;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAllBranches() {
        ResponseEntity responseEntity = branchApi.allBranches();

        if (responseEntity.getStatusCode().value() == 200) {
            assertTrue(((Map) responseEntity.getBody()).get("data") instanceof List);
        } else {
            assertTrue(responseEntity.getStatusCode().value() == 404);
            assertTrue(responseEntity.getBody() == null);
        }
    }

    @Test
    public void testScheduleSlot() throws Exception {
        ResponseEntity responseEntity =
                vaccinationApi.scheduleSlot(1, 1, 1, "22032021", "1000");

        if (responseEntity.getStatusCode().value() == 200) {
            assertTrue(((Map) responseEntity.getBody()).get("data") instanceof BranchScheduleDto);
        } else {
            assertTrue(responseEntity.getStatusCode().value() == 404);
            assertTrue(responseEntity.getBody() == null);
        }
    }

    @Test
    public void testVaccPerBranch() {
        ResponseEntity responseEntity = reportingApi.vaccPerBranch();

        if (responseEntity.getStatusCode().value() == 200) {
            assertTrue(((Map) responseEntity.getBody()).get("data") instanceof BranchScheduleDto);
            if (!((List) ((Map) responseEntity.getBody()).get("data")).isEmpty())
                assertTrue(((List) ((Map) responseEntity.getBody()).get("data")).get(0) instanceof AppliedVaccination);
        } else {
            assertTrue(responseEntity.getStatusCode().value() == 404);
            assertTrue(responseEntity.getBody() == null);
        }
    }

}
