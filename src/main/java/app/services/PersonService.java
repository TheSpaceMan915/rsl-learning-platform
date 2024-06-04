package app.services;

import app.domain.Person;
import app.dtos.GetPersonRequest;
import app.components.mappers.PersonMapper;
import app.repositories.PersonRepository;

import org.springframework.http.*;
import org.springframework.stereotype.Service;

/**
 * Manages person-related operations for the educational platform.
 *
 * @author Nikita Kolychev
 */
@Service
public class PersonService {

    private final PersonRepository personRepo;
    private final PersonMapper personMapper;
    private final AuthorisationService authorisationService;
    private final ModuleProgressService moduleProgressService;
    private final LessonProgressService lessonProgressService;
    private final StepProgressService stepProgressService;

    /**
     * Constructs a new PersonService with necessary dependencies for managing persons.
     *
     * @param personRepo Repository for person data access
     * @param personMapper Mapper for converting DTOs to entity objects
     * @param authorisationService Service for authenticating users via Yandex ID
     * @param moduleProgressService Service for managing module progress
     * @param lessonProgressService Service for managing lesson progress
     * @param stepProgressService Service for managing step progress
     */
    public PersonService(PersonRepository personRepo,
                         PersonMapper personMapper,
                         AuthorisationService authorisationService,
                         ModuleProgressService moduleProgressService,
                         LessonProgressService lessonProgressService,
                         StepProgressService stepProgressService) {
        this.personRepo = personRepo;
        this.personMapper = personMapper;
        this.authorisationService = authorisationService;
        this.moduleProgressService = moduleProgressService;
        this.lessonProgressService = lessonProgressService;
        this.stepProgressService = stepProgressService;
    }

    /**
     * Retrieves a person's data using their OAuth token via the Yandex ID API, creates a new person if they do not exist.
     *
     * @param oauthToken OAuth token used to authenticate and retrieve person data
     * @return ResponseEntity with person data and HTTP status
     */
    public ResponseEntity<GetPersonRequest> getByToken(String oauthToken) {
        GetPersonRequest dto = authorisationService.authorise(oauthToken);

//        TODO: Should move it to a POST endpoint that will create a user
//        Person person = personMapper.toEntity(dto);
//        if (!exist(person)) {
//            person = create(person);
//        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /**
     * Checks if the person already exists in the database.
     *
     * @param person Person to check existence for
     * @return true if exists, false otherwise
     */
    private boolean exist(Person person) {
        return personRepo.existsById(person.getId());
    }
}
