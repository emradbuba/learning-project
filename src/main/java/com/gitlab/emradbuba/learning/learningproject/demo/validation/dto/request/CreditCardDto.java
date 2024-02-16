package com.gitlab.emradbuba.learning.learningproject.demo.validation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreditCardDto {

    @NotBlank
    public String creditCardType;

    @NotBlank
    public String creditCardNumber;

    @NotNull
    public BigInteger creditLimit;
}
