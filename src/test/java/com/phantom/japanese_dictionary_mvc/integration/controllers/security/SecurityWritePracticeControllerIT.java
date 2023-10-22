package com.phantom.japanese_dictionary_mvc.integration.controllers.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.context.WebApplicationContext;

public class SecurityWritePracticeControllerIT extends BaseSecurityControllerIT{

    private final String BASE_PATH = "/write-test";
    private final String PATH_TO_SHOW = BASE_PATH + "/show";

    private final String INDEX_VIEW_NAME = "writepractice/index";
    private final String SHOW_VIEW_NAME = "writepractice/multishow";

    @Autowired
    public SecurityWritePracticeControllerIT(WebApplicationContext context) {
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
}
