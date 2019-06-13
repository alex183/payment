package com.wirecard.payment.http.data.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import com.wirecard.payment.exception.handler.data.Error;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private String userMessage;
    private String developerMessage;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private List<Error> errors;

}
