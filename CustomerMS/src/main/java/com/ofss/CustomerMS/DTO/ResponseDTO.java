package com.ofss.CustomerMS.DTO;

import jakarta.validation.constraints.AssertFalse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ResponseDTO {
    private String message;
    private Object data;
}
