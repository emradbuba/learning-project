package com.gitlab.emradbuba.learning.learningproject.api.converters.person;

import com.gitlab.emradbuba.learning.learningproject.api.model.request.PutExistingPersonRequest;
import com.gitlab.emradbuba.learning.learningproject.model.Person;
import org.springframework.stereotype.Component;

import static com.gitlab.emradbuba.learning.learningproject.api.converters.MappingUtils.normalizeString;

@Component
public class PutExistingPersonRequestConverter {
    public Person toBusinessModel(PutExistingPersonRequest putExistingPersonRequest) {
        Person personFromRequest = new Person();
        personFromRequest.setFirstName(normalizeString(putExistingPersonRequest.getFirstName()));
        personFromRequest.setSurname(normalizeString(putExistingPersonRequest.getSurname()));
        personFromRequest.setDateOfBirth(putExistingPersonRequest.getDateOfBirth());
        return personFromRequest;
    }
}
