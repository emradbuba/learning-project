package com.gitlab.emradbuba.learning.learningproject.api.converters.idcard;

import com.gitlab.emradbuba.learning.learningproject.api.model.request.PostIdCardRequest;
import com.gitlab.emradbuba.learning.learningproject.model.IdCard;
import org.springframework.stereotype.Component;

import static com.gitlab.emradbuba.learning.learningproject.api.converters.MappingUtils.normalizeString;

@Component
public class PostIdCardRequestConverter {
    public IdCard toBusinessModel(PostIdCardRequest postIdCardRequest) {
        IdCard idCard = new IdCard();
        idCard.setSerialNumber(normalizeString(postIdCardRequest.getSerialNumber()));
        idCard.setPublishedBy(normalizeString(postIdCardRequest.getPublishedBy()));
        idCard.setValidUntil(postIdCardRequest.getValidUntil());
        return idCard;
    }
}
