package com.phantom.japanese_dictionary_mvc.controllers;

import com.phantom.japanese_dictionary_mvc.dto.AnswerDto;
import com.phantom.japanese_dictionary_mvc.dto.FailedQuizTaskDTO;
import com.phantom.japanese_dictionary_mvc.dto.QuizResultDTO;
import com.phantom.japanese_dictionary_mvc.mappers.QuizResultQuizResultDTOMapper;
import com.phantom.japanese_dictionary_mvc.models.Answer;
import com.phantom.japanese_dictionary_mvc.models.QuizResult;
import com.phantom.japanese_dictionary_mvc.models.QuizTask;
import com.phantom.japanese_dictionary_mvc.replies.GrammarDictionaryReply;
import com.phantom.japanese_dictionary_mvc.requests.QuizRequest;
import com.phantom.japanese_dictionary_mvc.requests.Request;
import com.phantom.japanese_dictionary_mvc.requests.RequestType;
import com.phantom.japanese_dictionary_mvc.services.QuizResultsService;
import com.phantom.japanese_dictionary_mvc.util.QuizConverter;
import com.phantom.japanese_dictionary_mvc.util.QuizResultChecker;
import com.phantom.japanese_dictionary_mvc.util.QuizStatisticsExporter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

class QuizControllerTest extends BaseControllerTest{

    @Mock
    private QuizConverter quizConverter;

    @Mock
    private QuizResultChecker quizResultChecker;

    @Mock
    private QuizResultsService quizResultsService;

    @Mock
    private QuizResultQuizResultDTOMapper quizResultQuizResultDTOMapper;

    @Mock
    private QuizStatisticsExporter quizStatisticsExporter;

    @InjectMocks
    private QuizController quizController;


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

    private final QuizTask TEST_QUIZ_TASK = QuizTask.builder()
            .number(1).question("q1").rightAnswer("a1").options(List.of("a1", "a2"))
            .build();
    private final Answer TEST_ANSWER = new Answer("answer");
    private final QuizResult TEST_QUIZ_RESULT = QuizResult.builder()
            .quizResultId(1).user(null).numberOfRightAnswers(1).numberOfTasks(1).dateOfQuiz(null).failedQuizTasks(null)
            .build();
    private final QuizResultDTO TEST_QUIZ_RESULT_DTO = QuizResultDTO.builder()
            .quizResultId(1).numberOfRightAnswers(1).numberOfTasks(2).dateOfQuiz("date").failedQuizTasks(List.of(new FailedQuizTaskDTO()))
            .build();

    @BeforeEach
    public void init() {
        mvc = MockMvcBuilders.standaloneSetup(quizController)
                .build();
    }


    @Test
    public void whenIndex_thenIndex() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(BASE_PATH).accept(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(INDEX_VIEW_NAME))
                .andReturn();
        Map<String, Object> model = mvcResult.getModelAndView().getModel();
        assertEquals(model.get("quizrequest").getClass(), QuizRequest.class);
    }

    @Test
    public void whenCorrectRequest_thenCorrectShow() throws Exception {
        QuizRequest request = new QuizRequest(RequestType.TRANSLATION, 2, 3);

        Mockito.doReturn(List.of(TEST_QUIZ_TASK)).when(quizConverter).getQuizTasks(request);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(PATH_TO_SHOW).accept(MediaType.TEXT_HTML)
                .flashAttr("quizrequest", request))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(SHOW_VIEW_NAME))
                .andReturn();

        Map<String, Object> model = mvcResult.getModelAndView().getModel();
        List<QuizTask> quiztasks = (List<QuizTask>) model.get("quiztasks");

        QuizTask quizTask = quiztasks.get(0);
        assertEquals(QuizTask.class, quizTask.getClass());
        assertEquals(TEST_QUIZ_TASK, quizTask);

        AnswerDto answerDto = (AnswerDto) model.get("answer_form");

    }

    @Test
    public void when1Option_thenIndex() throws Exception {
        QuizRequest request = new QuizRequest(RequestType.TRANSLATION, 1, 3);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(PATH_TO_SHOW).accept(MediaType.TEXT_HTML)
                .flashAttr("quizrequest", request))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(INDEX_VIEW_NAME))
                .andReturn();
    }

    @Test
    public void when11Option_thenIndex() throws Exception {
        QuizRequest request = new QuizRequest(RequestType.TRANSLATION, 11, 3);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(PATH_TO_SHOW).accept(MediaType.TEXT_HTML)
                .flashAttr("quizrequest", request))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(INDEX_VIEW_NAME))
                .andReturn();
    }

    @Test
    public void when0Tasks_thenIndex() throws Exception {
        QuizRequest request = new QuizRequest(RequestType.TRANSLATION, 2, 0);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(PATH_TO_SHOW).accept(MediaType.TEXT_HTML)
                .flashAttr("quizrequest", request))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(INDEX_VIEW_NAME))
                .andReturn();
    }

    @Test
    public void when21Tasks_thenIndex() throws Exception {
        QuizRequest request = new QuizRequest(RequestType.TRANSLATION, 21, 0);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(PATH_TO_SHOW).accept(MediaType.TEXT_HTML)
                .flashAttr("quizrequest", request))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(INDEX_VIEW_NAME))
                .andReturn();
    }

    @Test
    public void whenCheck_thenResult () throws Exception {
        doReturn(List.of(TEST_ANSWER)).when(quizResultChecker).createUserAnswersForCheck(any(), any());
        doReturn(1).when(quizResultChecker).getNumberOfRightAnswers(any(), any());

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(PATH_TO_CHECK).accept(MediaType.TEXT_HTML)
                .sessionAttr("quiztasks", List.of(TEST_QUIZ_TASK)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(RESULT_VIEW_NAME))
                .andReturn();

        Map<String, Object> model = mvcResult.getModelAndView().getModel();
        assertEquals(1, model.get("numberOfRightAnswers"));

    }

    @Test
    public void whenExportStatistics_thenOk() {
        List <QuizResultDTO> quizResultDTOS = List.of(TEST_QUIZ_RESULT_DTO);
        HttpServletResponse httpServletResponse = new MockHttpServletResponse();
        doReturn(new XSSFWorkbook()).when(quizStatisticsExporter).createXlsFile(quizResultDTOS);
        quizController.exportStatistics(quizResultDTOS, httpServletResponse);
        assertEquals("application/vnd.ms-excel", httpServletResponse.getContentType());
        assertEquals("attachment; filename=quiz results.xlsx",
                httpServletResponse.getHeader("Content-Disposition"));
    }


}