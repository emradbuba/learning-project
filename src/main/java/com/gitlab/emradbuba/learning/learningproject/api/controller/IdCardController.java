package com.gitlab.emradbuba.learning.learningproject.api.controller;

import com.gitlab.emradbuba.learning.learningproject.api.converters.idcard.PostIdCardRequestToCommandConverter;
import com.gitlab.emradbuba.learning.learningproject.api.converters.idcard.PutIdCardRequestToCommandConverter;
import com.gitlab.emradbuba.learning.learningproject.api.model.request.PostIdCardRequest;
import com.gitlab.emradbuba.learning.learningproject.api.model.request.PutIdCardRequest;
import com.gitlab.emradbuba.learning.learningproject.service.commands.AddNewIdCardCommand;
import com.gitlab.emradbuba.learning.learningproject.service.commands.UpdateExistingIdCardCommand;
import com.gitlab.emradbuba.learning.learningproject.model.IdCard;
import com.gitlab.emradbuba.learning.learningproject.model.Person;
import com.gitlab.emradbuba.learning.learningproject.service.IdCardService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/idcard/")
@AllArgsConstructor
public class IdCardController {

    private final IdCardService idCardService;
    private final PostIdCardRequestToCommandConverter postIdCardRequestToCommandConverter;
    private final PutIdCardRequestToCommandConverter putIdCardRequestToCommandConverter;

    @GetMapping("/person/{personBusinessId}")
    public ResponseEntity<IdCard> getPersonIdCard(@PathVariable("personBusinessId") String personBusinessId) {
        return idCardService
                .getIdCardFromPerson(personBusinessId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping("/person/{personBusinessId}")
    public ResponseEntity<Person> addPersonIdCard(@PathVariable("personBusinessId") String personBusinessId,
                                                  @RequestBody PostIdCardRequest postIdCardRequest) {
        final AddNewIdCardCommand addNewIdCardCommand = postIdCardRequestToCommandConverter
                .toCommand(personBusinessId, postIdCardRequest);
        final Person personAfterChanges = idCardService.addIdCardToPerson(addNewIdCardCommand);
        return new ResponseEntity<>(personAfterChanges, HttpStatus.CREATED);
    }

    @PutMapping("/person/{personBusinessId}")
    public ResponseEntity<Person> updatePersonIdCard(@PathVariable("personBusinessId") String personBusinessId,
                                                     @RequestBody PutIdCardRequest putIdCardRequest) {
        final UpdateExistingIdCardCommand updateExistingIdCardCommand =
                putIdCardRequestToCommandConverter.toCommand(personBusinessId, putIdCardRequest);
        Person personAfterChanges = idCardService.updateIdCardInPerson(updateExistingIdCardCommand);
        return ResponseEntity.ok(personAfterChanges);
    }

    @DeleteMapping("/person/{personBusinessId}")
    public ResponseEntity<Person> deletePersonIdCard(@PathVariable("personBusinessId") String personBusinessId) {
        return ResponseEntity.ok(idCardService.deleteIdCardFromPerson(personBusinessId));
    }
}
