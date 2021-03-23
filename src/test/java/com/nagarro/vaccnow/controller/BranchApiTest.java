package com.nagarro.vaccnow.controller;

import com.nagarro.vaccnow.config.VaccNowApplication;
import com.nagarro.vaccnow.model.dto.BranchDto;
import com.nagarro.vaccnow.service.BranchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
@ContextConfiguration(classes = VaccNowApplication.class)
@RunWith(SpringRunner.class)
@WebMvcTest(BranchApi.class)
public class BranchApiTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BranchService branchService;

    @Test
    public void givenBranches_whenGetAllBranches_thenReturnJson()
            throws Exception {
        String start = "0700", end = "2100";
        DateFormat dateFormat = new SimpleDateFormat("HHmm");
        Date date = dateFormat.parse(start);
        Time opensAt = new Time(date.getTime());
        date = dateFormat.parse(end);
        Time closesAt = new Time(date.getTime());
        Timestamp today = new Timestamp(new Date().getTime());

        BranchDto branchDto = new BranchDto(1, "First Branch", opensAt, closesAt, today, null);
        List<BranchDto> allBranches = Arrays.asList(branchDto);

        given(branchService.getAllBranches()).willReturn(allBranches);

        mvc.perform(get("/availability/allBranches")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(branchDto.getName())));
    }

}
