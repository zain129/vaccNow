package com.nagarro.vaccnow.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.nagarro.vaccnow.model.PaymentStatus;
import com.nagarro.vaccnow.model.dto.BranchScheduleDto;
import com.nagarro.vaccnow.model.jpa.*;
import com.nagarro.vaccnow.repo.*;
import com.nagarro.vaccnow.service.BranchService;
import com.nagarro.vaccnow.service.VaccinationService;
import com.nagarro.vaccnow.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service("vaccinationService")
@Slf4j
public class VaccinationServiceImpl implements VaccinationService {

    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private VaccineRepository vaccineRepository;
    @Autowired
    private VaccinationRepository vaccinationRepository;
    @Autowired
    private DoseRepository doseRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BranchService branchService;

    @Override
    public BranchScheduleDto scheduleVaccineSlot
            (Integer branchId, Integer patientId, Integer vaccineId, String date, String startTime) throws Exception {
        if (branchId == null) {
            throw new Exception("Cannot schedule. Branch Id is missing.");
        }
        if (patientId == null) {
            throw new Exception("Cannot schedule. Patient Id is missing.");
        }
        if (vaccineId == null) {
            throw new Exception("Cannot schedule. Vaccine Id is missing.");
        }
        if (date.isEmpty() || startTime.isEmpty()) {
            throw new Exception("Cannot schedule. Either date or time is missing.");
        }

        DateFormat format = new SimpleDateFormat("ddMMyyyy");
        Date dateObj = format.parse(date);
        format = new SimpleDateFormat("HHmm");
        Time startTimeObj = new Time(format.parse(startTime).getTime());
        Time endTimeObj = Util.calculateEndTime(startTimeObj);
        String endTime = endTimeObj.toString().replace(":", "").substring(0, 4);

        Branch branch = branchService.getBranchById(branchId);
        if (branch == null) {
            throw new Exception("Cannot schedule. Branch doesn't exists.");
        }
        if (branch.getOpenAt().after(startTimeObj)) {
            throw new Exception("Cannot schedule. Branch Opens at " + branch.getOpenAt().toString());
        }
        if (branch.getCloseAt().before(endTimeObj)) {
            throw new Exception("Cannot schedule. Branch Closes at " + branch.getCloseAt().toString());
        }

        Patient patient = patientRepository.getOne(patientId);
        if (patient == null) {
            throw new Exception("Cannot schedule. Patient doesn't exists.");
        }

        Vaccine vaccine = vaccineRepository.getOne(vaccineId);
        if (vaccine == null) {
            throw new Exception("Cannot schedule. Vaccine doesn't exists.");
        }

        List<Schedule> scheduleByPatientId = patient.getScheduleByPatientId();
        scheduleByPatientId = scheduleByPatientId.stream()
                .filter(obj -> (obj.getDate().getTime() == dateObj.getTime()))
                .collect(Collectors.toList());

        for (int i = 0; i < scheduleByPatientId.size(); i++) {
            Schedule schedule = scheduleByPatientId.get(i);
            if (schedule.getStartTime().equals(startTimeObj)
                    || schedule.getEndTime().equals(endTimeObj)
                    || Util.timeBetweenTwoTimeslots(schedule.getStartTime(), schedule.getEndTime(), startTime)
                    || Util.timeBetweenTwoTimeslots(schedule.getStartTime(), schedule.getEndTime(), endTime)) {
                throw new Exception("Cannot schedule. This slot has already been assigned.");
            }
        }

        Dose doseByBranchIdAndVaccineId = doseRepository.getDoseByBranchIdAndVaccineId(branchId, vaccineId);
        if (doseByBranchIdAndVaccineId == null || doseByBranchIdAndVaccineId.getDosageQuantity() == 0) {
            throw new Exception("Cannot schedule. Vaccine is not available.");
        }

        Schedule newSchedule = new Schedule();
        newSchedule.setScheduleId(scheduleRepository.getMaxId() + 1);
        newSchedule.setBranchByBranchId(branch);
        newSchedule.setPatientByPatientId(patient);
        newSchedule.setDate(dateObj);
        newSchedule.setStartTime(startTimeObj);
        newSchedule.setEndTime(endTimeObj);
        newSchedule.setCreated(new Timestamp(new Date().getTime()));
        newSchedule.setStatus(true);
        newSchedule = scheduleRepository.save(newSchedule);

        Integer dosageQuantity = doseByBranchIdAndVaccineId.getDosageQuantity();
        doseByBranchIdAndVaccineId.setDosageQuantity(dosageQuantity - 1);
        doseByBranchIdAndVaccineId = doseRepository.save(doseByBranchIdAndVaccineId);

        Vaccination vaccination = new Vaccination();
        vaccination.setDoseByDoseId(doseByBranchIdAndVaccineId);
        vaccination.setPatientByPatientId(patient);
        vaccination.setScheduleByScheduleId(newSchedule);
        vaccination.setVaccinationId(vaccinationRepository.getMaxId() + 1);
        vaccination.setDate(dateObj);
        vaccination.setCreated(new Timestamp(new Date().getTime()));
        vaccination = vaccinationRepository.save(vaccination);

        BranchScheduleDto branchScheduleDto = new BranchScheduleDto();
        branchScheduleDto.setBranchId(branchId);
        branchScheduleDto.setBranchName(branch.getName());
        branchScheduleDto.setPatientId(patientId);
        branchScheduleDto.setPatientName(patient.getName());
        branchScheduleDto.setVaccineId(vaccineId);
        branchScheduleDto.setVaccineName(vaccine.getName());
        branchScheduleDto.setScheduleId(newSchedule.getScheduleId());
        branchScheduleDto.setDate(dateObj);
        branchScheduleDto.setStartTime(startTimeObj);
        branchScheduleDto.setEndTime(endTimeObj);

        /*
         * Confirm scheduled vaccination by email
         */
        sendEmail();

        return branchScheduleDto;
    }

