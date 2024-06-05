package app.controllers;

import app.dtos.GetPersonRequest;
import app.services.PersonService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/persons",
                produces = "application/json")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(path = "/{oauthToken}")
    public ResponseEntity<GetPersonRequest> getByToken(@PathVariable String oauthToken) {
        return personService.getByToken(oauthToken);
    }
}
