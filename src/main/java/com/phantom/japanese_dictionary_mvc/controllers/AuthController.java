package com.phantom.japanese_dictionary_mvc.controllers;

import com.phantom.japanese_dictionary_mvc.dto.PersonDTO;
import com.phantom.japanese_dictionary_mvc.models.Person;
import com.phantom.japanese_dictionary_mvc.services.PeopleService;
import com.phantom.japanese_dictionary_mvc.util.PersonValidator;
import org.modelmapper.ModelMapper;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final PeopleService peopleService;
    private final ModelMapper modelMapper;
    private final PersonValidator personValidator;

    public AuthController(PeopleService peopleService, ModelMapper modelMapper, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.modelMapper = modelMapper;
        this.personValidator = personValidator;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/signup")
    public String signUp(Model model) {
        model.addAttribute("person", new PersonDTO());
        return "auth/signup";
    }

    @PostMapping("/saveUser")
    public String saveUser (@ModelAttribute ("person") @Valid PersonDTO userDTO, BindingResult bindingResult) {
        personValidator.validate(userDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "auth/signup";
        }
        Person user = convertToPerson(userDTO);
        peopleService.saveUser(user);
        return "redirect:/auth/login";
    }

    private Person convertToPerson(PersonDTO userDTO) {
        Person user = modelMapper.map(userDTO, Person.class);
        user.setRole("ROLE_USER");
        return user;
    }


}
