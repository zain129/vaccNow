package com.nagarro.vaccnow.model.jpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @Column(name = "payment_id")
    private Integer paymentId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", referencedColumnName = "schedule_id", nullable = false)
    private Schedule scheduleByScheduleId;
    @Basic
    @Column(name = "mode", nullable = false)
    private String mode;        // Cash, Credit, Fawry
    @Basic
    @Column(name = "account_number", nullable = true)
    private String accountNumber;
    @Basic
    @Column(name = "date", nullable = false)
    private Date date;
    @Basic
    @Column(name = "created", nullable = false, updatable = false)
    private Timestamp created;
    @Basic
    @Column(name = "updated", nullable = true)
    private Timestamp updated;
}
