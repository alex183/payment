package com.wirecard.payment.http.data.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Buyer {
    private String name;
    private String email;
    private String cpf;
}
