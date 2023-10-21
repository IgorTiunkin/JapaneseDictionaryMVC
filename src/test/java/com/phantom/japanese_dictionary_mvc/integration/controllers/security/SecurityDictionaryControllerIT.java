package com.phantom.japanese_dictionary_mvc.integration.controllers.security;

import com.phantom.japanese_dictionary_mvc.controllers.DictionaryController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

public class SecurityDictionaryControllerIT extends BaseSecurityControllerIT {


    private final String BASE_PATH = "/dictionary";
    private final String PATH_TO_SHOW = BASE_PATH + "/show";
    private final String PATH_TO_IMPORT = BASE_PATH + "/import";
    private final String PATH_TO_IMPORTING = BASE_PATH + "/importing";

    private final String INDEX_VIEW_NAME = "dictionaries/index";
    private final String MULTISHOW_VIEW_NAME = "dictionaries/multishow";
    private final String IMPORT_VIEW_NAME = "dictionaries/import";

    @Autowired
    public SecurityDictionaryControllerIT(WebApplicationContext context) {
        super(context);
    }


    @Test
    public void whenUnloggedBase_ThenRedirect() throws Exception {
        whenUnlogged_thenRedirect(BASE_PATH);
    }

    @Test
    @WithMockUser
    public void whenLoggedBase_ThenOk() throws Exception {
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
