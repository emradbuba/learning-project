package com.gitlab.emradbuba.learning.learningproject.api.controller;

import com.gitlab.emradbuba.learning.learningproject.api.converters.certificate.PostCertificateRequestConverter;
import com.gitlab.emradbuba.learning.learningproject.api.converters.certificate.PutCertificateRequestConverter;
import com.gitlab.emradbuba.learning.learningproject.api.model.request.PostCertificateRequest;
import com.gitlab.emradbuba.learning.learningproject.api.model.request.PutCertificateRequest;
import com.gitlab.emradbuba.learning.learningproject.model.EmploymentCertificate;
import com.gitlab.emradbuba.learning.learningproject.model.Person;
import com.gitlab.emradbuba.learning.learningproject.service.EmploymentCertificateService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/certificate/")
@AllArgsConstructor
public class EmploymentCertificateController {
    private final EmploymentCertificateService employmentCertificateService;
    private final PostCertificateRequestConverter postCertificateRequestConverter;
    private final PutCertificateRequestConverter putCertificateRequestConverter;

    @GetMapping("/person/{personId}")
    public ResponseEntity<List<EmploymentCertificate>> getPersonEmploymentCertificates(@PathVariable("personId") Long personId) {
        return ResponseEntity.ok(employmentCertificateService.getCertificatesFromPerson(personId));
    }

    @PostMapping("/person/{personId}")
    public ResponseEntity<Person> addPersonCertificate(@PathVariable("personId") Long personId,
                                                       @RequestBody PostCertificateRequest postCertificateRequest) {
        EmploymentCertificate employmentCertificate = postCertificateRequestConverter.toBusinessModel(postCertificateRequest);
        return new ResponseEntity<>(
                employmentCertificateService.addCertificateToPerson(personId, employmentCertificate),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/person/{personId}/{certificateId}")
    public ResponseEntity<Person> updatePersonIdCard(@PathVariable("personId") Long personId,
                                                     @PathVariable("certificateId") Long certificateId,
                                                     @RequestBody PutCertificateRequest putCertificateRequest) {
        EmploymentCertificate employmentCertificate = putCertificateRequestConverter.toBusinessModel(putCertificateRequest);
        return ResponseEntity.ok(employmentCertificateService.updateCertificateInPerson(personId, certificateId,
                employmentCertificate));
    }

    @DeleteMapping("/person/{personId}/{certificateId}")
    public ResponseEntity<Person> deletePersonIdCard(@PathVariable("personId") Long personId,
                                                     @PathVariable("certificateId") Long certificateId) {
        return ResponseEntity.ok(employmentCertificateService.deleteCertificateFromPerson(personId, certificateId));
    }

    @DeleteMapping("/all/person/{personId}")
    public ResponseEntity<Person> deleteAllCertificatesFromPerson(@PathVariable("personId") Long personId) {
        return ResponseEntity.ok(employmentCertificateService.deleteAllCertificatesFromPerson(personId));
    }
}
