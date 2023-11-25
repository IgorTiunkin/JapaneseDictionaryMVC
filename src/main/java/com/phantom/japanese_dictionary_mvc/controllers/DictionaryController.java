package com.phantom.japanese_dictionary_mvc.controllers;


import com.phantom.japanese_dictionary_mvc.exceptions.FileIOException;
import com.phantom.japanese_dictionary_mvc.requests.Request;
import com.phantom.japanese_dictionary_mvc.requests.RequestType;
import com.phantom.japanese_dictionary_mvc.replies.DictionaryReply;
import com.phantom.japanese_dictionary_mvc.util.DictionaryExcelImporter;
import com.phantom.japanese_dictionary_mvc.util.DictionaryReplyConverter;
import org.apache.poi.EmptyFileException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/dictionary")
@SessionAttributes("request") //needed for view in different pages
public class DictionaryController {

    private final DictionaryReplyConverter dictionaryReplyConverter;
    private final DictionaryExcelImporter dictionaryExcelImporter;
    private final static Logger LOGGER = LoggerFactory.getLogger(DictionaryController.class);

    public DictionaryController(DictionaryReplyConverter dictionaryReplyConverter, DictionaryExcelImporter dictionaryExcelImporter) {
        this.dictionaryReplyConverter = dictionaryReplyConverter;
        this.dictionaryExcelImporter = dictionaryExcelImporter;
    }

    @GetMapping
    public String index (Model model) {
        model.addAttribute("request", new Request());
        model.addAttribute("types", RequestType.values());
        return "dictionaries/index";
    }

    @GetMapping("/show")
    public String show (@ModelAttribute ("request") @Valid Request request, BindingResult bindingResult,
                        Model model,
                        @RequestParam (required = false, value = "page", defaultValue = "0") Integer page) {
        LOGGER.trace("Accepted dictionary request: request type = {}; word to find = {}; match = {}",
                request.getRequestType(), request.getWord(), request.isOnlyFullMatch());
        if (bindingResult.hasErrors()) {
            model.addAttribute("types", RequestType.values());
            return "dictionaries/index";
        }

        DictionaryReply dictionaryReply = dictionaryReplyConverter.getDictionaryReplyForCurrentPage(request, page);
        model.addAttribute("dictionaryReply", dictionaryReply);
        model.addAttribute("currentPage", page);
        return "dictionaries/multishow";
    }



    @GetMapping ("/import")
    public String importFile(@ModelAttribute ("emptyFile") String emptyFile) {
        return "dictionaries/import";
    }

    @PostMapping("/importing")
    public String loadExcelFileToDb(@RequestParam("file") MultipartFile multipartFile,
                                    RedirectAttributes redirectAttributes) {

        try (XSSFWorkbook workbook = new XSSFWorkbook(multipartFile.getInputStream())) {

            dictionaryExcelImporter.importExcelToDB(workbook);
            return "redirect:/dictionary";
        } catch (IOException exception) {
            throw new FileIOException("Ошибка при импорте словаря");
        } catch (EmptyFileException exception) {
            redirectAttributes.addFlashAttribute("emptyFile", "Выберите файл для загрузки");
            LOGGER.info("Файл для загрузки не выбран");
            return "redirect:/dictionary/import";
        }
    }



}
