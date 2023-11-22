package com.phantom.japanese_dictionary_mvc.controllers;


import com.phantom.japanese_dictionary_mvc.exceptions.FileIOException;
import com.phantom.japanese_dictionary_mvc.models.Note;
import com.phantom.japanese_dictionary_mvc.requests.Request;
import com.phantom.japanese_dictionary_mvc.requests.RequestType;
import com.phantom.japanese_dictionary_mvc.services.NoteService;
import com.phantom.japanese_dictionary_mvc.replies.DictionaryReply;
import com.phantom.japanese_dictionary_mvc.util.DictionaryReplyConverter;
import org.apache.poi.EmptyFileException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Locale;

@Controller
@RequestMapping("/dictionary")
@SessionAttributes("request") //needed for view in different pages
public class DictionaryController {

    private final DictionaryReplyConverter dictionaryReplyConverter;
    private final NoteService noteService;
    private final static Logger LOGGER = LoggerFactory.getLogger(DictionaryController.class);

    public DictionaryController(DictionaryReplyConverter dictionaryReplyConverter, NoteService noteService) {
        this.dictionaryReplyConverter = dictionaryReplyConverter;
        this.noteService = noteService;
    }

    @GetMapping
    public String index (Model model) {
        LocaleContextHolder.getLocale();
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

            XSSFSheet worksheet = workbook.getSheetAt(0);

            for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
                Note note = new Note();
                XSSFRow row = worksheet.getRow(i);
                if (row != null) {
                    XSSFCell currentCell = row.getCell(0);
                    if (currentCell != null && currentCell.getCellType() == CellType.STRING) {
                        note.setRomadji(currentCell.getStringCellValue());
                    } else if (currentCell != null && currentCell.getCellType() == CellType.NUMERIC) {
                        note.setRomadji(String.valueOf(currentCell.getNumericCellValue()));
                    }
                    currentCell = row.getCell(1);
                    if (currentCell != null) note.setKanji(currentCell.getStringCellValue());
                    currentCell = row.getCell(2);
                    if (currentCell != null) note.setHiragana(currentCell.getStringCellValue());
                    currentCell = row.getCell(3);
                    if (currentCell != null && currentCell.getCellType() == CellType.STRING) {
                        note.setTranslation(currentCell.getStringCellValue());
                    } else if (currentCell != null && currentCell.getCellType() == CellType.NUMERIC) {
                        note.setTranslation(String.valueOf(currentCell.getNumericCellValue()));
                    }
                    System.out.println(note);
                    noteService.saveNote(note);
                }
            }
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
