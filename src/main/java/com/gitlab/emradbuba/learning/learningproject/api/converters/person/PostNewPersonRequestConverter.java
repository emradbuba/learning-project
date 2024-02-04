package com.gitlab.emradbuba.learning.learningproject.api.converters.person;

import com.gitlab.emradbuba.learning.learningproject.api.model.request.PostNewPersonRequest;
import com.gitlab.emradbuba.learning.learningproject.model.Person;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static com.gitlab.emradbuba.learning.learningproject.api.converters.MappingUtils.normalizeString;

@Component
public class PostNewPersonRequestConverter {
    public Person toBusinessModel(PostNewPersonRequest postNewPersonRequest) {
        Person person = new Person();
        person.setFirstName(normalizeString(postNewPersonRequest.getFirstName()));
        person.setSurname(normalizeString(postNewPersonRequest.getSurname()));
        person.setDateOfBirth(postNewPersonRequest.getDateOfBirth());
        person.setCertificates(Collections.emptySet());
        return person;
    }
}
