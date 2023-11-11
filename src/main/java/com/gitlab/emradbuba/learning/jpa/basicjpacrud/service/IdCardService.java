package com.gitlab.emradbuba.learning.jpa.basicjpacrud.service;

import com.gitlab.emradbuba.learning.jpa.basicjpacrud.api.model.request.PostIdCardRequest;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.api.model.request.PutIdCardRequest;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.exceptions.IdCardAlreadyExistsAppException;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.exceptions.IdCardDoesNotExistAppException;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.exceptions.PersonNotFoundAppException;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.model.IdCard;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.model.Person;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.persistance.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class IdCardService {
    private final PersonRepository personRepository;

    public Optional<IdCard> getIdCardFromPerson(Long personId) {
        Person existingPerson = getPersonByIdOrThrow(personId);
        return Optional.of(existingPerson.getIdCard());
    }

    @Transactional
    public Person addIdCardToPerson(Long personId, PostIdCardRequest postIdCardDtoRequest) {
        Person existingPerson = getPersonByIdOrThrow(personId);
        IdCard existingIdCard = existingPerson.getIdCard();
        if (existingIdCard != null) {
            throw new IdCardAlreadyExistsAppException("IdCard already exists for person with id: " + personId);
        }
        IdCard idCard = new IdCard();
        idCard.setSerialNumber(postIdCardDtoRequest.getSerialNumber());
        idCard.setValidUntil(postIdCardDtoRequest.getValidUntil());
        idCard.setPublishedBy(postIdCardDtoRequest.getPublishedBy());
        idCard.setPerson(existingPerson);
        existingPerson.setIdCard(idCard);

        // idCardRepository.save(idCard); // <-- cascade.REMOVE ensures this...
        return personRepository.save(existingPerson);
    }

    @Transactional
    public Person updateIdCardInPerson(Long personId, PutIdCardRequest putIdCardDtoRequest) {
        Person existingPerson = getPersonByIdOrThrow(personId);
        IdCard existingIdCard = existingPerson.getIdCard();
        if (existingIdCard == null) {
            throw new IdCardDoesNotExistAppException("IdCard does not exist for person with id: " + personId);
        }
        IdCard idCard = existingPerson.getIdCard();
        idCard.setSerialNumber(putIdCardDtoRequest.getSerialNumber());
        idCard.setValidUntil(putIdCardDtoRequest.getValidUntil());
        idCard.setPublishedBy(putIdCardDtoRequest.getPublishedBy());
        idCard.setPerson(existingPerson);
        existingPerson.setIdCard(idCard);

        // idCardRepository.save(idCard); // <-- cascade.REMOVE ensures this...
        return personRepository.save(existingPerson);
    }

    @Transactional
    public Person deleteIdCardFromPerson(Long personId) {
        Person existingPerson = getPersonByIdOrThrow(personId);
        if (existingPerson.getIdCard() != null) {
            existingPerson.setIdCard(null); // orphanRemoval removes "orphaned" cardId
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