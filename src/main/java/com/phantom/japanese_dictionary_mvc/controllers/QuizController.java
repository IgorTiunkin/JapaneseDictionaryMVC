package com.phantom.japanese_dictionary_mvc.controllers;

import com.phantom.japanese_dictionary_mvc.models.QuizTask;
import com.phantom.japanese_dictionary_mvc.requests.QuizRequest;
import com.phantom.japanese_dictionary_mvc.requests.RequestType;
import com.phantom.japanese_dictionary_mvc.services.NoteService;
import com.phantom.japanese_dictionary_mvc.models.Answer;
import com.phantom.japanese_dictionary_mvc.dto.AnswerDto;
import com.phantom.japanese_dictionary_mvc.util.QuizConverter;
import com.phantom.japanese_dictionary_mvc.util.QuizResultChecker;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/quiz")
@SessionAttributes({"quiztasks"})
public class QuizController {
    private final NoteService noteService;
    private final QuizConverter quizConverter;
    private final QuizResultChecker quizResultChecker;

    public QuizController(NoteService noteService, QuizConverter quizConverter, QuizResultChecker quizResultChecker) {
        this.noteService = noteService;
        this.quizConverter = quizConverter;
        this.quizResultChecker = quizResultChecker;
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
        System.out.println(form.getAnswers());
        System.out.println(quizTasks);
        int result = quizResultChecker.getNumberOfRightAnswers(form, quizTasks);
        model.addAttribute("result", result);
        model.addAttribute("user_answers", form.getAnswers());
        return "quiz/result";
    }



}
