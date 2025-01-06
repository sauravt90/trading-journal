package com.system.journal.common.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomResponseException  extends RuntimeException{
    private String message;
    private List<String> details;
    private HttpStatus status;

    public CustomResponseException(String message,HttpStatus status){
        this.message = message;
        this.status = status;
    }

}
