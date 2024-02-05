package com.gitlab.emradbuba.learning.learningproject.service.converters;

import com.gitlab.emradbuba.learning.learningproject.model.IdCard;
import com.gitlab.emradbuba.learning.learningproject.persistance.model.IdCardEntity;
import org.springframework.stereotype.Component;

@Component
public class IdCardEntityToIdCardConverter {
    public IdCard fromIdCardEntity(IdCardEntity idCardEntity) {
        return IdCard.builder()
                .serialNumber(idCardEntity.getSerialNumber())
                .publishedBy(idCardEntity.getPublishedBy())
                .validUntil(idCardEntity.getValidUntil())
                .businessId(idCardEntity.getBusinessId())
                .build();
    }
}
