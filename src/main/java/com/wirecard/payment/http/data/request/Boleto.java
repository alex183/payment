package com.wirecard.payment.http.data.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Boleto {
    @NotNull
    @Size(min = 44, max = 44)
    @Pattern(regexp = "\\d+", message = "Invalid boleto's number. Only numbers are allowed")
    private String number;
}