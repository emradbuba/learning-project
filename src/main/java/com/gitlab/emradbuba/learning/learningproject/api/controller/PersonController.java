package com.gitlab.emradbuba.learning.learningproject.api.controller;

import com.gitlab.emradbuba.learning.learningproject.api.converters.person.PostNewPersonRequestToCommandConverter;
import com.gitlab.emradbuba.learning.learningproject.api.converters.person.PutExistingPersonRequestToCommandConverter;
import com.gitlab.emradbuba.learning.learningproject.api.model.request.person.PostNewPersonRequest;
import com.gitlab.emradbuba.learning.learningproject.api.model.request.person.PutExistingPersonRequest;
import com.gitlab.emradbuba.learning.learningproject.model.Person;
import com.gitlab.emradbuba.learning.learningproject.service.PersonService;
import com.gitlab.emradbuba.learning.learningproject.service.commands.AddNewPersonCommand;
import com.gitlab.emradbuba.learning.learningproject.service.commands.UpdateExistingPersonCommand;
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
    @ApiResponse(responseCode = "200", description = "When person exists in the system",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Person.class, description = "The found person")))
    public ResponseEntity<Person> getPerson(@Parameter(description = "UUID - businessId of a person", required = true,
            example = "f131dd87-a582-48e1-af07-a083122daa3c")
                                            @PathVariable("personBusinessId") String personBusinessId) {
        return new ResponseEntity<>(personService.getPerson(personBusinessId), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Creates a new person",
            description = "Creates a new person with information from payload")
    @ApiResponse(responseCode = "201", description = "When person successfully created",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Person.class, description = "Newly created person with generated " +
                            "businessId"))})
    public ResponseEntity<Person> createNewPerson(@RequestBody PostNewPersonRequest postNewPersonRequest) {
        AddNewPersonCommand addNewPersonCommand = postNewPersonRequestToCommandConverter.toCommand(postNewPersonRequest);
        Person newlyCreatedPerson = personService.storeNewPerson(addNewPersonCommand);
        return new ResponseEntity<>(newlyCreatedPerson, HttpStatus.CREATED);
    }

    @PutMapping("/{personBusinessId}")
    @ApiResponse(responseCode = "200", description = "When person successfully updated", content = {@Content(mediaType =
            "application/json", schema = @Schema(implementation = Person.class,
            description = "Person with updated data"))})
    @Operation(summary = "Updates an existing person",
            description = "Updates an existing person with a given businessId with data sent in payload")
    public ResponseEntity<Person> updateExistingPerson(@RequestBody PutExistingPersonRequest putExistingPersonRequest,
                                                       @Parameter(description = "UUID - businessId of a person",
                                                               required = true, example = "f131dd87-a582-48e1-af07" +
                                                               "-a083122daa3c")
                                                       @PathVariable("personBusinessId") String personBusinessId) {
        UpdateExistingPersonCommand updateExistingPersonCommand =
                putExistingPersonRequestToCommandConverter.toCommand(personBusinessId, putExistingPersonRequest);
        return new ResponseEntity<>(personService.updateExistingPerson(updateExistingPersonCommand), HttpStatus.OK);
    }

    @DeleteMapping("/{personBusinessId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes an existing person",
            description = "Deletes an existing person by businessId if such person exists")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "When person successfully deleted"),
    })
    public void deletePerson(
            @Parameter(description = "UUID - businessId of a person to delete", required = true, example = "f131dd87" +
                    "-a582-48e1-af07-a083122daa3c")
            @PathVariable("personBusinessId") String personBusinessId) {
        personService.deletePerson(personBusinessId);
    }
}
