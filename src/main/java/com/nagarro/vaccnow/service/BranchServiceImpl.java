package com.nagarro.vaccnow.service;

import com.nagarro.vaccnow.model.dto.BranchAvailabiltyDto;
import com.nagarro.vaccnow.model.dto.BranchDto;
import com.nagarro.vaccnow.model.dto.BranchVaccinesDto;
import com.nagarro.vaccnow.model.jpa.Branch;
import com.nagarro.vaccnow.model.jpa.Dose;
import com.nagarro.vaccnow.model.jpa.Schedule;
import com.nagarro.vaccnow.model.jpa.Vaccine;
import com.nagarro.vaccnow.repo.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service("branchService")
public class BranchServiceImpl implements BranchService {

    @Autowired
    private BranchRepository branchRepository;

    @Override
    public List<BranchDto> getAllBranches() {
        List<Branch> branchList = branchRepository.findAll();
        List<BranchDto> allBranches = new ArrayList<>();

        for (Branch branch : branchList) {
            BranchDto branchDto = new BranchDto();
            branchDto.setBranchId(branch.getBranchId());
            branchDto.setName(branch.getName());
            branchDto.setOpenAt(branch.getOpenAt());
            branchDto.setCloseAt(branch.getCloseAt());
            branchDto.setCreated(branch.getCreated());
            branchDto.setUpdated(branch.getUpdated());
            allBranches.add(branchDto);
        }

        return allBranches;
    }

    @Override
    public List<BranchVaccinesDto> getAvailableVaccincesPerBranch() {
        List<BranchVaccinesDto> result = new ArrayList<>();
        List<Branch> branchList = branchRepository.findAll();
        for (int i = 0; i < branchList.size(); i++) {
            Branch branch = branchList.get(i);
            List<Dose> doseByBranchId = branch.getDoseByBranchId();
            for (int j = 0; j < doseByBranchId.size(); j++) {
                Dose dose = doseByBranchId.get(j);
                Vaccine vaccine = dose.getVaccineByVaccineId();
                BranchVaccinesDto branchVaccinesDto = new BranchVaccinesDto();

                branchVaccinesDto.setBranchId(branch.getBranchId());
                branchVaccinesDto.setBranchName(branch.getName());
                branchVaccinesDto.setVaccineId(vaccine.getVaccineId());
                branchVaccinesDto.setVaccineName(vaccine.getName());
                branchVaccinesDto.setVaccineQty(dose.getDosageQuantity());

                result.add(branchVaccinesDto);
            }
        }
        return result;
    }

    @Override
    public List<BranchAvailabiltyDto> getBranchAvailability(String date, String startTime, String endTime) throws ParseException {
        List<BranchAvailabiltyDto> result = new ArrayList<>();
        try {
            DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
            Date parseDate = dateFormat.parse(date);

            dateFormat = new SimpleDateFormat("HHmm");
            long timeStart = dateFormat.parse(startTime).getTime();
            Time startTimeObj = new Time(timeStart);

            long timeEnd = dateFormat.parse(endTime).getTime();
            Time endTimeObj = new Time(timeEnd);

            List<Branch> branchList = branchRepository.findAll();
            for (int i = 0; i < branchList.size(); i++) {
                Branch branch = branchList.get(i);
                BranchAvailabiltyDto branchAvailabilty = new BranchAvailabiltyDto();
                branchAvailabilty.setBranchId(branch.getBranchId());
                branchAvailabilty.setBranchName(branch.getName());
                branchAvailabilty.setDate(parseDate);
                branchAvailabilty.setStartTime(startTimeObj);
                branchAvailabilty.setEndTime(endTimeObj);

                List<Schedule> scheduleByBranchId = branch.getScheduleByBranchId();
                branchAvailabilty.setAvailable(scheduleByBranchId.isEmpty() ? false : true);

                result.add(branchAvailabilty);
            }
        } catch (ParseException e) {
            throw e;
        }
        return result;
    }
}
