package com.phantom.japanese_dictionary_mvc.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

class WelcomeControllerTest extends BaseControllerTest{

    private WelcomeController welcomeController;

    private final String BASE_PATH = "/welcome";
    private final String WELCOME_VIEW_NAME = "welcome/index";

    @Autowired
    public WelcomeControllerTest(WelcomeController welcomeController) {
        this.welcomeController = welcomeController;
    }

    @BeforeEach
    public void init() {
        mvc = MockMvcBuilders.standaloneSetup(welcomeController)
                .build();
    }

    @Test
    public void whenWelcome_thenWelcome() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(BASE_PATH).accept(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(WELCOME_VIEW_NAME));
    }

}