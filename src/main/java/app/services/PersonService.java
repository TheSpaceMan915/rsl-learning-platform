package app.services;

import app.domain.Person;
import app.dtos.unique.PersonYandexData;
import app.mappers.PersonMapper;
import app.repositories.PersonRepository;

import org.springframework.http.*;
import org.springframework.stereotype.Service;

// TODO: As soon as I've finished the methods, document them using the comments
@Service
public class PersonService {

    private PersonRepository personRepo;
    private PersonMapper personMapper;
    private AuthenticationService authenticationService;
    private ModuleProgressService moduleProgressService;
    private LessonProgressService lessonProgressService;
    private StepProgressService stepProgressService;

    public PersonService(PersonRepository personRepo,
                         PersonMapper personMapper,
                         AuthenticationService authenticationService,
                         ModuleProgressService moduleProgressService,
                         LessonProgressService lessonProgressService,
                         StepProgressService stepProgressService) {
        this.personRepo = personRepo;
        this.personMapper = personMapper;
        this.authenticationService = authenticationService;
        this.moduleProgressService = moduleProgressService;
        this.lessonProgressService = lessonProgressService;
        this.stepProgressService = stepProgressService;
    }

//    Finds the Person who has this oauthToken using Yandex ID API.
//    If the Person is new, they are saved in the db
    public ResponseEntity<PersonYandexData> getByToken(String oauthToken) {
        PersonYandexData dto = authenticationService.authenticate(oauthToken);
        Person person = personMapper.toEntity(dto);
        if (!exist(person)) {
            person = create(person);
        }
        //    TODO:
        //     Add the fields that I need to return in response from PersonRequestDto to Person.
//             And then using Person, create PersonResponseDto
//             with the Person's data and the Progress data
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

//    Checks if the Person with this id exists
    private boolean exist(Person person) {
        return personRepo.existsById(person.getId());
    }

//    Creates a new Person with Module, Lesson and Step Progresses
    private Person create(Person person) {
        moduleProgressService.create(person);
        lessonProgressService.create(person);
        stepProgressService.create(person);
        return personRepo.save(person);
    }
}
