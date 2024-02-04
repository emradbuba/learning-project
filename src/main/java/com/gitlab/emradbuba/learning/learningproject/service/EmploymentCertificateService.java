package com.gitlab.emradbuba.learning.learningproject.service;

import com.gitlab.emradbuba.learning.learningproject.api.model.request.PostCertificateRequest;
import com.gitlab.emradbuba.learning.learningproject.api.model.request.PutCertificateRequest;
import com.gitlab.emradbuba.learning.learningproject.exceptions.CertificateNotFoundException;
import com.gitlab.emradbuba.learning.learningproject.exceptions.PersonNotFoundAppException;
import com.gitlab.emradbuba.learning.learningproject.model.EmploymentCertificate;
import com.gitlab.emradbuba.learning.learningproject.model.Person;
import com.gitlab.emradbuba.learning.learningproject.persistance.CertRepo;
import com.gitlab.emradbuba.learning.learningproject.persistance.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Log
public class EmploymentCertificateService {
    private final PersonRepository personRepository;
    private final CertRepo certRepo;

    public List<EmploymentCertificate> getCertificatesFromPerson(Long personId) {
        Person existingPerson = getPersonByIdOrThrow(personId);
        return new ArrayList<>(
                Optional.ofNullable(existingPerson.getCertificates())
                        .orElse(Collections.emptySet())
        );
    }

    @Transactional
    public Person addCertificateToPerson(Long personId, PostCertificateRequest postCertificateRequest) {
        Person existingPerson = getPersonByIdOrThrow(personId);

        EmploymentCertificate newCertificate = new EmploymentCertificate();
        newCertificate.setPerson(existingPerson);
        newCertificate.setCompanyName(postCertificateRequest.getCompanyName());
        newCertificate.setStartDate(postCertificateRequest.getStartDate());
        newCertificate.setEndDate(postCertificateRequest.getEndDate());

        // As it is a new entity, it has to be persisted ("new" -> "managed" entity transition)
        // (In this case it's not required due to cascading (PERSIST/MERGE), but will not hurt)
        certRepo.save(newCertificate);
        existingPerson.getCertificates().add(newCertificate);

        return personRepository.save(existingPerson);
    }

    @Transactional
    public Person updateCertificateInPerson(Long personId, Long certificateId,
                                            PutCertificateRequest putCertificateRequest) {
        Person existingPerson = getPersonByIdOrThrow(personId);
        EmploymentCertificate existingCertificate = getPersonCertificate(existingPerson, certificateId);

        existingCertificate.setStartDate(putCertificateRequest.getStartDate());
        existingCertificate.setEndDate(putCertificateRequest.getEndDate());
        existingCertificate.setCompanyName(putCertificateRequest.getCompanyName());

        // Saving certificate/person not required as it's already managed and commit() will do the work... but we want return person...
        return personRepository.save(existingPerson);
    }

    @Transactional
    public Person deleteCertificateFromPerson(Long personId, Long certificateId) {
        Person existingPerson = getPersonByIdOrThrow(personId);
        EmploymentCertificate certificateToDelete = getPersonCertificate(existingPerson, certificateId);
        existingPerson.getCertificates().remove(certificateToDelete);

        certificateToDelete.setPerson(null);

        return personRepository.save(existingPerson);
    }

    @Transactional
    public Person deleteAllCertificatesFromPerson(Long personId) {
        Person existingPerson = getPersonByIdOrThrow(personId);

        existingPerson.getCertificates().forEach(certificate -> certificate.setPerson(null));
        existingPerson.setCertificates(Collections.emptySet());

        return personRepository.save(existingPerson);
    }

    private Person getPersonByIdOrThrow(Long personId) {
        return personRepository
                .findById(personId)
                .orElseThrow(() -> new PersonNotFoundAppException("No person found for given personId: " + personId));
    }

    private EmploymentCertificate getPersonCertificate(Person existingPerson, Long certificateId) {
        return existingPerson.getCertificates()
                .stream()
                .filter(cert -> cert.getId().equals(certificateId))
                .findFirst()
                .orElseThrow(() ->
                        new CertificateNotFoundException(
                                String.format("Person with id=%s has no certificate with id=%s", existingPerson.getId(),
                                        certificateId)
                        )
                );
    }
}
