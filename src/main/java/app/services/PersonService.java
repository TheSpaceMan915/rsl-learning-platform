package app.services;

import app.domain.Person;
import app.dtos.PersonDto;
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

    public PersonService(PersonRepository personRepo,
                         RestTemplate rest) {
        this.personRepo = personRepo;
        this.rest = rest;
    }

//    Finds a Person who has this oauthToken using Yandex ID API.
//    If the Person is new, they are saved in the db
    public ResponseEntity<PersonDto> find(String oauthToken) {
        PersonDto dto = request(oauthToken);
        if (!exist(dto)) {
            create(dto);
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //    TODO: Write the logic to work with refresh tokens
    // Sends a GET request to Yandex ID API to get the Person's data.
    private PersonDto request(String oauthToken) {
        // Set up the request
        String url = "https://login.yandex.ru/info";
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "OAuth " + oauthToken);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        // Get the response
        ResponseEntity<PersonDto> response = rest.exchange(
                url,
                HttpMethod.GET, requestEntity,
                PersonDto.class);
        return response.getBody();
    }

    private boolean exist(PersonDto dto) {
        Long id = Long.valueOf(dto.id());
        return personRepo.existsById(id);
    }

    private Person create(PersonDto dto) {
        Person person = PersonMapper.MAPPER.toEntity(dto);
//        TODO:
//         Create the Progress services
//         Write the methods linking the Progress entities to the Person
        return personRepo.save(person);
    }
}
