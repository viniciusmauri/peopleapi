package com.developer.personaapi.mapper;

import com.developer.personaapi.dto.request.PersonDTO;
import com.developer.personaapi.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {

	PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

	@Mapping(target = "birthDay", source = "birthDay", dateFormat = "dd-MM-yyyy")
	Person toModel(PersonDTO personDTO);

	PersonDTO toDTO(Person person);
}
