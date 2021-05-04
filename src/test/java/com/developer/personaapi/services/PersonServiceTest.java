package com.developer.personaapi.services;

import com.developer.personaapi.dto.mapper.PersonMapper;
import com.developer.personaapi.dto.request.PersonDTO;
import com.developer.personaapi.dto.response.MessageResponseDTO;
import com.developer.personaapi.entity.Person;
import com.developer.personaapi.exception.PersonNotFoundException;
import com.developer.personaapi.repository.PersonRepository;
import com.developer.personaapi.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.developer.personaapi.utils.PersonUtils.createFakeDTO;
import static com.developer.personaapi.utils.PersonUtils.createFakeEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

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

        MessageResponseDTO successMessage = personService.create(personDTO);

        assertEquals("Pessoa Criada com Sucesso com o ID: ", successMessage);
    }

    @Test
    void testGivenPersonIdThenReturnThisPerson() throws PersonNotFoundException {
        PersonDTO expectedPersonDTO = createFakeDTO();
        Person expectedSavedPerson = createFakeEntity();
        expectedPersonDTO.setId(expectedSavedPerson.getId());

        when(personRepository.findById(expectedSavedPerson.getId())).thenReturn(Optional.of(expectedSavedPerson));
        when(personMapper.toDTO(expectedSavedPerson)).thenReturn(expectedPersonDTO);

        PersonDTO personDTO = personService.findById(expectedSavedPerson.getId());

        assertEquals(expectedPersonDTO, expectedSavedPerson);

        assertEquals(expectedSavedPerson.getId(), personDTO.getId());
        assertEquals(expectedSavedPerson.getFirstName(), personDTO.getFirstName());
    }

    @Test
    void testGivenInvalidPersonIdThenThrowException() {
        var invalidPersonId = 1L;
        when(personRepository.findById(invalidPersonId))
                .thenReturn(Optional.ofNullable(any(Person.class)));

        assertThrows(PersonNotFoundException.class, () -> personService.findById(invalidPersonId));
    }

    @Test
    void testGivenValidPersonIdAndUpdateInfoThenReturnSuccessOnUpdate() throws PersonNotFoundException {
        var updatedPersonID = 2L;

        PersonDTO updatedPersonDTORequest = createFakeDTO();
        updatedPersonDTORequest.setId(updatedPersonID);
        updatedPersonDTORequest.setLastName("Pessoa Atualizada");

        Person expectedPersonToUpdate = createFakeEntity();
        expectedPersonToUpdate.setId(updatedPersonID);

        Person expectedPersonUpdated = createFakeEntity();
        expectedPersonUpdated.setId(updatedPersonID);
        expectedPersonToUpdate.setLastName(updatedPersonDTORequest.getLastName());

        when(personRepository.findById(updatedPersonID)).thenReturn(Optional.of(expectedPersonUpdated));
        when(personMapper.toModel(updatedPersonDTORequest)).thenReturn(expectedPersonUpdated);
        when(personRepository.save(any(Person.class))).thenReturn(expectedPersonUpdated);

        MessageResponseDTO successMessage = personService.update(updatedPersonID, updatedPersonDTORequest);

        assertEquals("Sucesso ao atualizar pessoa que tem o ID: 2", successMessage.getMessage());
    }

    @Test
    void testGivenInvalidPersonIdAndUpdateInfoThrowExceptionOnUpdate() throws PersonNotFoundException {
        var invalidPersonID = 1L;

        PersonDTO updatePersonDTORequest = createFakeDTO();
        updatePersonDTORequest.setId(invalidPersonID);
        updatePersonDTORequest.setLastName("Pessoa Atualizada");

        when(personRepository.findById(invalidPersonID)).thenReturn(Optional.ofNullable(any(Person.class)));
        assertThrows(PersonNotFoundException.class, () -> personService.findById(invalidPersonID));
    }

    @Test
    void testGivenValidPersonIdThenReturnSuccessOnDelete() throws PersonNotFoundException {
        var deletedPersonID = 1L;
        when(personRepository.findById(deletedPersonID)).thenReturn(Optional.ofNullable(any(Person.class)));
        assertThrows(PersonNotFoundException.class, () -> personService.findById(deletedPersonID));
    }
}
