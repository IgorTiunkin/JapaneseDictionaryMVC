package com.phantom.japanese_dictionary_mvc.controllers;


import com.phantom.japanese_dictionary_mvc.dto.NoteDTO;
import com.phantom.japanese_dictionary_mvc.models.Note;
import com.phantom.japanese_dictionary_mvc.requests.Request;
import com.phantom.japanese_dictionary_mvc.requests.RequestType;
import com.phantom.japanese_dictionary_mvc.services.NoteService;
import com.phantom.japanese_dictionary_mvc.util.DictionaryReply;
import com.phantom.japanese_dictionary_mvc.util.ReplyConverter;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/dictionary")
public class DictionaryController {

    private final ReplyConverter replyConverter;
    private final NoteService noteService;
    private final static Logger LOGGER = LoggerFactory.getLogger(DictionaryController.class);

    public DictionaryController(ReplyConverter replyConverter, NoteService noteService) {
        this.replyConverter = replyConverter;
        this.noteService = noteService;
    }


    @GetMapping
    public String index (Model model) {
        model.addAttribute("request", new Request());
        model.addAttribute("types", RequestType.values());
        return "dictionaries/index";
    }

    @GetMapping("/show")
    public String show (@ModelAttribute ("request") @Valid Request request, BindingResult bindingResult
            , Model model) {
        LOGGER.trace("Accepted dictionary request: request type = {}; word to find = {}; match = {}",
                request.getRequestType(), request.getWord(), request.isOnlyFullMatch());
        if (bindingResult.hasErrors()) {
            model.addAttribute("types", RequestType.values());
            return "dictionaries/index";
        }

        DictionaryReply dictionaryReply = replyConverter.getDictionaryReply(request);
        model.addAttribute("dictionaryReply", dictionaryReply);
        return "dictionaries/multishow";
    }



    @GetMapping ("/import")
    public String excelFile () {
        return "dictionaries/import";
    }

    @PostMapping("/import")
    public String loadExcelFileToDb(@RequestParam("file") MultipartFile multipartFile) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        for(int i=1; i<worksheet.getPhysicalNumberOfRows(); i++) {
            Note note = new Note();
            XSSFRow row = worksheet.getRow(i);
            if (row!=null) {
                XSSFCell currentCell = row.getCell(0);
                if (currentCell!=null && currentCell.getCellType()== CellType.STRING) {
                    note.setRomadji(currentCell.getStringCellValue());
                } else if (currentCell!=null && currentCell.getCellType()== CellType.NUMERIC){
                    note.setRomadji(String.valueOf(currentCell.getNumericCellValue()));
                }
                currentCell = row.getCell(1);
                if (currentCell!=null) note.setKanji(currentCell.getStringCellValue());
                currentCell = row.getCell(2);
                if (currentCell!=null) note.setHiragana(currentCell.getStringCellValue());
                currentCell = row.getCell(3);
                if (currentCell!=null && currentCell.getCellType()== CellType.STRING) {
                    note.setTranslation(currentCell.getStringCellValue());
                } else if (currentCell!=null && currentCell.getCellType()== CellType.NUMERIC){
                    note.setTranslation(String.valueOf(currentCell.getNumericCellValue()));
                }
                System.out.println(note);
                noteService.saveNote(note);
            }
        }
        return "redirect:/dictionary";
    }

}