    @Override
    public PaymentStatus madePayment(Integer scheduleId, String paymentMethod, String accountNumber) throws Exception {
        if (scheduleId == null) {
            throw new Exception("Payment cannot be made. Schedule Id is missing.");
        }

        paymentMethod = paymentMethod.trim();
        accountNumber = accountNumber != null ? accountNumber.trim() : accountNumber;

        if (paymentMethod.isEmpty()) {
            throw new Exception("Payment cannot be made. Patient Method is missing.");
        }

        if (!paymentMethod.equalsIgnoreCase("cash") && accountNumber.isEmpty()) {
            throw new Exception("Payment cannot be made. Account Number is missing.");
        }

        if (!"cash".equalsIgnoreCase(paymentMethod) && !"credit".equalsIgnoreCase(paymentMethod) &&
                !"fawry".equalsIgnoreCase(paymentMethod)) {
            throw new Exception("Payment cannot be made. Only these methods are allowed; Cash, Credit, and Fawry.");
        }

        PaymentStatus paymentStatus = null;
        Schedule schedule = scheduleRepository.getOne(scheduleId);
        if (schedule == null) {
            throw new Exception("Payment cannot be made. Nothing is scheduled.");
        } else {
            List<Payment> paymentByScheduleId = schedule.getPaymentByScheduleId();

            if (paymentByScheduleId.isEmpty()) {
                Payment payment = new Payment();
                payment.setScheduleByScheduleId(schedule);
                payment.setMode(paymentMethod.toUpperCase());
                payment.setCreated(new Timestamp(new Date().getTime()));
                payment.setDate(new Date());
                payment.setAccountNumber(paymentMethod.equalsIgnoreCase("cash") ? "CASH" : accountNumber);
                payment.setPaymentId(paymentRepository.getMaxId() + 1);
                payment = paymentRepository.save(payment);

                Patient patient = schedule.getPatientByPatientId();
                paymentStatus = new PaymentStatus();
                paymentStatus.setAccountNumber(paymentMethod.equalsIgnoreCase("cash") ? "CASH" : accountNumber);
                paymentStatus.setPatientId(patient.getPatientId());
                paymentStatus.setPatientName(patient.getName());
                paymentStatus.setPaymentDate(payment.getCreated());
                paymentStatus.setPaymentMode(paymentMethod.toUpperCase());
                paymentStatus.setScheduleId(scheduleId);

                /*
                 * Generate Vaccination Certificate (PDF)
                 */
                createPDF(patient.getName(), schedule.getDate(), schedule.getStartTime(), schedule.getEndTime(),
                        payment.getMode(), payment.getAccountNumber());
            } else {
                throw new Exception("A Payment has already been made.");
            }
        }
        return paymentStatus;
    }

    private void createPDF(String name, Date date, Time startTime, Time endTime, String mode, String accountNumber)
            throws IOException, DocumentException {
        DateFormat dateFormat = new SimpleDateFormat("MMddyyyyHHmmss");
        Date today = new Date();
        String fileName = "vaccCert-" + name + "-" + dateFormat.format(today) + ".pdf";
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(fileName));
        document.open();

        Paragraph paragraph = new Paragraph();
        paragraph.add(new Paragraph(" "));
        paragraph.add(new Paragraph("Vaccination Certificate",
                new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD)));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        paragraph = new Paragraph();
        paragraph.setIndentationLeft(20);
        paragraph.add(new Paragraph(" "));
        paragraph.add(new Paragraph(" "));
        paragraph.add(new Paragraph("Name: " + name));
        paragraph.add(new Paragraph(" "));
        paragraph.add(new Paragraph("Date: " + new SimpleDateFormat("MMM dd, yyyy").format(date)));
        paragraph.add(new Paragraph(" "));
        paragraph.add(new Paragraph("Timeslot: " + startTime.toString() + " - " + endTime.toString()));
        paragraph.add(new Paragraph(" "));
        paragraph.add(new Paragraph("Payment Mode: " + mode));
        paragraph.add(new Paragraph(" "));
        if (!mode.equalsIgnoreCase("cash")) {
            paragraph.add(new Paragraph("Account Number: " + accountNumber));
            paragraph.add(new Paragraph(" "));
        }
        paragraph.add(new Paragraph(" "));
        paragraph.add(new Paragraph(" "));
        paragraph.add(new Paragraph(" "));
        paragraph.add(new Paragraph(" "));
        document.add(paragraph);

        paragraph = new Paragraph();
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(new Paragraph(
                "This is a system generated document and does not required any signature.",
                new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED)));

        document.addTitle("Vaccination Certificate - " + name);
        document.addSubject("Vaccination Certificate");
        document.addAuthor("VaccNow-Nagarro");
        document.addCreator("Zain Imtiaz");
        document.add(paragraph);
        document.close();

        log.info("\n******************** VACCINATION CERTIFICATE GENERATED ********************: \n");
        log.info("\n File Name: " + fileName + "\n\n");
    }

    public void sendEmail() throws Exception {
        List<Schedule> schedules = scheduleRepository.findAll();
        for (Schedule obj : schedules) {
            String msg = "Vaccination has been scheduled on " + obj.getDate() +
                    " from " + obj.getStartTime().toString() + " to " + obj.getEndTime().toString() +
                    " for " + obj.getPatientByPatientId().getName() +
                    " possessing national id " + obj.getPatientByPatientId().getNationalId() +
                    " at Branch " + obj.getBranchByBranchId().getName();
            log.info("\n******************** EMAIL SENT ********************: \n" + msg);
        }
    }
}
