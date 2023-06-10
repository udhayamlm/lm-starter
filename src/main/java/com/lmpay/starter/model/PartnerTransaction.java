package com.lmpay.starter.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
public class PartnerTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "transaction_no", nullable = false)
    private String transactionNo;

    @Column(name = "partner_reference_no1", nullable = false, length = 20)
    private String partnerReferenceNo1;

    @Column(name = "partner_reference_no2", nullable = false, length = 20)
    private String partnerReferenceNo2;

    @Column(name = "transaction_type", length = 2)
    private String transactionType;

    @Column(name = "partner_name", length = 100)
    private String partnerName;

    @Column(name = "bank_name", length = 100)
    private String bankName;

    @Column(length = 20)
    private String processor;

    @Column(name = "bank_routing_code", length = 10)
    private String bankRoutingCode;

    @Column(name = "sending_currency", length = 2)
    private String sendingCurrency;

    @Column(name = "receiving_currency", length = 2)
    private String receivingCurrency;

    @Column(name = "transaction_date")
    private Timestamp transactionDate;

    @Column
    private Integer amount;

    @Column(name = "retry_attempts")
    private Integer retryAttempts;

    @Column(name = "partner_request", columnDefinition = "json")
    private String partnerRequest;

    @Column(name = "partner_response", columnDefinition = "json")
    private String partnerResponse;

    @Column(name = "transaction_status", length = 10)
    private String transactionStatus;

    @Column(name = "response_time", length = 10)
    private String responseTime;

    @Column(name = "created_on", nullable = false, updatable = false)
    private Timestamp createdOn;

    @Column(name = "created_by", length = 20)
    private String createdBy;

    @Column(name = "updated_on")
    private Timestamp updatedOn;

    @Column(name = "updated_by", length = 20)
    private String updatedBy;

    // Add constructors, getters, setters, and other methods as needed

}
