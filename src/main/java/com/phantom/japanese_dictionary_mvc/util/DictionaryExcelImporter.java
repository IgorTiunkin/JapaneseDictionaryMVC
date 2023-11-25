package com.phantom.japanese_dictionary_mvc.util;

import com.phantom.japanese_dictionary_mvc.models.Note;
import com.phantom.japanese_dictionary_mvc.services.NoteService;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
public class DictionaryExcelImporter {

    private final NoteService noteService;

    public DictionaryExcelImporter(NoteService noteService) {
        this.noteService = noteService;
    }

    public void importExcelToDB(XSSFWorkbook workbook) {
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
                noteService.saveNote(note);
            }
        }
    }
}
