package app.components.mappers;

import app.domain.Person;
import app.dtos.GetPersonRequest;

import org.springframework.stereotype.Component;

@Component
public class PersonMapper {

    public Person toEntity(GetPersonRequest person) {
        return new Person(Long.valueOf(person.id()));
    }
}