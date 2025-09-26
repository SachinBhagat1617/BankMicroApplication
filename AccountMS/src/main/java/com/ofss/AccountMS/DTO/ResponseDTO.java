package com.ofss.AccountMS.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDTO {
    private String message;
    private int statusCode;
    private Object data;
}
