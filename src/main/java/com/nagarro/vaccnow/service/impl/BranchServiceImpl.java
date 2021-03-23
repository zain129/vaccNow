package com.nagarro.vaccnow.service.impl;

import com.nagarro.vaccnow.model.dto.BranchAvailabiltyDto;
import com.nagarro.vaccnow.model.dto.BranchDto;
import com.nagarro.vaccnow.model.dto.BranchVaccinesDto;
import com.nagarro.vaccnow.model.jpa.Branch;
import com.nagarro.vaccnow.model.jpa.BranchVaccinesJoin;
import com.nagarro.vaccnow.model.jpa.Schedule;
import com.nagarro.vaccnow.repo.BranchRepository;
import com.nagarro.vaccnow.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        List<BranchVaccinesJoin> branchVaccines = branchRepository.getBranchVaccinesJoin();

        for (int i = 0; i < branchVaccines.size(); i++) {
            BranchVaccinesJoin branchVaccinesJoin = branchVaccines.get(i);

            BranchVaccinesDto branchVaccinesDto = new BranchVaccinesDto();
            branchVaccinesDto.setBranchId(branchVaccinesJoin.getBranchId());
            branchVaccinesDto.setBranchName(branchVaccinesJoin.getBranchName());
            branchVaccinesDto.setVaccineId(branchVaccinesJoin.getVaccineId());
            branchVaccinesDto.setVaccineName(branchVaccinesJoin.getVaccineName());
            branchVaccinesDto.setVaccineQty(branchVaccinesJoin.getVaccineQty());

            result.add(branchVaccinesDto);
        }
        return result;
    }

    @Override
    public List<BranchAvailabiltyDto> getBranchAvailability(String date, String startTime, String endTime) throws ParseException {
        List<BranchAvailabiltyDto> result = new ArrayList<>();
        try {
            if (date == null || date.isEmpty() || startTime == null
                    || startTime.isEmpty() || endTime == null || endTime.isEmpty()) {
                throw new ParseException("Please provide the 3 required params (date, startTime, endTime).", 1);
            }

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
//                0323 4422123
                result.add(branchAvailabilty);
            }
        } catch (ParseException e) {
            throw new ParseException(
                    "Please provide the 3 required params (date, startTime, endTime). Also, please check date and time format. " +
                            "Date format 'ddMMyyyy' (01012020), Time Format 'HHmm' (1300)",
                    e.getErrorOffset());
        }
        return result;
    }

    @Override
    public List<BranchAvailabiltyDto> getBranchAvailabilityByDate(Integer branchId, String date) throws ParseException {
        List<BranchAvailabiltyDto> result = new ArrayList<>();
        try {
            if (date == null || date.isEmpty()) {
                throw new ParseException("", 1);
            }
            DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
            Date parseDate = dateFormat.parse(date);
            Timestamp dated = new Timestamp(parseDate.getTime());

            Branch branch = branchRepository.getOne(branchId);
            BranchDto branchDto = new BranchDto();
            branchDto.setBranchId(branch.getBranchId());
            branchDto.setName(branch.getName());
            branchDto.setOpenAt(branch.getOpenAt());
            branchDto.setCloseAt(branch.getCloseAt());
            branchDto.setCreated(branch.getCreated());
            branchDto.setUpdated(branch.getUpdated());

            List<Schedule> scheduleByBranchId = branch.getScheduleByBranchId();
            if (!scheduleByBranchId.isEmpty()) {
                scheduleByBranchId = scheduleByBranchId.stream()
                        .filter(obj -> obj.getDate().equals(dated))
                        .collect(Collectors.toList());
            }

            if (scheduleByBranchId.isEmpty()) {
                BranchAvailabiltyDto branchAvailabilty = new BranchAvailabiltyDto();
                branchAvailabilty.setBranchId(branchDto.getBranchId());
                branchAvailabilty.setBranchName(branchDto.getName());
                branchAvailabilty.setDate(parseDate);
                branchAvailabilty.setStartTime(branchDto.getOpenAt());
                branchAvailabilty.setEndTime(branchDto.getCloseAt());
                branchAvailabilty.setAvailable(true);
                result.add(branchAvailabilty);
            } else {
                for (int j = 0; j < scheduleByBranchId.size(); j++) {
                    BranchAvailabiltyDto branchAvailabilty = new BranchAvailabiltyDto();
                    Schedule schedule = scheduleByBranchId.get(j);
                    if (schedule.getStartTime().after(branchDto.getOpenAt())) {
                        branchAvailabilty.setBranchId(branchDto.getBranchId());
                        branchAvailabilty.setBranchName(branchDto.getName());
                        branchAvailabilty.setDate(parseDate);
                        branchAvailabilty.setStartTime(branchDto.getOpenAt());
                        branchAvailabilty.setEndTime(schedule.getStartTime());
                        branchAvailabilty.setAvailable(true);

                        branchDto.setOpenAt(schedule.getEndTime());
                    }
                    result.add(branchAvailabilty);
                }
                if (branchDto.getOpenAt().before(branchDto.getCloseAt())) {
                    BranchAvailabiltyDto branchAvailabilty = new BranchAvailabiltyDto();
                    branchAvailabilty.setBranchId(branchDto.getBranchId());
                    branchAvailabilty.setBranchName(branchDto.getName());
                    branchAvailabilty.setDate(parseDate);
                    branchAvailabilty.setStartTime(branchDto.getOpenAt());
                    branchAvailabilty.setEndTime(branchDto.getCloseAt());
                    branchAvailabilty.setAvailable(true);
                    result.add(branchAvailabilty);
                }
            }
        } catch (ParseException e) {
            throw new ParseException(
                    "Please provide the date in a valid format 'ddMMyyyy' (01012020)", e.getErrorOffset());
        }
        return result;
    }

    @Override
    public Branch getBranchById(Integer branchId) {
        return branchRepository.getOne(branchId);
    }
}
