package com.developer.personaapi.service;

import com.developer.personaapi.dto.response.MessageResponseDTO;
import com.developer.personaapi.entity.Person;
import com.developer.personaapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

	private PersonRepository personRepository;

	@Autowired
	public PersonService(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	public MessageResponseDTO createPerson(Person person) {
		Person personSaved = personRepository.save(person);
		return MessageResponseDTO.builder()
				.message("Pessoa criada com o ID " + personSaved.getId())
				.build();
	}
}
