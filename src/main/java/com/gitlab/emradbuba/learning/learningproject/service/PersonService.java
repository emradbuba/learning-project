package com.gitlab.emradbuba.learning.learningproject.service;

import com.gitlab.emradbuba.learning.learningproject.BusinessIdUtils;
import com.gitlab.emradbuba.learning.learningproject.model.Person;
import com.gitlab.emradbuba.learning.learningproject.persistance.PersonRepository;
import com.gitlab.emradbuba.learning.learningproject.persistance.model.PersonEntity;
import com.gitlab.emradbuba.learning.learningproject.service.commands.AddNewPersonCommand;
import com.gitlab.emradbuba.learning.learningproject.service.commands.UpdateExistingPersonCommand;
import com.gitlab.emradbuba.learning.learningproject.service.converters.PersonEntityToPersonConverter;
import com.gitlab.emradbuba.learning.learningproject.validation.ValidationUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static com.gitlab.emradbuba.learning.learningproject.exceptions.LPServiceErrorUtils.createLPPersonNotFoundException;

@Service
@AllArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final PersonEntityToPersonConverter personEntityToPersonConverter;

    public Person getPerson(final String personBusinessId) {
        ValidationUtils.validateUUID(personBusinessId);
        PersonEntity personEntity = personRepository.findByBusinessId(personBusinessId)
                .orElseThrow(() -> createLPPersonNotFoundException(personBusinessId)
                        .withSolutionTip("Maybe person was not yet create or removed?"));
        return personEntityToPersonConverter.fromPersonEntity(personEntity);
    }

    @Transactional
    public Person storeNewPerson(final AddNewPersonCommand addNewPersonCommand) {
        final String businessId = BusinessIdUtils.generateBusinessId();
        final PersonEntity newPersonEntity = new PersonEntity();
        newPersonEntity.setBusinessId(businessId);
        newPersonEntity.setFirstName(addNewPersonCommand.getFirstName());
        newPersonEntity.setSurname(addNewPersonCommand.getSurname());
        newPersonEntity.setDateOfBirth(addNewPersonCommand.getDateOfBirth());
        newPersonEntity.setCertificates(Collections.emptySet());
        newPersonEntity.setIdCard(null);

        PersonEntity createdPersonEntity = personRepository.save(newPersonEntity);
        return personEntityToPersonConverter.fromPersonEntity(createdPersonEntity);
    }

    @Transactional
    public Person updateExistingPerson(final UpdateExistingPersonCommand updateExistingPersonCommand) {
        String personBusinessId = updateExistingPersonCommand.getBusinessId();
        PersonEntity existingPersonEntity = personRepository.findByBusinessId(personBusinessId)
                .orElseThrow(() -> createLPPersonNotFoundException(personBusinessId)
                        .withSolutionTip("Make sure the person you want to update wasn't removed before"));
        existingPersonEntity.setFirstName(updateExistingPersonCommand.getFirstName());
        existingPersonEntity.setSurname(updateExistingPersonCommand.getSurname());
        existingPersonEntity.setDateOfBirth(updateExistingPersonCommand.getDateOfBirth());

        PersonEntity personEntity = personRepository.save(existingPersonEntity);
        return personEntityToPersonConverter.fromPersonEntity(personEntity);
    }

    @Transactional
    public void deletePerson(final String personBusinessId) {
        PersonEntity existingPersonEntity = personRepository.findByBusinessId(personBusinessId)
                .orElseThrow(() -> createLPPersonNotFoundException(personBusinessId)
                        .withSolutionTip("Maybe person was already removed by other system"));
        personRepository.deleteById(existingPersonEntity.getId());
    }

    public long countPerson() {
        return personRepository.count();
    }
}
