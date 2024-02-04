package com.gitlab.emradbuba.learning.learningproject.api.converters.idcard;

import com.gitlab.emradbuba.learning.learningproject.api.model.request.PutIdCardRequest;
import com.gitlab.emradbuba.learning.learningproject.model.IdCard;
import org.springframework.stereotype.Component;

import static com.gitlab.emradbuba.learning.learningproject.api.converters.MappingUtils.normalizeString;

@Component
public class PutIdCardRequestConverter {
    public IdCard toBusinessModel(PutIdCardRequest putIdCardRequest) {
        IdCard idCard = new IdCard();
        idCard.setSerialNumber(normalizeString(putIdCardRequest.getSerialNumber()));
        idCard.setPublishedBy(normalizeString(putIdCardRequest.getPublishedBy()));
        idCard.setValidUntil(putIdCardRequest.getValidUntil());
        return idCard;
    }
}
