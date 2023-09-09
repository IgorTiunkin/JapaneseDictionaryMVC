package com.phantom.japanese_dictionary_mvc.controllers;

import com.phantom.japanese_dictionary_mvc.exceptions.FileIOException;
import com.phantom.japanese_dictionary_mvc.models.GrammarNote;
import com.phantom.japanese_dictionary_mvc.requests.Request;
import com.phantom.japanese_dictionary_mvc.services.GrammarNoteService;
import com.phantom.japanese_dictionary_mvc.replies.GrammarDictionaryReply;
import com.phantom.japanese_dictionary_mvc.util.GrammarDictionaryReplyConverter;
import org.apache.poi.EmptyFileException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
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
@SessionAttributes({"request"})
public class GrammarDictionaryController {

    private final GrammarNoteService grammarNoteService;
    private final ModelMapper modelMapper;
    private final GrammarDictionaryReplyConverter grammarDictionaryReplyConverter;
    private final static Logger LOGGER = LoggerFactory.getLogger(GrammarDictionaryController.class);

    @Autowired
    public GrammarDictionaryController(GrammarNoteService grammarNoteService, ModelMapper modelMapper, GrammarDictionaryReplyConverter grammarDictionaryReplyConverter) {
        this.grammarNoteService = grammarNoteService;
        this.modelMapper = modelMapper;
        this.grammarDictionaryReplyConverter = grammarDictionaryReplyConverter;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("request", new Request());
        return "grammar/index";
    }

    @GetMapping("/show")
    public String show (@ModelAttribute("request") @Valid Request request, BindingResult bindingResult
            , Model model, @RequestParam (required = false, value = "page", defaultValue = "0") Integer page) {
        LOGGER.trace("Accepted grammar request: requesttype = {}; word to find = {}",
                request.getRequestType(), request.getWord());
        if (bindingResult.hasErrors()) {
            return "grammar/index";
        }
        GrammarDictionaryReply grammarDictionaryReply = grammarDictionaryReplyConverter.getGrammarDictionaryReplyForCurrentPage(request, page);
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
            XSSFSheet worksheet = workbook.getSheetAt(0);

            for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
                GrammarNote grammarNote = new GrammarNote();

                XSSFRow row = worksheet.getRow(i);
                if (row != null) {
                    XSSFCell currentCell = row.getCell(0);
                    if (currentCell != null) grammarNote.setSource(currentCell.getStringCellValue());
                    currentCell = row.getCell(1);
                    if (currentCell != null) grammarNote.setRule(currentCell.getStringCellValue());
                    currentCell = row.getCell(2);
                    if (currentCell != null) grammarNote.setExplanation(currentCell.getStringCellValue());
                    currentCell = row.getCell(3);
                    if (currentCell != null) grammarNote.setExample(currentCell.getStringCellValue());
                    System.out.println(grammarNote);
                    grammarNoteService.saveGrammarNote(grammarNote);
                }
            }
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
