package com.gitlab.emradbuba.learning.learningproject.demo.validation.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Business model
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Wallet {
    private String walletName;
    private String systemId;
    private String creditCardSystemIds;
    private String drivingLicenseSystemId;
}
