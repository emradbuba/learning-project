package com.gitlab.emradbuba.learning.learningproject.demo.validation.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class CreateWalletResponseDto {
    private String contextId;
    // BasicInfo
    private String walletName;
    // SystemAcceptanceInfo:
    private String walletSystemId;
    private String creditCardSystemId;
    private String drivingLicenseSystemId;
}
