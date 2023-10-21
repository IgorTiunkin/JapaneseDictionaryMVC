package com.phantom.japanese_dictionary_mvc.integration.controllers.security;

import com.phantom.japanese_dictionary_mvc.integration.BaseIT;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class BaseSecurityControllerIT extends BaseIT {

    private final WebApplicationContext context;

    protected MockMvc mvc;

    @Autowired
    public BaseSecurityControllerIT(WebApplicationContext context) {
        this.context = context;
    }

    @BeforeEach
    public void init() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }


    protected void whenLogged_thenOk(String path, String viewName) throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(path).accept(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(viewName));
    }


    protected void whenUnlogged_thenRedirect(String path) throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(path).accept(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/auth/login"));
    }

    protected void whenUser_thenForbidden(String path) throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(path).accept(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
