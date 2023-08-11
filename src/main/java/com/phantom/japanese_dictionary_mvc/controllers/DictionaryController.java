package com.phantom.japanese_dictionary_mvc.controllers;


import com.phantom.japanese_dictionary_mvc.models.Note;
import com.phantom.japanese_dictionary_mvc.models.Request;
import com.phantom.japanese_dictionary_mvc.models.RequestType;
import com.phantom.japanese_dictionary_mvc.util.ReplyConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/dictionary")
public class DictionaryController {

    private final ReplyConverter replyConverter;

    public DictionaryController(ReplyConverter replyConverter) {
        this.replyConverter = replyConverter;
    }


    @GetMapping
    public String index (Model model) {
        model.addAttribute("request", new Request());
        model.addAttribute("types", RequestType.values());
        return "dictionaries/index";
    }

    @GetMapping("/show")
    public String show (@ModelAttribute ("request") Request request, Model model) {
        List<Note> fullMatchNotes = replyConverter.getFullReplies(request);
        model.addAttribute("fullMatchNotes", fullMatchNotes);
        List<Note> partialMatchNotes = replyConverter.getPartialReplies(request);
        model.addAttribute("partialMatchNotes", partialMatchNotes);
        return "dictionaries/multishow";
    }

}
