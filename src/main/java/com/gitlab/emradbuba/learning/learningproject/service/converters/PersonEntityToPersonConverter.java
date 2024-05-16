package com.gitlab.emradbuba.learning.learningproject.service.converters;

import com.gitlab.emradbuba.learning.learningproject.model.EmploymentCertificate;
import com.gitlab.emradbuba.learning.learningproject.model.IdCard;
import com.gitlab.emradbuba.learning.learningproject.model.Person;
import com.gitlab.emradbuba.learning.learningproject.persistance.model.PersonEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PersonEntityToPersonConverter {
    private final IdCardEntityToIdCardConverter idCardEntityToIdCardConverter;
    private final CertificateEntityToCertificateConverter certificateEntityToCertificateConverter;

    public Person fromPersonEntity(final PersonEntity personEntity) {
        final IdCard idCard = getIdCard(personEntity);
        final Set<EmploymentCertificate> certificates = getCertificates(personEntity);

        return Person.builder()
                .businessId(personEntity.getBusinessId())
                .firstName(personEntity.getFirstName())
                .surname(personEntity.getSurname())
                .dateOfBirth(personEntity.getDateOfBirth())
                .idCard(idCard)
                .certificates(certificates)
                .build();
    }

    private IdCard getIdCard(PersonEntity personEntity) {
        return Optional.ofNullable(personEntity.getIdCard())
                .map(idCardEntityToIdCardConverter::fromIdCardEntity)
                .orElse(null);
    }

    private Set<EmploymentCertificate> getCertificates(PersonEntity personEntity) {
        return Optional.ofNullable(personEntity.getCertificates())
                .orElse(Collections.emptySet())
                .stream()
                .map(certificateEntityToCertificateConverter::fromCertificateEntity)
                .collect(Collectors.toSet());
    }
}
