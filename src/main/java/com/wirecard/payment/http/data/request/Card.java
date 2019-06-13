package com.wirecard.payment.http.data.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Card {
    @NotNull
    @Pattern(regexp = "\\D* \\D*", message = "Invalid holder's name. Must contain only letters. numbers and surname")
    private String holdersName;
    @NotNull
    @Size(min = 16, max = 16)
    @Pattern(regexp = "\\d+", message = "Invalid card's number. Only numbers are allowed")
    private String number;
    @NotNull
    private LocalDate expirationDate;
    @NotNull
    @Size(min = 3, max = 3)
    @Pattern(regexp = "\\d+", message = "Invalid cvv's number. Only numbers are allowed")
    private String cvv;
}
