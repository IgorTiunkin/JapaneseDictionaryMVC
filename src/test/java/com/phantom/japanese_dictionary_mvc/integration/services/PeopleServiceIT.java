package com.phantom.japanese_dictionary_mvc.integration.services;

import com.phantom.japanese_dictionary_mvc.integration.BaseIT;
import com.phantom.japanese_dictionary_mvc.models.Person;
import com.phantom.japanese_dictionary_mvc.models.QuizResult;
import com.phantom.japanese_dictionary_mvc.services.PeopleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PeopleServiceIT extends BaseIT {

    private final PeopleService peopleService;

    private final Person PERSON = Person.builder()
            .personId(1).username("username").password("password").role("admib").quizResultList(new ArrayList<QuizResult>())
            .build();

    @Autowired
    public PeopleServiceIT(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Test
    public void whenSave_thenTrue() {
        boolean saveUser = peopleService.saveUser(PERSON);
        assertTrue(saveUser);
    }

    @Test
    public void whenPresent_thenPresent() {
        Optional<Person> user = peopleService.getUserByUsername("user");
        assertTrue(user.isPresent());
    }

    @Test
    public void whenAbsent_thenEmpty() {
        Optional<Person> user = peopleService.getUserByUsername("test");
        assertTrue(user.isEmpty());
    }


}
