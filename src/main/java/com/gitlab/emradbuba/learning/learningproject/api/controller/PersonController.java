package com.gitlab.emradbuba.learning.learningproject.api.controller;

import com.gitlab.emradbuba.learning.learningproject.api.converters.person.PostNewPersonRequestToCommandConverter;
import com.gitlab.emradbuba.learning.learningproject.api.converters.person.PutExistingPersonRequestToCommandConverter;
import com.gitlab.emradbuba.learning.learningproject.api.model.request.PostNewPersonRequest;
import com.gitlab.emradbuba.learning.learningproject.api.model.request.PutExistingPersonRequest;
import com.gitlab.emradbuba.learning.learningproject.service.commands.AddNewPersonCommand;
import com.gitlab.emradbuba.learning.learningproject.service.commands.UpdateExistingPersonCommand;
import com.gitlab.emradbuba.learning.learningproject.model.Person;
import com.gitlab.emradbuba.learning.learningproject.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping("/api/v1/person")
@AllArgsConstructor
public class PersonController {
    private final PersonService personService;
    private final PostNewPersonRequestToCommandConverter postNewPersonRequestToCommandConverter;
    private final PutExistingPersonRequestToCommandConverter putExistingPersonRequestToCommandConverter;

    @GetMapping("/{personBusinessId}")
    public ResponseEntity<Person> getPerson(@PathVariable("personBusinessId") String personBusinessId) {
        return new ResponseEntity<>(personService.getPerson(personBusinessId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Person> createNewPerson(@RequestBody PostNewPersonRequest postNewPersonRequest) {
        AddNewPersonCommand addNewPersonCommand = postNewPersonRequestToCommandConverter.toCommand(postNewPersonRequest);
        Person newlyCreatedPerson = personService.storeNewPerson(addNewPersonCommand);
        return new ResponseEntity<>(newlyCreatedPerson, HttpStatus.CREATED);
    }

    @PutMapping("/{personBusinessId}")
    public ResponseEntity<Person> updateExistingPerson(@RequestBody PutExistingPersonRequest putExistingPersonRequest,
                                                             @PathVariable("personBusinessId") String personBusinessId) {

        UpdateExistingPersonCommand updateExistingPersonCommand = putExistingPersonRequestToCommandConverter.toCommand(personBusinessId, putExistingPersonRequest);
        return new ResponseEntity<>(personService.updateExistingPerson(updateExistingPersonCommand), HttpStatus.OK);
    }

    @DeleteMapping("/{personBusinessId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@PathVariable("personBusinessId") String personBusinessId) {
        personService.deletePerson(personBusinessId);
    }
}
