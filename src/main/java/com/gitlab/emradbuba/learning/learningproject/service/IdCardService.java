package com.gitlab.emradbuba.learning.learningproject.service;

import com.gitlab.emradbuba.learning.learningproject.BusinessIdUtils;
import com.gitlab.emradbuba.learning.learningproject.service.commands.AddNewIdCardCommand;
import com.gitlab.emradbuba.learning.learningproject.service.commands.UpdateExistingIdCardCommand;
import com.gitlab.emradbuba.learning.learningproject.service.converters.IdCardEntityToIdCardConverter;
import com.gitlab.emradbuba.learning.learningproject.service.converters.PersonEntityToPersonConverter;
import com.gitlab.emradbuba.learning.learningproject.exceptions.IdCardAlreadyExistsAppException;
import com.gitlab.emradbuba.learning.learningproject.exceptions.IdCardDoesNotExistAppException;
import com.gitlab.emradbuba.learning.learningproject.exceptions.PersonNotFoundAppException;
import com.gitlab.emradbuba.learning.learningproject.model.IdCard;
import com.gitlab.emradbuba.learning.learningproject.model.Person;
import com.gitlab.emradbuba.learning.learningproject.persistance.IdCardRepository;
import com.gitlab.emradbuba.learning.learningproject.persistance.PersonRepository;
import com.gitlab.emradbuba.learning.learningproject.persistance.model.IdCardEntity;
import com.gitlab.emradbuba.learning.learningproject.persistance.model.PersonEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class IdCardService {
    private final PersonRepository personRepository;
    private final IdCardRepository idCardRepository;
    private final IdCardEntityToIdCardConverter idCardEntityToIdCardConverter;
    private final PersonEntityToPersonConverter personEntityToPersonConverter;

    public Optional<IdCard> getIdCardFromPerson(String personBusinessId) {
        PersonEntity existingPersonEntity = getPersonByBusinessIdOrThrow(personBusinessId);
        return Optional.ofNullable(existingPersonEntity.getIdCard())
                .map(idCardEntityToIdCardConverter::fromIdCardEntity);
    }

    @Transactional
    public Person addIdCardToPerson(AddNewIdCardCommand addNewIdCardCommand) {
        String personBusinessId = addNewIdCardCommand.getPersonBusinessId();
        PersonEntity existingPersonEntity = getPersonByBusinessIdOrThrow(personBusinessId);
        IdCardEntity existingIdCardEntity = existingPersonEntity.getIdCard();
        if (existingIdCardEntity != null) {
            throw new IdCardAlreadyExistsAppException("IdCard already exists for person with businessId: " + personBusinessId);
        }
        String idCardBusinessId = BusinessIdUtils.generateBusinessId();
        IdCardEntity newIdCardEntity = new IdCardEntity();
        newIdCardEntity.setBusinessId(idCardBusinessId);
        newIdCardEntity.setSerialNumber(addNewIdCardCommand.getSerialNumber());
        newIdCardEntity.setValidUntil(addNewIdCardCommand.getValidUntil());
        newIdCardEntity.setPublishedBy(addNewIdCardCommand.getPublishedBy());
        newIdCardEntity.setPerson(existingPersonEntity);

        existingPersonEntity.setIdCard(newIdCardEntity);
        // vv Cascade.PERSIST will also persist a newly created IdCardEntity vv
        PersonEntity personEntityAfterChanges = personRepository.save(existingPersonEntity);
        return personEntityToPersonConverter.fromPersonEntity(personEntityAfterChanges);
    }

    @Transactional
    public Person updateIdCardInPerson(UpdateExistingIdCardCommand updateExistingIdCardCommand) {
        String personBusinessId = updateExistingIdCardCommand.getPersonBusinessId();
        PersonEntity existingPersonEntity = getPersonByBusinessIdOrThrow(personBusinessId);
        IdCardEntity existingIdCardEntity = Optional.ofNullable(existingPersonEntity.getIdCard())
                .orElseThrow(() -> new IdCardDoesNotExistAppException("IdCard does not exist for person with businessId:" +
                        " " + personBusinessId));
        existingIdCardEntity.setSerialNumber(updateExistingIdCardCommand.getSerialNumber());
        existingIdCardEntity.setValidUntil(updateExistingIdCardCommand.getValidUntil());
        existingIdCardEntity.setPublishedBy(updateExistingIdCardCommand.getPublishedBy());
        existingIdCardEntity.setPerson(existingPersonEntity);
        existingPersonEntity.setIdCard(existingIdCardEntity);

        PersonEntity personEntityAfterChanges = personRepository.save(existingPersonEntity);
        return personEntityToPersonConverter.fromPersonEntity(personEntityAfterChanges);
    }

    @Transactional
    public Person deleteIdCardFromPerson(String personBusinessId) {
        PersonEntity existingPersonEntity = getPersonByBusinessIdOrThrow(personBusinessId);
        IdCardEntity idCardEntityToDelete = existingPersonEntity.getIdCard();
        if (idCardEntityToDelete != null) {
            existingPersonEntity.setIdCard(null); // orphanRemoval "true" would remove idCard as it has no more
            // references...
            idCardRepository.delete(idCardEntityToDelete); // (without orphanRemoval this is necessary)
            PersonEntity personEntityAfterChages = personRepository.save(existingPersonEntity);
            return personEntityToPersonConverter.fromPersonEntity(personEntityAfterChages);
        }
        throw new PersonNotFoundAppException("No person found for given businessId: " + personBusinessId);
    }

    private PersonEntity getPersonByBusinessIdOrThrow(String personBusinessId) {
        return personRepository
                .findByBusinessId(personBusinessId)
                .orElseThrow(() -> new PersonNotFoundAppException("No person found for given businessId: " + personBusinessId));
    }
}