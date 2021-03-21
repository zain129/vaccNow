package com.nagarro.vaccnow.service.impl;

import com.nagarro.vaccnow.model.PaymentStatus;
import com.nagarro.vaccnow.model.dto.BranchScheduleDto;
import com.nagarro.vaccnow.model.jpa.*;
import com.nagarro.vaccnow.repo.*;
import com.nagarro.vaccnow.service.BranchService;
import com.nagarro.vaccnow.service.VaccinationService;
import com.nagarro.vaccnow.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

//@Transactional
@Service("vaccinationService")
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
                    || Util.timeBetweenTwoTimeslots(schedule.getStartTime(), schedule.getEndTime(), endTime)
            ) {
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
        vaccination.setVaccinationId(vaccinationRepository.getMaxId() + 1);
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

        return branchScheduleDto;
    }

    @Override
    public PaymentStatus madePayment(Integer scheduleId, String paymentMethod, String accountNumber) throws Exception {
        PaymentStatus paymentStatus = null;
        if (scheduleId == null) {
            throw new Exception("Payment cannot be made. Schedule Id is missing.");
        }

        paymentMethod = paymentMethod.trim();
        accountNumber = accountNumber.trim();

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
            } else {
                throw new Exception("A Payment has already been made.");
            }
        }
        return paymentStatus;
    }

    @Override
    public void sendEmail() throws Exception {
        List<Schedule> schedules = scheduleRepository.findAll();

        for (Schedule obj : schedules) {
//            if(!obj.isEmailSent()) {
            String msg = "Vaccination has been scheduled on " + obj.getDate() +
                    " from " + obj.getStartTime().toString() + " to " + obj.getEndTime().toString() +
                    " for " + obj.getPatientByPatientId().getName() +
                    " possessing national id " + obj.getPatientByPatientId().getNationalId() +
                    " at Branch " + obj.getBranchByBranchId().getName();

            sendMail(msg);
//              obj.setEmailSent(true);
//              scheduleRepository.save(obj);
//            }
        }
    }

    private void sendMail(String msgBody) throws AddressException, MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", Util.getProperty("mail.smtp.auth"));
        props.put("mail.smtp.starttls.enable", Util.getProperty("mail.smtp.starttls.enable"));
        props.put("mail.smtp.host", Util.getProperty("mail.smtp.host"));
        props.put("mail.smtp.port", Util.getProperty("mail.smtp.port"));

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                try {
                    return new PasswordAuthentication(Util.getProperty("mail.sender.auth.username"),
                            Util.getProperty("mail.sender.auth.password"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(Util.getProperty("mail.sender.auth.username"), false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(Util.getProperty("mail.receiver.email")));
        msg.setSubject("Vaccination Scheduled");
        msg.setContent(msgBody, "text/html");
        msg.setSentDate(new Date());

//        MimeBodyPart messageBodyPart = new MimeBodyPart();
//        messageBodyPart.setContent(msgBody, "text/html");
//        Multipart multipart = new MimeMultipart();
//        multipart.addBodyPart(messageBodyPart);
//        MimeBodyPart attachPart = new MimeBodyPart();
//        attachPart.attachFile("/var/tmp/image19.png");
//        multipart.addBodyPart(attachPart);
//        msg.setContent(multipart);

        Transport.send(msg);
    }
}
