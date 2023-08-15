package com.phantom.japanese_dictionary_mvc.controllers;

import com.phantom.japanese_dictionary_mvc.models.Note;
import com.phantom.japanese_dictionary_mvc.requests.RequestType;
import com.phantom.japanese_dictionary_mvc.requests.WritePracticeRequest;
import com.phantom.japanese_dictionary_mvc.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/write-test")
public class WritePracticeController {
    private final NoteService noteService;

    @Autowired
    public WritePracticeController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public String index (Model model) {
        model.addAttribute("writePracticeRequest", new WritePracticeRequest());
        model.addAttribute("types", RequestType.values());
        return "writepractice/index";
    }

    @GetMapping("/show")
    public String showPractice(@ModelAttribute ("writePracticeRequest")
                                   @Valid WritePracticeRequest writePracticeRequest,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("types", RequestType.values());
            return "writepractice/index";
        }
        List<Note> writePracticeList = noteService.getRandomVariants(writePracticeRequest.getQuantity());
        model.addAttribute("writePracticeList", writePracticeList);
        return "writepractice/multishow";
    }

}
