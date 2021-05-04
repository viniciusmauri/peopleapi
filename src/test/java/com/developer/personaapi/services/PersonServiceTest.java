package com.developer.personaapi.services;

import com.developer.personaapi.dto.mapper.PersonMapper;
import com.developer.personaapi.dto.request.PersonDTO;
import com.developer.personaapi.dto.response.MessageResponseDTO;
import com.developer.personaapi.entity.Person;
import com.developer.personaapi.repository.PersonRepository;
import com.developer.personaapi.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static com.developer.personaapi.utils.PersonUtils.createFakeDTO;
import static com.developer.personaapi.utils.PersonUtils.createFakeEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonMapper personMapper;

    @InjectMocks
    private PersonService personService;

    @Test
    void testeGivenPersonDTOThenReturnSuccessSavedMessage() {
        PersonDTO personDTO = createFakeDTO();
        Person expectedSavedPerson = createFakeEntity();

        when(personMapper.toModel(personDTO)).thenReturn(expectedSavedPerson);
        when(personRepository.save(any(Person.class))).thenReturn(expectedSavedPerson);

        MessageResponseDTO expectedSuccessMessage = createExpectedSuccessMessage(expectedSavedPerson.getId());
        MessageResponseDTO successMessage = personService.create(personDTO);

        assertEquals(expectedSuccessMessage, successMessage);
    }

    private MessageResponseDTO createExpectedSuccessMessage(Long SavedPersonId) {
        return MessageResponseDTO.builder()
                .message("Pessoa Criada com Sucesso com o ID: " + SavedPersonId)
                .build();
    }


}
