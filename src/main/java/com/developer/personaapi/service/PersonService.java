package com.developer.personaapi.service;

import com.developer.personaapi.dto.mapper.PersonMapper;
import com.developer.personaapi.dto.request.PersonDTO;
import com.developer.personaapi.dto.response.MessageResponseDTO;
import com.developer.personaapi.entity.Person;
import com.developer.personaapi.exception.PersonNotFoundException;
import com.developer.personaapi.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {

	private final PersonMapper personMapper;

	private final PersonRepository personRepository;

	public MessageResponseDTO create(PersonDTO personDTO) {
		Person personToSave = personMapper.toModel(personDTO);
		Person savedPerson = personRepository.save(personToSave);

		MessageResponseDTO messageResponse =
				createMessageResponse("Pessoa Criada com Sucesso com o ID: ", savedPerson.getId());
		return messageResponse;
	}

	public PersonDTO findById(Long id) throws PersonNotFoundException {
		Person person = personRepository
				.findById(id)
				.orElseThrow(() -> new PersonNotFoundException(id));

		return personMapper.toDTO(person);
	}

	public void delete(Long id) throws PersonNotFoundException {
		verifyExists(id);

		personRepository.deleteById(id);
	}

	public List<PersonDTO> listAll() {
		List<Person> allPeople = personRepository.findAll();
		return allPeople.stream()
				.map(personMapper::toDTO)
				.collect(Collectors.toList());
	}

	public MessageResponseDTO update(Long id, PersonDTO personDTO) throws PersonNotFoundException {
		verifyExists(id);

		Person updatedPerson = personMapper.toModel(personDTO);
		Person savedPerson = personRepository.save(updatedPerson);

		MessageResponseDTO messageResponse = createMessageResponse("Pessoa atualizada com sucesso" +
						"Id da pessoa atualizada ",
				savedPerson.getId());

		return messageResponse;
	}

	private MessageResponseDTO createMessageResponse(String s, Long id2) {
		return MessageResponseDTO.builder()
				.message(s + id2)
				.build();
	}

	private void verifyExists(Long id) throws PersonNotFoundException {
		personRepository.findById(id)
				.orElseThrow(() -> new PersonNotFoundException(id));
	}
}
