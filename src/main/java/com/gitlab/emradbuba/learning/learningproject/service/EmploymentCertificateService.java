package com.gitlab.emradbuba.learning.learningproject.service;

import com.gitlab.emradbuba.learning.learningproject.BusinessIdUtils;
import com.gitlab.emradbuba.learning.learningproject.exceptions.LPServiceExceptionErrorCode;
import com.gitlab.emradbuba.learning.learningproject.exceptions.LPServiceExceptionUtils;
import com.gitlab.emradbuba.learning.learningproject.libs.exceptions.core.notfound.LPEmploymentCertNotFoundException;
import com.gitlab.emradbuba.learning.learningproject.libs.exceptions.core.notfound.LPPersonNotFoundException;
import com.gitlab.emradbuba.learning.learningproject.model.EmploymentCertificate;
import com.gitlab.emradbuba.learning.learningproject.model.Person;
import com.gitlab.emradbuba.learning.learningproject.persistance.CertRepo;
import com.gitlab.emradbuba.learning.learningproject.persistance.PersonRepository;
import com.gitlab.emradbuba.learning.learningproject.persistance.model.EmploymentCertificateEntity;
import com.gitlab.emradbuba.learning.learningproject.persistance.model.PersonEntity;
import com.gitlab.emradbuba.learning.learningproject.service.commands.AddNewCertificateCommand;
import com.gitlab.emradbuba.learning.learningproject.service.commands.UpdateExistingCertificateCommand;
import com.gitlab.emradbuba.learning.learningproject.service.converters.CertificateEntityToCertificateConverter;
import com.gitlab.emradbuba.learning.learningproject.service.converters.PersonEntityToPersonConverter;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.gitlab.emradbuba.learning.learningproject.exceptions.LPServiceExceptionUtils.createLPPersonNotFoundException;

// TODO: Podstawowy ControllerAdvice - jeśli jakis LP exception, to zbuduj jakis error response...

@Service
@AllArgsConstructor
@Log
public class EmploymentCertificateService {
    private final PersonRepository personRepository;
    private final CertRepo certRepo;
    private final CertificateEntityToCertificateConverter certificateEntityToCertificateConverter;
    private final PersonEntityToPersonConverter personEntityToPersonConverter;

    public List<EmploymentCertificate> getCertificatesFromPerson(String personBusinessId) {
        PersonEntity existingPersonEntity = getPersonByBusinessIdOrThrow(personBusinessId);

        Set<EmploymentCertificateEntity> certificateEntities = Optional.ofNullable(existingPersonEntity.getCertificates())
                .orElse(Collections.emptySet());

        return certificateEntities.stream()
                .map(certificateEntityToCertificateConverter::fromCertificateEntity)
                .toList();
    }

    @Transactional
    public Person addCertificateToPerson(AddNewCertificateCommand addNewCertificateCommand) {
        final String newBusinessId = BusinessIdUtils.generateBusinessId();
        PersonEntity existingPersonEntity = getPersonByBusinessIdOrThrow(addNewCertificateCommand.getPersonBusinessId());

        EmploymentCertificateEntity newCertificateEntity = new EmploymentCertificateEntity();
        newCertificateEntity.setBusinessId(newBusinessId);
        newCertificateEntity.setPerson(existingPersonEntity);
        newCertificateEntity.setCompanyName(addNewCertificateCommand.getCompanyName());
        newCertificateEntity.setStartDate(addNewCertificateCommand.getStartDate());
        newCertificateEntity.setEndDate(addNewCertificateCommand.getEndDate());

        // As it is a new entity, it has to be persisted ("new" -> "managed" entity transition)
        // (In this case it's not required due to cascading (PERSIST/MERGE), but it will not hurt)
        certRepo.save(newCertificateEntity);
        existingPersonEntity.getCertificates().add(newCertificateEntity);

        PersonEntity personEntityAfterChanges = personRepository.save(existingPersonEntity);
        return personEntityToPersonConverter.fromPersonEntity(personEntityAfterChanges);
    }

    @Transactional
    public Person updateCertificateInPerson(UpdateExistingCertificateCommand updateExistingCertificateCommand) {
        final String personBusinessId = updateExistingCertificateCommand.getPersonBusinessId();
        final String certificateBusinessId = updateExistingCertificateCommand.getCertificateBusinessId();
        PersonEntity existingPersonEntity = getPersonByBusinessIdOrThrow(personBusinessId);
        EmploymentCertificateEntity existingCertificateEntity = getPersonCertificate(existingPersonEntity, certificateBusinessId);

        existingCertificateEntity.setStartDate(updateExistingCertificateCommand.getStartDate());
        existingCertificateEntity.setEndDate(updateExistingCertificateCommand.getEndDate());
        existingCertificateEntity.setCompanyName(updateExistingCertificateCommand.getCompanyName());

        // Saving certificate/person not required as it's already managed and commit() will do the work... but we want return person...
        PersonEntity personEntityAfterChanges = personRepository.save(existingPersonEntity);
        return personEntityToPersonConverter.fromPersonEntity(personEntityAfterChanges);
    }

    @Transactional
    public Person deleteCertificateFromPerson(String personBusinessId, String certificateBusinessId) {
        PersonEntity existingPersonEntity = getPersonByBusinessIdOrThrow(personBusinessId);
        EmploymentCertificateEntity certificateEntityToDelete = getPersonCertificate(existingPersonEntity,
                certificateBusinessId);
        existingPersonEntity.getCertificates().remove(certificateEntityToDelete);

        certificateEntityToDelete.setPerson(null);

        PersonEntity personEntityAfterChanges = personRepository.save(existingPersonEntity);
        return personEntityToPersonConverter.fromPersonEntity(personEntityAfterChanges);
    }

    @Transactional
    public Person deleteAllCertificatesFromPerson(String personBusinessId) {
        PersonEntity existingPersonEntity = getPersonByBusinessIdOrThrow(personBusinessId);

        existingPersonEntity.getCertificates().forEach(certificateEntity -> certificateEntity.setPerson(null));
        existingPersonEntity.setCertificates(Collections.emptySet());

        PersonEntity personEntityAfterChanges = personRepository.save(existingPersonEntity);
        return personEntityToPersonConverter.fromPersonEntity(personEntityAfterChanges);
    }

    private PersonEntity getPersonByBusinessIdOrThrow(String personBusinessId) {
        return personRepository
                .findByBusinessId(personBusinessId)
                .orElseThrow(() -> createLPPersonNotFoundException(personBusinessId));
    }

    private EmploymentCertificateEntity getPersonCertificate(PersonEntity existingPersonEntity, String certId) {
        return existingPersonEntity.getCertificates()
                .stream()
                .filter(cert -> cert.getBusinessId().equals(certId))
                .findFirst()
                .orElseThrow(() ->
                        new LPEmploymentCertNotFoundException(
                                String.format("Person with businessId=%s has no certificate with businessId=%s", existingPersonEntity.getBusinessId(), certId))
                                .withPersonBusinessId(existingPersonEntity.getBusinessId())
                                .withHttpStatusCodeValue(409)
                                .withUniqueErrorCode(LPServiceExceptionErrorCode.CERT_ID_NOT_FOUND.getReasonCode())
                                .withDescription(LPServiceExceptionErrorCode.CERT_ID_NOT_FOUND.getDescription())
                                .withSolutionTip("Are you sure you meant the right person?")
                );
    }
}
