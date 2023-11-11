package com.gitlab.emradbuba.learning.jpa.basicjpacrud.service;

import com.gitlab.emradbuba.learning.jpa.basicjpacrud.api.model.request.PostNewPersonRequest;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.api.model.request.PutExistingPersonRequest;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.exceptions.PersonNotFoundAppException;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.model.Person;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.persistance.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PersonService {
    private PersonRepository personRepository;

    public Person createNewPerson(PostNewPersonRequest postNewPersonRequest) {
        Person newPerson = new Person();
        newPerson.setFirstName(postNewPersonRequest.getFirstName());
        newPerson.setSurname(postNewPersonRequest.getSurname());
        newPerson.setDateOfBirth(postNewPersonRequest.getDateOfBirth());
        return personRepository.save(newPerson);
    }

    public void deletePerson(Long personId) {
        if (personRepository.existsById(personId)) {
            personRepository.deleteById(personId);
        }
        throw new PersonNotFoundAppException("No person found for given personId: " + personId);
    }

    public Person updateExistingPerson(PutExistingPersonRequest putExistingPersonRequest, Long personId) {
        Person existingPerson = personRepository.findById(personId)
                .orElseThrow(() -> new PersonNotFoundAppException("No person found for given personId: " + personId));
        existingPerson.setFirstName(putExistingPersonRequest.getFirstName());
        existingPerson.setSurname(putExistingPersonRequest.getSurname());
        existingPerson.setDateOfBirth(putExistingPersonRequest.getDateOfBirth());
        return personRepository.save(existingPerson);
    }

    public Person getPerson(Long personId) {
        return personRepository
                .findById(personId)
                .orElseThrow(() -> new PersonNotFoundAppException("No person found for given personId: " + personId));
    }
}
