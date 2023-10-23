package com.phantom.japanese_dictionary_mvc.controllers;

import com.phantom.japanese_dictionary_mvc.models.Note;
import com.phantom.japanese_dictionary_mvc.requests.RequestType;
import com.phantom.japanese_dictionary_mvc.requests.WritePracticeRequest;
import com.phantom.japanese_dictionary_mvc.services.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/write-test")
public class WritePracticeController {
    private final NoteService noteService;
    private final static Logger LOGGER = LoggerFactory.getLogger(WritePracticeController.class);
    private final static List<RequestType> REQUEST_TYPE_LIST = List.of(RequestType.TRANSLATION, RequestType.SPELLING, RequestType.KANJI, RequestType.KANA);


    @Autowired
    public WritePracticeController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public String index (Model model) {
        model.addAttribute("writePracticeRequest", new WritePracticeRequest());
        model.addAttribute("types", REQUEST_TYPE_LIST);
        return "writepractice/index";
    }

    @GetMapping("/show")
    public String showPractice(@ModelAttribute ("writePracticeRequest")
                                   @Valid WritePracticeRequest writePracticeRequest,
                               BindingResult bindingResult, Model model) {
        LOGGER.trace("Accepted write practice request: requesttype = {}; quantity = {}",
                writePracticeRequest.getRequestType(), writePracticeRequest.getQuantity());
        if (bindingResult.hasErrors()) {
            model.addAttribute("types", REQUEST_TYPE_LIST);
            return "writepractice/index";
        }
        int practiceRequestQuantity = writePracticeRequest.getQuantity();
        List<Note> writePracticeList = noteService.getRandomVariants(practiceRequestQuantity);
        model.addAttribute("writePracticeList", writePracticeList);
        return "writepractice/multishow";
    }

}
