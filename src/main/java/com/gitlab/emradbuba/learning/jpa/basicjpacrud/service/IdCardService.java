package com.gitlab.emradbuba.learning.jpa.basicjpacrud.service;

import com.gitlab.emradbuba.learning.jpa.basicjpacrud.api.model.request.PostIdCardRequest;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.api.model.request.PutIdCardRequest;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.exceptions.IdCardAlreadyExistsAppException;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.exceptions.IdCardDoesNotExistAppException;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.exceptions.PersonNotFoundAppException;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.model.IdCard;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.model.Person;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.persistance.IdCardRepository;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.persistance.PersonRepository;
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
        return personRepository.save(existingPerson); // <-- Cascade.PERSIST will also persist a newly created IdCard entity
    }

    @Transactional
    public Person updateIdCardInPerson(Long personId, PutIdCardRequest putIdCardDtoRequest) {
        Person existingPerson = getPersonByIdOrThrow(personId);
        IdCard existingIdCard = existingPerson.getIdCard();
        if (existingIdCard == null) {
            throw new IdCardDoesNotExistAppException("IdCard does not exist for person with id: " + personId);
        }
        existingIdCard.setSerialNumber(putIdCardDtoRequest.getSerialNumber());
        existingIdCard.setValidUntil(putIdCardDtoRequest.getValidUntil());
        existingIdCard.setPublishedBy(putIdCardDtoRequest.getPublishedBy());
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