package com.gitlab.emradbuba.learning.learningproject.api.controller;

import com.gitlab.emradbuba.learning.learningproject.api.converters.certificate.PostCertificateRequestToCommandConverter;
import com.gitlab.emradbuba.learning.learningproject.api.converters.certificate.PutCertificateRequestToCommandConverter;
import com.gitlab.emradbuba.learning.learningproject.api.model.request.certificate.PostCertificateRequest;
import com.gitlab.emradbuba.learning.learningproject.api.model.request.certificate.PutCertificateRequest;
import com.gitlab.emradbuba.learning.learningproject.model.EmploymentCertificate;
import com.gitlab.emradbuba.learning.learningproject.model.Person;
import com.gitlab.emradbuba.learning.learningproject.service.EmploymentCertificateService;
import com.gitlab.emradbuba.learning.learningproject.service.commands.AddNewCertificateCommand;
import com.gitlab.emradbuba.learning.learningproject.service.commands.UpdateExistingCertificateCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/certificate/")
@AllArgsConstructor
@Tag(name = "Certificates API methods", description = "This part of API enables to manage certificates assigned to a " +
        "person")
public class EmploymentCertificateController {
    private final EmploymentCertificateService employmentCertificateService;
    private final PostCertificateRequestToCommandConverter postCertificateRequestToCommandConverter;
    private final PutCertificateRequestToCommandConverter putCertificateRequestToCommandConverter;

    @GetMapping("/person/{personBusinessId}")
    @Operation(summary = "Gets employment certificates", description = "Gets employment certificates of a person with " +
            "given businessId")
    public ResponseEntity<List<EmploymentCertificate>> getPersonEmploymentCertificates(@PathVariable("personBusinessId") String personBusinessId) {
        List<EmploymentCertificate> certificatesFromPerson =
                employmentCertificateService.getCertificatesFromPerson(personBusinessId);
        return ResponseEntity.ok(certificatesFromPerson);
    }

    @PostMapping("/person/{personBusinessId}")
    @Operation(summary = "Adds a new employment certificate",
            description = "Adds a new employment certificate to a person with given businessId. New certificated has a" +
                    "generated businessId")
    public ResponseEntity<Person> addPersonCertificate(
            @Parameter(description = "UUID - businessId of a person",
                    required = true,
                    example = "f131dd87-a582-48e1-af07-a083122daa3c")
            @PathVariable("personBusinessId") String personBusinessId,
            @RequestBody PostCertificateRequest postCertificateRequest) {
        AddNewCertificateCommand addNewCertificateCommand =
                postCertificateRequestToCommandConverter.toCommand(personBusinessId, postCertificateRequest);
        Person personAfterChanges = employmentCertificateService.addCertificateToPerson(addNewCertificateCommand);
        return new ResponseEntity<>(personAfterChanges, HttpStatus.CREATED);
    }

    @PutMapping("/person/{personBusinessId}/{certificateBusinessId}")
    @Operation(summary = "Updates an employment certificate",
            description = "Updates an existing employment certificate with a given businessId in a person with given " +
                    "businessId")
    public ResponseEntity<Person> updatePersonCertificate(
            @Parameter(description = "UUID - businessId of a person",
                    required = true,
                    example = "f131dd87-a582-48e1-af07-a083122daa3c")
            @PathVariable("personBusinessId") String personBusinessId,
            @Parameter(description = "UUID - businessId of a certificate to update",
                    required = true,
                    example = "abc31dd8e-a58f-c8e1-ff07-f083122da77f")
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
    @Operation(summary = "Deletes an employment certificate",
            description = "Deletes an existing certificate with specified businessId from a person with given businessId")
    public ResponseEntity<Person> deleteEmploymentCertificate(@PathVariable("personBusinessId") String personBusinessId,
                                                              @PathVariable("certificateBusinessId") String certificateBusinessId) {
        Person personAfterChanges = employmentCertificateService.deleteCertificateFromPerson(personBusinessId,
                certificateBusinessId);
        return ResponseEntity.ok(personAfterChanges);
    }

    @DeleteMapping("/all/person/{personBusinessId}")
    @Operation(summary = "Removes all employment certificates",
            description = "Removes all employment certificates from a person with given businessId")
    public ResponseEntity<Person> deleteAllCertificatesFromPerson(@PathVariable("personBusinessId") String personBusinessId) {
        Person personAfterChanges = employmentCertificateService.deleteAllCertificatesFromPerson(personBusinessId);
        return ResponseEntity.ok(personAfterChanges);
    }
}
