package com.gitlab.emradbuba.learning.learningproject.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class IdCard {
    private String businessId;
    private String serialNumber;
    private LocalDate validUntil;
    private String publishedBy;
}
