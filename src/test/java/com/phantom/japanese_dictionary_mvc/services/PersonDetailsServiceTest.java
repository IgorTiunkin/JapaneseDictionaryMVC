package com.phantom.japanese_dictionary_mvc.services;

import com.phantom.japanese_dictionary_mvc.models.Person;
import com.phantom.japanese_dictionary_mvc.models.QuizResult;
import com.phantom.japanese_dictionary_mvc.repositories.PeopleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class PersonDetailsServiceTest {

    private final Person PERSON = new Person(1, "username", "password", "admin",
            new ArrayList<QuizResult>());

    @Mock
    private PeopleRepository peopleRepository;

    @InjectMocks
    private PersonDetailsService personDetailsService;

    @Test
    public void whenUserAbsent_throwUserNotFound() {
        doReturn(Optional.empty()).when(peopleRepository).findByUsername(any());
        assertThrows(UsernameNotFoundException.class,
                () -> personDetailsService.loadUserByUsername("test"));
    }

    @Test
    public void whenUserPresent_thenPersonDetails() {
        doReturn(Optional.of(PERSON)).when(peopleRepository).findByUsername(any());
        assertEquals(PERSON.getUsername(),
                personDetailsService.loadUserByUsername(PERSON.getUsername()).getUsername());
    }

}