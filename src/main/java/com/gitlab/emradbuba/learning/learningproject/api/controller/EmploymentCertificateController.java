package com.gitlab.emradbuba.learning.learningproject.api.controller;

import com.gitlab.emradbuba.learning.learningproject.api.converters.certificate.PostCertificateRequestToCommandConverter;
import com.gitlab.emradbuba.learning.learningproject.api.converters.certificate.PutCertificateRequestToCommandConverter;
import com.gitlab.emradbuba.learning.learningproject.api.model.request.PostCertificateRequest;
import com.gitlab.emradbuba.learning.learningproject.api.model.request.PutCertificateRequest;
import com.gitlab.emradbuba.learning.learningproject.model.EmploymentCertificate;
import com.gitlab.emradbuba.learning.learningproject.model.Person;
import com.gitlab.emradbuba.learning.learningproject.service.EmploymentCertificateService;
import com.gitlab.emradbuba.learning.learningproject.service.commands.AddNewCertificateCommand;
import com.gitlab.emradbuba.learning.learningproject.service.commands.UpdateExistingCertificateCommand;
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
    private final PostCertificateRequestToCommandConverter postCertificateRequestToCommandConverter;
    private final PutCertificateRequestToCommandConverter putCertificateRequestToCommandConverter;

    @GetMapping("/person/{personBusinessId}")
    public ResponseEntity<List<EmploymentCertificate>> getPersonEmploymentCertificates(@PathVariable("personBusinessId") String personBusinessId) {
        List<EmploymentCertificate> certificatesFromPerson =
                employmentCertificateService.getCertificatesFromPerson(personBusinessId);
        return ResponseEntity.ok(certificatesFromPerson);
    }

    @PostMapping("/person/{personBusinessId}")
    public ResponseEntity<Person> addPersonCertificate(@PathVariable("personBusinessId") String personBusinessId,
                                                       @RequestBody PostCertificateRequest postCertificateRequest) {
        AddNewCertificateCommand addNewCertificateCommand =
                postCertificateRequestToCommandConverter.toCommand(personBusinessId, postCertificateRequest);
        Person personAfterChanges = employmentCertificateService.addCertificateToPerson(addNewCertificateCommand);
        return new ResponseEntity<>(personAfterChanges, HttpStatus.CREATED);
    }

    @PutMapping("/person/{personBusinessId}/{certificateBusinessId}")
    public ResponseEntity<Person> updatePersonCertificate(@PathVariable("personBusinessId") String personBusinessId,
                                                          @PathVariable("certificateBusinessId") String certificateBusinessId,
                                                          @RequestBody PutCertificateRequest putCertificateRequest) {
        UpdateExistingCertificateCommand updateExistingCertificateCommand =
                putCertificateRequestToCommandConverter.toCommand(personBusinessId, certificateBusinessId,
                        putCertificateRequest);
        Person personAfterChanges =
                employmentCertificateService.updateCertificateInPerson(updateExistingCertificateCommand);
        return ResponseEntity.ok(personAfterChanges);
    }

    @DeleteMapping("/person/{personBusinessId}/{certificateBusinessId}")
    public ResponseEntity<Person> deletePersonIdCard(@PathVariable("personBusinessId") String personBusinessId,
                                                     @PathVariable("certificateBusinessId") String certificateBusinessId) {
        Person personAfterChanges = employmentCertificateService.deleteCertificateFromPerson(personBusinessId,
                certificateBusinessId);
        return ResponseEntity.ok(personAfterChanges);
    }

    @DeleteMapping("/all/person/{personBusinessId}")
    public ResponseEntity<Person> deleteAllCertificatesFromPerson(@PathVariable("personBusinessId") String personBusinessId) {
        Person personAfterChanges = employmentCertificateService.deleteAllCertificatesFromPerson(personBusinessId);
        return ResponseEntity.ok(personAfterChanges);
    }
}
