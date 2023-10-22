package com.phantom.japanese_dictionary_mvc.integration.controllers.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

public class SecurityGrammarDictionaryControllerIT extends BaseSecurityControllerIT{

    private final String BASE_PATH = "/grammar";
    private final String PATH_TO_SHOW = BASE_PATH + "/show";
    private final String PATH_TO_IMPORT = BASE_PATH + "/import";
    private final String PATH_TO_IMPORTING = BASE_PATH + "/importing";

    private final String INDEX_VIEW_NAME = "grammar/index";
    private final String SHOW_VIEW_NAME = "grammar/multishow";
    private final String IMPORT_VIEW_NAME = "grammar/import";

    @Autowired
    public SecurityGrammarDictionaryControllerIT(WebApplicationContext context) {
        super(context);
    }

    @Test
    public void whenUnloggedBase_thenRedirect() throws Exception {
        whenUnlogged_thenRedirect(BASE_PATH);
    }

    @Test
    @WithMockUser
    public void whenLoggedBase_thenOk() throws Exception {
        whenLogged_thenOk(BASE_PATH, INDEX_VIEW_NAME);
    }

    @Test
    public void whenUnloggedShow_thenRedirect() throws Exception {
        whenUnlogged_thenRedirect(PATH_TO_SHOW);
    }

    @Test
    public void whenUnloggedImport_thenRedirect() throws Exception {
        whenUnlogged_thenRedirect(PATH_TO_IMPORT);
    }

    @Test
    @WithMockUser
    public void whenDefaultImport_thenForbidden() throws Exception {
        whenUser_thenForbidden(PATH_TO_IMPORT);
    }

    @Test
    public void whenUnloggedImporting_thenRedirect() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(PATH_TO_IMPORTING).accept(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/auth/login"));
    }


}
