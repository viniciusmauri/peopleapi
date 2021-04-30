package com.developer.personaapi.service;

import com.developer.personaapi.dto.request.PersonDTO;
import com.developer.personaapi.dto.response.MessageResponseDTO;
import com.developer.personaapi.entity.Person;
import com.developer.personaapi.mapper.PersonMapper;
import com.developer.personaapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

	private final PersonMapper personMapper = PersonMapper.INSTANCE;

	private PersonRepository personRepository;

	@Autowired
	public PersonService(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	public MessageResponseDTO createPerson(PersonDTO personDTO) {
		Person personToSave = personMapper.toModel(personDTO);

		Person savedPerson = personRepository.save(personToSave);
		return MessageResponseDTO.builder()
				.message("Pessoa criada com o ID " + savedPerson.getId())
				.build();
	}
}
