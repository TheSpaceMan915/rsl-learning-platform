package app.controllers;

import app.dtos.PersonRequestDto;
import app.services.PersonService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//TODO: Add CrossOrigin annotation
@RestController
@RequestMapping(path = "/persons",
                produces = "application/json")
public class PersonController {

    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(path = "/{oauthToken}")
    public ResponseEntity<PersonRequestDto> getPerson(@PathVariable("oauthToken") String oauthToken) {
        return personService.find(oauthToken);
    }
}
