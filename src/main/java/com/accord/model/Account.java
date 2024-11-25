package com.accord.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * User : Tanvir Ahmed
 * Date: 11/20/2024.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder(builderClassName = "Builder", toBuilder = true)
@Table(name = "accounts")
public class Account extends BaseEntity{

    private static final long serialVersionUID = -6370322298846342045L;

    @Column(name = "account_number")
    private String ACCOUNT_NUMBER;

    @Column(name = "tran_amount")
    private BigDecimal TRX_AMOUNT;

    @Column(name = "description")
    private String DESCRIPTION;

    @Column(name = "tran_date")
    private LocalDate TRX_DATE;

    @Column(name = "tran_time")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime TRX_TIME;

    @Column(name = "customer_id")
    private String CUSTOMER_ID;



}
