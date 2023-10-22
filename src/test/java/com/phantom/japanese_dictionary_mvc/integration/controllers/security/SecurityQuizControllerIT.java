package com.phantom.japanese_dictionary_mvc.integration.controllers.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.context.WebApplicationContext;

public class SecurityQuizControllerIT extends BaseSecurityControllerIT{

    private final String BASE_PATH = "/quiz";
    private final String PATH_TO_SHOW = BASE_PATH + "/show";
    private final String PATH_TO_CHECK = BASE_PATH + "/check";
    private final String PATH_TO_SAVE = BASE_PATH + "/saveResult";
    private final String PATH_TO_STATISTICS = BASE_PATH + "/showStatistics";
    private final String PATH_TO_EXPORT = BASE_PATH + "/exportStatistics";

    private final String INDEX_VIEW_NAME = "quiz/index";
    private final String SHOW_VIEW_NAME = "quiz/multishow";
    private final String RESULT_VIEW_NAME = "quiz/result";
    private final String STATISTICS_VIEW_NAME = "quiz/statistics";

    @Autowired
    public SecurityQuizControllerIT(WebApplicationContext context) {
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
    public void whenUnloggedCheck_thenRedirect() throws Exception {
        whenUnlogged_thenRedirect(PATH_TO_CHECK);
    }


    @Test
    public void whenUnloggedSave_thenRedirect() throws Exception {
        whenUnlogged_thenRedirect(PATH_TO_SAVE);
    }

    @Test
    public void whenUnloggedStatistics_thenRedirect() throws Exception {
        whenUnlogged_thenRedirect(PATH_TO_STATISTICS);
    }

    @Test
    public void whenUnloggedExport_thenRedirect() throws Exception {
        whenUnlogged_thenRedirect(PATH_TO_EXPORT);
    }

}
