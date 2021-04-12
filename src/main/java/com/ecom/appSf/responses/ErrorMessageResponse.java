package com.ecom.appSf.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
public class ErrorMessageResponse {
    private Date timestamp;
    private String message;
}
