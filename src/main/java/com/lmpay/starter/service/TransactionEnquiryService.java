package com.lmpay.starter.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.lmpay.starter.config.AppProperties;
import com.lmpay.starter.dto.LMResponse;
import com.lmpay.starter.dto.TransactionEnquiryDto;
import com.lmpay.starter.dto.TranscationEnquiryResponse;
import com.lmpay.starter.enums.BankType;
import com.lmpay.starter.helper.Helper;
import com.lmpay.starter.model.PartnerTransaction;
import com.lmpay.starter.repository.PartnerTransactionRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Slf4j
@Service
public class TransactionEnquiryService {

    PartnerTransactionRepository partnerTransactionRepository;
    private final AppProperties appProperties;
    private final RestTemplate restTemplate;
    @Autowired
    public TransactionEnquiryService(AppProperties appProperties, RestTemplate restTemplate, PartnerTransactionRepository partnerTransactionRepository) {
        this.appProperties = appProperties;
        this.restTemplate = restTemplate;
        this.partnerTransactionRepository = partnerTransactionRepository;
    }

    public String getTransactionEnquiry(TransactionEnquiryDto transactionEnquiryDto) {
        try {
            String transactionNumber = transactionEnquiryDto.getLMTransactionRefNo();
            log.info("Initializing transaction enquiry for : ", transactionEnquiryDto.getBankType());
            if (transactionEnquiryDto.getBankType().equals(BankType.FEDERAL_BANK)) {
                if(transactionEnquiryDto.getLMPartnerRefNo() == null){
                    return "Invalid Payload!";
                }
                String federalRequest = prepareJsonForFederalBank(transactionEnquiryDto);
                return postFederalResponse(federalRequest);
            } else {
                PartnerTransaction partnerTransaction = partnerTransactionRepository.findByTransactionNo(transactionNumber);
                if(partnerTransaction != null){
                    String helloPaisaJson = prepareJsonRequestForHelloPaisa(transactionEnquiryDto, partnerTransaction);
                    return postHelloPaisaResponse(helloPaisaJson,appProperties.statusUrl , "POST");
                } else {
                    return "No Transaction Record Found";
                }
            }
        } catch (Exception e) {
            log.error("Error occurred while processing transaction in service ", e);
            return "FAILED TO SERVE";
        }
    }
    public String prepareJsonRequestForHelloPaisa(TransactionEnquiryDto transactionEnquiryDto, PartnerTransaction partnerTransaction) {
        JSONObject jsonObject = new JSONObject();

        JSONArray dcmRequestArray = new JSONArray();
        JSONObject dcmRequest = new JSONObject();
        dcmRequest.put("ReferenceNo", transactionEnquiryDto.getLMTransactionRefNo());
        if (transactionEnquiryDto.getLMPartnerRefNo() != null) {
            dcmRequest.put("DCode", transactionEnquiryDto.getLMPartnerRefNo());
        } else {
            dcmRequest.put("DCode", partnerTransaction.getPartnerReferenceNo1());
        }
        if (transactionEnquiryDto.getLMBankRouteCode() != null) {
            dcmRequest.put("BankRoute", transactionEnquiryDto.getLMBankRouteCode());
        } else {
            dcmRequest.put("BankRoute", partnerTransaction.getBankRoutingCode());
        }
        dcmRequest.put("RoutingCode", "DCM001");
        JSONObject dcmLogin = new JSONObject();
        dcmLogin.put("userId", "LMEXCHANGEUAE001");
        dcmRequest.put("DcmLogin", dcmLogin);

        dcmRequestArray.put(dcmRequest);
        jsonObject.put("DcmRequest", dcmRequestArray);
        return jsonObject.toString();
    }
    public String prepareJsonForFederalBank(TransactionEnquiryDto transactionEnquiryDto){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userid", appProperties.userId);
        jsonObject.put("password", appProperties.userPassword);
        jsonObject.put("sendercd", appProperties.sendercd);
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = today.format(formatter);
        jsonObject.put("Tran_Date", formattedDate);
        jsonObject.put("ReferenceId", transactionEnquiryDto.getLMTransactionRefNo());
        jsonObject.put("Org_ReferenceId", transactionEnquiryDto.getLMPartnerRefNo());
        jsonObject.put("TxnType", "FT");
        return jsonObject.toString();
    }
    private String postHelloPaisaResponse(String jsonResult, String url, String requestType) throws IOException {
        log.info("Request Payload is {} and url is {} ",jsonResult, url);
        Helper.init(appProperties, restTemplate);
        // Process the response
        ObjectMapper mapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
        Response response = Helper.httpBasicAuth(jsonResult, url, "POST");
        if (response.isSuccessful()) {
            try {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    String responseJson = responseBody.string();
                    LMResponse lmResponse = new LMResponse();
                    try {
                        JsonNode rootNode = mapper.readTree(responseJson);
                        JsonNode dcmResponseNode = rootNode.get("DcmResponse");

                        lmResponse.setLMResponseMessageCode(dcmResponseNode.get("responseCode").asInt());
                        lmResponse.setLMResponseMessage(dcmResponseNode.get("responseMessage").asText());


                        ObjectMapper nonNullMapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
                        nonNullMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                        return nonNullMapper.writeValueAsString(lmResponse);
                    } catch (Exception e) {
                        log.error("Error parsing response JSON: {}", e.getMessage());
                        return null;
                    }
                } else {
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }
    private String postFederalResponse(String jsonResult) throws IOException {
        Helper.init(appProperties, restTemplate);
        // Process the response
        ObjectMapper mapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
        log.info("Request payload is {} and url is : {}",jsonResult, appProperties.transactionUrl);
        String response = Helper.httpWithCertificates( appProperties.transactionUrl, jsonResult);
        return response;
    }
}