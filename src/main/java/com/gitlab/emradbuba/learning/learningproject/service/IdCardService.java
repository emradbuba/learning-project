package com.gitlab.emradbuba.learning.learningproject.service;

import com.gitlab.emradbuba.learning.learningproject.exceptions.IdCardAlreadyExistsAppException;
import com.gitlab.emradbuba.learning.learningproject.exceptions.IdCardDoesNotExistAppException;
import com.gitlab.emradbuba.learning.learningproject.exceptions.PersonNotFoundAppException;
import com.gitlab.emradbuba.learning.learningproject.model.IdCard;
import com.gitlab.emradbuba.learning.learningproject.model.Person;
import com.gitlab.emradbuba.learning.learningproject.persistance.IdCardRepository;
import com.gitlab.emradbuba.learning.learningproject.persistance.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class IdCardService {
    private final PersonRepository personRepository;
    private final IdCardRepository idCardRepository;

    public Optional<IdCard> getIdCardFromPerson(Long personId) {
        Person existingPerson = getPersonByIdOrThrow(personId);
        return Optional.ofNullable(existingPerson.getIdCard());
    }

    @Transactional
    public Person addIdCardToPerson(Long personId, IdCard idCard) {
        Person existingPerson = getPersonByIdOrThrow(personId);
        IdCard existingIdCard = existingPerson.getIdCard();
        if (existingIdCard != null) {
            throw new IdCardAlreadyExistsAppException("IdCard already exists for person with id: " + personId);
        }
        IdCard newIdCard = new IdCard();
        newIdCard.setSerialNumber(idCard.getSerialNumber());
        newIdCard.setValidUntil(idCard.getValidUntil());
        newIdCard.setPublishedBy(idCard.getPublishedBy());
        newIdCard.setPerson(existingPerson);

        existingPerson.setIdCard(newIdCard);
        return personRepository.save(existingPerson); // <-- Cascade.PERSIST will also persist a newly created IdCard
        // entity
    }

    @Transactional
    public Person updateIdCardInPerson(Long personId, IdCard idCardFromRequest) {
        Person existingPerson = getPersonByIdOrThrow(personId);
        IdCard existingIdCard = existingPerson.getIdCard();
        if (existingIdCard == null) {
            throw new IdCardDoesNotExistAppException("IdCard does not exist for person with id: " + personId);
        }
        existingIdCard.setSerialNumber(idCardFromRequest.getSerialNumber());
        existingIdCard.setValidUntil(idCardFromRequest.getValidUntil());
        existingIdCard.setPublishedBy(idCardFromRequest.getPublishedBy());
        existingIdCard.setPerson(existingPerson);

        return personRepository.save(existingPerson);
    }

    @Transactional
    public Person deleteIdCardFromPerson(Long personId) {
        Person existingPerson = getPersonByIdOrThrow(personId);
        IdCard idCardToDelete = existingPerson.getIdCard();
        if (idCardToDelete != null) {
            existingPerson.setIdCard(null); // orphanRemoval "true" would remove idCard as it has no more references...
            idCardRepository.delete(idCardToDelete); // (without orphanRemoval this is necessary)
            return personRepository.save(existingPerson);
        }
        throw new PersonNotFoundAppException("No person found for given personId: " + personId);
    }

    private Person getPersonByIdOrThrow(Long personId) {
        return personRepository
                .findById(personId)
                .orElseThrow(() -> new PersonNotFoundAppException("No person found for given personId: " + personId));
    }
}