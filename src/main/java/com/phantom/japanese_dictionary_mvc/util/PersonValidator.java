package com.phantom.japanese_dictionary_mvc.util;

import com.phantom.japanese_dictionary_mvc.dto.PersonDTO;
import com.phantom.japanese_dictionary_mvc.models.Person;
import com.phantom.japanese_dictionary_mvc.services.PeopleService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;
import java.util.Set;

@Component
public class PersonValidator implements Validator {

    private final PeopleService peopleService;

    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        PersonDTO user = (PersonDTO) o;
        String currentUsername = user.getUsername();
        Optional <Person> dublicateUser = peopleService.getUserByUsername(currentUsername);
        if (dublicateUser.isPresent()) {
            errors.rejectValue("username", "", "Пользователь с таким именем уже существует. Выберите другое.");
        }

    }
}
