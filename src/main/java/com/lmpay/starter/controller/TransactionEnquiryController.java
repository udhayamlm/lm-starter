package com.lmpay.starter.controller;

import com.lmpay.starter.dto.TransactionEnquiryDto;
import com.lmpay.starter.dto.TranscationEnquiryResponse;
import com.lmpay.starter.service.TransactionEnquiryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class TransactionEnquiryController {
    @Autowired
    TransactionEnquiryService transactionEnquiryService;
    @PostMapping("/transaction-enquiry")
    public ResponseEntity<String> transactionEnquiry(@Valid @RequestBody TransactionEnquiryDto transactionEnquiryDto, BindingResult bindingResult){
        try{
            String transactionEnquiryResponse = transactionEnquiryService.getTransactionEnquiry(transactionEnquiryDto);
            return new ResponseEntity<>(transactionEnquiryResponse, HttpStatusCode.valueOf(200));
        }catch (Exception e){
            log.error("Error occurred while transaction Enquiry: ", e);
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(200));
        }
    }
}
