package app.mappers;

import app.domain.Person;
import app.dtos.unique.PersonYandexData;

import org.springframework.stereotype.Component;

@Component
public class PersonMapper {

    public Person toEntity(PersonYandexData person) {
        return new Person(Long.valueOf(person.id()));
    }
}