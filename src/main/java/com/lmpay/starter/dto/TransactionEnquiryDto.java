package com.lmpay.starter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmpay.starter.enums.BankType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TransactionEnquiryDto {
    @NotNull
    @JsonProperty("LMTransactionRefNo")
    String  LMTransactionRefNo;

    @JsonProperty("LMPartnerRefNo")
    @Size(min=2, message="LMPartnerRefNo should have atleast 2 characters")
    String LMPartnerRefNo;

    @JsonProperty("LMBankRouteCode")
    @Size(min=2, message="LMBankRouteCode should have atleast 2 characters")
    String LMBankRouteCode;

    @NotNull
    @JsonProperty("BankType")
    BankType BankType;
}
