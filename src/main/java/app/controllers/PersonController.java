package app.controllers;

import app.dtos.PersonDto;
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

//    TODO: Add a refresh token as a parameter
    @GetMapping(path = "/{oauthToken}")
    public ResponseEntity<PersonDto> getPerson(@PathVariable("oauthToken") String oauthToken) {
        return personService.find(oauthToken);
    }
}
