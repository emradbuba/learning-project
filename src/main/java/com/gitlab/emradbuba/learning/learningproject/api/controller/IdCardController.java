package com.gitlab.emradbuba.learning.learningproject.api.controller;

import com.gitlab.emradbuba.learning.learningproject.api.converters.idcard.PostIdCardRequestToCommandConverter;
import com.gitlab.emradbuba.learning.learningproject.api.converters.idcard.PutIdCardRequestToCommandConverter;
import com.gitlab.emradbuba.learning.learningproject.api.model.request.idcard.PostIdCardRequest;
import com.gitlab.emradbuba.learning.learningproject.api.model.request.idcard.PutIdCardRequest;
import com.gitlab.emradbuba.learning.learningproject.model.IdCard;
import com.gitlab.emradbuba.learning.learningproject.model.Person;
import com.gitlab.emradbuba.learning.learningproject.service.IdCardService;
import com.gitlab.emradbuba.learning.learningproject.service.commands.AddNewIdCardCommand;
import com.gitlab.emradbuba.learning.learningproject.service.commands.UpdateExistingIdCardCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/idcard/")
@AllArgsConstructor
@Tag(name = "IdCards API methods", description = "This part of API enables to manage id cards assigned to a person")
public class IdCardController {

    private final IdCardService idCardService;
    private final PostIdCardRequestToCommandConverter postIdCardRequestToCommandConverter;
    private final PutIdCardRequestToCommandConverter putIdCardRequestToCommandConverter;

    @GetMapping("/person/{personBusinessId}")
    @Operation(summary = "Gets person's idCard",
            description = "Gets an IdCard of a person with given businessId")
    @ApiResponse(responseCode = "200", description = "When card id for a person was found",
            content = @Content(
                    schema = @Schema(implementation = IdCard.class)))
    public ResponseEntity<IdCard> getPersonIdCard(
            @Parameter(description = "UUID - businessId of a person", example = "72ae530b-c70a-4041-81db-a7f6a4df1ecc")
            @PathVariable("personBusinessId") String personBusinessId) {
        return idCardService
                .getIdCardFromPerson(personBusinessId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping("/person/{personBusinessId}")
    @Operation(summary = "Adds a new IdCard",
            description = "Adds IdCard to a person with given businessId. Card's businessId is generated. Person is " +
                    "allowed to have only one IdCard")
    @ApiResponse(responseCode = "201", description = "When card id for a person was added",
            content = @Content(
                    schema = @Schema(implementation = Person.class, description = "Person with added IdCard")))
    public ResponseEntity<Person> addPersonIdCard(
            @Parameter(description = "UUID - businessId of a person", example = "72ae530b-c70a-4041-81db-a7f6a4df1ecc")
            @PathVariable("personBusinessId") String personBusinessId,
            @RequestBody PostIdCardRequest postIdCardRequest) {
        final AddNewIdCardCommand addNewIdCardCommand = postIdCardRequestToCommandConverter
                .toCommand(personBusinessId, postIdCardRequest);
        final Person personAfterChanges = idCardService.addIdCardToPerson(addNewIdCardCommand);
        return new ResponseEntity<>(personAfterChanges, HttpStatus.CREATED);
    }

    @PutMapping("/person/{personBusinessId}")
    @Operation(summary = "Updates an IdCard",
            description = "Updates information about IdCard of a person with specified businessId")
    @ApiResponse(responseCode = "200", description = "When card id for a person was updated",
            content = @Content(
                    schema = @Schema(implementation = Person.class, description = "Person with updated IdCard")))
    public ResponseEntity<Person> updatePersonIdCard(
            @Parameter(description = "UUID - businessId of a person", example = "72ae530b-c70a-4041-81db-a7f6a4df1ecc")
            @PathVariable("personBusinessId") String personBusinessId,
            @RequestBody PutIdCardRequest putIdCardRequest) {
        final UpdateExistingIdCardCommand updateExistingIdCardCommand =
                putIdCardRequestToCommandConverter.toCommand(personBusinessId, putIdCardRequest);
        Person personAfterChanges = idCardService.updateIdCardInPerson(updateExistingIdCardCommand);
        return ResponseEntity.ok(personAfterChanges);
    }

    @DeleteMapping("/person/{personBusinessId}")
    @Operation(summary = "Deletes an IdCard", description = "Deletes the IdCard from a person with specified businessId")
    @ApiResponse(responseCode = "200", description = "When card id for a person was deleted",
            content = @Content(
                    schema = @Schema(implementation = Person.class, description = "Person with removed IdCard")))
    public ResponseEntity<Person> deletePersonIdCard(
            @Parameter(description = "UUID - businessId of a person", example = "72ae530b-c70a-4041-81db-a7f6a4df1ecc")
            @PathVariable("personBusinessId") String personBusinessId) {
        return ResponseEntity.ok(idCardService.deleteIdCardFromPerson(personBusinessId));
    }
}
