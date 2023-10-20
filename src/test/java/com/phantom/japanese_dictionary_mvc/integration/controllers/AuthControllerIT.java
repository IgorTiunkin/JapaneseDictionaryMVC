package com.phantom.japanese_dictionary_mvc.integration.controllers;

import com.phantom.japanese_dictionary_mvc.controllers.AuthController;
import com.phantom.japanese_dictionary_mvc.dto.PersonDTO;
import com.phantom.japanese_dictionary_mvc.integration.BaseIT;
import com.phantom.japanese_dictionary_mvc.models.Person;
import com.phantom.japanese_dictionary_mvc.services.PeopleService;
import com.phantom.japanese_dictionary_mvc.util.PersonValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

public class AuthControllerIT extends BaseIT {
    private final AuthController authController;

    private final String BASE_PATH = "/auth";

    private final String PATH_TO_LOGIN = BASE_PATH + "/login";
    private final String PATH_TO_SIGN_UP = BASE_PATH + "/signup";
    private final String PATH_TO_SAVE_USER = BASE_PATH + "/saveUser";

    private final String LOGIN_VIEW_NAME = "auth/login";
    private final String SIGN_UP_VIEW_NAME = "auth/signup";

    private MockMvc mvc;

    @Autowired
    public AuthControllerIT(AuthController authController) {
        this.authController = authController;
    }

    @BeforeEach
    public void init() {
        mvc = MockMvcBuilders.standaloneSetup(authController)
                .build();
    }

    @Test
    public void whenSaveCorrect_thenRedirectLogin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(PATH_TO_SAVE_USER)
                .flashAttr("person", new PersonDTO("username", "password"))
                .accept(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/"+LOGIN_VIEW_NAME));
    }

    @Test
    public void whenSaveNull_thenSignUp() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(PATH_TO_SAVE_USER)
                .flashAttr("person", new PersonDTO(null, null))
                .accept(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(SIGN_UP_VIEW_NAME));
    }

    @Test
    public void whenSavePresent_thenSignUp() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(PATH_TO_SAVE_USER)
                .flashAttr("person", new PersonDTO("user", "test"))
                .accept(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(SIGN_UP_VIEW_NAME));
    }



}
