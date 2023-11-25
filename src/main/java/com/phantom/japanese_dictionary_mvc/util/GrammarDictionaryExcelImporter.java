package com.phantom.japanese_dictionary_mvc.util;

import com.phantom.japanese_dictionary_mvc.models.GrammarNote;
import com.phantom.japanese_dictionary_mvc.services.GrammarNoteService;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
public class GrammarDictionaryExcelImporter {

    private final GrammarNoteService grammarNoteService;

    public GrammarDictionaryExcelImporter(GrammarNoteService grammarNoteService) {
        this.grammarNoteService = grammarNoteService;
    }

    public void importExcelToDB(XSSFWorkbook workbook) {
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
                grammarNoteService.saveGrammarNote(grammarNote);
            }
        }
    }
}
