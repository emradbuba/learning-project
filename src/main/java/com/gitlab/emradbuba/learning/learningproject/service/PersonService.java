package com.gitlab.emradbuba.learning.learningproject.service;

import com.gitlab.emradbuba.learning.learningproject.exceptions.PersonNotFoundAppException;
import com.gitlab.emradbuba.learning.learningproject.model.Person;
import com.gitlab.emradbuba.learning.learningproject.persistance.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Person createNewPerson(Person newPerson) {
        return personRepository.save(newPerson);
    }

    @Transactional
    public Person updateExistingPerson(Long personId, Person personWithUpdateData) {
        Person existingPerson = personRepository.findById(personId)
                .orElseThrow(() -> new PersonNotFoundAppException("No person found for given personId: " + personId));
        existingPerson.setFirstName(personWithUpdateData.getFirstName());
        existingPerson.setSurname(personWithUpdateData.getSurname());
        existingPerson.setDateOfBirth(personWithUpdateData.getDateOfBirth());
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
