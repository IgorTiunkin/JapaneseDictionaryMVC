package com.phantom.japanese_dictionary_mvc.controllers;

import com.phantom.japanese_dictionary_mvc.dto.PersonDTO;
import com.phantom.japanese_dictionary_mvc.models.Person;
import com.phantom.japanese_dictionary_mvc.services.PeopleService;
import com.phantom.japanese_dictionary_mvc.util.PersonValidator;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class AuthControllerTest {

    @Mock
    private PeopleService peopleService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PersonValidator personValidator;

    @InjectMocks
    private AuthController authController;

    private MockMvc mvc;

    private final String BASE_PATH = "/auth";

    private final String PATH_TO_LOGIN = BASE_PATH + "/login";
    private final String PATH_TO_SIGN_UP = BASE_PATH + "/signup";
    private final String PATH_TO_SAVE_USER = BASE_PATH + "/saveUser";

    private final String LOGIN_VIEW_NAME = "auth/login";
    private final String SIGN_UP_VIEW_NAME = "auth/signup";


    @BeforeEach
    public void init() {
        mvc = MockMvcBuilders.standaloneSetup(authController)
                .build();
    }


    @Test
    public void whenLogin_loginPage() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(PATH_TO_LOGIN)
                .accept(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(LOGIN_VIEW_NAME));
    }

    @Test
    public void whenSignUp_thenSignUp() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(PATH_TO_SIGN_UP)
                .accept(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(SIGN_UP_VIEW_NAME))
                .andExpect(MockMvcResultMatchers.model().attribute("person", Matchers.instanceOf(PersonDTO.class)));

    }

    @Test
    public void whenSaveCorrect_thenRedirectLogin() throws Exception {
        doNothing().when(personValidator).validate(any(),any());
        doReturn(true).when(peopleService).saveUser(any());
        doReturn(new Person()).when(modelMapper).map(any(),any());

        mvc.perform(MockMvcRequestBuilders.post(PATH_TO_SAVE_USER)
                .flashAttr("person", new PersonDTO("username", "password"))
                .accept(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/"+LOGIN_VIEW_NAME));
    }

    @Test
    public void whenSaveInCorrect_thenSignUp() throws Exception {
        doNothing().when(personValidator).validate(any(),any());
        doReturn(true).when(peopleService).saveUser(any());
        doReturn(new Person()).when(modelMapper).map(any(),any());
        mvc.perform(MockMvcRequestBuilders.post(PATH_TO_SAVE_USER)
                .flashAttr("person", new PersonDTO(null, null))
                .accept(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(SIGN_UP_VIEW_NAME));
    }



}