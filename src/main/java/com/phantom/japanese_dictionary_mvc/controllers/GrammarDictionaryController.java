package com.phantom.japanese_dictionary_mvc.controllers;

import com.phantom.japanese_dictionary_mvc.finders.grammar.GrammarFinder;
import com.phantom.japanese_dictionary_mvc.finders.grammar.GrammarFinderFactory;
import com.phantom.japanese_dictionary_mvc.models.GrammarNote;
import com.phantom.japanese_dictionary_mvc.models.Request;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/grammar")
public class GrammarDictionaryController {

    private final GrammarFinderFactory grammarFinderFactory;

    public GrammarDictionaryController(GrammarFinderFactory grammarFinderFactory) {
        this.grammarFinderFactory = grammarFinderFactory;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("request", new Request());
        return "grammar/index";
    }

    @GetMapping("/show")
    public String show (@ModelAttribute("request") @Valid Request request, BindingResult bindingResult
            , Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("request", new Request());
            return "grammar/index";
        }
        GrammarFinder grammarFinder = grammarFinderFactory.getInstance(request);
        List<GrammarNote> notesFromRepository = grammarFinder.getNotesFromRepository(request.getWord());
        model.addAttribute("notes", notesFromRepository);
        return "grammar/multishow";
    }
}
