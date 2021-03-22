package com.nagarro.vaccnow.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentStatus {
    private Integer scheduleId;
    private Integer patientId;
    private String patientName;
    private String paymentMode;
    private String accountNumber;
    private Date paymentDate;
}
