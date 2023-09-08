package com.phantom.japanese_dictionary_mvc.controllers;

import com.phantom.japanese_dictionary_mvc.dto.FailedQuizTaskDTO;
import com.phantom.japanese_dictionary_mvc.dto.QuizResultDTO;
import com.phantom.japanese_dictionary_mvc.mappers.QuizResultQuizResultDTOMapper;
import com.phantom.japanese_dictionary_mvc.models.*;
import com.phantom.japanese_dictionary_mvc.requests.QuizRequest;
import com.phantom.japanese_dictionary_mvc.requests.RequestType;
import com.phantom.japanese_dictionary_mvc.dto.AnswerDto;
import com.phantom.japanese_dictionary_mvc.security.PersonDetails;
import com.phantom.japanese_dictionary_mvc.services.QuizResultsService;
import com.phantom.japanese_dictionary_mvc.util.QuizConverter;
import com.phantom.japanese_dictionary_mvc.util.QuizResultChecker;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/quiz")
@SessionAttributes({"quiztasks", "numberOfRightAnswers", "user_answers", "quizResultList"})
public class QuizController {
    private final QuizConverter quizConverter;
    private final QuizResultChecker quizResultChecker;
    private final QuizResultsService quizResultsService;
    private final QuizResultQuizResultDTOMapper quizResultQuizResultDTOMapper;
    private final static Logger LOGGER = LoggerFactory.getLogger(QuizController.class);


    public QuizController(QuizConverter quizConverter, QuizResultChecker quizResultChecker, QuizResultsService quizResultsService, QuizResultQuizResultDTOMapper quizResultQuizResultDTOMapper) {
        this.quizConverter = quizConverter;
        this.quizResultChecker = quizResultChecker;
        this.quizResultsService = quizResultsService;
        this.quizResultQuizResultDTOMapper = quizResultQuizResultDTOMapper;
    }


    @GetMapping
    public String index (Model model) {
        model.addAttribute("quizrequest", new QuizRequest());
        model.addAttribute("types", List.of(RequestType.KANJI, RequestType.TRANSLATION));
        return "quiz/index";
    }

    @GetMapping("/show")
    public String showQuiz(@ModelAttribute ("quizrequest") @Valid QuizRequest quizRequest,
                           BindingResult bindingResult, Model model) {
        LOGGER.trace("Accepted quiz request: request type = {}; number of tasks = {}; number of options = {}",
                quizRequest.getRequestType(),quizRequest.getNumberOfTasks(), quizRequest.getNumberOfOptions());
        if (bindingResult.hasErrors()) {
            return "quiz/index";
        }

        List <QuizTask> quizTasks = quizConverter.getQuizTasks(quizRequest);
        model.addAttribute("quiztasks", quizTasks);

        AnswerDto answerDto = new AnswerDto();
        for (int i = 0; i < quizRequest.getNumberOfTasks(); i++) answerDto.addAnswer(new Answer("1"));

        model.addAttribute("answer_form", answerDto);
        return "quiz/multishow";
    }

    @GetMapping("/check")
    public String checkAnswer(@ModelAttribute ("answer_form") AnswerDto form,
                              @ModelAttribute ("quiztasks") List <QuizTask> quizTasks,
                              Model model) {
        //User is not always fill all fields - as result we can get null, list of less size or null values
        // for view we need to create full size list with stub values instead of null
        List<Answer> userAnswersForCheck = quizResultChecker.createUserAnswersForCheck(quizTasks, form.getAnswers());
        form.setAnswers(userAnswersForCheck);
        int numberOfRightAnswers = quizResultChecker.getNumberOfRightAnswers(userAnswersForCheck, quizTasks);
        model.addAttribute("numberOfRightAnswers", numberOfRightAnswers);
        model.addAttribute("user_answers", form.getAnswers());
        return "quiz/result";
    }

    @GetMapping("/saveResult")
    public String saveResult (@ModelAttribute ("quiztasks") List <QuizTask> quizTasks,
                              @ModelAttribute ("numberOfRightAnswers") int numberOfRightAnswers,
                              @ModelAttribute ("user_answers") List<Answer> userAnswers) {
            QuizResult quizResult = quizResultChecker.createQuizResultForSave(
                    numberOfRightAnswers, quizTasks, userAnswers, getCurrentUser());
            quizResultsService.saveQuizResult(quizResult);
        return "redirect:/quiz/showStatistics";

    }

    @GetMapping("/showStatistics")
    public String showStatistics (Model model) {
        Person currentUser = getCurrentUser();
        List <QuizResult> quizResultHistory = quizResultsService.getQuizResultsByUser(currentUser);
        List <QuizResultDTO> quizResultHistoryDTO =
                quizResultHistory.stream().
                map(quizResultQuizResultDTOMapper::quizResultToQuizResultDTO)
                .collect(Collectors.toList());
        model.addAttribute("quizResultList", quizResultHistoryDTO);
        return "quiz/statistics";
    }


    private Person getCurrentUser() {
        PersonDetails personDetails = (PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return personDetails.getPerson();
    }


    @GetMapping ("/exportStatistics")
    public String exportStatistics(@ModelAttribute ("quizResultList") List <QuizResultDTO> quizResultDTOS,
                                   HttpServletResponse response) throws IOException {
        Workbook workbook = createXlsFile(quizResultDTOS);
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=quiz results.xlsx");
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
            workbook.close();
        }
        return "redirect:/quiz/showStatistics";
    }

    private Workbook createXlsFile(List<QuizResultDTO> quizResultDTOS) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Results");
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Дата квиза");
        headerRow.createCell(1).setCellValue("Число правильных ответов");
        headerRow.createCell(2).setCellValue("Число заданий");
        headerRow.createCell(3).setCellValue("Задания с неправильным ответом");

        int rowNum = 1;
        if (quizResultDTOS !=null) {
            for (QuizResultDTO quizResultDTO : quizResultDTOS) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(quizResultDTO.getDateOfQuiz());
                row.createCell(1).setCellValue(quizResultDTO.getNumberOfRightAnswers());
                row.createCell(2).setCellValue(quizResultDTO.getNumberOfTasks());

                StringBuilder stringBuilder = new StringBuilder();
                for (FailedQuizTaskDTO failedQuizTask : quizResultDTO.getFailedQuizTasks()) {
                     stringBuilder.append(failedQuizTask.getFailedQuestion()).append("; ");
                }
                row.createCell(3).setCellValue(stringBuilder.toString());
            }
        }
        return workbook;
    }




}
