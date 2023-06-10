package com.lmpay.starter.dto;

import jakarta.json.Json;
import lombok.Data;

@Data
public class LMResponse {
    Integer LMResponseMessageCode;
    String LMResponseMessage;
    String VenderResponse;
}
