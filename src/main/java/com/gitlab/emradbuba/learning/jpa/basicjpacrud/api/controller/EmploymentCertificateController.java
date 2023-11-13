package com.gitlab.emradbuba.learning.jpa.basicjpacrud.api.controller;

import com.gitlab.emradbuba.learning.jpa.basicjpacrud.api.model.request.PostCertificateRequest;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.api.model.request.PostIdCardRequest;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.api.model.request.PutCertificateRequest;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.api.model.request.PutIdCardRequest;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.model.EmploymentCertificate;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.model.IdCard;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.model.Person;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.service.EmploymentCertificateService;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.service.IdCardService;
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

    @GetMapping("/person/{personId}")
    public ResponseEntity<List<EmploymentCertificate>> getPersonEmploymentCertificates(@PathVariable("personId") Long personId) {
        return ResponseEntity.ok(employmentCertificateService.getCertificatesFromPerson(personId));
    }

    @PostMapping("/person/{personId}")
    public ResponseEntity<Person> addPersonCertificate(@PathVariable("personId") Long personId,
                                                       @RequestBody PostCertificateRequest postCertificateRequest) {
        return new ResponseEntity<>(
                employmentCertificateService.addCertificateToPerson(personId, postCertificateRequest),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/person/{personId}/{certificateId}")
    public ResponseEntity<Person> updatePersonIdCard(@PathVariable("personId") Long personId,
                                                     @PathVariable("certificateId") Long certificateId,
                                                     @RequestBody PutCertificateRequest putCertificateRequest) {
        return ResponseEntity.ok(employmentCertificateService.updateCertificateInPerson(personId, certificateId,
                putCertificateRequest));
    }

    @DeleteMapping("/person/{personId}/{certificateId}")
    public ResponseEntity<Person> deletePersonIdCard(@PathVariable("personId") Long personId,
                                                     @PathVariable("certificateId") Long certificateId) {
        employmentCertificateService.deleteCertificateFromPerson(personId, certificateId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all/person/{personId}")
    public ResponseEntity<Person> deleteAllCertificatesFromPerson(@PathVariable("personId") Long personId) {
        employmentCertificateService.deleteAllCertificatesFromPerson(personId);
        return ResponseEntity.noContent().build();
    }
}
