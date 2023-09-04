package com.phantom.japanese_dictionary_mvc.controllers;

import com.phantom.japanese_dictionary_mvc.models.Person;
import com.phantom.japanese_dictionary_mvc.models.QuizResult;
import com.phantom.japanese_dictionary_mvc.models.QuizTask;
import com.phantom.japanese_dictionary_mvc.requests.QuizRequest;
import com.phantom.japanese_dictionary_mvc.requests.RequestType;
import com.phantom.japanese_dictionary_mvc.models.Answer;
import com.phantom.japanese_dictionary_mvc.dto.AnswerDto;
import com.phantom.japanese_dictionary_mvc.security.PersonDetails;
import com.phantom.japanese_dictionary_mvc.services.QuizResultsService;
import com.phantom.japanese_dictionary_mvc.util.QuizConverter;
import com.phantom.japanese_dictionary_mvc.util.QuizResultChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/quiz")
@SessionAttributes({"quiztasks", "numberOfRightAnswers", "user_answers"})
public class QuizController {
    private final QuizConverter quizConverter;
    private final QuizResultChecker quizResultChecker;
    private final QuizResultsService quizResultsService;
    private final static Logger LOGGER = LoggerFactory.getLogger(QuizController.class);


    public QuizController(QuizConverter quizConverter, QuizResultChecker quizResultChecker, QuizResultsService quizResultsService) {
        this.quizConverter = quizConverter;
        this.quizResultChecker = quizResultChecker;
        this.quizResultsService = quizResultsService;
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
        List <QuizResult> quizResultList = quizResultsService.getQuizResultsByUser(currentUser);
        model.addAttribute("quizResultList", quizResultList);
        return "quiz/statistics";
    }

    private Person getCurrentUser() {
        PersonDetails personDetails = (PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return personDetails.getPerson();
    }




}
