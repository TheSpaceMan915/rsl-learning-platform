package app.mappers;

import app.domain.Person;
import app.dtos.PersonRequestDto;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PersonMapper {

    PersonMapper MAPPER = Mappers.getMapper(PersonMapper.class);

    Person toEntity(PersonRequestDto personRequestDto);

    PersonRequestDto toDto(Person person);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Person partialUpdate(PersonRequestDto personRequestDto, @MappingTarget Person person);
}