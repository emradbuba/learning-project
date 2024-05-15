package com.gitlab.emradbuba.learning.learningproject.service;

import com.gitlab.emradbuba.learning.learningproject.BusinessIdUtils;
import com.gitlab.emradbuba.learning.learningproject.libs.exceptions.core.LPExceptionErrorCode;
import com.gitlab.emradbuba.learning.learningproject.libs.exceptions.core.notfound.LPPersonNotFoundException;
import com.gitlab.emradbuba.learning.learningproject.model.Person;
import com.gitlab.emradbuba.learning.learningproject.persistance.PersonRepository;
import com.gitlab.emradbuba.learning.learningproject.persistance.model.PersonEntity;
import com.gitlab.emradbuba.learning.learningproject.service.commands.AddNewPersonCommand;
import com.gitlab.emradbuba.learning.learningproject.service.commands.UpdateExistingPersonCommand;
import com.gitlab.emradbuba.learning.learningproject.service.converters.PersonEntityToPersonConverter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@AllArgsConstructor
public class PersonService {
    public static final String PERSON_ID_NOT_FOUND_MSG = "No person found for given businessId: ";
    private final PersonRepository personRepository;
    private final PersonEntityToPersonConverter personEntityToPersonConverter;

    public Person getPerson(String personBusinessId) {
        PersonEntity personEntity = personRepository
                .findByBusinessId(personBusinessId)
                .orElseThrow(() -> new LPPersonNotFoundException(PERSON_ID_NOT_FOUND_MSG + personBusinessId)
                        .withPersonBusinessId(personBusinessId)
                        .withLPExceptionErrorCode(LPExceptionErrorCode.PERSON_ID_NOT_FOUND)
                        .withHttpStatusCodeValue(HttpStatus.NOT_FOUND.value())
                        .withSolutionTip("Make sure a person exists in this database"));
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
        String personBusinessId = updateExistingPersonCommand.getBusinessId();
        PersonEntity existingPersonEntity = personRepository.findByBusinessId(personBusinessId)
                .orElseThrow(() -> new LPPersonNotFoundException(PERSON_ID_NOT_FOUND_MSG + personBusinessId)
                        .withPersonBusinessId(personBusinessId)
                        .withLPExceptionErrorCode(LPExceptionErrorCode.PERSON_ID_NOT_FOUND)
                        .withHttpStatusCodeValue(HttpStatus.NOT_FOUND.value()));
        existingPersonEntity.setFirstName(updateExistingPersonCommand.getFirstName());
        existingPersonEntity.setSurname(updateExistingPersonCommand.getSurname());
        existingPersonEntity.setDateOfBirth(updateExistingPersonCommand.getDateOfBirth());

        PersonEntity personEntity = personRepository.save(existingPersonEntity);
        return personEntityToPersonConverter.fromPersonEntity(personEntity);
    }

    @Transactional
    public void deletePerson(String businessId) {
        PersonEntity existingPersonEntity = personRepository.findByBusinessId(businessId)
                .orElseThrow(() -> new LPPersonNotFoundException(PERSON_ID_NOT_FOUND_MSG + businessId)
                        .withPersonBusinessId(businessId)
                        .withLPExceptionErrorCode(LPExceptionErrorCode.PERSON_ID_NOT_FOUND)
                        .withHttpStatusCodeValue(HttpStatus.NOT_FOUND.value())
                );
        personRepository.deleteById(existingPersonEntity.getId());
    }

    public long countPerson() {
        return personRepository.count();
    }
}
