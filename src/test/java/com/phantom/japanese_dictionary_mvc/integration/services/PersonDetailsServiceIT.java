package com.phantom.japanese_dictionary_mvc.integration.services;

import com.phantom.japanese_dictionary_mvc.integration.BaseIT;
import com.phantom.japanese_dictionary_mvc.services.PersonDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class PersonDetailsServiceIT extends BaseIT {

    private final PersonDetailsService personDetailsService;

    @Autowired
    public PersonDetailsServiceIT(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Test
    public void whenUserAbsent_thenException() {
        assertThrows(UsernameNotFoundException.class,
                () -> personDetailsService.loadUserByUsername("test"));
    }

    @Test
    public void whenUserPresent_thenNotException() {
        assertDoesNotThrow(()->personDetailsService.loadUserByUsername("user"));
    }

}
