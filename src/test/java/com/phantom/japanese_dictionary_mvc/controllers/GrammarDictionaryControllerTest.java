package com.phantom.japanese_dictionary_mvc.controllers;

import com.phantom.japanese_dictionary_mvc.dto.GrammarNoteDTO;
import com.phantom.japanese_dictionary_mvc.models.GrammarNote;
import com.phantom.japanese_dictionary_mvc.replies.DictionaryReply;
import com.phantom.japanese_dictionary_mvc.replies.GrammarDictionaryReply;
import com.phantom.japanese_dictionary_mvc.requests.GrammarRequest;
import com.phantom.japanese_dictionary_mvc.requests.Request;
import com.phantom.japanese_dictionary_mvc.requests.RequestType;
import com.phantom.japanese_dictionary_mvc.services.GrammarNoteService;
import com.phantom.japanese_dictionary_mvc.util.GrammarDictionaryExcelImporter;
import com.phantom.japanese_dictionary_mvc.util.GrammarDictionaryReplyConverter;
import org.apache.poi.EmptyFileException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GrammarDictionaryControllerTest extends BaseControllerTest{

    @Mock
    private GrammarDictionaryExcelImporter grammarDictionaryExcelImporter;

    @Mock
    private GrammarDictionaryReplyConverter grammarDictionaryReplyConverter;

    @InjectMocks
    private GrammarDictionaryController grammarDictionaryController;

    private final String BASE_PATH = "/grammar";
    private final String PATH_TO_SHOW = BASE_PATH + "/show";
    private final String PATH_TO_IMPORT = BASE_PATH + "/import";
    private final String PATH_TO_IMPORTING = BASE_PATH + "/importing";

    private final String INDEX_VIEW_NAME = "grammar/index";
    private final String SHOW_VIEW_NAME = "grammar/multishow";
    private final String IMPORT_VIEW_NAME = "grammar/import";

    private final GrammarNoteDTO TEST_GRAMMAR_NOTE_DTO = GrammarNoteDTO.builder()
            .source("test source").rule("test rule").explanation("test explanation").example("test example")
            .build();

    @BeforeEach
    public void init() {
        mvc = MockMvcBuilders.standaloneSetup(grammarDictionaryController)
                .build();
    }


    @Test
    public void whenIndex_thenIndex() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(BASE_PATH).accept(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(INDEX_VIEW_NAME))
                .andReturn();
        Map<String, Object> model = mvcResult.getModelAndView().getModel();
        assertEquals(model.get("request").getClass(), GrammarRequest.class);
    }

    @Test
    public void whenCorrectRequest_thenCorrectShow() throws Exception {
        GrammarRequest grammarRequest = new GrammarRequest("test");
        GrammarDictionaryReply dictionaryReply = new GrammarDictionaryReply();
        dictionaryReply.setMatchCount(2);
        dictionaryReply.setIndexOfLastPage(0);
        dictionaryReply.setGrammarNoteDTOS(List.of(TEST_GRAMMAR_NOTE_DTO));
        doReturn(dictionaryReply).when(grammarDictionaryReplyConverter).getGrammarDictionaryReplyForCurrentPage(grammarRequest, 0);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(PATH_TO_SHOW).accept(MediaType.TEXT_HTML)
                .flashAttr("request", grammarRequest))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(SHOW_VIEW_NAME))
                .andReturn();

        Map<String, Object> model = mvcResult.getModelAndView().getModel();
        assertEquals(model.get("grammarDictionaryReply").getClass(), GrammarDictionaryReply.class);
        assertEquals(model.get("currentPage"), 0);

        GrammarDictionaryReply dictionaryReplyResult = (GrammarDictionaryReply) model.get("grammarDictionaryReply");
        assertEquals(dictionaryReplyResult, dictionaryReply);
    }

    @Test
    public void whenIncorrectRequest_thenIndex() throws Exception {
        GrammarRequest grammarRequest = new GrammarRequest("");

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(PATH_TO_SHOW).accept(MediaType.TEXT_HTML)
                .flashAttr("request", grammarRequest))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(INDEX_VIEW_NAME))
                .andReturn();
    }

    @Test
    public void whenImportFile_thenImportFile() throws Exception {
        doNothing().when(grammarDictionaryExcelImporter).importExcelToDB(any());
        mvc.perform(MockMvcRequestBuilders.get(PATH_TO_IMPORT).accept(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(IMPORT_VIEW_NAME));
    }

    @Test
    public void whenFileEmpty_thenDictionaryImport() throws Exception {

        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "".getBytes()
        );

        doThrow(EmptyFileException.class).when(grammarDictionaryExcelImporter).importExcelToDB(any());
        mvc.perform(MockMvcRequestBuilders.multipart(PATH_TO_IMPORTING).file(file).accept(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:" + PATH_TO_IMPORT));
    }

    @Test
    public void whenFileIsNotEmpty_thenDictionary() throws Exception {

        Workbook workBook = new XSSFWorkbook();
        Sheet sheet = workBook.createSheet("Test");
        Row headerRow = sheet.createRow(0);
        Row row = sheet.createRow(1);
        row.createCell(0).setCellValue("test source");
        row.createCell(1).setCellValue("test rule");
        row.createCell(2).setCellValue("test explanation");
        row.createCell(3).setCellValue("test wxample");

        byte[] bytes = null;

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            workBook.write(bos);
            bytes = bos.toByteArray();
        }

        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                "",
                bytes
        );

        doNothing().when(grammarDictionaryExcelImporter).importExcelToDB(any());
        mvc.perform(MockMvcRequestBuilders.multipart(PATH_TO_IMPORTING).file(file).accept(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:" + BASE_PATH));
    }

}