package com.gitlab.emradbuba.learning.learningproject.service;

import com.gitlab.emradbuba.learning.learningproject.BusinessIdUtils;
import com.gitlab.emradbuba.learning.learningproject.service.commands.AddNewPersonCommand;
import com.gitlab.emradbuba.learning.learningproject.service.commands.UpdateExistingPersonCommand;
import com.gitlab.emradbuba.learning.learningproject.service.converters.PersonEntityToPersonConverter;
import com.gitlab.emradbuba.learning.learningproject.exceptions.PersonNotFoundAppException;
import com.gitlab.emradbuba.learning.learningproject.model.Person;
import com.gitlab.emradbuba.learning.learningproject.persistance.PersonRepository;
import com.gitlab.emradbuba.learning.learningproject.persistance.model.PersonEntity;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@AllArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final PersonEntityToPersonConverter personEntityToPersonConverter;

    public Person getPerson(String personBusinessId) {
        PersonEntity personEntity = personRepository
                .findByBusinessId(personBusinessId)
                .orElseThrow(() -> new PersonNotFoundAppException("No person found for given personBusinessId: " + personBusinessId));
        return personEntityToPersonConverter.fromPersonEntity(personEntity);
    }

    @Transactional
    public Person storeNewPerson(AddNewPersonCommand addNewPersonCommand) {
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
    public Person updateExistingPerson(UpdateExistingPersonCommand updateExistingPersonCommand) {
        String businessId = updateExistingPersonCommand.getBusinessId();
        PersonEntity existingPersonEntity = personRepository.findByBusinessId(businessId)
                .orElseThrow(() -> new PersonNotFoundAppException("No person found for given businessId: " + businessId));
        existingPersonEntity.setFirstName(updateExistingPersonCommand.getFirstName());
        existingPersonEntity.setSurname(updateExistingPersonCommand.getSurname());
        existingPersonEntity.setDateOfBirth(updateExistingPersonCommand.getDateOfBirth());

        PersonEntity personEntity = personRepository.save(existingPersonEntity);
        return personEntityToPersonConverter.fromPersonEntity(personEntity);
    }

    @Transactional
    public void deletePerson(String businessId) {
        PersonEntity existingPersonEntity = personRepository.findByBusinessId(businessId)
                .orElseThrow(() -> new PersonNotFoundAppException("No person found for given businessId: " + businessId));
        personRepository.deleteById(existingPersonEntity.getId());
    }

    public long countPerson() {
        return personRepository.count();
    }
}
