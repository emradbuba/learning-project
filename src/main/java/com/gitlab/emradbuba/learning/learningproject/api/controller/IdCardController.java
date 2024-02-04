package com.gitlab.emradbuba.learning.learningproject.api.controller;

import com.gitlab.emradbuba.learning.learningproject.api.converters.idcard.PostIdCardRequestConverter;
import com.gitlab.emradbuba.learning.learningproject.api.converters.idcard.PutIdCardRequestConverter;
import com.gitlab.emradbuba.learning.learningproject.api.model.request.PostIdCardRequest;
import com.gitlab.emradbuba.learning.learningproject.api.model.request.PutIdCardRequest;
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
    private final PostIdCardRequestConverter postIdCardRequestConverter;
    private final PutIdCardRequestConverter putIdCardRequestConverter;

    @GetMapping("/person/{personId}")
    public ResponseEntity<IdCard> getPersonIdCard(@PathVariable("personId") Long personId) {
        return idCardService
                .getIdCardFromPerson(personId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping("/person/{personId}")
    public ResponseEntity<Person> addPersonIdCard(@PathVariable("personId") Long personId,
                                                  @RequestBody PostIdCardRequest postIdCardRequest) {
        final IdCard idCardFromRequest = postIdCardRequestConverter.toBusinessModel(postIdCardRequest);
        return new ResponseEntity<>(
                idCardService.addIdCardToPerson(personId, idCardFromRequest),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/person/{personId}")
    public ResponseEntity<Person> updatePersonIdCard(@PathVariable("personId") Long personId,
                                                     @RequestBody PutIdCardRequest putIdCardRequest) {
        final IdCard idCardFromRequest = putIdCardRequestConverter.toBusinessModel(putIdCardRequest);
        return ResponseEntity.ok(idCardService.updateIdCardInPerson(personId, idCardFromRequest));
    }

    @DeleteMapping("/person/{personId}")
    public ResponseEntity<Person> deletePersonIdCard(@PathVariable("personId") Long personId) {
        return ResponseEntity.ok(idCardService.deleteIdCardFromPerson(personId));
    }
}
