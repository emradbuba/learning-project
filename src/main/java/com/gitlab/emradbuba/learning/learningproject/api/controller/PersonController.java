package com.gitlab.emradbuba.learning.learningproject.api.controller;

import com.gitlab.emradbuba.learning.learningproject.api.model.request.PostNewPersonRequest;
import com.gitlab.emradbuba.learning.learningproject.api.model.request.PutExistingPersonRequest;
import com.gitlab.emradbuba.learning.learningproject.model.Person;
import com.gitlab.emradbuba.learning.learningproject.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/person")
@AllArgsConstructor
public class PersonController {
    private final PersonService personService;

    @GetMapping("/{personId}")
    public ResponseEntity<Person> getPerson(@PathVariable("personId") Long personId) {
        return new ResponseEntity<>(personService.getPerson(personId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Person> createNewPerson(@RequestBody PostNewPersonRequest postNewPersonRequest) {
        return new ResponseEntity<>(personService.createNewPerson(postNewPersonRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{personId}")
    public ResponseEntity<Person> updateExistingPerson(@RequestBody PutExistingPersonRequest putExistingPersonRequest,
                                                       @PathVariable("personId") Long personId) {
        return new ResponseEntity<>(personService.updateExistingPerson(personId, putExistingPersonRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{personId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@PathVariable("personId") Long id) {
        personService.deletePerson(id);
    }
}
