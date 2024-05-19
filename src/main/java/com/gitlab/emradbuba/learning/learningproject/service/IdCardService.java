package com.gitlab.emradbuba.learning.learningproject.service;

import com.gitlab.emradbuba.learning.learningproject.BusinessIdUtils;
import com.gitlab.emradbuba.learning.learningproject.exceptions.LPServiceExceptionErrorCode;
import com.gitlab.emradbuba.learning.learningproject.libs.exceptions.core.business.LPBusinessRulesViolationException;
import com.gitlab.emradbuba.learning.learningproject.libs.exceptions.core.notfound.LPIdCardNotFoundException;
import com.gitlab.emradbuba.learning.learningproject.model.IdCard;
import com.gitlab.emradbuba.learning.learningproject.model.Person;
import com.gitlab.emradbuba.learning.learningproject.persistance.IdCardRepository;
import com.gitlab.emradbuba.learning.learningproject.persistance.PersonRepository;
import com.gitlab.emradbuba.learning.learningproject.persistance.model.IdCardEntity;
import com.gitlab.emradbuba.learning.learningproject.persistance.model.PersonEntity;
import com.gitlab.emradbuba.learning.learningproject.service.commands.AddNewIdCardCommand;
import com.gitlab.emradbuba.learning.learningproject.service.commands.UpdateExistingIdCardCommand;
import com.gitlab.emradbuba.learning.learningproject.service.converters.IdCardEntityToIdCardConverter;
import com.gitlab.emradbuba.learning.learningproject.service.converters.PersonEntityToPersonConverter;
import com.gitlab.emradbuba.learning.learningproject.validation.ValidationUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.gitlab.emradbuba.learning.learningproject.exceptions.LPServiceExceptionUtils.createLPPersonNotFoundException;

@Service
@AllArgsConstructor
public class IdCardService {
    private final PersonRepository personRepository;
    private final IdCardRepository idCardRepository;
    private final IdCardEntityToIdCardConverter idCardEntityToIdCardConverter;
    private final PersonEntityToPersonConverter personEntityToPersonConverter;

    public Optional<IdCard> getIdCardFromPerson(String personBusinessId) {
        ValidationUtils.validateUUID(personBusinessId);
        PersonEntity existingPersonEntity = getPersonByBusinessIdOrThrow(personBusinessId);
        return Optional.ofNullable(existingPersonEntity.getIdCard())
                .map(idCardEntityToIdCardConverter::fromIdCardEntity);
    }

    @Transactional
    public Person addIdCardToPerson(AddNewIdCardCommand addNewIdCardCommand) {
        String personBusinessIdFromCmd = addNewIdCardCommand.getPersonBusinessId();
        PersonEntity existingPersonEntity = getPersonByBusinessIdOrThrow(personBusinessIdFromCmd);
        IdCardEntity existingIdCardEntity = existingPersonEntity.getIdCard();
        if (existingIdCardEntity != null) {
            throw new LPBusinessRulesViolationException(
                    String.format(
                            "Person [%s] already has an idCard %s - cannot add a new one",
                            existingPersonEntity.getBusinessId(),
                            existingIdCardEntity.getBusinessId()))
                    .withUniqueErrorCode(LPServiceExceptionErrorCode.ID_CARD_ALREADY_DEFINED.getReasonCode())
                    .withDescription(LPServiceExceptionErrorCode.ID_CARD_ALREADY_DEFINED.getDescription())
                    .withPersonBusinessId(existingPersonEntity.getBusinessId())
                    .withHttpStatusCodeValue(409)
                    .withSolutionTip("Remove the old idCard before adding a new one");
        }
        String idCardBusinessId = BusinessIdUtils.generateBusinessId();
        IdCardEntity newIdCardEntity = new IdCardEntity();
        newIdCardEntity.setBusinessId(idCardBusinessId);
        newIdCardEntity.setSerialNumber(addNewIdCardCommand.getSerialNumber());
        newIdCardEntity.setValidUntil(addNewIdCardCommand.getValidUntil());
        newIdCardEntity.setPublishedBy(addNewIdCardCommand.getPublishedBy());
        newIdCardEntity.setPerson(existingPersonEntity);

        existingPersonEntity.setIdCard(newIdCardEntity);
        // Cascade.PERSIST will also persist a newly created IdCardEntity (when saving person entity)
        PersonEntity personEntityAfterChanges = personRepository.save(existingPersonEntity);
        return personEntityToPersonConverter.fromPersonEntity(personEntityAfterChanges);
    }

    @Transactional
    public Person updateIdCardInPerson(UpdateExistingIdCardCommand updateExistingIdCardCommand) {
        String personBusinessIdFromCmd = updateExistingIdCardCommand.getPersonBusinessId();
        PersonEntity existingPersonEntity = getPersonByBusinessIdOrThrow(personBusinessIdFromCmd);
        IdCardEntity existingIdCardEntity = Optional.ofNullable(existingPersonEntity.getIdCard())
                .orElseThrow(() -> new LPIdCardNotFoundException("IdCard does not exist for person with businessId: " + personBusinessIdFromCmd)
                        .withHttpStatusCodeValue(409)
                        .withUniqueErrorCode(LPServiceExceptionErrorCode.ID_CARD_ID_NOT_FOUND.getReasonCode())
                        .withDescription(LPServiceExceptionErrorCode.ID_CARD_ID_NOT_FOUND.getDescription())
                        .withSolutionTip("Make sure you put the right person id")
                        .withPersonBusinessId(personBusinessIdFromCmd));
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
        ValidationUtils.validateUUID(personBusinessId);
        PersonEntity existingPersonEntity = getPersonByBusinessIdOrThrow(personBusinessId);
        IdCardEntity idCardEntityToDelete = existingPersonEntity.getIdCard();
        if (idCardEntityToDelete != null) {
            existingPersonEntity.setIdCard(null); // orphanRemoval "true" would remove idCard as it has no more references...
            idCardRepository.delete(idCardEntityToDelete); // (without orphanRemoval this is necessary)
            PersonEntity personEntityAfterChanges = personRepository.save(existingPersonEntity);
            return personEntityToPersonConverter.fromPersonEntity(personEntityAfterChanges);
        }
        return personEntityToPersonConverter.fromPersonEntity(existingPersonEntity);
    }

    private PersonEntity getPersonByBusinessIdOrThrow(String personBusinessId) {
        return personRepository
                .findByBusinessId(personBusinessId)
                .orElseThrow(() -> createLPPersonNotFoundException(personBusinessId));
    }
}