package com.phantom.japanese_dictionary_mvc.util;

import com.phantom.japanese_dictionary_mvc.dto.PersonDTO;
import com.phantom.japanese_dictionary_mvc.models.Person;
import com.phantom.japanese_dictionary_mvc.services.PeopleService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {

    private final PeopleService peopleService;
    private final MessageSource messageSource;

    public PersonValidator(PeopleService peopleService, MessageSource messageSource) {
        this.peopleService = peopleService;
        this.messageSource = messageSource;
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
            String errorMessage = messageSource.getMessage("personvalidator.username.duplicate", null,
                    LocaleContextHolder.getLocale());
            errors.rejectValue("username", "", errorMessage);
        }

    }
}
