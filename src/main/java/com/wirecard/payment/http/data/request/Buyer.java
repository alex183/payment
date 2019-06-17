package com.wirecard.payment.http.data.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Buyer {
    @NotNull
    @Size(max = 50)
    @Pattern(regexp = "\\D* \\D*", message = "Invalid name. Must contain only letters and a surname")
    private String name;
    @NotNull
    @Size(max = 50)
    @Email(message = "Invalid e-mail")
    private String email;
    @CPF(message = "Invalid CPF")
    @NotNull
    private String cpf;
}
