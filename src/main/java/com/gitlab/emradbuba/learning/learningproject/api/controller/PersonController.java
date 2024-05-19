package com.gitlab.emradbuba.learning.learningproject.api.controller;

import com.gitlab.emradbuba.learning.learningproject.api.converters.person.PostNewPersonRequestToCommandConverter;
import com.gitlab.emradbuba.learning.learningproject.api.converters.person.PutExistingPersonRequestToCommandConverter;
import com.gitlab.emradbuba.learning.learningproject.api.model.request.person.PostNewPersonRequest;
import com.gitlab.emradbuba.learning.learningproject.api.model.request.person.PutExistingPersonRequest;
import com.gitlab.emradbuba.learning.learningproject.exceptions.LPErrorResponse;
import com.gitlab.emradbuba.learning.learningproject.libs.exceptions.core.LPException;
import com.gitlab.emradbuba.learning.learningproject.model.Person;
import com.gitlab.emradbuba.learning.learningproject.service.PersonService;
import com.gitlab.emradbuba.learning.learningproject.service.commands.AddNewPersonCommand;
import com.gitlab.emradbuba.learning.learningproject.service.commands.UpdateExistingPersonCommand;
import com.gitlab.emradbuba.learning.learningproject.validation.PersonCommandValidator;
import com.gitlab.emradbuba.learning.learningproject.validation.ValidationUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/person")
@AllArgsConstructor
@Tag(name = "Person management", description = "This part of API enables to manage persons")
public class PersonController {

    private final PersonService personService;
    private final PostNewPersonRequestToCommandConverter postNewPersonRequestToCommandConverter;
    private final PutExistingPersonRequestToCommandConverter putExistingPersonRequestToCommandConverter;

    @GetMapping("/{personBusinessId}")
    @Operation(summary = "Finds a person by person's businessId", description = "Returns a person by businessId")
    @ApiResponse(responseCode = "200", description = "When person exists in the system", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Person.class, description = "The found person")))
    @ApiResponse(responseCode = "404", description = "When person does not exist in the system", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LPErrorResponse.class, description = "Standard error response from learning project")))
    @ApiResponse(responseCode = "422", description = "When request cannot be processed due to incorrect input", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LPErrorResponse.class, description = "Standard error response from learning project")))
    public ResponseEntity<Person> getPerson(
            @Parameter(description = "UUID - businessId of a person", required = true, example = "f131dd87-a582-48e1-af07-a083122daa3c")
            @PathVariable("personBusinessId") String personBusinessId) {
        try {
            return new ResponseEntity<>(personService.getPerson(personBusinessId), HttpStatus.OK);
        } catch (Exception e) {
            throw new LPException("Error while getting a person by id", e)
                    .withPersonBusinessId(personBusinessId);
        }
    }

    @PostMapping
    @Operation(summary = "Creates a new person", description = "Creates a new person with information from payload")
    @ApiResponse(responseCode = "201", description = "When person successfully created", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Person.class, description = "Newly created person with generated businessId"))})
    @ApiResponse(responseCode = "404", description = "When person does not exist in the system", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LPErrorResponse.class, description = "Standard error response from learning project")))
    @ApiResponse(responseCode = "422", description = "When request cannot be processed due to incorrect input", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LPErrorResponse.class, description = "Standard error response from learning project")))
    public ResponseEntity<Person> createNewPerson(@RequestBody PostNewPersonRequest postNewPersonRequest) {
        try {
            AddNewPersonCommand addNewPersonCommand = postNewPersonRequestToCommandConverter.toCommand(postNewPersonRequest);
            PersonCommandValidator.validateAddNewPersonCommand(addNewPersonCommand);
            Person newlyCreatedPerson = personService.storeNewPerson(addNewPersonCommand);
            return new ResponseEntity<>(newlyCreatedPerson, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new LPException("Error while creating a new person", e);
        }
    }

    @PutMapping("/{personBusinessId}")
    @ApiResponse(responseCode = "200", description = "When person successfully updated", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Person.class, description = "Person with updated data"))})
    @ApiResponse(responseCode = "404", description = "When person does not exist in the system", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LPErrorResponse.class, description = "Standard error response from learning project")))
    @ApiResponse(responseCode = "422", description = "When request cannot be processed due to incorrect input", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LPErrorResponse.class, description = "Standard error response from learning project")))
    @Operation(summary = "Updates an existing person", description = "Updates an existing person with a given businessId with data sent in payload")
    public ResponseEntity<Person> updateExistingPerson(@RequestBody PutExistingPersonRequest putExistingPersonRequest,
                                                       @Parameter(description = "UUID - businessId of a person", required = true, example = "f131dd87-a582-48e1-af07-a083122daa3c")
                                                       @PathVariable("personBusinessId") String personBusinessId) {
        try {
            UpdateExistingPersonCommand updateExistingPersonCommand =
                    putExistingPersonRequestToCommandConverter.toCommand(personBusinessId, putExistingPersonRequest);
            ValidationUtils.validateUUID(updateExistingPersonCommand.getBusinessId());
            PersonCommandValidator.validateUpdateExistingPersonCommand(updateExistingPersonCommand);
            return new ResponseEntity<>(personService.updateExistingPerson(updateExistingPersonCommand), HttpStatus.OK);
        } catch (Exception e) {
            throw new LPException("Error while updating an existing person", e)
                    .withPersonBusinessId(personBusinessId);
        }
    }

    @DeleteMapping("/{personBusinessId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes an existing person", description = "Deletes an existing person by businessId if such person exists")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "When person successfully deleted"),
            @ApiResponse(responseCode = "404", description = "When person to delete was not found"),
            @ApiResponse(responseCode = "422", description = "When request cannot be processed - uuid is incorrect")}
    )
    public void deletePerson(
            @Parameter(description = "UUID - businessId of a person to delete", required = true, example = "f131dd87-a582-48e1-af07-a083122daa3c")
            @PathVariable("personBusinessId") String personBusinessId) {
        try {
            ValidationUtils.validateUUID(personBusinessId);
            personService.deletePerson(personBusinessId);
        } catch (Exception e) {
            throw new LPException("Error while deleting an existing person", e)
                    .withPersonBusinessId(personBusinessId);
        }
    }
}
