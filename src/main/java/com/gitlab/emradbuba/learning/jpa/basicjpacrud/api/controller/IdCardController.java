package com.gitlab.emradbuba.learning.jpa.basicjpacrud.api.controller;

import com.gitlab.emradbuba.learning.jpa.basicjpacrud.api.model.request.PostIdCardRequest;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.api.model.request.PutIdCardRequest;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.model.IdCard;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.model.Person;
import com.gitlab.emradbuba.learning.jpa.basicjpacrud.service.IdCardService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/idcard/")
@AllArgsConstructor
public class IdCardController {

    private final IdCardService idCardService;

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
        return new ResponseEntity<>(
                idCardService.addIdCardToPerson(personId, postIdCardRequest),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/person/{personId}")
    public ResponseEntity<Person> updatePersonIdCard(@PathVariable("personId") Long personId,
                                                     @RequestBody PutIdCardRequest putIdCardRequest) {
        return ResponseEntity.ok(idCardService.updateIdCardInPerson(personId, putIdCardRequest));
    }

    @DeleteMapping("/person/{personId}")
    public ResponseEntity<Person> deletePersonIdCard(@PathVariable("personId") Long personId) {
        idCardService.deleteIdCardFromPerson(personId);
        return ResponseEntity.noContent().build();
    }
}
