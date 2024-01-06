package com.gitlab.emradbuba.learning.jpa.basicjpacrud.service;

import com.gitlab.emradbuba.learning.jpa.basicjpacrud.api.model.request.PostNewPersonRequest;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.api.model.request.PutExistingPersonRequest;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.exceptions.PersonNotFoundAppException;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.model.Person;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.persistance.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@AllArgsConstructor
public class PersonService {
    private PersonRepository personRepository;

    public Person getPerson(Long personId) {
        return personRepository
                .findById(personId)
                .orElseThrow(() -> new PersonNotFoundAppException("No person found for given personId: " + personId));
    }

    @Transactional
    public Person createNewPerson(PostNewPersonRequest postNewPersonRequest) {
        Person newPerson = new Person();
        newPerson.setFirstName(postNewPersonRequest.getFirstName());
        newPerson.setSurname(postNewPersonRequest.getSurname());
        newPerson.setDateOfBirth(postNewPersonRequest.getDateOfBirth());
        newPerson.setCertificates(Collections.emptySet());
        return personRepository.save(newPerson);
    }

    @Transactional
    public Person updateExistingPerson(Long personId, PutExistingPersonRequest putExistingPersonRequest) {
        Person existingPerson = personRepository.findById(personId)
                .orElseThrow(() -> new PersonNotFoundAppException("No person found for given personId: " + personId));
        existingPerson.setFirstName(putExistingPersonRequest.getFirstName());
        existingPerson.setSurname(putExistingPersonRequest.getSurname());
        existingPerson.setDateOfBirth(putExistingPersonRequest.getDateOfBirth());
        return personRepository.save(existingPerson);
    }

    @Transactional
    public void deletePerson(Long personId) {
        if (!personRepository.existsById(personId)) {
            throw new PersonNotFoundAppException("No person found for given personId: " + personId);
        }
        personRepository.deleteById(personId);
    }
}
