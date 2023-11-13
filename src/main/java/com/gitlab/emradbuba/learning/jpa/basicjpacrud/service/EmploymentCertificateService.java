package com.gitlab.emradbuba.learning.jpa.basicjpacrud.service;

import com.gitlab.emradbuba.learning.jpa.basicjpacrud.api.model.request.PostCertificateRequest;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.api.model.request.PutCertificateRequest;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.exceptions.PersonNotFoundAppException;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.model.EmploymentCertificate;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.model.Person;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.persistance.CertRepo;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.persistance.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
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

    public Person addCertificateToPerson(Long personId, PostCertificateRequest postCertificateRequest) {
        Person existingPerson = getPersonByIdOrThrow(personId);
        Set<EmploymentCertificate> existingCertificates = existingPerson.getCertificates();
        EmploymentCertificate newCertificate = new EmploymentCertificate();
        newCertificate.setPerson(existingPerson);
        newCertificate.setCompanyName(postCertificateRequest.getCompanyName());
        newCertificate.setStartDate(postCertificateRequest.getStartDate());
        newCertificate.setEndDate(postCertificateRequest.getEndDate());

        certRepo.save(newCertificate);
        existingCertificates.add(newCertificate);
        existingPerson.setCertificates(existingCertificates);
        return personRepository.save(existingPerson);
    }

    public Person updateCertificateInPerson(Long personId, Long certificateId,
                                            PutCertificateRequest putCertificateRequest) {
        return null;
    }

    public void deleteCertificateFromPerson(Long personId, Long certificateId) {

    }

    public void deleteAllCertificatesFromPerson(Long personId) {

    }

    private Person getPersonByIdOrThrow(Long personId) {
        return personRepository
                .findById(personId)
                .orElseThrow(() -> new PersonNotFoundAppException("No person found for given personId: " + personId));
    }
}
