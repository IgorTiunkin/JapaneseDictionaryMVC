package com.phantom.japanese_dictionary_mvc.controllers;

import com.phantom.japanese_dictionary_mvc.finders.grammar.GrammarFinder;
import com.phantom.japanese_dictionary_mvc.finders.grammar.GrammarFinderFactory;
import com.phantom.japanese_dictionary_mvc.models.GrammarNote;
import com.phantom.japanese_dictionary_mvc.requests.Request;
import com.phantom.japanese_dictionary_mvc.services.GrammarNoteService;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/grammar")
public class GrammarDictionaryController {

    private final GrammarFinderFactory grammarFinderFactory;
    private final GrammarNoteService grammarNoteService;

    @Autowired
    public GrammarDictionaryController(GrammarFinderFactory grammarFinderFactory, GrammarNoteService grammarNoteService) {
        this.grammarFinderFactory = grammarFinderFactory;
        this.grammarNoteService = grammarNoteService;
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
            return "grammar/index";
        }
        GrammarFinder grammarFinder = grammarFinderFactory.getInstance(request);
        List<GrammarNote> notesFromRepository = grammarFinder.getNotesFromRepository(request.getWord());
        model.addAttribute("notes", notesFromRepository);
        return "grammar/multishow";
    }

    @GetMapping ("/import")
    public String excelFile () {
        return "grammar/import";
    }


    @PostMapping("/import")
    public String loadExcelFileToDb(@RequestParam("file") MultipartFile multipartFile) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);


        for(int i=1; i<worksheet.getPhysicalNumberOfRows(); i++) {
            GrammarNote grammarNote = new GrammarNote();

            XSSFRow row = worksheet.getRow(i);
            if (row!=null) {
                XSSFCell currentCell = row.getCell(0);
                if (currentCell!=null) grammarNote.setSource(currentCell.getStringCellValue());
                currentCell = row.getCell(1);
                if (currentCell!=null) grammarNote.setRule(currentCell.getStringCellValue());
                currentCell = row.getCell(2);
                if (currentCell!=null) grammarNote.setExplanation(currentCell.getStringCellValue());
                currentCell = row.getCell(3);
                if (currentCell!=null) grammarNote.setExample(currentCell.getStringCellValue());
                System.out.println(grammarNote);
                grammarNoteService.saveGrammarNote(grammarNote);
            }
        }
        return "redirect:/grammar";
    }
}
