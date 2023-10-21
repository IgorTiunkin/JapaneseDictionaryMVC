package com.phantom.japanese_dictionary_mvc.integration.controllers;

import com.phantom.japanese_dictionary_mvc.controllers.QuizController;
import com.phantom.japanese_dictionary_mvc.dto.AnswerDto;
import com.phantom.japanese_dictionary_mvc.dto.FailedQuizTaskDTO;
import com.phantom.japanese_dictionary_mvc.dto.QuizResultDTO;
import com.phantom.japanese_dictionary_mvc.integration.BaseIT;
import com.phantom.japanese_dictionary_mvc.models.Answer;
import com.phantom.japanese_dictionary_mvc.models.Person;
import com.phantom.japanese_dictionary_mvc.models.QuizResult;
import com.phantom.japanese_dictionary_mvc.models.QuizTask;
import com.phantom.japanese_dictionary_mvc.requests.QuizRequest;
import com.phantom.japanese_dictionary_mvc.requests.RequestType;
import com.phantom.japanese_dictionary_mvc.security.PersonDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class QuizControllerIT extends BaseIT {

    private final QuizController quizController;

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

    private final QuizTask TEST_QUIZ_TASK = new QuizTask(1, "q1", "a1" , List.of("a1", "a2"));
    private final Answer TEST_ANSWER = new Answer("answer");
    private final QuizResult TEST_QUIZ_RESULT = new QuizResult(1, null, 1, 1, null, null);
    private final QuizResultDTO TEST_QUIZ_RESULT_DTO = new QuizResultDTO(1, 1, 2, "date", List.of(new FailedQuizTaskDTO()));


    private final WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    public QuizControllerIT(QuizController quizController, WebApplicationContext context) {
        this.quizController = quizController;
        this.context = context;
    }


    @BeforeEach
    public void init() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    @WithMockUser
    public void whenCorrectRequest_thenCorrectShow() throws Exception {
        QuizRequest request = new QuizRequest(RequestType.TRANSLATION, 2, 2);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(PATH_TO_SHOW).accept(MediaType.TEXT_HTML)
                .flashAttr("quizrequest", request))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(SHOW_VIEW_NAME))
                .andReturn();

        Map<String, Object> model = mvcResult.getModelAndView().getModel();
        List<QuizTask> quiztasks = (List<QuizTask>) model.get("quiztasks");

        assertEquals(2, quiztasks.size());
        assertEquals(2, quiztasks.get(0).getOptions().size());
        assertEquals(2, quiztasks.get(1).getOptions().size());

        AnswerDto answerDto = (AnswerDto) model.get("answer_form");

    }


    @Test
    @WithMockUser
    public void whenCheck1RightAnswer_thenResult1 () throws Exception {
        AnswerDto answerDto = new AnswerDto();
        answerDto.addAnswer(new Answer("a1"));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(PATH_TO_CHECK).accept(MediaType.TEXT_HTML)
                .sessionAttr("quiztasks", List.of(TEST_QUIZ_TASK))
                .flashAttr("answer_form", answerDto))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(RESULT_VIEW_NAME))
                .andReturn();

        Map<String, Object> model = mvcResult.getModelAndView().getModel();
        assertEquals(1, model.get("numberOfRightAnswers"));
        List <Answer> answers = (List<Answer>) model.get("user_answers");
        assertEquals("a1", answers.get(0).getAnswer());
    }

    @Test
    @WithMockUser
    public void whenEmptyAnswer_thenResult0AndStub () throws Exception {
        AnswerDto answerDto = new AnswerDto();

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(PATH_TO_CHECK).accept(MediaType.TEXT_HTML)
                .sessionAttr("quiztasks", List.of(TEST_QUIZ_TASK))
                .flashAttr("answer_form", answerDto))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(RESULT_VIEW_NAME))
                .andReturn();

        Map<String, Object> model = mvcResult.getModelAndView().getModel();
        assertEquals(0, model.get("numberOfRightAnswers"));
        List <Answer> answers = (List<Answer>) model.get("user_answers");
        assertEquals("-1", answers.get(0).getAnswer());
    }

    @Test
    @WithMockUser
    public void whenAbsentAnswer_thenResult0AndStub () throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(PATH_TO_CHECK).accept(MediaType.TEXT_HTML)
                .sessionAttr("quiztasks", List.of(TEST_QUIZ_TASK)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(RESULT_VIEW_NAME))
                .andReturn();

        Map<String, Object> model = mvcResult.getModelAndView().getModel();
        assertEquals(0, model.get("numberOfRightAnswers"));
        List <Answer> answers = (List<Answer>) model.get("user_answers");
        assertEquals("-1", answers.get(0).getAnswer());
    }

    @Test
    @WithUserDetails
    public void whenSave_thenOK() throws Exception {
        List <QuizTask> quizTasks = List.of(TEST_QUIZ_TASK);
        List <Answer> answers = List.of(TEST_ANSWER);
        int numberOfRightAnswers = 0;

        mvc.perform(MockMvcRequestBuilders.get(PATH_TO_SAVE)
                .sessionAttr("quiztasks", quizTasks)
                .sessionAttr("numberOfRightAnswers", numberOfRightAnswers)
                .sessionAttr("user_answers", answers))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:"+PATH_TO_STATISTICS));
    }

    @Test
    @WithUserDetails
    public void whenShowStatisticsForUser_then4() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(PATH_TO_STATISTICS).accept(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(STATISTICS_VIEW_NAME))
                .andReturn();
        Map<String, Object> model = mvcResult.getModelAndView().getModel();
        List <QuizResultDTO> quizResultHistoryDTO = (List<QuizResultDTO>) model.get("quizResultList");
        assertEquals(4, quizResultHistoryDTO.size());
    }

    @Test
    @WithUserDetails (value = "user2")
    public void whenShowStatisticsForUser2_then2() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(PATH_TO_STATISTICS).accept(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(STATISTICS_VIEW_NAME))
                .andReturn();
        Map<String, Object> model = mvcResult.getModelAndView().getModel();
        List <QuizResultDTO> quizResultHistoryDTO = (List<QuizResultDTO>) model.get("quizResultList");
        assertEquals(2, quizResultHistoryDTO.size());
    }


}
