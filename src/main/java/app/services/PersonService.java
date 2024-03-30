package app.services;

import app.domain.Person;
import app.dtos.PersonRequestDto;
import app.mappers.PersonMapper;
import app.repositories.PersonRepository;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

// TODO: As soon as I've finished the methods, document them using the comments
@Service
public class PersonService {

    private PersonRepository personRepo;
    private RestTemplate rest;
    private ModuleProgressService moduleProgressService;
    private LessonProgressService lessonProgressService;
    private StepProgressService stepProgressService;

    public PersonService(PersonRepository personRepo,
                         RestTemplate rest,
                         ModuleProgressService moduleProgressService,
                         LessonProgressService lessonProgressService,
                         StepProgressService stepProgressService) {
        this.personRepo = personRepo;
        this.rest = rest;
        this.moduleProgressService = moduleProgressService;
        this.lessonProgressService = lessonProgressService;
        this.stepProgressService = stepProgressService;
    }

//    Finds a Person who has this oauthToken using Yandex ID API.
//    If the Person is new, they are saved in the db
    public ResponseEntity<PersonRequestDto> find(String oauthToken) {
        PersonRequestDto dto = request(oauthToken);
        Person person = PersonMapper.MAPPER.toEntity(dto);
        if (!exist(person)) {
            person = create(person);
        }
        //    TODO:
        //     Return PersonResponseDto
        //     Return the Person's data as well as the Progress data
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // Sends a GET request to Yandex ID API to get the Person's data.
    private PersonRequestDto request(String oauthToken) {
        // Set up the request
        String url = "https://login.yandex.ru/info";
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "OAuth " + oauthToken);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        // Get the response
        ResponseEntity<PersonRequestDto> response = rest.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                PersonRequestDto.class);
        return response.getBody();
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
