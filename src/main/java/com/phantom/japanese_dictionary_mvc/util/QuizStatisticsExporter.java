package com.phantom.japanese_dictionary_mvc.util;

import com.phantom.japanese_dictionary_mvc.dto.FailedQuizTaskDTO;
import com.phantom.japanese_dictionary_mvc.dto.QuizResultDTO;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuizStatisticsExporter {

    private final MessageSource messageSource;

    public QuizStatisticsExporter(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public Workbook createXlsFile(List<QuizResultDTO> quizResultDTOS) {
        Workbook workbook = new XSSFWorkbook();
        String title = messageSource.getMessage("quizstatisticsexporter.export.title", null,
                LocaleContextHolder.getLocale());
        Sheet sheet = workbook.createSheet(title);
        Row headerRow = sheet.createRow(0);

        String date = messageSource.getMessage("quizstatisticsexporter.export.date", null,
                LocaleContextHolder.getLocale());
        headerRow.createCell(0).setCellValue(date);
        String right = messageSource.getMessage("quizstatisticsexporter.export.right", null,
                LocaleContextHolder.getLocale());
        headerRow.createCell(1).setCellValue(right);
        String tasks = messageSource.getMessage("quizstatisticsexporter.export.tasks", null,
                LocaleContextHolder.getLocale());
        headerRow.createCell(2).setCellValue(tasks);
        String wrong = messageSource.getMessage("quizstatisticsexporter.export.wrong", null,
                LocaleContextHolder.getLocale());
        headerRow.createCell(3).setCellValue(wrong);

        int rowNum = 1;
        if (quizResultDTOS !=null) {
            for (QuizResultDTO quizResultDTO : quizResultDTOS) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(quizResultDTO.getDateOfQuiz());
                row.createCell(1).setCellValue(quizResultDTO.getNumberOfRightAnswers());
                row.createCell(2).setCellValue(quizResultDTO.getNumberOfTasks());

                StringBuilder stringBuilder = new StringBuilder();
                for (FailedQuizTaskDTO failedQuizTask : quizResultDTO.getFailedQuizTasks()) {
                    stringBuilder.append(failedQuizTask.getFailedQuestion()).append("; ");
                }
                row.createCell(3).setCellValue(stringBuilder.toString());
            }
        }
        return workbook;
    }

}
