package com.gitlab.emradbuba.learning.learningproject.demo.validation.service;

import com.gitlab.emradbuba.learning.learningproject.demo.validation.dto.request.CreditCardDto;
import com.gitlab.emradbuba.learning.learningproject.demo.validation.dto.request.DrivingLicenseDto;
import com.gitlab.emradbuba.learning.learningproject.demo.validation.dto.request.PostCreateNewWalletDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final Validator beanValidator; // <-- Injected by spring...

    public Wallet saveWalletService(PostCreateNewWalletDto postCreateNewWalletService) {
        return Wallet.builder()
                .systemId(postCreateNewWalletService.getUuidNumber() + ":" + UUID.randomUUID())
                .walletName(postCreateNewWalletService.getWalletName())
                .creditCardSystemIds(buildCreditCardIds(postCreateNewWalletService))
                .drivingLicenseSystemId(UUID.randomUUID().toString())
                .build();
    }

    private static String buildCreditCardIds(PostCreateNewWalletDto postCreateNewWalletService) {
        StringBuilder sb = new StringBuilder();
        for (CreditCardDto creditCard : postCreateNewWalletService.getCreditCards()) {
            String type = creditCard.getCreditCardType();
            String cardNumberSubstring = creditCard.getCreditCardNumber().substring(0, 4);
            String cardSystemInfo = String.format("<Card '%s' | %s:%s>", type, cardNumberSubstring, UUID.randomUUID());
            sb.append(cardSystemInfo);
        }
        return sb.toString();
    }

    /*
     * Just a test method to demo injected validation usage...
     */
    private void testDrivingLicense(final DrivingLicenseDto drivingLicenseDto) {
        Set<ConstraintViolation<DrivingLicenseDto>> validationResult = beanValidator.validate(drivingLicenseDto);
        if (!validationResult.isEmpty()) {
            throw new ConstraintViolationException(validationResult);
        }
    }
}
