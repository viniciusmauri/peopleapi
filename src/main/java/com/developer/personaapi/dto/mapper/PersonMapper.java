package com.developer.personaapi.dto.mapper;

import com.developer.personaapi.dto.request.PersonDTO;
import com.developer.personaapi.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PersonMapper {

	@Mapping(target = "birthDate", source = "birthDate", dateFormat = "dd-MM-yyyy")
	Person toModel(PersonDTO dto);

	PersonDTO toDTO(Person dto);
}