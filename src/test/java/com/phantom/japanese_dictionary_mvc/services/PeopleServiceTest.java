package com.phantom.japanese_dictionary_mvc.services;

import com.phantom.japanese_dictionary_mvc.models.Person;
import com.phantom.japanese_dictionary_mvc.models.QuizResult;
import com.phantom.japanese_dictionary_mvc.repositories.PeopleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class PeopleServiceTest {

    private final Person PERSON = new Person(1, "username", "password", "admin",
            new ArrayList<QuizResult>());

    @Mock
    private PeopleRepository peopleRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private PeopleService peopleService;

    @Test
    public void whenSave_thenTrue() {
        doReturn("test").when(bCryptPasswordEncoder).encode(any());
        doReturn(PERSON).when(peopleRepository).save(any());
        Assertions.assertTrue(peopleService.saveUser(PERSON));
    }

    @Test
    public void whenGetUserByUsername_thenOptional() {
        doReturn(Optional.of(PERSON)).when(peopleRepository).findByUsername(any());
        Assertions.assertEquals(PERSON.getPersonId(), peopleService.getUserByUsername(PERSON.getUsername()).get().getPersonId());
    }

}