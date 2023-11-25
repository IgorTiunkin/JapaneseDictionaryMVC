package com.phantom.japanese_dictionary_mvc.controllers;

import com.phantom.japanese_dictionary_mvc.exceptions.FileIOException;
import com.phantom.japanese_dictionary_mvc.requests.GrammarRequest;
import com.phantom.japanese_dictionary_mvc.replies.GrammarDictionaryReply;
import com.phantom.japanese_dictionary_mvc.util.GrammarDictionaryExcelImporter;
import com.phantom.japanese_dictionary_mvc.util.GrammarDictionaryReplyConverter;
import org.apache.poi.EmptyFileException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/grammar")
@SessionAttributes("request") //needed for view in different pages
public class GrammarDictionaryController {

    private final GrammarDictionaryReplyConverter grammarDictionaryReplyConverter;
    private final GrammarDictionaryExcelImporter grammarDictionaryExcelImporter;
    private final static Logger LOGGER = LoggerFactory.getLogger(GrammarDictionaryController.class);

    @Autowired
    public GrammarDictionaryController(GrammarDictionaryReplyConverter grammarDictionaryReplyConverter, GrammarDictionaryExcelImporter grammarDictionaryExcelImporter) {
        this.grammarDictionaryReplyConverter = grammarDictionaryReplyConverter;
        this.grammarDictionaryExcelImporter = grammarDictionaryExcelImporter;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("request", new GrammarRequest());
        return "grammar/index";
    }

    @GetMapping("/show")
    public String show (@ModelAttribute("request") @Valid GrammarRequest grammarRequest, BindingResult bindingResult,
                        Model model,
                        @RequestParam (required = false, value = "page", defaultValue = "0") Integer page) {
        LOGGER.trace("Accepted grammar request: word to find = {}",
                 grammarRequest.getWord());
        if (bindingResult.hasErrors()) {
            return "grammar/index";
        }
        GrammarDictionaryReply grammarDictionaryReply =
                grammarDictionaryReplyConverter.getGrammarDictionaryReplyForCurrentPage(grammarRequest, page);
        model.addAttribute("grammarDictionaryReply", grammarDictionaryReply);
        model.addAttribute("currentPage", page);
        return "grammar/multishow";
    }

    @GetMapping ("/import")
    public String importFile(@ModelAttribute ("emptyFile") String emptyFile) {
        return "grammar/import";
    }


    @PostMapping("/importing")
    public String loadExcelFileToDb(@RequestParam("file") MultipartFile multipartFile,
                                    RedirectAttributes redirectAttributes) {

        try (XSSFWorkbook workbook = new XSSFWorkbook(multipartFile.getInputStream())) {
            grammarDictionaryExcelImporter.importExcelToDB(workbook);
            return "redirect:/grammar";
        } catch (IOException exception) {
            throw new FileIOException("Ошибка при импорте грамматического словаря");
        } catch (EmptyFileException exception) {
            redirectAttributes.addFlashAttribute("emptyFile", "Выберите файл для загрузки");
            LOGGER.info("Файл для загрузки не выбран");
            return "redirect:/grammar/import";
        }
    }



}
