package com.system.journal.common.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@NoArgsConstructor
public class ErrorResponse{
    private String message;
    private List<String> details;
    private HttpStatus status;

    public ErrorResponse(String errMsg, HttpStatus httpStatus) {
        this.message = errMsg;
        this.status = httpStatus;
    }
    public ErrorResponse(String errMsg,List<String> details,HttpStatus httpStatus) {
        this.message = errMsg;
        this.details =details;
        this.status = httpStatus;
    }
}
